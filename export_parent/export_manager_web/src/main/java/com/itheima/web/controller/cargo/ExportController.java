package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.commons.utils.UtilFuns;
import com.itheima.domain.cargo.*;
import com.itheima.service.cargo.ContractService;
import com.itheima.service.cargo.ExportProductService;
import com.itheima.service.cargo.ExportService;
import com.itheima.vo.ExportProductVo;
import com.itheima.vo.ExportResult;
import com.itheima.vo.ExportVo;
import com.itheima.web.controller.BaseController;
import com.itheima.web.exceptions.CustomeException;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 报运单的控制器
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Controller
@RequestMapping("/cargo/export")
public class ExportController extends BaseController {

    @Reference
    private ContractService contractService;

    @Reference
    private ExportService exportService;

    @Reference
    private ExportProductService exportProductService;

    /**
     * 前往合同管理页面
     *   此时的合同应该是状态为1的
     * @return
     */
    @RequestMapping("/contractList")
    public String contractList(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int size){
        //1.创建查询条件对象
        ContractExample contractExample = new ContractExample();
        //2.设置查询条件
        ContractExample.Criteria criteria = contractExample.createCriteria();
        criteria.andStateEqualTo(1);
        criteria.andCompanyIdEqualTo(super.companyId);
        //3.执行查询
        PageInfo pageInfo = contractService.findAll(contractExample,page,size);
        //4.存入请求域中
        request.setAttribute("page",pageInfo);
        //5.前往合同管理的列表页面
        return "cargo/export/export-contractList";
    }

    /**
     * 前往新增报运单页面
     * @param id
     * @return
     */
    @RequestMapping("/toExport")
    public String toExport(String id){
        request.setAttribute("id",id);
        return "cargo/export/export-toExport";
    }

    /**
     * 保存或者更新报运单
     * @param export
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Export export){
        //1.设置企业id和名称
        export.setCompanyName(super.companyName);
        export.setCompanyId(super.companyId);
        //2.判断是保存还是更新
        if(UtilFuns.isEmpty(export.getId())){
            //保存
            System.out.println(export);
            exportService.save(export);
        }else{
            //更新
            exportService.update(export);
        }
        //3.前往报运单列表页面
        return "redirect:/cargo/export/list.do";
    }

    /**
     * 前往列表页面
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page ,@RequestParam(defaultValue = "5") int size){
        //1.创建查询条件
        ExportExample exportExample = new ExportExample();
        //2.设置条件
        exportExample.createCriteria().andCompanyIdEqualTo(super.companyId);
        //3.执行查询
        PageInfo pageInfo = exportService.findAll(exportExample,page,size);
        //4.存入请求域中
        request.setAttribute("page",pageInfo);
        return "cargo/export/export-list";
    }

    /**
     * 前往查看详情页面
     * @param id
     * @return
     */
    @RequestMapping("/toView")
    public String toView(String id){
        //1.调用service查询
        Export export = exportService.findById(id);
        //2.存入请求域中
        request.setAttribute("export",export);
        return "cargo/export/export-view";
    }

    /**
     * 提交：就是把状态改为1
     * @param id
     * @return
     */
    @RequestMapping("/submit")
    public String submit(String id){
        //1.创建报运单对象
        Export export = new Export();
        //2.设置报运单的状态和id
        export.setId(id);
        export.setState(1);//update co_export set state = 1 where id = #{id}
        //3.更新报运单
        exportService.update(export);
        return "redirect:/cargo/export/list.do";
    }


    /**
     * 提交：就是把状态改为1
     * @param id
     * @return
     */
    @RequestMapping("/cancel")
    public String cancel(String id){
        //1.创建报运单对象
        Export export = new Export();
        //2.设置报运单的状态和id
        export.setId(id);
        export.setState(0);
        //3.更新报运单
        exportService.update(export);
        return "redirect:/cargo/export/list.do";
    }

    /**
     * 前往更新页面
     * @param id
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        //1.调用service查询
        Export export = exportService.findById(id);
        //2.存入请求域中
        request.setAttribute("export",export);
        //3.查询当前报运单的所有货物
        ExportProductExample example = new ExportProductExample();
        example.createCriteria().andExportIdEqualTo(id);
        List<ExportProduct> exportProductList = exportProductService.findAll(example);
        //4.把货物信息也存入到请求域中
        request.setAttribute("eps",exportProductList);
        return "cargo/export/export-update";
    }



    @RequestMapping("/delete")
    public String delete(String id){
        //1.使用service删除报运单
        exportService.delete(id);
        return "redirect:/cargo/export/list.do";
    }

    /**
     * 电子报运
     * @param id
     * @return
     */
    @RequestMapping("/exportE")
    public String exportE(String id)throws Exception{
        //1.根据id查询报运单
        Export export = exportService.findById(id);
        //2.判断报运单的状态是否为1
        if(export.getState() != 1){
            //不能报运
            throw new CustomeException("报运单不支持报运，请查看状态是否为已上报");
        }
        //3.创建海关平台的报运单对象
        ExportVo evo = new ExportVo();
        //4.把saas平台的报运单数据拷贝到海关平台的报运单对象中
        BeanUtils.copyProperties(export,evo,new String[]{"id"});
        //5.设置海关平台报运单的id和exportId
        evo.setExportId(export.getId());
        String eid = UtilFuns.generateId();
        evo.setId(eid);
        //6.使用saas平台的报运单id，查询报运单下的商品
        ExportProductExample example = new ExportProductExample();
        example.createCriteria().andExportIdEqualTo(export.getId());
        List<ExportProduct> eps = exportProductService.findAll(example);
        //7.遍历eps，取出每个商品，用于构建海关报运单的商品对象
        for(ExportProduct ep : eps){
            //8.创建海关平台的商品对象
            ExportProductVo epvo = new ExportProductVo();
            //9.把saas平台的商品数据拷贝到海关平台的商品对象中
            BeanUtils.copyProperties(ep,epvo,new String[]{"id"});
            //10.设置海关平台商品对象的id
            epvo.setId(UtilFuns.generateId());//海关平台的商品表id
            epvo.setEid(eid);//海关平台的报运单id
            epvo.setExportProductId(ep.getId());//sass平台的商品id
            epvo.setExportId(export.getId());//能拷贝过来，saas平台的报运单id
            //11.把epvo加入到evo里面
            evo.getProducts().add(epvo);
        }
        //12.发送请求，实现报运
        WebClient.create("http://localhost:8558/jk_export/ws/export/user").type(MediaType.APPLICATION_JSON).post(evo);
        //13.再次发送请求，查询报运结果
        ExportResult er = WebClient.create("http://localhost:8558/jk_export/ws/export/user/"+eid).type(MediaType.APPLICATION_JSON).get(ExportResult.class);
        //14.调用service更新报运单的状态
        exportService.updateER(er);
        //15.报运成功，显示报运单列表页面
        return "redirect:/cargo/export/list.do";
    }
}

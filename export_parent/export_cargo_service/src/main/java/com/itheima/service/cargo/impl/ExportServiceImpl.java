package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.commons.utils.UtilFuns;
import com.itheima.dao.cargo.*;
import com.itheima.domain.cargo.*;
import com.itheima.service.cargo.ExportService;
import com.itheima.vo.ExportProductResult;
import com.itheima.vo.ExportResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private ExportDao exportDao;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ContractProductDao contractProductDao;

    @Autowired
    private ExportProductDao exportProductDao;

    @Autowired
    private ExtEproductDao extEproductDao;

    @Override
    public void save(Export export) {
        //1.补全报运单中的必要信息
        String exportId = UtilFuns.generateId();
        export.setId(exportId);//主键信息
        export.setInputDate(new Date());//报运单创建时间
        export.setState(0);//报运单状态为草稿
        //2.取出报运单下的购销合同id集合
        String[] contractIds = export.getContractIds().split(",");//每个元素就是合同的id
        //3.遍历购销合同id数组
        StringBuilder ss = new StringBuilder();
        for(String contractId : contractIds){
            //4.根据购销合同id查询购销合同
            Contract contract = contractDao.selectByPrimaryKey(contractId);
            //5.拼接合同号
            ss.append(contract.getContractNo()).append(" ");
            //6.购销合同当已经生成报运单之后，状态要改成2
            contract.setState(2);
            //7.更新购销合同
            contractDao.updateByPrimaryKeySelective(contract);
        }
        //8.设置报运单中的合同号
        ss.delete(ss.toString().length()-1,ss.toString().length());//删除最后一个空格
        export.setCustomerContract(ss.toString());
        //9.定义查询货物的条件对象
        ContractProductExample contractProductExample = new ContractProductExample();
        //10.把购销合同id数组转成集合
        List<String> contractIdList = Arrays.asList(contractIds);
        //11.拼装查询条件
        contractProductExample.createCriteria().andContractIdIn(contractIdList);
        //12.取出购销合同中所有的货物
        List<ContractProduct> cps = contractProductDao.selectByExample(contractProductExample);
        //13.遍历合同下货物的集合
        int extNum = 0;
        for(ContractProduct cp : cps){
            //14.创建报运单商品对象
            ExportProduct ep = new ExportProduct();
            //15.把合同下货物的数据填充到商品里面去
            BeanUtils.copyProperties(cp,ep,new String[]{"id"});
            //16.设置商品的id
            String exportProductId = UtilFuns.generateId();
            ep.setId(exportProductId);
            //17.给商品设置报运单的id
            ep.setExportId(exportId);
            //18.保存商品信息
            exportProductDao.insertSelective(ep);
            //19.取出每个货物的附件
            List<ExtCproduct> extcs = cp.getExtCproducts();
            extNum+=extcs.size();
            //20.遍历货物的附件
            for(ExtCproduct extc : extcs){
                //21.创建报运单附件对象
                ExtEproduct exte = new ExtEproduct();
                //22.数据拷贝
                BeanUtils.copyProperties(extc,exte,new String[]{"id"});
                //23.设置附件的id
                exte.setId(UtilFuns.generateId());
                //24.设置商品id
                exte.setExportProductId(exportProductId);
                //25.设置报运单id
                exte.setExportId(exportId);
                //26.保存商品的附件
                extEproductDao.insertSelective(exte);
            }
        }
        //27.设置商品数和附件数
        export.setProNum(cps.size());
        export.setExtNum(extNum);
        //28.保存报运单
        exportDao.insertSelective(export);
    }

    /**
     * 删除报运单
     * 删除报运单的商品
     * 删除报运单的附件
     * 更新购销合同的状态
     * @param id
     */
    @Override
    public void delete(String id) {
        //根据id查询出来报运单对象
        Export export = exportDao.selectByPrimaryKey(id);
        //取出报运单下所有的合同id
        String[] contractIds = export.getContractIds().split(",");//它里面是用逗号分隔的合同id。
        //遍历合同id
        for(String contractId : contractIds){
            //创建合同对象
            Contract contract = new Contract();
            //设置合同id和状态
            contract.setId(contractId);
            contract.setState(1);//把已报运的状态改为已上报，也就是把状态由2改成1
            //更新购销合同
            contractDao.updateByPrimaryKeySelective(contract);
        }

        //1.删除报运单
        exportDao.deleteByPrimaryKey(id);
        //2.创建报运单商品的条件查询对象
        ExportProductExample exportProductExample = new ExportProductExample();
        //3.设置商品的查询条件
        exportProductExample.createCriteria().andExportIdEqualTo(id);
        //4.获取报运单下的商品集合
        List<ExportProduct> eps = exportProductDao.selectByExample(exportProductExample);
        //5.遍历集合，删除商品
        for(ExportProduct ep : eps){
            exportProductDao.deleteByPrimaryKey(ep.getId());
        }
        //6.创建报运单附件的条件查询对象
        ExtEproductExample extEproductExample = new ExtEproductExample();
        //7.设置附件的查询条件
        extEproductExample.createCriteria().andExportIdEqualTo(id);
        //8.查询报运单的附件
        List<ExtEproduct> extes = extEproductDao.selectByExample(extEproductExample);
        //9.遍历附件的集合，删除每个附件
        for(ExtEproduct exte : extes){
            extEproductDao.deleteByPrimaryKey(exte.getId());
        }
    }






















    @Override
    public void update(Export export) {
        //1.更新报运单
        exportDao.updateByPrimaryKeySelective(export);
        //判断是否有商品
        if(export.getExportProducts() != null) {
            //2.更新报运单中的商品
            List<ExportProduct> exportProducts = export.getExportProducts();
            //3.遍历集合
            for (ExportProduct ep : exportProducts) {
                //4.更新报运单下的商品
                exportProductDao.updateByPrimaryKeySelective(ep);
            }
        }
    }


    @Override
    public PageInfo findAll(ExportExample example, int page, int size) {
        PageHelper.startPage(page,size);
        List<Export> exportList = exportDao.selectByExample(example);
        return new PageInfo(exportList);
    }

    @Override
    public Export findById(String id) {
        return exportDao.selectByPrimaryKey(id);
    }

    /**
     * 更新报运单状态
     * @param exportResult
     */
    @Override
    public void updateER(ExportResult exportResult) {
        //1.使用exportResult中exportId查询报运单
        Export export = exportDao.selectByPrimaryKey(exportResult.getExportId());
        //2.设置报运单的状态和remark
        export.setState(exportResult.getState());
        export.setRemark(exportResult.getRemark());
        //3.取出exportResult中的商品集合
        Set<ExportProductResult> eprs = exportResult.getProducts();
        //4.遍历集合，取出每个商品的id
        for(ExportProductResult epr : eprs ){
            //5.根据Id查询商品信息
            ExportProduct ep = exportProductDao.selectByPrimaryKey(epr.getExportProductId());
            //6.设置商品的税率
            ep.setTax(epr.getTax());
            //7.更新商品
            exportProductDao.updateByPrimaryKeySelective(ep);
        }
        //8.更新报运单
        exportDao.updateByPrimaryKeySelective(export);
    }
}

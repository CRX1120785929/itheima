package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.commons.utils.BeanMapUtils;
import com.itheima.domain.cargo.Export;
import com.itheima.domain.cargo.ExportProduct;
import com.itheima.domain.cargo.ExportProductExample;
import com.itheima.service.cargo.ExportProductService;
import com.itheima.service.cargo.ExportService;
import com.itheima.web.controller.BaseController;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 生成报运单的pdf
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Controller
@RequestMapping("/cargo/export")
public class PdfController extends BaseController {

    @Reference
    private ExportService exportService;

    @Reference
    private ExportProductService exportProductService;


    /**
     * Jasper Report生成统计图表
     * @param id
     * @throws Exception

    @RequestMapping("/exportPdf")
    public void exportPdf(String id)throws Exception{
        //准备集合数据
        List<Map> list = new ArrayList();
        for(int i=0;i<6;i++){
            Map map = new HashMap();
            map.put("title","厂家"+i);
            map.put("value",new Random().nextInt(100));
            list.add(map);
        }


        //创建JRdataSource
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
        //1.读取jasper文件
        String path = session.getServletContext().getRealPath("/jasper/test06.jasper");
        InputStream in = new FileInputStream(path);
        //2.创建pdf的输出对象
        JasperPrint jasperPrint = JasperFillManager.fillReport(in,new HashMap<>(), jrDataSource);
        //3.输出pdf
        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
        //4.关流
        in.close();
    } */


    /**
     * Jasper Report填充分组数据
     * @param id
     * @throws Exception

    @RequestMapping("/exportPdf")
    public void exportPdf(String id)throws Exception{
        //准备集合数据
        List<User> list = new ArrayList();
        for(int i = 1;i<50;i++){
            User user = new User();
            user.setUserName("用户"+i);
            user.setDeptName("部门"+i);
            //此种方式不行，它的分组不具备计算，只能是把相同数据放到一起
//            if(i%2==0){
//                user.setCompanyName("传智播客");
//            }else{
//                user.setCompanyName("黑马程序员");
//            }

            if(i<30){
                user.setCompanyName("传智播客");
            }else{
                user.setCompanyName("黑马程序员");
            }

            user.setEmail("test"+i+"@export.com");
            list.add(user);
        }


        //创建JRdataSource
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);

        //1.读取jasper文件
        String path = session.getServletContext().getRealPath("/jasper/test05.jasper");
        InputStream in = new FileInputStream(path);
        //2.创建pdf的输出对象
        JasperPrint jasperPrint = JasperFillManager.fillReport(in,new HashMap<>(), jrDataSource);
        //3.输出pdf
        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
        //4.关流
        in.close();
    }*/



    /**
     * Jasper Report填充List集合数据
     * @param id
     * @throws Exception

    @RequestMapping("/exportPdf")
    public void exportPdf(String id)throws Exception{
        //准备集合数据
        List<User> list = new ArrayList();
        for(int i = 1;i<11;i++){
            User user = new User();
            user.setUserName("用户"+i);
            user.setDeptName("部门"+i);
            user.setCompanyName("企业"+i);
            user.setEmail("test"+i+"@export.com");
            list.add(user);
        }


        //创建JRdataSource
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);

        //1.读取jasper文件
        String path = session.getServletContext().getRealPath("/jasper/test04.jasper");
        InputStream in = new FileInputStream(path);
        //2.创建pdf的输出对象
        JasperPrint jasperPrint = JasperFillManager.fillReport(in,new HashMap<>(), jrDataSource);
        //3.输出pdf
        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
        //4.关流
        in.close();
    }*/


    /**
     * Jasper Report传递Connection填充数据
     * @param id
     * @throws Exception

    @RequestMapping("/exportPdf")
    public void exportPdf(String id)throws Exception{
        //1.注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        //2.获取连接
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/saas-export-ee88","root","1234");

        //1.读取jasper文件
        String path = session.getServletContext().getRealPath("/jasper/test03.jasper");
        InputStream in = new FileInputStream(path);
        //2.创建pdf的输出对象
        JasperPrint jasperPrint = JasperFillManager.fillReport(in,new HashMap<>(),conn);
        //3.输出pdf
        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
        //4.关流
        in.close();
        conn.close();
    }*/

    /**
     * Jasper Report填充map格式的数据
     * @param id
     * @throws Exception

    @RequestMapping("/exportPdf")
    public void exportPdf(String id)throws Exception{
        //创建填充的数据对象Map
        Map map = new HashMap<>();
        map.put("username","泰斯特");
        map.put("companyName","传智播客");
        map.put("deptName","教研部");
        map.put("email","test@export.com");

        //1.读取jasper文件
        String path = session.getServletContext().getRealPath("/jasper/test02.jasper");
        InputStream in = new FileInputStream(path);
        //2.创建pdf的输出对象
        JasperPrint jasperPrint = JasperFillManager.fillReport(in,map,new JREmptyDataSource());
        //3.输出pdf
        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
        //4.关流
        in.close();
    }*/











    /**
     * Jasper Report的入门案例
     * @param id
     * @throws Exception

    @RequestMapping("/exportPdf")
    public void exportPdf(String id)throws Exception{
        //1.读取jasper文件
        String path = session.getServletContext().getRealPath("/jasper/test01.jasper");
        InputStream in = new FileInputStream(path);
        //2.创建pdf的输出对象
        JasperPrint jasperPrint = JasperFillManager.fillReport(in,new HashMap<>(),new JREmptyDataSource());
        //3.输出pdf
        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
        //4.关流
        in.close();
    }*/




    /**
     * 生成报运单的pdf
     * @param id
     */
    @RequestMapping("/exportPdf")
    public void exportPdf(String id)throws Exception{
        //读取jasper文件
        String path = session.getServletContext().getRealPath("/jasper/export.jasper");
        InputStream in = new FileInputStream(path);

        //1.根据id查询报运单
        Export export = exportService.findById(id);
        //2.把报运单转成一个Map对象
        Map map = BeanMapUtils.beanToMap(export);
        //3.使用报运单的id，查询商品
        ExportProductExample example = new ExportProductExample();
        example.createCriteria().andExportIdEqualTo(id);
        List<ExportProduct> eps = exportProductService.findAll(example);
        //4.创建JRDataSource
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(eps);
        //5.创建pdf的输出对象
        //第一个参数：字节输入流。用于读取jasper文件的
        //第二个参数：一个map。用于传递map类型的数据的（键值对数据的）
        //第三个参数：一个pdf的datasource。用于传入list集合数据的（除了list集合可以之外，connection也行）
        JasperPrint jasperPrint = JasperFillManager.fillReport(in,map,jrDataSource);
        //6.输出pdf
        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
        //7.关流
        in.close();

    }
}

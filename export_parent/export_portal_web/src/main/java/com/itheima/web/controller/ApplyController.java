package com.itheima.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.domain.company.Company;
import com.itheima.service.company.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Controller
public class ApplyController {

    @Reference
    private CompanyService companyService;

    /**
     * 企业申请
     * @return
     */
    @RequestMapping("/apply")
    public @ResponseBody String applyCompany(Company company){
        //1.调用service实现保存
        companyService.save(company);
        return "1";
    }
}

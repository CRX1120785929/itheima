package com.itheima.web.controller.stat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.stat.StatService;
import com.itheima.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Controller
@RequestMapping("/stat")
public class StatController extends BaseController {

    @Reference
    private StatService statService;

    /**
     * 前往统计图表页面
     * @param chartsType
     * @return
     */
    @RequestMapping("/toCharts")
    public String toCharts(String chartsType){
        return "stat/stat-"+chartsType;
    }

    /**
     * 生产厂家销售情况的饼状图数据
     * @return
     */
    @RequestMapping("/getFactoryData")
    public @ResponseBody List getFactoryData(){
        return statService.findFactorySellData(super.companyId);
    }

    /**
     * 产品销售情况的柱状图
     * @return
     */
    @RequestMapping("/getSellData")
    public @ResponseBody List getSellData(){
        return statService.findProductSellData(super.companyId);
    }

    /**
     * 在线人数统计的折线图
     * @return
     */
    @RequestMapping("/getOnlineData")
    public @ResponseBody List getOnlineData(){
        return statService.findOnlineData(super.companyId);
    }
}

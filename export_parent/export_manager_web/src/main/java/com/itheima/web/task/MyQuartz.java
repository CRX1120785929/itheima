package com.itheima.web.task;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Configuration
@EnableScheduling
public class MyQuartz {

    /**
     *  spring的定时任务调度注解指定cron时间格式，不支持年。最多只能写6个
     *  秒  分  时  日  月  周
     */
    @Scheduled(fixedRate = 5000l )
    public void showMessage(){
        System.out.println("Quartz定时任务调度执行了。。。。");
    }
}

package com.itheima.test;

import com.itheima.domain.cargo.Factory;
import com.itheima.domain.cargo.FactoryExample;
import com.itheima.service.cargo.FactoryService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */

public class FactoryTest {

    public static void main(String[] args) throws Exception{
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
        FactoryService factoryService = (FactoryService)applicationContext.getBean("factoryServiceImpl");
        System.out.println(factoryService);
        FactoryExample example = new FactoryExample();
        example.createCriteria().andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(example);
        for(Factory factory : factoryList){
            System.out.println(factory.getFactoryName());
        }
    }
}

package com.itheima.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class StatProvider {

    public static void main(String[] args) throws Exception{
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
        applicationContext.start();
        System.in.read();
    }
}

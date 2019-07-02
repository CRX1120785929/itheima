package com.itheima.web.listeners;

import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

/**
 * 监听servletContext域中属性发生变化的监听器
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class ServletContextAttributeListenerImpl implements ServletContextAttributeListener{

    /**
     * 存入应用域中时
     * @param scab
     */
    @Override
    public void attributeAdded(ServletContextAttributeEvent scab) {
        //1.取出ServletContext对象
        ServletContext context = scab.getServletContext();
        //2.取出spring的ioc容器
        WebApplicationContext wac = (WebApplicationContext)context.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        //3.取出所有的bean名称
        String[] names = wac.getBeanDefinitionNames();
        for(String name : names){
            System.out.println(name);
        }
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent scab) {

    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent scab) {

    }
}

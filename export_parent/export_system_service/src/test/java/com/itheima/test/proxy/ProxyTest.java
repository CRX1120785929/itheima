package com.itheima.test.proxy;

import com.itheima.dao.system.UserDao;
import com.itheima.service.system.impl.UserServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class ProxyTest {

    public static void main(String[] args) {
        Proxy.newProxyInstance(UserDao.class.getClassLoader(),
                new Class[]{UserDao.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //增强的代码写在这里
                        return null;
                    }
                });


        Proxy.newProxyInstance(UserServiceImpl.class.getClassLoader(),
                UserServiceImpl.class.getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //增强的代码写在这里
                        return null;
                    }
                });
    }
}

package com.itheima.web.exceptions;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义异常处理器类
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Component
public class CustomeExceptionResovler implements HandlerExceptionResolver{

    /**
     * 此方法是用于前往错误页面
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //1.定义返回值对象
        ModelAndView mv = new ModelAndView();
        //2.设置响应页面
        mv.setViewName("error");
        //3.设置错误消息
        //判断ex是不是自定义异常类型
        if(ex instanceof  CustomeException){
            //自定义异常类型
            mv.addObject("errorMsg",ex.getMessage());
        }else if(ex instanceof UnauthorizedException){
            mv.setViewName("forward:/unauthorized.jsp");
        }else {
            ex.printStackTrace();//打印堆栈信息到控制台！
            mv.addObject("errorMsg", "服务器忙！");
        }
        return mv;
    }
}

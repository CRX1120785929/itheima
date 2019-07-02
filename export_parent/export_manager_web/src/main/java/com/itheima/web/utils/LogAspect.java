package com.itheima.web.utils;

import com.itheima.commons.utils.UtilFuns;
import com.itheima.domain.system.SysLog;
import com.itheima.domain.system.User;
import com.itheima.service.system.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 用于记录系统日志的
 * 需求：
 *   在用户点击了页面链接请求到达控制器之前，先来记录系统日志
 * 要求：
 *   使用spring基于注解的aop配置
 *   通知使用环绕通知
 *   增强的方法写在控制器方法执行之前
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Aspect//第一步：让当前类成为切面类
@Component//第二步：让spring接管切面类的创建
public class LogAspect {

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private HttpServletRequest request;
    /**
     * 环绕通知
     * @param pjp  它是用于调用切入点方法（核心方法）的对象
     * @return
     *
     *      private String id;          日志的唯一标识，此处不用处理，在service中处理
            private String userName;    用户的名称，此处要处理     在用户对象里，用户对象在session域中，需要一个session
            private String ip;          来访者ip，此处要处理       在request对象里                   需要一个request
            private Date time;          来访时间，此处要处理       获取当前系统时间
        private String method;      操作的方法，此处要处理     在环绕通知的参数中（pjp）
        private String action;      操作的名称，此处要处理     在环绕通知的参数中，同时要求控制器方法的RequestMapping注解的name属性必须有值
            private String companyId;   来访者的企业id            在用户对象里
            private String companyName; 来访者的企业名称           在用户对象里
     */
    @Around("execution(* com.itheima.web.controller.*.*.*(..))")//第三步：配置环绕通知，同时指定切入点表达式
    public Object aroundSysLog(ProceedingJoinPoint pjp){
        try{
            //获取pjp的签名
            Signature signature = pjp.getSignature();
            //判断当前的签名是不是方法签名
            if(signature instanceof MethodSignature){
                //转成方法签名
                MethodSignature ms = (MethodSignature)signature;
                //从方法签名中获取当前方法
                Method method = ms.getMethod();
                //判断当前方法是不是被RequestMapping注解了
                if(method.isAnnotationPresent(RequestMapping.class)){
                    //创建返回值对象
                    SysLog log = new SysLog();
                    //取出用户信息
                    User user = (User)httpSession.getAttribute("user");
                    //给log中关于用户信息的数据赋值
                    if(user == null || UtilFuns.isEmpty(user.getUserName())){
                        log.setUserName("匿名");
                    }else {
                        log.setUserName(user.getUserName());
                        log.setCompanyId(user.getCompanyId());
                        log.setCompanyName(user.getCompanyName());
                    }
                    //设置日志创建时间
                    log.setTime(new Date());
                    //获取来访者ip
                    String remoteIp = request.getRemoteAddr();
                    log.setIp(remoteIp);
                    System.out.println(remoteIp);
                    //取出RequestMapping注解
                    RequestMapping mapping = method.getAnnotation(RequestMapping.class);
                    //取出注解中name属性的值
                    String action = mapping.name();
                    //给log的action属性赋值
                    log.setAction(action);
                    //给方法名称赋值
                    log.setMethod(method.getName());
                    //保存系统日志
                    sysLogService.saveLog(log);
                }
            }
            //获取当前执行方法所需的参数
            Object[] args = pjp.getArgs();
            //执行方法并返回
            return pjp.proceed(args);
        }catch (Throwable e){
            throw new RuntimeException(e);
        }
    }

    //第四步：在配置文件中开启spring对注解aop的支持
}

package com.itheima.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Component
public class EmailListener implements MessageListener {

    @Autowired
    private SimpleMailMessage mailMessage;

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage)message;
        try {
            System.out.println("接收到了消息");
            String username = mapMessage.getString("username");
            String password = mapMessage.getString("password");
            String email = mapMessage.getString("email");
            String conpanyName = mapMessage.getString("companyName");
            String content = username+"先生/女士，欢迎您加入"+conpanyName+",您的平台账号是："+email+",登录密码是："+password+",请尽快修改您的密码。";
            mailMessage.setTo(email);
            mailMessage.setSubject("入职欢迎邮件");
            mailMessage.setText(content);
            mailSender.send(mailMessage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

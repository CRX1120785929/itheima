package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.commons.utils.Encrypt;
import com.itheima.dao.system.ModuleDao;
import com.itheima.dao.system.UserDao;
import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;
import com.itheima.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import java.util.List;
import java.util.UUID;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ModuleDao moduleDao;

    @Override
    public PageInfo findAll(String companyId, int page, int size) {
        PageHelper.startPage(page,size);
        List<User> userList = userDao.findAll(companyId);
        return new PageInfo(userList);
    }

    @Override
    public User findById(String userId) {
        return userDao.findById(userId);
    }

    @Override
    public void delete(String userId) {
        userDao.delete(userId);
    }

    @Autowired
    private JmsTemplate jmsQueueTemplate;

    @Override
    public void save(User user) {
        String id = UUID.randomUUID().toString().replace("-","").toUpperCase();
        user.setId(id);
        String password = user.getPassword();
        String md5Password = Encrypt.md5(password,user.getEmail());
        user.setPassword(md5Password);
        userDao.save(user);
//        //创建线程对象
//        Thread tl = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //发送欢迎邮件，如果发送邮件出现了异常，则事务回滚
//            }
//        });
//        //启动线程
//        tl.start();

        //往消息中间件里写一条消息
        jmsQueueTemplate.send("export-queue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("email",user.getEmail());
                mapMessage.setString("username",user.getUserName());
                mapMessage.setString("companyName",user.getCompanyName());
                mapMessage.setString("password",password);
                return mapMessage;
            }
        });
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void changeRole(String userId, String[] roleIds) {
        //1.删除用户和角色的中间表中，当前用户的全部角色
        userDao.deleteUserRole(userId);
        //2.遍历角色id的数组
        for(String roleId : roleIds) {
            //3.在用户和角色的中间表中插入数据
            userDao.saveUserRole(userId,roleId);
        }
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public List<Module> findUserMenus(String userId) {
        //1.根据id查询用户
        User user = userDao.findById(userId);
        //2.判断用户是saas管理员，企业内部的系统管理员，租户员工
        if(user.getDegree() == 0 ){
            //是saas系统管理员
            return moduleDao.findModuleByBelong(0);
        }else if(user.getDegree() == 1){
            //是企业系统管理员
            return moduleDao.findModuleByBelong(1);
        }else{
            //是租户
            return moduleDao.findModuleByUserId(userId);
        }

        //return moduleDao.findModuleByUserId(userId);
    }
}

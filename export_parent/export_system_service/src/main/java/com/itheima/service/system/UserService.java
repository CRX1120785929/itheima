package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;

import java.util.List;

/**
 * 用户的业务层接口
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public interface UserService {

    //根据企业id查询全部
    PageInfo findAll(String companyId,int page,int size);

    //根据id查询
    User findById(String userId);

    //根据id删除
    void delete(String userId);

    //保存
    void save(User user);

    //更新
    void update(User user);

    //更新用户的角色
    void changeRole(String userId,String[] roleIds);

    //根据邮箱查询用户
    User findByEmail(String email);

    //查询用户可以操作的菜单
    List<Module> findUserMenus(String userId);
}

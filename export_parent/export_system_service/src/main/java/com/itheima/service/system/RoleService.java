package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Role;

import java.util.List;
import java.util.Map;

/**
 * 角色的业务层接口
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public interface RoleService {

    //根据id查询
    Role findById(String id);

    //查询全部用户
    PageInfo findAll(String companyId, int page, int size);

    //根据id删除
    void delete(String id);

    //添加
    void save(Role role);

    //更新
    void update(Role role);

    //获取属性结构的json数据
    List<Map> findTreeJson(String roleId);

    //更新角色可以操作的模块信息
    void updateRoleModule(String roleId,String moduleIds);

    //查询当前企业的所有角色
    List<Role> findAll(String companyId);

    //根据用户id查询角色信息
    List<String> findRoleByUserId(String userId);//我们目前看这么写很合理，但是过一会还得改回来
}

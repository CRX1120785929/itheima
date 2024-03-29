package com.itheima.dao.system;

import com.itheima.domain.system.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserDao {

	//根据企业id查询全部
	List<User> findAll(String companyId);

	//根据id查询
    User findById(String userId);

	//根据id删除
	int delete(String userId);

	//保存
	int save(User user);

	//更新
	int update(User user);

	//删除当前用户所具备的角色
	void deleteUserRole(String userId);

	//保存用户和角色的关系
	void saveUserRole(@Param("userId") String userId,@Param("roleId") String roleId);

	//根据邮箱查询用户
	User findByEmail(String email);
}
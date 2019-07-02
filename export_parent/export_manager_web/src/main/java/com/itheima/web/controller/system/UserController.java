package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Dept;
import com.itheima.domain.system.Role;
import com.itheima.domain.system.User;
import com.itheima.service.system.DeptService;
import com.itheima.service.system.RoleService;
import com.itheima.service.system.UserService;
import com.itheima.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private RoleService roleService;

    /**
     * 查询所有用户
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "5")int size){
        //1.查询所有用户
        PageInfo pageInfo = userService.findAll(super.companyId,page,size);
        //2.存入请求域中
        request.setAttribute("page",pageInfo);
        //3.前往列表页面
        return "system/user/user-list";
    }

    /**
     * 前往添加页面
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(){
        List<Dept> deptList = deptService.findAll(super.companyId);
        request.setAttribute("deptList",deptList);
        return "system/user/user-add";
    }

    /**
     * 前往更新页面
     * @param id
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        User user = userService.findById(id);
        request.setAttribute("user",user);
        List<Dept> deptList = deptService.findAll(super.companyId);
        request.setAttribute("deptList",deptList);
        return "system/user/user-add";
    }

    /**
     * 保存或者更新
     * @param user
     * @return
     */
    @RequestMapping("/edit")
    public String edit(User user){
        user.setCompanyId(super.companyId);
        user.setCompanyName(super.companyName);
        if(StringUtils.isEmpty(user.getId())){
            userService.save(user);
        }else {
            userService.update(user);
        }
        return "redirect:/system/user/list.do";
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id){
        userService.delete(id);
        return "redirect:/system/user/list.do";
    }

    /**
     * 前往分配角色页面
     * @param id
     * @return
     */
    @RequestMapping(value = "/roleList",name = "获取用户角色")
    public String roleList(String id){
        //1.用户信息
        User user = userService.findById(id);
        //2.当前企业的所有角色信息
        List<Role> roleList = roleService.findAll(super.companyId);
        //3.当前用户的角色信息
        List<String> userRoleList = roleService.findRoleByUserId(id);
        //4.把用户所具备的角色id集合转成字符串
        String userRoleIds = userRoleList.toString();
        //5.存入请求域中
        request.setAttribute("user",user);
        request.setAttribute("roleList",roleList);
        request.setAttribute("userRoleStr",userRoleIds);
        //页面跳转，转发到user-role.jsp
        return "system/user/user-role";
    }

    /**
     * 更新用户所具备的角色
     * @param id
     * @param roleIds
     * @return
     */
    @RequestMapping("/changeRole")
    public String changeRole(@RequestParam("userid") String id,String[] roleIds){
        userService.changeRole(id,roleIds);
        //重定向到用户的列表页面
        return "redirect:/system/user/list.do";
    }





































}

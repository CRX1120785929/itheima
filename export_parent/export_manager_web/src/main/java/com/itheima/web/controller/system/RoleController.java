package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.commons.utils.UtilFuns;
import com.itheima.domain.system.Role;
import com.itheima.service.system.RoleService;
import com.itheima.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 角色的控制器
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询所有角色
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int size){
        //1.调用业务层查询所有
        PageInfo pageInfo = roleService.findAll(super.companyId,page,size);
        //2.存入请求域中
        request.setAttribute("page",pageInfo);
        //3.前往角色列表页面
        return "system/role/role-list";
    }

    /**
     * 前往新增页面
     */
    @RequestMapping("/toAdd")
    public String toAdd(){
        return "system/role/role-add";
    }

    /**
     * 前往更新页面
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        //1.根据id查询角色
        Role role = roleService.findById(id);
        //2.存入请求域中
        request.setAttribute("role",role);
        //3.前往编辑页面
        return "system/role/role-update";
    }


    /**
     * 保存或者更新
     */
    @RequestMapping("/edit")
    public String edit(Role role){
        //设置企业id和企业名称
        role.setCompanyId(super.companyId);
        role.setCompanyName(super.companyName);
        //1.判断是保存还是更新
        if(UtilFuns.isEmpty(role.getId())){
            //保存操作

            roleService.save(role);
        }else {
            //更新操作：非全字段更新需要先查询
            roleService.update(role);
        }
        return "redirect:/system/role/list.do";
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public String delete(String id){
        roleService.delete(id);
        return "redirect:/system/role/list.do";
    }



    /**
     *
     * 第二种处理思路：
     *      在前往分配权限页面的方法中不管树形结构的json数据生成
     *      在role-module.jsp中发起ajax请求，例如springmvc提供的@ResponseBody注解配合jackson组件实现自动生成json数据
     * 前往分配权限页面
     * @param id
     * @return
     *
     */
    @RequestMapping("/roleModule")
    public String roleModule(@RequestParam("roleid") String id){
        //1.根据id查询角色
        Role role = roleService.findById(id);
        //2.存入请求域中
        request.setAttribute("role",role);
        return "system/role/role-module";
    }

    /**
     * 获取树形结构的json数据
     * @param id
     * @return
     */
    @RequestMapping("/initModuleData")
    public @ResponseBody List initModuleData(@RequestParam("roleid") String id){
        List<Map> list = roleService.findTreeJson(id);
        return list;
    }

    /**
     * @param id  是当前的角色id
     * @param moduleIds 是用,拼接的模块id
     * @return
     */
    @RequestMapping("/updateRoleModule")
    public String updateRoleModule(@RequestParam("roleid") String id,String moduleIds){
        //1.调用业务层实现更新权限
        roleService.updateRoleModule(id,moduleIds);
        //2.重定向到角色列表页面
        return "redirect:/system/role/list.do";
    }











    /**
     * 第二种思路中关于json格式数据生成的分析：
     *      我们需要查询出来所有的模块列表
     *      同时还要获取当前角色的模块列表
     *      两个列表进行比对，最终决定哪些模块是被选中的状态，哪些是不选中的。
     *      比对完成后，按照要求生成返回值对象
     *
     *  问题
     *      此种方式，需要查询数据库两次。
     *      第一次是：获取所有模块列表
     *      第二次是：获取当前角色列表
     *
     *
     * 异步请求，获取生成树形结构的json数据
     * json数据的格式是：
     *      [
     *           { id:11, pId:1, name:"随意勾选 1-1", open:true},
     *           { id:111, pId:11, name:"随意勾选 1-1-1"}
     *      ]
     * 返回值要求是一个json格式数据，我们可以利用springmvc的注解加上jackson组件
     * @param id
     * @return

    @RequestMapping("/initModuleData")
    public @ResponseBody List initModuleData(@RequestParam("roleid") String id){
        //创建返回值对象
        List<Map> returnList = new ArrayList<>();

        //查询所有模块列表
        List<Module> allModuleList = null;
        //查询当前角色id所具备的模块列表
        List<Module> currentRoleIdModuleList = null;
        //遍历所有模块列表
        for(Module module : allModuleList){
            //创建返回值集合中的map元素
            Map map = new HashMap();
            //设置Map中的数据
            map.put("id",module.getId());
            map.put("pId",module.getParentId());
            map.put("name",module.getName());
            if(currentRoleIdModuleList.contains(module)) {
                map.put("checked","true");
            }else {
                map.put("checked","false");
            }
            //加入到返回值集合中
            returnList.add(map);
        }
        return returnList;
    }*/



























    /**
     *
     * 第一种处理思路：
     *      在前往分配权限页面的方法中拼接json字符串
     * 问题：
     *      json格式数据，由我们自己拼接，出错的几率很大
     *
     * 前往分配权限页面
     * @param id
     * @return
     * [
        { id:11, pId:1, name:"随意勾选 1-1", open:true},
        { id:111, pId:11, name:"随意勾选 1-1-1"}
        ]
    @RequestMapping("/roleModule")
    public String roleModule(@RequestParam("roleid") String id){
        //1.根据id查询角色
        Role role = roleService.findById(id);
        //2.存入请求域中
        request.setAttribute("role",role);

        //查询所有模块列表
        List<Module> allModuleList = null;
        //查询当前角色id所具备的模块列表
        List<Module> currentRoleIdModuleList = null;
        //遍历所有模块列表
        StringBuilder ss = new StringBuilder();
        ss.append("[");
        for(Module module : allModuleList){
            ss.append("{ id :").append("'"+module.getId()+"'},");
            ss.append("{ pId :").append("'"+module.getParentId()+"'},");
            ss.append("{ name :").append("'"+module.getName()+"'},");

            if(currentRoleIdModuleList.contains(module)) {
                ss.append("{ checked :").append("'" + true + "'},");
            }else {
                ss.append("{ checked :").append("'" + false + "'},");
            }
        }
        String json = ss.toString();
        json.substring(0,json.length()-1);
        json.concat("]");
        return "system/role/role-module";
    }*/
}

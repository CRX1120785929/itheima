package com.itheima.controller;

import com.itheima.pojo.TbBrand;
import com.itheima.utils.Result;
import com.ithema.service.TbBrandinterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class Controller {
    @Autowired
    private TbBrandinterface tbBrandinterface;
/*查询所有*/
    @RequestMapping("/findAll")
    public List<TbBrand> findAll() {
        return tbBrandinterface.selectByExample(null);
    }

    /*新增*/
    @RequestMapping("/insert")
    public Result insert(@RequestBody TbBrand tbBrand) {
        try {
            tbBrandinterface.insert(tbBrand);
            return new Result(true, "新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "新增失败");
        }
    }
    /*删除*/
    @RequestMapping("/delete")
    public Result delete(){
        try {
            tbBrandinterface.deleteByPrimaryKey(1L);
            return new Result(true, "新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "新增失败");
        }
    }
    /*修改*/
    @RequestMapping("/update")
    public Result update(TbBrand tbBrand){
        try {
            tbBrandinterface.updateByPrimaryKey(tbBrand);
            return new Result(true, "新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "新增失败");
        }

    }


}
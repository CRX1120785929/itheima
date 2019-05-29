package com.ithema.service;

import com.itheima.pojo.TbBrand;

import com.itheima.pojo.TbBrandExample;

import java.util.List;

public interface TbBrandinterface {
    /*删除 */
    int deleteByPrimaryKey(Long id);

    /*添加*/
    void insert(TbBrand record);

    /*查询所有*/
    List<TbBrand> selectByExample(TbBrandExample example);

    /*根据id查询*/
    TbBrand selectByPrimaryKey(Long id);

    /*修改*/
    void updateByPrimaryKey(TbBrand record);
}

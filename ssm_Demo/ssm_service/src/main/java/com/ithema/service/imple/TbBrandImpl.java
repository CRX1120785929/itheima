package com.ithema.service.imple;

import com.itheima.mapper.TbBrandMapper;
import com.itheima.pojo.TbBrand;
import com.itheima.pojo.TbBrandExample;
import com.ithema.service.TbBrandinterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbBrandImpl implements TbBrandinterface {
    @Autowired
    private TbBrandMapper tbBrandMapper;


    @Override
    public int deleteByPrimaryKey(Long id) {
        return tbBrandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insert(TbBrand record) {
        tbBrandMapper.insert(record);
    }

    /*查询*/
    @Override
    public List<TbBrand> selectByExample(TbBrandExample example) {
        //不给条件就是查全部 所以为null
        return tbBrandMapper.selectByExample(example);
    }

    @Override
    public TbBrand selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public void updateByPrimaryKey(TbBrand record) {
        tbBrandMapper.updateByPrimaryKey(record);
    }
}

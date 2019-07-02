package com.itheima.service.stat;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.stat.StatDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Service
public class StatServiceImpl implements StatService {

    @Autowired
    private StatDao statDao;

    @Override
    public List<Map> findFactorySellData(String companyId) {
        return statDao.findFactorySellData(companyId);
    }

    @Override
    public List<Map> findProductSellData(String companyId) {
        return statDao.findProductSellData(companyId);
    }

    @Override
    public List<Map> findOnlineData(String companyId) {
        return statDao.findOnlineData(companyId);
    }
}

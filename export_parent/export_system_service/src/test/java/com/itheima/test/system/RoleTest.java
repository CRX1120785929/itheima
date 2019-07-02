package com.itheima.test.system;

import com.itheima.service.system.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext-*.xml")
public class RoleTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void testFindTreeJson(){
        List<Map> list = roleService.findTreeJson("4028a1c34ec2e5c8014ec2ebf8430001");
        for(Map map : list){
            System.out.println(map);
        }
//        System.out.println(list);
    }
}

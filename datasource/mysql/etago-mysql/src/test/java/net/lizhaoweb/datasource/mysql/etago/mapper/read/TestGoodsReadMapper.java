/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor DataSource Mysql Etago
 * @Title : TestGoodsReadMapper.java
 * @Package : net.lizhaoweb.datasource.mysql.etago.mapper.read
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月30日
 * @Time : 下午4:07:30
 */
package net.lizhaoweb.datasource.mysql.etago.mapper.read;

import net.lizhaoweb.datasource.mysql.etago.model.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version
 * @notes Created on 2016年6月30日<br>
 *        Revision of last commit:$Revision$<br>
 *        Author of last commit:$Author$<br>
 *        Date of last commit:$Date$<br>
 *        <p />
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/schema/spring/spring-mysql_etago-model.xml", "classpath*:/schema/spring/spring-mysql_etago-datasource.xml", "classpath*:/schema/spring/spring-mysql_etago-mapper.xml"})
public class TestGoodsReadMapper {

    @Autowired
    private IGoodsReadMapper readMapper;

    @Test
    public void getAll() {
        List<Goods> goodList = readMapper.getAll();
        System.out.println(goodList);
        System.out.println();
        System.out.println();

        for (Goods goods : goodList) {
            System.out.println(goods.getTagList());
        }
    }

    @Test
    public void getAll2() {
        List<Goods> goodList = readMapper.getAll2();
        System.out.println(goodList);
        System.out.println();
        System.out.println();

        for (Goods goods : goodList) {
            System.out.println(goods.getTagList());
        }
    }
}

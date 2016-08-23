/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.datasource.redis.etago.write.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 16:38
 */
package com.easarrive.datasource.redis.etago.write.impl;

import com.easarrive.datasource.redis.etago.read.IThumborReadDao;
import com.easarrive.datasource.redis.etago.write.IThumborWriteDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月25日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/schema/spring/spring-redis_etago-datasource.xml", "classpath*:/schema/spring/spring-redis_etago-mapper.xml"})
public class TestThumborImageDao {

    @Autowired
    private IThumborWriteDao<String, String> writeDao;

    @Autowired
    private IThumborReadDao<String, String> readDao;

    @Before
    public void init() {
    }

    @Test
    public void save() {
        writeDao.save("test", "abc");
    }

    @Test
    public void get() {
        String value = readDao.get("37197125b517e5a0555a1b402dfd0639027d6f211470234670_750x750.png");
        System.out.println(value);
    }

    @Test
    public void allKeys() {
        Set<String> list = readDao.allKeys();
        for (String value : list) {
            System.out.println(value);
        }
    }
}

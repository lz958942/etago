/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : net.lizhaoweb.datasource.mysql.etago.mapper.write
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 17:30
 */
package net.lizhaoweb.datasource.mysql.etago.mapper.write;

import net.lizhaoweb.datasource.mysql.etago.model.CronTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月29日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/schema/spring/spring-mysql_etago-model.xml", "classpath*:/schema/spring/spring-mysql_etago-datasource.xml", "classpath*:/schema/spring/spring-mysql_etago-mapper.xml"})
public class TestCronTaskWriteMapper {

    @Autowired
    private ICronTaskWriteMapper writeMapper;

    @Test
    public void insert() {
        CronTask bean = new CronTask(0L, "etago", "etago_goods_tags", System.currentTimeMillis(), "abd", System.currentTimeMillis(), System.currentTimeMillis(), System.currentTimeMillis(), System.currentTimeMillis(), 0);
        Long count = writeMapper.insert(bean);
        System.out.println(count);
    }

    @Test
    public void update() {
        CronTask bean = new CronTask(1L, "etago", "etago_goods_tags", System.currentTimeMillis(), "abc", System.currentTimeMillis(), System.currentTimeMillis(), System.currentTimeMillis(), System.currentTimeMillis(), 0);
        Long count = writeMapper.update(bean);
        System.out.println(count);
    }

    @Test
    public void delete() {
        Long count = writeMapper.delete(1L);
        System.out.println(count);
    }
}

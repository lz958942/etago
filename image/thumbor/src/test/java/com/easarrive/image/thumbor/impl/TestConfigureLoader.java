/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor Image Thumbor
 * @Title : TestConfigureLoader.java
 * @Package : com.easarrive.image.thumbor.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月21日
 * @Time : 下午6:15:56
 */
package com.easarrive.image.thumbor.impl;

import com.easarrive.image.thumbor.IConfigureLoader;
import com.easarrive.image.thumbor.bean.ThumborConfigure;
import com.fasterxml.jackson.core.JsonProcessingException;
import net.lizhaoweb.common.util.base.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version
 * @notes Created on 2016年6月21日<br>
 *        Revision of last commit:$Revision$<br>
 *        Author of last commit:$Author$<br>
 *        Date of last commit:$Date$<br>
 *        <p />
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath*:/schema/spring/spring-thumbor-model.xml",
        "classpath*:/schema/spring/spring-thumbor-datasource.xml",
        "classpath*:/schema/spring/spring-thumbor-mapper.xml",
        "classpath*:/schema/spring/spring-thumbor-service.xml"
})
public class TestConfigureLoader {

    @Autowired
    private IConfigureLoader configureLoader;

    @Test
    public void load() {
        try {
            ThumborConfigure configure = configureLoader.getConfigure();
            String json = JsonUtil.toJson(configure);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

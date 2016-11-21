/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.datasource.redis.etago.read.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 20:54
 */
package com.easarrive.datasource.redis.etago.read.impl;

import com.easarrive.datasource.redis.etago.AbstractDaoByRedisTemplate;
import com.easarrive.datasource.redis.etago.model.ThumborConfigure;
import com.easarrive.datasource.redis.etago.read.IThumborReadDao;
import com.easarrive.datasource.redis.etago.util.Constant;
import net.lizhaoweb.common.util.base.JsonUtil;

import java.util.Set;

/**
 * <h1>实现 - Redis - 读取 - Thumbor配置</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月25日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class ThumborConfigureReadDao extends AbstractDaoByRedisTemplate<String, String, String> implements IThumborReadDao<String, ThumborConfigure> {

    @Override
    public ThumborConfigure get(String key) {
        String json = this.hashGet(Constant.Thumbor.System.Config.NAMESPACE, key);
        ThumborConfigure bean = JsonUtil.toBean(json, ThumborConfigure.class);
        return bean;
    }

    @Override
    public Set<String> allKeys() {
        return this.hashKeys(Constant.Thumbor.System.Config.NAMESPACE);
    }

    @Override
    public String getJson(String key) {
        return this.hashGet(Constant.Thumbor.System.Config.NAMESPACE, key);
    }
}

/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.datasource.redis.etago.read.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 19:14
 */
package com.easarrive.datasource.redis.etago.read.impl;

import com.easarrive.datasource.redis.etago.AbstractDaoByRedisTemplate;
import com.easarrive.datasource.redis.etago.read.IThumborReadDao;
import com.easarrive.datasource.redis.etago.util.Constant;

import java.util.Set;

/**
 * <h1>实现 - Redis - 读取 - 产品</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年08月02日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class ThumborImageGoodsReadDao extends AbstractDaoByRedisTemplate<String, String, String> implements IThumborReadDao<String, String> {

    @Override
    public String get(String key) {
        return this.hashGet(Constant.Thumbor.Path.Image.GOODS, key);
    }

    @Override
    public Set<String> allKeys() {
        return this.hashKeys(Constant.Thumbor.Path.Image.GOODS);
    }

    @Override
    public String getJson(String key) {
        return this.hashGet(Constant.Thumbor.Path.Image.GOODS, key);
    }
}

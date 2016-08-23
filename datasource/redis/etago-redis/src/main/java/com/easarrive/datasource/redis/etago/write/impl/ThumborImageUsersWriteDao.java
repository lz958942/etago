/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.datasource.redis.etago.write.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 16:30
 */
package com.easarrive.datasource.redis.etago.write.impl;

import com.easarrive.datasource.redis.etago.AbstractDaoByRedisTemplate;
import com.easarrive.datasource.redis.etago.util.Constant;
import com.easarrive.datasource.redis.etago.write.IThumborWriteDao;

/**
 * <h1>接口 - Redis - 写入 - 用户</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月25日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class ThumborImageUsersWriteDao extends AbstractDaoByRedisTemplate<String, String, String> implements IThumborWriteDao<String, String> {

    @Override
    public void save(String key, String bean) {
        this.hashPut(Constant.Thumbor.Path.Image.USERS, key, bean);
    }

    @Override
    public Long delete(String key) {
        return this.hashDelete(Constant.Thumbor.Path.Image.USERS, key);
    }
}

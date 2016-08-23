/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.datasource.redis.etago
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 19:53
 */
package com.easarrive.datasource.redis.etago;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.lizhaoweb.common.util.base.JsonUtil;

import java.io.IOException;
import java.util.List;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年08月02日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public abstract class AbstracStringDaoBytRedis<B> extends AbstractDaoByRedis<String, String, String> {


    public void put(String key, String feild, B bean) throws JsonProcessingException {
        String json = JsonUtil.toJson(bean);
        this.put(key, feild, json);
    }

    public B get(String key, String feild, Class<B> clazz) throws IOException {
        byte[] bytes = this.get(key, feild);
        String json = new String(bytes);
        B bean = JsonUtil.toBean(json, clazz);
        return bean;
    }

    public List<B> list(String namespace, Class<B> clazz) throws IOException {
//        List<String> stringList = this.list(namespace);
//        List<B> list = new ArrayList<>();
//        for (String json : stringList) {
//            B bean = JsonUtil.toBean(json, clazz);
//            list.add(bean);
//        }
//        return list;
        return null;
    }
}

/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.datasource.redis.etago.write.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 19:46
 */
package com.easarrive.datasource.redis.etago;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月25日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public abstract class AbstractDaoByRedis<K, F, V> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Setter
    private RedisSerializer keySerializer;

    @Setter
    private RedisSerializer valueSerializer;

    @Setter
    private JedisConnectionFactory jedisConnetionFactory;

    public void put(K key, F feild, V bean) {
        final byte[] rawKey = rawKey(key);
        final byte[] rawHashKey = rawHashKey(feild);
        final byte[] rawHashValue = rawHashValue(bean);
        this.jedisConnetionFactory.getConnection().hSet(rawKey, rawHashKey, rawHashValue);
    }

    public byte[] get(K key, F feild) {
        final byte[] rawKey = rawKey(key);
        final byte[] rawHashKey = rawHashKey(feild);
        return jedisConnetionFactory.getConnection().hGet(rawKey, rawHashKey);
    }

    public Set<byte[]> keys(K namespace) {
        String keyFormat = String.format("%s*", namespace);
        Set<byte[]> keys = jedisConnetionFactory.getConnection().keys(keyFormat.getBytes());
        return keys;
    }

    public Set<String> stringKeys(K namespace) {
        Set<String> resultList = new HashSet<String>();
        Set<byte[]> keySet = this.keys(namespace);
        for (byte[] bytes : keySet) {
            resultList.add(new String(bytes));
        }
        return resultList;
    }

    byte[] rawKey(Object key) {
        Assert.notNull(key, "non null key required");
        if (this.keySerializer() == null && key instanceof byte[]) {
            return (byte[]) key;
        }
        return this.keySerializer().serialize(key);
    }

    <HK> byte[] rawHashKey(HK hashKey) {
        Assert.notNull(hashKey, "non null hash key required");
        if (this.keySerializer() == null && hashKey instanceof byte[]) {
            return (byte[]) hashKey;
        }
        return this.keySerializer().serialize(hashKey);
    }

    <HV> byte[] rawHashValue(HV value) {
        if (this.valueSerializer() == null & value instanceof byte[]) {
            return (byte[]) value;
        }
        return this.valueSerializer().serialize(value);
    }

    RedisSerializer keySerializer() {
        return this.keySerializer;
    }

    RedisSerializer valueSerializer() {
        return this.valueSerializer;
    }
}

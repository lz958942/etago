/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.datasource.redis.etago
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 10:38
 */
package com.easarrive.datasource.redis.etago;

import lombok.Setter;
import org.springframework.data.redis.core.*;

import java.util.List;
import java.util.Set;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年08月04日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public abstract class AbstractDaoByRedisTemplate<K, F, V> {

    @Setter
    private RedisTemplate<K, V> redisTemplate;

    //HASH
    public void hashPut(K key, F feild, V bean) {
        HashOperations<K, F, V> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, feild, bean);
    }

    public V hashGet(K key, F feild) {
        HashOperations<K, F, V> hashOperations = redisTemplate.opsForHash();
        V object = null;
        if (hashOperations.hasKey(key, feild)) {
            object = hashOperations.get(key, feild);
        }
        return object;
    }

    public Set<F> hashKeys(K key) {
        HashOperations<K, F, V> hashOperations = redisTemplate.opsForHash();
        Set<F> keySet = hashOperations.keys(key);
        return keySet;
    }

    public List<V> hashValues(K key) {
        HashOperations<K, F, V> hashOperations = redisTemplate.opsForHash();
        List<V> valueList = hashOperations.values(key);
        return valueList;
    }

    public Long hashDelete(K key, F... feilds) {
        HashOperations<K, F, V> hashOperations = redisTemplate.opsForHash();
        Long count = hashOperations.delete(key, feilds);
        return count;
    }


    //LIST
    public void listSet(K key, long index, V bean) {
        ListOperations<K, V> listOperations = redisTemplate.opsForList();
        listOperations.set(key, index, bean);
    }

    public V listGet(K key, long index) {
        ListOperations<K, V> listOperations = redisTemplate.opsForList();
        V object = listOperations.index(key, index);
        return object;
    }

    public Long listDelete(K key, long index) {
        ListOperations<K, V> listOperations = redisTemplate.opsForList();
        Long count = listOperations.remove(key, index, null);
        return count;
    }


    //VALUE
    public void valueSet(K key, V bean) {
        ValueOperations<K, V> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, bean);
    }

    public V valueGet(K key) {
        ValueOperations<K, V> valueOperations = redisTemplate.opsForValue();
        V object = valueOperations.get(key);
        return object;
    }


    //SET
    public Long setAdd(K key, V... beans) {
        SetOperations<K, V> setOperations = redisTemplate.opsForSet();
        Long count = setOperations.add(key, beans);
        return count;
    }

    public V setPOP(K key) {
        SetOperations<K, V> setOperations = redisTemplate.opsForSet();
        V object = setOperations.pop(key);
        return object;
    }

    public Long setDelete(K key, V... beans) {
        SetOperations<K, V> setOperations = redisTemplate.opsForSet();
        Long count = setOperations.remove(key, beans);
        return count;
    }
}

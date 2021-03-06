/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.datasource.redis.etago.read
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 20:47
 */
package com.easarrive.datasource.redis.etago.read;

import java.util.Set;

/**
 * <h1>接口 - Redis - 读取</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月25日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public interface IThumborReadDao<K, B> {

    B get(K key);

    Set<K> allKeys();

    String getJson(String key);
}

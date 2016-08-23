/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor DataSource Mysql Etago
 * @Title : IGood4CloudSearchReadMapper.java
 * @Package : net.lizhaoweb.datasource.mysql.etago.mapper.read
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月5日
 * @Time : 下午5:45:44
 */
package net.lizhaoweb.datasource.mysql.etago.mapper.read;

import net.lizhaoweb.datasource.mysql.etago.model.Good4CloudSearch;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <h1>持久层(云搜索) - 产品读取</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年7月5日.<br>
 * Revision of last commit:$Revision$.<br>
 * Author of last commit:$Author$.<br>
 * Date of last commit:$Date$.<br>
 *
 */
public interface IGood4CloudSearchReadMapper {

    /**
     * 云搜索 - 通过产品ID列表查询产品对象列表。
     *
     * @param goodsIds 产品ID列表。
     * @return 返回产品对象列表。
     */
    List<Good4CloudSearch> getAll(String[] goodsIds);

    /**
     * 通过更新时间查询新增的记录 ID 和更新时间列表。
     *
     * @param updateTime 基准更新时间。
     * @return 返回新增的记录 ID 和更新时间列表。
     */
    List<Map<String, Object>> getGoodIds4Update(@Param("updateTime") Long updateTime);
}

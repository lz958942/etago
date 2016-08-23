/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : YiDa
 * @Package : net.lizhaoweb.datasource.mysql.etago.mapper.read
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 14:32
 */
package net.lizhaoweb.datasource.mysql.etago.mapper.read;

import net.lizhaoweb.datasource.mysql.etago.model.Reply4CloudSearch;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <h1>持久层(云搜索) - 用户回复</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年07月12日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public interface IReply4CloudSearchReadMapper {

    /**
     * 云搜索 - 通过回复ID列表查询回复对象列表。
     *
     * @param replyIds 回复ID列表。
     * @return 返回回复对象列表。
     */
    List<Reply4CloudSearch> getAll(String[] replyIds);

    /**
     * 通过更新时间查询新增的记录 ID 和更新时间列表。
     *
     * @param updateTime 基准更新时间。
     * @return 返回新增的记录 ID 和更新时间列表。
     */
    List<Map<String, Object>> getReplyIds4Update(@Param("updateTime") Long updateTime);
}

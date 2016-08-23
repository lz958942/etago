/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : net.lizhaoweb.datasource.mysql.etago.mapper.read
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 16:21
 */
package net.lizhaoweb.datasource.mysql.etago.mapper.read;

import net.lizhaoweb.datasource.mysql.etago.model.CronTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月29日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public interface ICronTaskReadMapper {

    /**
     * 查询定时任务列表。
     *
     * @param dbName    数据库名
     * @param tableName 表名
     * @param taskName  任务名，可以设置为 null 。
     * @return 返回定时任务列表。
     */
    List<CronTask> find(@Param("dbName") String dbName, @Param("tableName") String tableName, @Param("taskName") String taskName);
}

/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : net.lizhaoweb.datasource.mysql.etago.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 16:16
 */
package net.lizhaoweb.datasource.mysql.etago.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>模型 - 定时任务</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月29日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CronTask {

    /**
     * 唯一标识。
     */
    @JsonProperty(value = "id")
    private Long id;

    /**
     * 数据库名。
     */
    @JsonProperty(value = "db_name")
    private String dbName;

    /**
     * 表名。
     */
    @JsonProperty(value = "table_name")
    private String tableName;

    /**
     * 数据库的更新时间。
     */
    @JsonProperty(value = "table_update_time")
    private Long tableUpdateTime;

    /**
     * 任务名称。
     */
    @JsonProperty(value = "task_name")
    private String taskName;

    /**
     * 执行时间。
     */
    @JsonProperty(value = "execute_time")
    private Long execTime;

    /**
     * 上次执行时间。
     */
    @JsonProperty(value = "last_execute_time")
    private Long lastExecTime;

    /**
     * 增加时间。
     */
    @JsonProperty(value = "add_time")
    private Long addTime;

    /**
     * 更新时间。
     */
    @JsonProperty(value = "update_time")
    private Long updateTime;

    /**
     * 删除标志位。
     */
    @JsonProperty(value = "del_flag")
    private Integer delFlag;
}

/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.quartz.aws.service
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 19:10
 */
package com.easarrive.quartz.aws.service;

/**
 * <h1>接口 - 分词任务服务</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月29日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public interface IWordSegmenterService extends ITaskService {

    /**
     * 通过数据库词库教分词器学习。
     *
     * @param wordSegmenterURL 分词学习接口。
     * @param dbName           数据库名。
     * @param tableName        表名。
     * @param taskName         任务名。
     */
    void teachFromDatabase(String wordSegmenterURL, String dbName, String tableName, String taskName);
}

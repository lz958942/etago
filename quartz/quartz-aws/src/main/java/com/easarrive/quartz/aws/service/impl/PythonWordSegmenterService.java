/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.quartz.aws.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 19:11
 */
package com.easarrive.quartz.aws.service.impl;

import com.easarrive.quartz.aws.service.IWordSegmenterService;
import net.lizhaoweb.common.util.base.Constant;
import net.lizhaoweb.common.util.base.HttpClientUtil;
import net.lizhaoweb.common.util.base.StringUtil;
import net.lizhaoweb.datasource.mysql.etago.mapper.read.ICronTaskReadMapper;
import net.lizhaoweb.datasource.mysql.etago.mapper.read.IGoodsTagsReadMapper;
import net.lizhaoweb.datasource.mysql.etago.mapper.write.ICronTaskWriteMapper;
import net.lizhaoweb.datasource.mysql.etago.model.CronTask;
import net.lizhaoweb.datasource.mysql.etago.model.GoodsTags;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月29日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class PythonWordSegmenterService implements IWordSegmenterService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ICronTaskWriteMapper cronTaskWriteMapper;

    @Autowired
    private ICronTaskReadMapper cronTaskReadMapper;

    @Autowired
    private IGoodsTagsReadMapper goodsTagsReadMapper;

    public void executeTask() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void teachFromDatabase(final String wordSegmenterURL, String dbName, String tableName, String taskName) {

        //获取教学档案
        List<CronTask> cronTaskList = cronTaskReadMapper.find(dbName, tableName, taskName);
        CronTask dbCronTask = null;

        //保证数据库只有一条记录
        if (cronTaskList != null && cronTaskList.size() > 0) {
            dbCronTask = cronTaskList.get(0);
            if (cronTaskList.size() > 1) {
                for (int index = 1; index < cronTaskList.size(); index++) {
                    CronTask deleteCronTask = cronTaskList.get(index);
                    Long count = cronTaskWriteMapper.delete(deleteCronTask.getId());
                    if (logger.isDebugEnabled()) {
                        logger.debug("Python 分词库学习任务重复记录删除影响结果 ： {}", count);
                    }
                }
            }
        }

        //获取更新时间
        Long tableUpdateTime = null;
        if (dbCronTask != null) {
            tableUpdateTime = dbCronTask.getTableUpdateTime();
            dbCronTask.setLastExecTime(dbCronTask.getExecTime());
            dbCronTask.setExecTime(System.currentTimeMillis());
            dbCronTask.setUpdateTime(System.currentTimeMillis());
        }
        if (tableUpdateTime == null) {
            tableUpdateTime = -1L;
        }

        //准备教案
        List<GoodsTags> goodsTagsList = goodsTagsReadMapper.getAll(tableUpdateTime);
        if (goodsTagsList == null || goodsTagsList.size() < 1) {
            if (logger.isInfoEnabled()) {
                logger.info("Python 分词库学习无教学内容");
            }
            return;
        }

        //开始教学
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<Map<Integer, Boolean>>> futureList = new ArrayList<Future<Map<Integer, Boolean>>>();
        for (final GoodsTags tags : goodsTagsList) {
            if (tags == null) {
                continue;
            }
            Future<Map<Integer, Boolean>> future = executorService.submit(new Callable<Map<Integer, Boolean>>() {
                @Override
                public Map<Integer, Boolean> call() throws Exception {
                    Map<Integer, Boolean> result = new HashMap<Integer, Boolean>();
                    result.put(tags.getId(), null);
                    if (StringUtil.isEmpty(tags.getTag())) {
                        return result;
                    }
                    try {
                        String wordSegmenterURLFinal = String.format("%s%s", wordSegmenterURL, URLEncoder.encode(tags.getTag(), Constant.Charset.UTF8));
                        HttpResponse httpResponse = HttpClientUtil.get(wordSegmenterURLFinal, null);
                        int statusCode = httpResponse.getStatusLine().getStatusCode();
                        result.put(tags.getId(), statusCode == 200);
                    } catch (Exception e) {
                        result.put(tags.getId(), false);
                    }
                    return result;
                }
            });
            futureList.add(future);
            if (tableUpdateTime < tags.getAddTime()) {
                tableUpdateTime = tags.getAddTime();
            }
        }

        //查看教学质量
        for (Future<Map<Integer, Boolean>> future : futureList) {
            try {
                Map<Integer, Boolean> result = future.get();
                if (logger.isInfoEnabled()) {
                    logger.info("Python 分词库学习质量 ： {}", result);
                }
            } catch (InterruptedException e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
            } catch (ExecutionException e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

        //更新教学档案
        if (dbCronTask == null) {
            dbCronTask = new CronTask(0L, dbName, tableName, tableUpdateTime, taskName, System.currentTimeMillis(), System.currentTimeMillis(), System.currentTimeMillis(), System.currentTimeMillis(), 0);
            Long id = cronTaskWriteMapper.insert(dbCronTask);
            if (logger.isDebugEnabled()) {
                logger.debug("新增 Python 分词库学习任务记录标识 ： {}", id);
            }
        } else {
            Long count = cronTaskWriteMapper.update(dbCronTask);
            if (logger.isDebugEnabled()) {
                logger.debug("更新 Python 分词库学习任务记录影响结果 ： {}", count);
            }
        }
    }
}

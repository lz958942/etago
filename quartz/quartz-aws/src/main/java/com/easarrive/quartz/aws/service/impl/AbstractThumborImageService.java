/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.quartz.aws.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 14:03
 */
package com.easarrive.quartz.aws.service.impl;

import com.easarrive.datasource.redis.etago.read.IThumborReadDao;
import com.easarrive.datasource.redis.etago.write.IThumborWriteDao;
import com.easarrive.image.thumbor.IGeneratePicture;
import com.easarrive.image.thumbor.bean.ThumborRequest;
import com.easarrive.image.thumbor.exception.ThumborException;
import com.easarrive.quartz.aws.bean.ThumborSeparateCallResult;
import com.easarrive.quartz.aws.service.IImageTaskService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年08月05日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public abstract class AbstractThumborImageService<K, V> implements IImageTaskService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 图片生成器
     */
    @Setter
    private IGeneratePicture generatePicture;

    /**
     * Redis 写入
     */
    @Setter
    private IThumborWriteDao<K, V> thumborImageWriteDao;

    /**
     * Redis 读取
     */
    @Setter
    private IThumborReadDao<K, V> thumborImageReadDao;

    /**
     * 桶名
     */
    @Setter
    @Getter
    private String bucketName;

    /**
     * 最大处理线程数量
     */
    @Setter
    private int maxNumberOfProcessingThreads;

    //源路径格式
    private String sourceFormat;

    //目标路径格式
    private String targetFormat;

    public AbstractThumborImageService(String sourceFormat, String targetFormat) {
        this.sourceFormat = sourceFormat;
        this.targetFormat = targetFormat;
        maxNumberOfProcessingThreads = 10;
    }

    @Override
    public void executeTask() {
        try {
            Set<K> redisKeys = thumborImageReadDao.allKeys();
            if (redisKeys == null) {
                throw new ThumborException("The redis keys is null");
            }
            if (redisKeys.isEmpty()) {
                if (logger.isInfoEnabled()) {
                    logger.info("The redis keys is empty");
                }
                return;
            }
            List<K> redisKeyList = new ArrayList<K>(redisKeys);
            for (int index = 0; index < redisKeyList.size(); index += maxNumberOfProcessingThreads) {
                //启用线程
                ExecutorService executorService = Executors.newCachedThreadPool();
                List<Future<ThumborSeparateCallResult<K>>> resultList = new ArrayList<Future<ThumborSeparateCallResult<K>>>();
                for (int thumborIndex = index; thumborIndex < index + maxNumberOfProcessingThreads && thumborIndex < redisKeyList.size(); thumborIndex++) {
                    try {
                        K redisKey = redisKeyList.get(thumborIndex);
                        if (redisKey == null) {
                            throw new ThumborException("The redis key is null");
                        }
                        V redisValue = thumborImageReadDao.get(redisKey);
                        if (redisValue == null) {
                            throw new ThumborException("The redis value is null for key (%s)", redisKey);
                        }

                        Future<ThumborSeparateCallResult<K>> future = executorService.submit(new ThumborSeparateCallable(
                                generatePicture,
                                thumborImageWriteDao,
                                bucketName,
                                targetFormat,
                                redisKey,
                                this.getThumborRequest(redisKey, redisValue),
                                this.getSeparateId(redisKey, redisValue)
                        ));
                        resultList.add(future);
                    } catch (Exception e) {
                        if (logger.isErrorEnabled()) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }

                //遍历任务的结果
                for (Future<ThumborSeparateCallResult<K>> fs : resultList) {
                    try {
                        ThumborSeparateCallResult<K> callResult = fs.get();
                        if (logger.isDebugEnabled()) {
                            logger.debug("\n\t|=======- The SQS message request result to handle is {}\n", callResult);
                        }
                    } catch (Exception e) {
                        if (logger.isErrorEnabled()) {
                            logger.error(e.getMessage(), e);
                        }
                    } finally {
                        //启动一次顺序关闭，执行以前提交的任务，但不接受新任务。如果已经关闭，则调用没有其他作用。
                        executorService.shutdown();
                    }
                }
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
    }

//    private boolean checkAndDeleteRedis(K redisKey) {
//        try {
//            String targetAccessUrl = String.format(targetFormat, bucketName, redisKey);
//            HttpResponse httpResponse = HttpClientUtil.head(targetAccessUrl, null);
//            if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                thumborImageWriteDao.delete(redisKey);
//                return true;
//            }
//        } catch (Exception e) {
//            if (logger.isErrorEnabled()) {
//                logger.error(e.getMessage(), e);
//            }
//        }
//        return false;
//    }

    abstract ThumborRequest getThumborRequest(K redisKey, V redisValue) throws ThumborException;

    abstract String getSeparateId(K redisKey, V redisValue);
}

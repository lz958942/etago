/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.quartz.aws.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 18:46
 */
package com.easarrive.quartz.aws.service.impl;

import com.easarrive.datasource.redis.etago.write.IThumborWriteDao;
import com.easarrive.image.thumbor.IGeneratePicture;
import com.easarrive.image.thumbor.bean.ThumborRequest;
import com.easarrive.quartz.aws.bean.ThumborSeparateCallResult;
import lombok.Getter;
import lombok.Setter;
import net.lizhaoweb.common.util.base.HttpClientUtil;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年08月06日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class ThumborSeparateCallable<K, V> implements Callable<ThumborSeparateCallResult<K>> {

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
     * 桶名
     */
    @Setter
    @Getter
    private String bucketName;

    //目标路径格式
    private String targetFormat;

    private K redisKey;

    private ThumborRequest thumborRequest;

    private String separateId;

    public ThumborSeparateCallable(
            IGeneratePicture generatePicture,
            IThumborWriteDao<K, V> thumborImageWriteDao,
            String bucketName,
            String targetFormat,
            K redisKey,
            ThumborRequest thumborRequest,
            String separateId
    ) {
        this.generatePicture = generatePicture;
        this.thumborImageWriteDao = thumborImageWriteDao;
        this.bucketName = bucketName;
        this.targetFormat = targetFormat;
        this.redisKey = redisKey;
        this.thumborRequest = thumborRequest;
        this.separateId = separateId;
    }

    @Override
    public ThumborSeparateCallResult<K> call() throws Exception {
        try {
            if (this.checkAndDeleteRedis(redisKey)) {
                return new ThumborSeparateCallResult<K>(redisKey, true);
            }
            boolean success = generatePicture.generate(thumborRequest, separateId);
            if (success) {
                return new ThumborSeparateCallResult<K>(redisKey, thumborImageWriteDao.delete(redisKey) > 0);
            }
            return new ThumborSeparateCallResult<K>(redisKey, success);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
        return new ThumborSeparateCallResult<K>(redisKey, false);
    }

    private boolean checkAndDeleteRedis(K redisKey) {
        try {
            String targetAccessUrl = String.format(targetFormat, bucketName, redisKey);
            HttpResponse httpResponse = HttpClientUtil.head(targetAccessUrl, null);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return thumborImageWriteDao.delete(redisKey) > 0;
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
        return false;
    }
}

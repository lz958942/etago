/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor Image Thumbor
 * @Title : ConfigureLoader.java
 * @Package : com.easarrive.image.thumbor.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月21日
 * @Time : 下午12:45:51
 */
package com.easarrive.image.thumbor.impl;

import com.easarrive.datasource.redis.etago.read.IThumborReadDao;
import com.easarrive.datasource.redis.etago.util.Constant.Thumbor.System.Config;
import com.easarrive.image.thumbor.IConfigureLoader;
import com.easarrive.image.thumbor.bean.ThumborConfigure;
import com.easarrive.image.thumbor.bean.ThumborContext;
import com.easarrive.image.thumbor.exception.ThumborException;
import com.easarrive.image.thumbor.util.Constant.Key;
import lombok.Setter;
import net.lizhaoweb.common.util.base.JsonUtil;
import net.lizhaoweb.common.util.base.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年6月21日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class ConfigureLoader implements IConfigureLoader {

    private Logger logger = LoggerFactory.getLogger(ConfigureLoader.class);

    @Setter
    private Properties configProperties;

    @Setter
    private IThumborReadDao thumborConfigureReadDao;

    //图片配置对象
    private ThumborConfigure configure;

    @Override
    public void load() {
        try {
            String imageThumborConfigAWSS3Json = this.getJson(thumborConfigureReadDao, configProperties);
            configure = this.analysisJson2ThumborConfigure(imageThumborConfigAWSS3Json);
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Load config fail from redis", e);
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Be Loaded configure : {}", configure);
        }
    }

    @Override
    public ThumborConfigure getConfigure() {
        if (logger.isDebugEnabled()) {
            logger.debug("Get configure : {}", configure);
        }
        return configure;
    }

    //加载 JSON 字符串
    private String getJson(IThumborReadDao thumborConfigureReadDao, Properties configProperties) {
        //从 Redis 加载 JSON
        String imageThumborConfigAWSS3Json = null;
        try {
            imageThumborConfigAWSS3Json = thumborConfigureReadDao.getJson(Config.FEILD_JSON);
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Load config fail from redis", e);
            }
            imageThumborConfigAWSS3Json = null;
        }

        //从属性文件加载 JSON
        if (StringUtil.isEmpty(imageThumborConfigAWSS3Json)) {
            try {
                imageThumborConfigAWSS3Json = configProperties.getProperty(Key.IMAGE_THUMBOR_CONFIG_AWS_S3);
            } catch (Exception e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Load config fail from property", e);
                }
                imageThumborConfigAWSS3Json = null;
            }
        }
        return imageThumborConfigAWSS3Json;
    }

    //解析 JSON 为 ThumborConfigure 对象
    private ThumborConfigure analysisJson2ThumborConfigure(String json) throws ThumborException {
        ThumborConfigure configure = null;
        if (StringUtil.isEmpty(json)) {
            configure = new ThumborConfigure();
        } else {
            try {
                configure = JsonUtil.toBean(json, ThumborConfigure.class);
            } catch (Exception e) {
                throw new ThumborException(e.getMessage(), e);
            }
        }
        return configure;
    }
}

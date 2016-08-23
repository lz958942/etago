/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.quartz.aws.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 19:24
 */
package com.easarrive.quartz.aws.service.impl;

import com.easarrive.image.thumbor.bean.ThumborRequest;
import com.easarrive.image.thumbor.exception.ThumborException;
import com.easarrive.quartz.aws.util.Constant.SQS.S3.URL.Access.Users;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年08月03日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class ThumborImageUsersService extends AbstractThumborImageService<String, String> {

    public ThumborImageUsersService() {
        super(Users.SOURCE_FORMAT, Users.TARGET_FORMAT);
    }

    @Override
    protected ThumborRequest getThumborRequest(String redisKey, String redisValue) throws ThumborException {
        String key = redisValue.substring(1);
        String accessUrl = String.format(Users.SOURCE_FORMAT, this.getBucketName(), key);
        String kindId = key.substring(0, key.lastIndexOf('/'));
        ThumborRequest thumborRequest = new ThumborRequest(redisKey, accessUrl, kindId);
        return thumborRequest;
    }

    @Override
    String getSeparateId(String redisKey, String redisValue) {
        Pattern pattern = Pattern.compile("^[^_]+_(\\d+x\\d+)\\.\\w+$");
        Matcher matcher = pattern.matcher(redisKey);
        String separateId = "";
        if (matcher.find()) {
            separateId = matcher.group(1);
        }
        return separateId;
    }
}

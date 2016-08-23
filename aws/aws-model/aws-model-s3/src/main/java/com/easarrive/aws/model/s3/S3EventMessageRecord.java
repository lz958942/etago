/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : S3EventMessageRecord.java
 * @Package : net.lizhaoweb.aws.plugin.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月1日
 * @Time : 下午2:31:32
 */
package com.easarrive.aws.model.s3;

import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月1日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@Data
public class S3EventMessageRecord implements Serializable {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = -4060091327370095423L;

    /**
     * 事件版本
     */
    private String eventVersion;

    /**
     * 事件来源
     */
    private String eventSource;

    /**
     * 注册区域
     */
    private String awsRegion;

    /**
     * 事件时间
     */
    private String eventTime;

    /**
     * 事件名称
     */
    private String eventName;

    /**
     * 用户标识
     */
    private S3EventMessageUserIdentity userIdentity;

    /**
     * 请求参数
     */
    private S3EventMessageRequestParameters requestParameters;

    /**
     * 响应实体
     */
    private S3EventMessageResponseElements responseElements;

    /**
     * S3
     */
    private S3EventMessageS3 s3;
}

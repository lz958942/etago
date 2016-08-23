/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : S3EventMessageContent.java
 * @Package : net.lizhaoweb.aws.plugin.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月1日
 * @Time : 下午2:29:33
 */
package com.easarrive.aws.model.s3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年7月1日<br>
 *        Revision of last commit:$Revision$<br>
 *        Author of last commit:$Author$<br>
 *        Date of last commit:$Date$<br>
 *
 */
@Data
public class S3EventMessageContent implements Serializable {

    /** 序列化标识 */
    private static final long serialVersionUID = 65559305908959212L;

    /** 服务 */
    @JsonProperty(value = "Service")
    private String service;

    /** 事件 */
    @JsonProperty(value = "Event")
    private String event;

    /** 时间 */
    @JsonProperty(value = "Time")
    private String time;

    /** 桶 */
    @JsonProperty(value = "Bucket")
    private String bucket;

    /** 请求标识 */
    @JsonProperty(value = "RequestId")
    private String requestId;

    /** 主机标识 */
    @JsonProperty(value = "HostId")
    private String hostId;

    /** 消息记录列表 */
    @JsonProperty(value = "Records")
    private List<S3EventMessageRecord> records;
}

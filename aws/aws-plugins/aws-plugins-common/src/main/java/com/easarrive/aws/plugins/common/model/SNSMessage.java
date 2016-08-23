/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : SNSMessage.java
 * @Package : net.lizhaoweb.aws.plugin.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月28日
 * @Time : 下午9:15:51
 */
package com.easarrive.aws.plugins.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version
 * @notes Created on 2016年6月28日<br>
 *        Revision of last commit:$Revision$<br>
 *        Author of last commit:$Author$<br>
 *        Date of last commit:$Date$<br>
 *        <p />
 *
 */
@Data
public class SNSMessage implements Serializable {

    /** 序列化标识 */
    private static final long serialVersionUID = 4203353959586063435L;

    /** 消息ID */
    @JsonProperty(value = "MessageId")
    private String messageId;

    /** 签名版本 */
    @JsonProperty(value = "SignatureVersion")
    private String signatureVersion;

    /** 主题资源 */
    @JsonProperty(value = "TopicArn")
    private String topicArn;

    /** 主题 */
    @JsonProperty(value = "Subject")
    private String subject;

    /** 消息 */
    @JsonProperty(value = "Message")
    private String message;

    /** 订阅URL地址 */
    @JsonProperty(value = "SubscribeURL")
    private String subscribeURL;

    /** 退订URL地址 */
    @JsonProperty(value = "UnsubscribeURL")
    private String unsubscribeURL;

    /** 签名证书URL地址 */
    @JsonProperty(value = "SigningCertURL")
    private String signingCertURL;

    /** 签名 */
    @JsonProperty(value = "Signature")
    private String signature;

    /** 消息类型 */
    @JsonProperty(value = "Type")
    private String type;

    /** 时间截 */
    @JsonProperty(value = "Timestamp")
    private String timestamp;

    /** 令牌 */
    @JsonProperty(value = "Token")
    private String token;
}

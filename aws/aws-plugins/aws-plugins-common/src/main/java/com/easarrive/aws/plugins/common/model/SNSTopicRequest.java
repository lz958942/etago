/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : SNSTopicRequest.java
 * @Package : net.lizhaoweb.aws.plugin.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月28日
 * @Time : 下午9:21:36
 */
package com.easarrive.aws.plugins.common.model;

import com.amazonaws.regions.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年6月28日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SNSTopicRequest extends SNSRequest {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = -5139175714144037699L;

    /**
     * 消息队列名称
     */
    private String snsName;

    /**
     * 属性名
     */
    private String attributeName;

    /**
     * 属性值
     */
    private String attributeValue;

    /**
     * 主题资源
     */
    private String topicArn;

    public SNSTopicRequest(Region region, String snsName, String attributeName, String attributeValue) {
        super(region);
        this.setSnsName(snsName);
        this.setAttributeName(attributeName);
        this.setAttributeValue(attributeValue);
    }

    public SNSTopicRequest(String snsName, String attributeName, String attributeValue) {
        super();
        this.setSnsName(snsName);
        this.setAttributeName(attributeName);
        this.setAttributeValue(attributeValue);
    }

    public SNSTopicRequest(Region region, String topicArn) {
        super(region);
        this.setTopicArn(topicArn);
    }

    public SNSTopicRequest(String topicArn) {
        super();
        this.setTopicArn(topicArn);
    }

    public SNSTopicRequest(String accessKey, String secretKey, Region region, String snsName, String attributeName, String attributeValue) {
        super(accessKey, secretKey, region);
        this.setSnsName(snsName);
        this.setAttributeName(attributeName);
        this.setAttributeValue(attributeValue);
    }

    public SNSTopicRequest(String accessKey, String secretKey, Region region, String topicArn) {
        super(accessKey, secretKey, region);
        this.setTopicArn(topicArn);
    }
}

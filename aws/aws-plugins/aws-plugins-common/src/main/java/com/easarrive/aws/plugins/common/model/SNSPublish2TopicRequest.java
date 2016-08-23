/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : SNSPublish2TopicRequest.java
 * @Package : net.lizhaoweb.aws.plugin.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月28日
 * @Time : 下午9:20:07
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
public class SNSPublish2TopicRequest extends SNSRequest {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 7867195293250033171L;

    /**
     * 主题资源
     */
    private String topicArn;

    /**
     * 消息
     */
    private String message;

    public SNSPublish2TopicRequest(Region region, String topicArn, String message) {
        super(region);
        this.setTopicArn(topicArn);
        this.setMessage(message);
    }

    public SNSPublish2TopicRequest(String accessKey, String secretKey, Region region, String topicArn, String message) {
        super(accessKey, secretKey, region);
        this.setTopicArn(topicArn);
        this.setMessage(message);
    }
}

/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : ISNSService.java
 * @Package : net.lizhaoweb.aws.plugin.service
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月28日
 * @Time : 下午9:25:39
 */
package com.easarrive.aws.plugins.common.service;

import com.amazonaws.regions.Region;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.*;
import com.easarrive.aws.plugins.common.model.SNSPlatformEndpointRequest;
import com.easarrive.aws.plugins.common.model.SNSPublish2TopicRequest;
import com.easarrive.aws.plugins.common.model.SNSSubscribingTopicRequest;
import com.easarrive.aws.plugins.common.model.SNSTopicRequest;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年6月28日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public interface ISNSService {

    /**
     * 创建主题。
     *
     * @param topicRequest
     * @return
     */
    CreateTopicResult createTopic(AmazonSNS client, SNSTopicRequest topicRequest);

    /**
     * 订阅主题。
     *
     * @param subscribingTopicRequest
     * @return
     */
    SubscribeResult subscribingTopic(AmazonSNS client, SNSSubscribingTopicRequest subscribingTopicRequest);

    /**
     * 向主题发布消息。
     *
     * @param publish2TopicRequest
     * @return
     */
    PublishResult publish2Topic(AmazonSNS client, SNSPublish2TopicRequest publish2TopicRequest);

    /**
     * 删除主题。
     *
     * @param topicRequest
     * @return
     */
    DeleteTopicResult deleteTopic(AmazonSNS client, SNSTopicRequest topicRequest);

    CreatePlatformEndpointResult createPlatformEndpoint(AmazonSNS client, SNSPlatformEndpointRequest platformEndpointRequest);

    AmazonSNS getAmazonSNSClient(Region region);
}

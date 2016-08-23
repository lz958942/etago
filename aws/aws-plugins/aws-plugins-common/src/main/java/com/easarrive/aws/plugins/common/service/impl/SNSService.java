/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : SNSService.java
 * @Package : net.lizhaoweb.aws.plugin.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月28日
 * @Time : 下午9:26:27
 */
package com.easarrive.aws.plugins.common.service.impl;

import com.amazonaws.regions.Region;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.*;
import com.easarrive.aws.plugins.common.model.SNSPlatformEndpointRequest;
import com.easarrive.aws.plugins.common.model.SNSPublish2TopicRequest;
import com.easarrive.aws.plugins.common.model.SNSSubscribingTopicRequest;
import com.easarrive.aws.plugins.common.model.SNSTopicRequest;
import com.easarrive.aws.plugins.common.service.ISNSService;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年6月28日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class SNSService implements ISNSService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Setter
    private AmazonSNS amazonSNS;

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateTopicResult createTopic(AmazonSNS client, SNSTopicRequest topicRequest) {
        if (topicRequest == null) {
            return null;
        }
        // create a new SNS topic
        CreateTopicRequest createTopicRequest = new CreateTopicRequest(topicRequest.getSnsName());
        CreateTopicResult result = client.createTopic(createTopicRequest);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubscribeResult subscribingTopic(AmazonSNS client, SNSSubscribingTopicRequest subscribingTopicRequest) {
        if (subscribingTopicRequest == null) {
            return null;
        }
        SubscribeRequest subscribeRequest = new SubscribeRequest(subscribingTopicRequest.getTopicArn(), subscribingTopicRequest.getProtocol(), subscribingTopicRequest.getEndpoint());
        SubscribeResult result = client.subscribe(subscribeRequest);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PublishResult publish2Topic(AmazonSNS client, SNSPublish2TopicRequest publish2TopicRequest) {
        if (publish2TopicRequest == null) {
            return null;
        }
        // publish to an SNS topic
        PublishRequest publishRequest = new PublishRequest(publish2TopicRequest.getTopicArn(), publish2TopicRequest.getMessage());
        PublishResult result = client.publish(publishRequest);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeleteTopicResult deleteTopic(AmazonSNS client, SNSTopicRequest topicRequest) {
        if (topicRequest == null) {
            return null;
        }
        // delete an SNS topic
        DeleteTopicRequest deleteTopicRequest = new DeleteTopicRequest(topicRequest.getTopicArn());
        DeleteTopicResult result = client.deleteTopic(deleteTopicRequest);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CreatePlatformEndpointResult createPlatformEndpoint(AmazonSNS client, SNSPlatformEndpointRequest platformEndpointRequest) {
        if (platformEndpointRequest == null) {
            return null;
        }
        CreatePlatformEndpointRequest createPlatformEndpointRequest = new CreatePlatformEndpointRequest();
        createPlatformEndpointRequest.setCustomUserData(platformEndpointRequest.getCustomUserData());
        createPlatformEndpointRequest.setPlatformApplicationArn(platformEndpointRequest.getPlatformApplicationArn());
        createPlatformEndpointRequest.setToken(platformEndpointRequest.getToken());
        createPlatformEndpointRequest.withAttributes(platformEndpointRequest.getAttributes());

        CreatePlatformEndpointResult result = client.createPlatformEndpoint(createPlatformEndpointRequest);
        return result;
    }

    // 获取亚马逊SNS客户端
    @Override
    public AmazonSNS getAmazonSNSClient(Region region) {
        // if (StringUtil.isEmpty(credentialsPath)) {
        // credentialsPath = "config/AwsCredentials.properties";
        // }
        // AWSCredentialsProvider provider = new
        // ClasspathPropertiesFileCredentialsProvider(credentialsPath);
        // AmazonSNSClient amazonSNSClient = new AmazonSNSClient(provider);

        // AWSCredentials credentials = new
        // BasicAWSCredentials(topicRequest.getAccessKey(),
        // topicRequest.getSecretKey());
        // AmazonSNSClient amazonSNSClient = new AmazonSNSClient(credentials);

        amazonSNS.setRegion(region);
        return amazonSNS;
    }
}

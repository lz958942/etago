/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : ISQSService.java
 * @Package : net.lizhaoweb.aws.plugin.service
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月1日
 * @Time : 上午11:58:48
 */
package com.easarrive.aws.plugins.common.service;

import com.amazonaws.regions.Region;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;

import java.util.Map;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月1日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public interface ISQSService {

    ReceiveMessageResult receiveMessage(AmazonSQS client, String queueUrl, int maxNumberOfMessages);

    ReceiveMessageResult receiveMessage(AmazonSQS client, String queueUrl, int maxNumberOfMessages, int visibilityTimeout);

    DeleteMessageResult deleteMessage(AmazonSQS client, String queueUrl, Message message);

    Map<String, String> getQueueAllAttributes(AmazonSQS client, String queueUrl);

    Long getQueueDelaySeconds(AmazonSQS client, String queueUrl);

    Long getQueueMessageRetentionPeriod(AmazonSQS client, String queueUrl);

    Long getQueueMaximumMessageSize(AmazonSQS client, String queueUrl);

    Long getQueueReceiveMessageWaitTimeSeconds(AmazonSQS client, String queueUrl);

    Long getQueueLastModifiedTimestamp(AmazonSQS client, String queueUrl);

    Long getQueueCreatedTimestamp(AmazonSQS client, String queueUrl);

    Long getQueueVisibilityTimeout(AmazonSQS client, String queueUrl);

    Long getQueueApproximateNumberOfMessages(AmazonSQS client, String queueUrl);

    Long getQueueApproximateNumberOfMessagesNotVisible(AmazonSQS client, String queueUrl);

    String getQueueQueueArn(AmazonSQS client, String queueUrl);

    Long getQueueApproximateNumberOfMessagesDelayed(AmazonSQS client, String queueUrl);

    AmazonSQS getAmazonSQSClient(Region region);
}

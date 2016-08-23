/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : SQSService.java
 * @Package : net.lizhaoweb.aws.plugin.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月1日
 * @Time : 上午11:59:03
 */
package com.easarrive.aws.plugins.common.service.impl;

import com.amazonaws.regions.Region;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import com.easarrive.aws.plugins.common.service.ISQSService;
import com.easarrive.aws.plugins.common.util.Constant;
import lombok.Setter;
import net.lizhaoweb.common.util.base.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月1日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class SQSService implements ISQSService {

    protected Logger logger = LoggerFactory.getLogger(SQSService.class);

    @Setter
    private AmazonSQS amazonSQS;

//    @Setter
//    private AmazonSQSAsync amazonSQSAsync;

    /**
     * {@inheritDoc}
     */
    @Override
    public ReceiveMessageResult receiveMessage(AmazonSQS client, String queueUrl, int maxNumberOfMessages) {
        return this.receiveMessage(client, queueUrl, maxNumberOfMessages, 30);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReceiveMessageResult receiveMessage(AmazonSQS client, String queueUrl, int maxNumberOfMessages, int visibilityTimeout) {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl).withMaxNumberOfMessages(maxNumberOfMessages).withVisibilityTimeout(visibilityTimeout);
        ReceiveMessageResult result = client.receiveMessage(receiveMessageRequest);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeleteMessageResult deleteMessage(AmazonSQS client, String queueUrl, Message message) {
        DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest(queueUrl, message.getReceiptHandle());
//        deleteMessageRequest.withQueueUrl(queueUrl).withReceiptHandle(message.getReceiptHandle());
        DeleteMessageResult result = client.deleteMessage(deleteMessageRequest);
        return result;
    }

    @Override
    public Map<String, String> getQueueAllAttributes(AmazonSQS client, String queueUrl) {
        GetQueueAttributesRequest getQueueAttributesRequest = new GetQueueAttributesRequest(queueUrl);
        getQueueAttributesRequest.withAttributeNames(Constant.SQS.Attribute.ALL);
        GetQueueAttributesResult result = client.getQueueAttributes(getQueueAttributesRequest);
        return result.getAttributes();
    }

    @Override
    public Long getQueueDelaySeconds(AmazonSQS client, String queueUrl) {
        GetQueueAttributesRequest getQueueAttributesRequest = new GetQueueAttributesRequest(queueUrl);
        getQueueAttributesRequest.withAttributeNames(Constant.SQS.Attribute.DELAY_SECONDS);
        GetQueueAttributesResult result = client.getQueueAttributes(getQueueAttributesRequest);
        if (result == null) {
            return null;
        }
        Map<String, String> attributeMap = result.getAttributes();
        if (attributeMap == null) {
            return null;
        }
        String value = attributeMap.get(Constant.SQS.Attribute.DELAY_SECONDS);
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        if (!value.matches("^\\d+$")) {
            return null;
        }
        return Long.valueOf(value);
    }

    @Override
    public Long getQueueMessageRetentionPeriod(AmazonSQS client, String queueUrl) {
        GetQueueAttributesRequest getQueueAttributesRequest = new GetQueueAttributesRequest(queueUrl);
        getQueueAttributesRequest.withAttributeNames(Constant.SQS.Attribute.MESSAGE_RETENTION_PERIOD);
        GetQueueAttributesResult result = client.getQueueAttributes(getQueueAttributesRequest);
        if (result == null) {
            return null;
        }
        Map<String, String> attributeMap = result.getAttributes();
        if (attributeMap == null) {
            return null;
        }
        String value = attributeMap.get(Constant.SQS.Attribute.MESSAGE_RETENTION_PERIOD);
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        if (!value.matches("^\\d+$")) {
            return null;
        }
        return Long.valueOf(value);
    }

    @Override
    public Long getQueueMaximumMessageSize(AmazonSQS client, String queueUrl) {
        GetQueueAttributesRequest getQueueAttributesRequest = new GetQueueAttributesRequest(queueUrl);
        getQueueAttributesRequest.withAttributeNames(Constant.SQS.Attribute.MAXIMUM_MESSAGE_SIZE);
        GetQueueAttributesResult result = client.getQueueAttributes(getQueueAttributesRequest);
        if (result == null) {
            return null;
        }
        Map<String, String> attributeMap = result.getAttributes();
        if (attributeMap == null) {
            return null;
        }
        String value = attributeMap.get(Constant.SQS.Attribute.MAXIMUM_MESSAGE_SIZE);
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        if (!value.matches("^\\d+$")) {
            return null;
        }
        return Long.valueOf(value);
    }

    @Override
    public Long getQueueReceiveMessageWaitTimeSeconds(AmazonSQS client, String queueUrl) {
        GetQueueAttributesRequest getQueueAttributesRequest = new GetQueueAttributesRequest(queueUrl);
        getQueueAttributesRequest.withAttributeNames(Constant.SQS.Attribute.RECEIVE_MESSAGE_WAIT_TIME_SECONDS);
        GetQueueAttributesResult result = client.getQueueAttributes(getQueueAttributesRequest);
        if (result == null) {
            return null;
        }
        Map<String, String> attributeMap = result.getAttributes();
        if (attributeMap == null) {
            return null;
        }
        String value = attributeMap.get(Constant.SQS.Attribute.RECEIVE_MESSAGE_WAIT_TIME_SECONDS);
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        if (!value.matches("^\\d+$")) {
            return null;
        }
        return Long.valueOf(value);
    }

    @Override
    public Long getQueueLastModifiedTimestamp(AmazonSQS client, String queueUrl) {
        GetQueueAttributesRequest getQueueAttributesRequest = new GetQueueAttributesRequest(queueUrl);
        getQueueAttributesRequest.withAttributeNames(Constant.SQS.Attribute.LAST_MODIFIED_TIMESTAMP);
        GetQueueAttributesResult result = client.getQueueAttributes(getQueueAttributesRequest);
        if (result == null) {
            return null;
        }
        Map<String, String> attributeMap = result.getAttributes();
        if (attributeMap == null) {
            return null;
        }
        String value = attributeMap.get(Constant.SQS.Attribute.LAST_MODIFIED_TIMESTAMP);
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        if (!value.matches("^\\d+$")) {
            return null;
        }
        return Long.valueOf(value);
    }

    @Override
    public Long getQueueCreatedTimestamp(AmazonSQS client, String queueUrl) {
        GetQueueAttributesRequest getQueueAttributesRequest = new GetQueueAttributesRequest(queueUrl);
        getQueueAttributesRequest.withAttributeNames(Constant.SQS.Attribute.CREATED_TIMESTAMP);
        GetQueueAttributesResult result = client.getQueueAttributes(getQueueAttributesRequest);
        if (result == null) {
            return null;
        }
        Map<String, String> attributeMap = result.getAttributes();
        if (attributeMap == null) {
            return null;
        }
        String value = attributeMap.get(Constant.SQS.Attribute.CREATED_TIMESTAMP);
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        if (!value.matches("^\\d+$")) {
            return null;
        }
        return Long.valueOf(value);
    }

    @Override
    public Long getQueueVisibilityTimeout(AmazonSQS client, String queueUrl) {
        GetQueueAttributesRequest getQueueAttributesRequest = new GetQueueAttributesRequest(queueUrl);
        getQueueAttributesRequest.withAttributeNames(Constant.SQS.Attribute.VISIBILITY_TIMEOUT);
        GetQueueAttributesResult result = client.getQueueAttributes(getQueueAttributesRequest);
        if (result == null) {
            return null;
        }
        Map<String, String> attributeMap = result.getAttributes();
        if (attributeMap == null) {
            return null;
        }
        String value = attributeMap.get(Constant.SQS.Attribute.VISIBILITY_TIMEOUT);
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        if (!value.matches("^\\d+$")) {
            return null;
        }
        return Long.valueOf(value);
    }

    @Override
    public Long getQueueApproximateNumberOfMessages(AmazonSQS client, String queueUrl) {
        GetQueueAttributesRequest getQueueAttributesRequest = new GetQueueAttributesRequest(queueUrl);
        getQueueAttributesRequest.withAttributeNames(Constant.SQS.Attribute.APPROXIMATE_NUMBER_OF_MESSAGES);
        GetQueueAttributesResult result = client.getQueueAttributes(getQueueAttributesRequest);
        if (result == null) {
            return null;
        }
        Map<String, String> attributeMap = result.getAttributes();
        if (attributeMap == null) {
            return null;
        }
        String value = attributeMap.get(Constant.SQS.Attribute.APPROXIMATE_NUMBER_OF_MESSAGES);
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        if (!value.matches("^\\d+$")) {
            return null;
        }
        return Long.valueOf(value);
    }

    @Override
    public Long getQueueApproximateNumberOfMessagesNotVisible(AmazonSQS client, String queueUrl) {
        GetQueueAttributesRequest getQueueAttributesRequest = new GetQueueAttributesRequest(queueUrl);
        getQueueAttributesRequest.withAttributeNames(Constant.SQS.Attribute.APPROXIMATE_NUMBER_OF_MESSAGES_NOT_VISIBLE);
        GetQueueAttributesResult result = client.getQueueAttributes(getQueueAttributesRequest);
        if (result == null) {
            return null;
        }
        Map<String, String> attributeMap = result.getAttributes();
        if (attributeMap == null) {
            return null;
        }
        String value = attributeMap.get(Constant.SQS.Attribute.APPROXIMATE_NUMBER_OF_MESSAGES_NOT_VISIBLE);
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        if (!value.matches("^\\d+$")) {
            return null;
        }
        return Long.valueOf(value);
    }

    @Override
    public String getQueueQueueArn(AmazonSQS client, String queueUrl) {
        GetQueueAttributesRequest getQueueAttributesRequest = new GetQueueAttributesRequest(queueUrl);
        getQueueAttributesRequest.withAttributeNames(Constant.SQS.Attribute.QUEUE_ARN);
        GetQueueAttributesResult result = client.getQueueAttributes(getQueueAttributesRequest);
        if (result == null) {
            return null;
        }
        Map<String, String> attributeMap = result.getAttributes();
        if (attributeMap == null) {
            return null;
        }
        String value = attributeMap.get(Constant.SQS.Attribute.QUEUE_ARN);
        return value;
    }

    @Override
    public Long getQueueApproximateNumberOfMessagesDelayed(AmazonSQS client, String queueUrl) {
        GetQueueAttributesRequest getQueueAttributesRequest = new GetQueueAttributesRequest(queueUrl);
        getQueueAttributesRequest.withAttributeNames(Constant.SQS.Attribute.APPROXIMATE_NUMBER_OF_MESSAGES_DELAYED);
        GetQueueAttributesResult result = client.getQueueAttributes(getQueueAttributesRequest);
        if (result == null) {
            return null;
        }
        Map<String, String> attributeMap = result.getAttributes();
        if (attributeMap == null) {
            return null;
        }
        String value = attributeMap.get(Constant.SQS.Attribute.APPROXIMATE_NUMBER_OF_MESSAGES_DELAYED);
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        if (!value.matches("^\\d+$")) {
            return null;
        }
        return Long.valueOf(value);
    }

    @Override
    public AmazonSQS getAmazonSQSClient(Region region) {
        amazonSQS.setRegion(region);
        return amazonSQS;
    }
}

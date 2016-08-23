/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : TestSQS.java
 * @Package : net.lizhaoweb.aws.plugin.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月30日
 * @Time : 下午7:40:53
 */
package com.easarrive.aws.plugins.common.service.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.amazonaws.services.sqs.model.*;
import com.easarrive.aws.plugins.common.exception.AWSPluginException;
import com.easarrive.aws.plugins.common.service.ISQSNotificationService;
import com.easarrive.aws.plugins.common.util.Constant;
import lombok.Setter;
import net.lizhaoweb.common.util.base.StringUtil;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年6月30日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class TestSQS {

    private static final String QUEUE_ATTRIBUTE_APPROXIMATE_NUMBER_OF_MESSAGES = "ApproximateNumberOfMessages";

    @Setter
    private ISQSNotificationService sqsNotificationService;

    @Test
    public void testSQS() {
        String queueName = "image-source";
        int maxNumberOfMessages = 10;
        int maxThreadCountRequestSQS = 500;
        AWSCredentials credentials = new BasicAWSCredentials("AKIAIDPJMKK4UHLE3OVA", "A+cn+TT3tUs6xbto5k1IKkWwPLBq995aOkqKxZNY");
        AmazonSQS amazonSQS = new AmazonSQSAsyncClient(credentials);

        Region region = Region.getRegion(Regions.US_WEST_2);
        amazonSQS.setRegion(region);

        Long visibilityMessageCount = this.getQueueApproximateNumberOfMessages(amazonSQS, queueName);
        if (visibilityMessageCount == null) {
            return;
        }
        int threadCountRequestSQS = 1;
        int remainder = (int) (visibilityMessageCount % maxNumberOfMessages);
        int multiple = (int) (visibilityMessageCount / maxNumberOfMessages);
        if (multiple < 1) {//位数小于1
            threadCountRequestSQS = 1;
        } else if (multiple >= maxThreadCountRequestSQS) {//倍数大于等于最大线程数
            threadCountRequestSQS = maxThreadCountRequestSQS;
        } else if (remainder > 0) {//位数大于0且小于最大线程数，并且余数大于0
            threadCountRequestSQS = multiple + 1;
        } else {//位数大于0且小于最大线程数，并且余数等于0
            threadCountRequestSQS = multiple;
        }

//        ExecutorService executorService = Executors.newCachedThreadPool();
//        for (INotificationHandler notificationHandler : notificationHandlerList) {
//            executorService.execute(new NotificationHandlerThread(notificationHandler, message));
//        }
//        executorService.shutdown();
        for (int index = 0; index < threadCountRequestSQS; index++) {
            ReceiveMessageResult result = this.receiveMessage(amazonSQS, queueName, maxNumberOfMessages, 60);
            if (result == null) {
                continue;
            }
            List<Message> messageList = result.getMessages();
            if (messageList == null || messageList.isEmpty()) {
                continue;
            }
            for (Message message : messageList) {
                if (!sqsNotificationService.isSignatureMessageValid(message)) {
                    continue;
                }
                try {
                    sqsNotificationService.handleNotification(message);
                } catch (AWSPluginException e) {
                    e.printStackTrace();
                }
                System.out.println(message);
            }
        }
    }

    private Long getQueueApproximateNumberOfMessages(AmazonSQS client, String queueUrl) {
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

    private ReceiveMessageResult receiveMessage(AmazonSQS client, String queueUrl, int maxNumberOfMessages, int visibilityTimeout) {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl).withMaxNumberOfMessages(maxNumberOfMessages).withVisibilityTimeout(visibilityTimeout);
        ReceiveMessageResult result = client.receiveMessage(receiveMessageRequest);
        return result;
    }
}

/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.image.thumbor.executer.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 12:37
 */
package com.easarrive.image.thumbor.executer.impl;

import com.amazonaws.regions.Region;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.easarrive.aws.plugins.common.model.NotificationHandleResult;
import com.easarrive.aws.plugins.common.service.ISQSNotificationService;
import com.easarrive.aws.plugins.common.service.ISQSService;
import com.easarrive.image.thumbor.exception.ThumborException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年08月02日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
class RequestSQSCallable implements Callable<Boolean> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Region region;
    private String queueUrl;
    private Integer maxNumberOfMessages;
    private Integer visibilityTimeout;

    private ISQSService sqsService;
    private ISQSNotificationService notificationService;

    RequestSQSCallable(ISQSService sqsService, ISQSNotificationService notificationService, Region region, String queueUrl, Integer maxNumberOfMessages, Integer visibilityTimeout) {
        this.sqsService = sqsService;
        this.notificationService = notificationService;
        this.region = region;
        this.queueUrl = queueUrl;
        this.maxNumberOfMessages = maxNumberOfMessages;
        this.visibilityTimeout = visibilityTimeout;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            AmazonSQS client = sqsService.getAmazonSQSClient(region);
            ReceiveMessageResult result = sqsService.receiveMessage(client, queueUrl, maxNumberOfMessages, visibilityTimeout);
            if (logger.isDebugEnabled()) {
                logger.debug("The messages to receive result is {}", result);
            }
            if (result == null) {
                throw new ThumborException("The messages to receive result is null");
            }
            List<Message> messageList = result.getMessages();
            if (messageList == null) {
                throw new ThumborException("The message list is null");
            }
            if (messageList.isEmpty()) {
                if (logger.isInfoEnabled()) {
                    logger.info("The message list is empty", result);
                }
                return true;
            }

            ExecutorService executorService = Executors.newCachedThreadPool();
            List<Future<Map<String, Object>>> resultList = new ArrayList<Future<Map<String, Object>>>();
            for (Message message : messageList) {
                Future<Map<String, Object>> future = executorService.submit(new MessageCallable(notificationService, message));
                resultList.add(future);
            }

            //遍历任务的结果
            for (Future<Map<String, Object>> fs : resultList) {
                try {
                    Map<String, Object> resultMap = fs.get();
                    if (logger.isDebugEnabled()) {
                        logger.debug("The SQS message result to handle is {}", resultMap);
                    }
                    Message message = (Message) resultMap.get(MessageCallable.KEY_MESSAGE);
                    List<NotificationHandleResult<Message, Boolean>> resultList1 = (List<NotificationHandleResult<Message, Boolean>>) resultMap.get(MessageCallable.KEY_RESULT);
                    logger.info("The SQS message result list is {}", resultList1);
                    boolean success = false;
                    for (NotificationHandleResult<Message, Boolean> resultA : resultList1) {
                        success = resultA.getData();
                        if (!resultA.getData()) {
                            break;
                        }
                    }
                    if (logger.isInfoEnabled()) {
                        logger.info("\n\t\t|=======- The SQS message (ID : {})  result to handle is {}", message.getMessageId(), success);
                    }
                    if (success) {
                        DeleteMessageResult deleteMessageResult = sqsService.deleteMessage(client, queueUrl, message);
                        if (logger.isInfoEnabled()) {
                            logger.info("\n\t\t\t\t\t\t<<======| [Delete SQS Message] The message (ID : {}) to delete result is {}\n", message.getMessageId(), deleteMessageResult);
                        }
                    }
                    return true;
                } catch (Exception e) {
                    if (logger.isErrorEnabled()) {
                        logger.error(e.getMessage(), e);
                    }
                } finally {
                    //启动一次顺序关闭，执行以前提交的任务，但不接受新任务。如果已经关闭，则调用没有其他作用。
                    executorService.shutdown();
                }
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
        return false;
    }
}

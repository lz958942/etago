/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor Image Thumbor Executer
 * @Title : SQSNotificationHandlerForThumbor.java
 * @Package : com.easarrive.image.thumbor.executer.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月3日
 * @Time : 上午12:06:48
 */
package com.easarrive.image.thumbor.executer.service.impl;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sqs.model.Message;
import com.easarrive.aws.model.s3.S3EventMessageContent;
import com.easarrive.aws.model.s3.S3EventMessageRecord;
import com.easarrive.aws.plugins.common.exception.AWSPluginException;
import com.easarrive.aws.plugins.common.model.NotificationHandleResult;
import com.easarrive.aws.plugins.common.service.INotificationHandler;
import com.easarrive.image.thumbor.IGeneratePicture;
import net.lizhaoweb.common.util.base.JsonUtil;
import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月3日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class SQSNotificationHandlerForThumbor implements INotificationHandler<Message, Boolean> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private IGeneratePicture generatePicture;

    public SQSNotificationHandlerForThumbor(IGeneratePicture generatePicture) {
        this.generatePicture = generatePicture;
    }

    @Override
    public List<NotificationHandleResult<Message, Boolean>> handle(Message message) throws AWSPluginException {
        String messageId = null;
        try {
            if (message == null) {
                throw new AWSPluginException("The message is null");
            }
            messageId = message.getMessageId();

            S3EventMessageContent messageContent = this.getMessageContent(message.getBody());
            if (messageContent == null) {
                throw new AWSPluginException("The S3EventMessageContent is null");
            }

            //遍历消息记录列表
            return this.ergodicS3EventMessageRecord(message, messageContent);
        } catch (Exception e) {
            String errorMessage = String.format("Handle exception {%s} for messge (ID : %s)", e.getMessage(), messageId);
            throw new AWSPluginException(errorMessage, e);
        }
    }

    //遍历消息记录列表
    private List<NotificationHandleResult<Message, Boolean>> ergodicS3EventMessageRecord(final Message message, S3EventMessageContent messageContent) throws AWSPluginException {
        List<S3EventMessageRecord> recordList = messageContent.getRecords();
        if (recordList == null) {
            throw new AWSPluginException("The S3EventMessageRecord list is null");
        }
        if (recordList.size() < 1) {
            throw new AWSPluginException("The S3EventMessageRecord list is empty");
        }

        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<NotificationHandleResult<Message, Boolean>>> resultList = new ArrayList<Future<NotificationHandleResult<Message, Boolean>>>();
        for (final S3EventMessageRecord s3EventMessageRecord : recordList) {
            Future<NotificationHandleResult<Message, Boolean>> future = executorService.submit(new SQSNotificationHandlerForThumborCallable(message, s3EventMessageRecord, generatePicture));
            resultList.add(future);
        }

        List<NotificationHandleResult<Message, Boolean>> resutList = new ArrayList<NotificationHandleResult<Message, Boolean>>();
        //遍历任务的结果
        for (Future<NotificationHandleResult<Message, Boolean>> fs : resultList) {
            try {
                NotificationHandleResult<Message, Boolean> result = fs.get();
                if (logger.isInfoEnabled()) {
                    logger.info("The SQS message record ({}) result to handle is {}", result.getMessageId(), result.getData());
                }
                resutList.add(result);
                if (!result.getData()) {
                    break;
                }
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
            } finally {
                //启动一次顺序关闭，执行以前提交的任务，但不接受新任务。如果已经关闭，则调用没有其他作用。
                executorService.shutdown();
            }
        }
        return resutList;
    }

    private String getAccessUrl(S3Object s3Object) throws Exception {
        S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
        HttpRequestBase httpRequestBase = s3ObjectInputStream.getHttpRequest();
        URI uri = httpRequestBase.getURI();
        String accessUrl = uri.toString();
        return accessUrl;
    }

    private S3EventMessageContent getMessageContent(String message) throws Exception {
        S3EventMessageContent s3EventMessageContent = JsonUtil.toBean(message, S3EventMessageContent.class);
        return s3EventMessageContent;
    }
}


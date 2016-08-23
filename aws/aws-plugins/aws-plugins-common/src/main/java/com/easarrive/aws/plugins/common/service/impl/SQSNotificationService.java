/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.aws.plugins.common.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 12:30
 */
package com.easarrive.aws.plugins.common.service.impl;

import com.amazonaws.services.sqs.model.Message;
import com.easarrive.aws.plugins.common.exception.AWSPluginException;
import com.easarrive.aws.plugins.common.model.NotificationHandleResult;
import com.easarrive.aws.plugins.common.service.INotificationHandler;
import com.easarrive.aws.plugins.common.service.ISQSNotificationService;
import lombok.Setter;
import net.lizhaoweb.common.util.base.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月23日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class SQSNotificationService implements ISQSNotificationService<Boolean> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Setter
    private List<INotificationHandler<Message, Boolean>> notificationHandlerList;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSignatureMessageValid(Message message) {
        if (message == null) {
            return false;
        }
        String md5MessageBody = message.getMD5OfBody();
        if (StringUtil.isEmpty(md5MessageBody)) {
            return false;
        }
        String messageBody = message.getBody();
        try {
            return md5MessageBody.equals(DigestUtils.md5Hex(messageBody));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<NotificationHandleResult<Message, Boolean>> handleNotification(Message message) throws AWSPluginException {
        try {
            if (message == null) {
                throw new AWSPluginException("The message is null");
            }
            if (notificationHandlerList == null) {
                throw new AWSPluginException("The notificationHandlerList is null for message (ID : %s)", message.getMessageId());
            }
            if (notificationHandlerList.isEmpty()) {
                throw new AWSPluginException("The notificationHandlerList is empty for message (ID : %s)", message.getMessageId());
            }
            ExecutorService executorService = Executors.newCachedThreadPool();
            List<Future<List<NotificationHandleResult<Message, Boolean>>>> resultList = new ArrayList<Future<List<NotificationHandleResult<Message, Boolean>>>>();
            for (INotificationHandler<Message, Boolean> notificationHandler : notificationHandlerList) {
                Future<List<NotificationHandleResult<Message, Boolean>>> future = executorService.submit(new NotificationHandlerCallable(notificationHandler, message));
                resultList.add(future);
            }

            List<NotificationHandleResult<Message, Boolean>> returnList = new ArrayList<NotificationHandleResult<Message, Boolean>>();
            //遍历任务的结果
            for (Future<List<NotificationHandleResult<Message, Boolean>>> fs : resultList) {
                try {
                    List<NotificationHandleResult<Message, Boolean>> result = fs.get();
                    for (NotificationHandleResult<Message, Boolean> notificationHandleResult : result) {
                        returnList.add(notificationHandleResult);
                    }
                } catch (Exception e) {
                    if (logger.isErrorEnabled()) {
                        logger.error(e.getMessage(), e);
                    }
                    returnList.add(new NotificationHandleResult<Message, Boolean>(message.getMessageId(), message, false));
                } finally {
                    //启动一次顺序关闭，执行以前提交的任务，但不接受新任务。如果已经关闭，则调用没有其他作用。
                    executorService.shutdown();
                }
            }
            return returnList;
        } catch (Exception e) {
            throw new AWSPluginException(e.getMessage(), e);
        }
    }
}

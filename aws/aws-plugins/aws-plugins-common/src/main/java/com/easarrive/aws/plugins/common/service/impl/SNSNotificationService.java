/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.aws.plugins.common.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 11:53
 */
package com.easarrive.aws.plugins.common.service.impl;

import com.easarrive.aws.plugins.common.model.SNSMessage;
import com.easarrive.aws.plugins.common.service.INotificationHandler;
import com.easarrive.aws.plugins.common.service.ISNSNotificationService;
import com.easarrive.aws.plugins.common.util.Constant;
import com.easarrive.aws.plugins.common.util.SNSUtil;
import lombok.Setter;
import net.lizhaoweb.common.util.base.JsonUtil;
import net.lizhaoweb.common.util.base.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月23日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class SNSNotificationService implements ISNSNotificationService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Setter
    private List<INotificationHandler<SNSMessage, Boolean>> notificationHandlerList;

    /**
     * {@inheritDoc}
     */
    @Override
    public SNSMessage getMessage(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        // Parse the JSON message in the message body and hydrate a Message object with its contents so that we have easy access to the name/value pairs from the JSON message.
        StringBuilder builder = SNSUtil.getHttpRequestContent(request);
        SNSMessage message = null;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug(builder.toString());
            }
            message = JsonUtil.toBean(builder.toString(), SNSMessage.class);
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
        return message;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public SNSMessage getMessage(String json) {
        if (StringUtil.isEmpty(json)) {
            return null;
        }
        // Parse the JSON message in the message body and hydrate a Message object with its contents so that we have easy access to the name/value pairs from the JSON message.
        SNSMessage message = null;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug(json);
            }
            message = JsonUtil.toBean(json, SNSMessage.class);
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSignatureVersionValid(SNSMessage message) {
        if (message == null) {
            return false;
        }
        // The signature is based on SignatureVersion 1. If the sig version is something other than 1, throw an exception.
        Constant.SNS.HTTP4AWS.Response.Message.SIGNATURE_VERSION signatureVersion = Constant.SNS.HTTP4AWS.Response.Message.SIGNATURE_VERSION.fromValue(message.getSignatureVersion());
        switch (signatureVersion) {
            case V1:
                // Check the signature and throw an exception if the signature verification fails.
                if (SNSUtil.isMessageSignatureValid(message)) {
                    if (logger.isInfoEnabled()) {
                        logger.info(">>Signature verification succeeded");
                    }
                    return true;
                } else {
                    if (logger.isInfoEnabled()) {
                        logger.info(">>Signature verification failed");
                    }
                    //throw new SecurityException("Signature verification failed.");
                    return false;
                }
            default:
                if (logger.isInfoEnabled()) {
                    logger.info(">>Unexpected signature version. Unable to verify signature.");
                }
                //throw new SecurityException("Unexpected signature version. Unable to verify signature.");
                return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subscriptionConfirmation(SNSMessage message) {
        if (message == null) {
            return;
        }
        StringBuilder sb = SNSUtil.getHttpRequestContent(message.getSubscribeURL());
        if (logger.isInfoEnabled()) {
            logger.info(">>Subscription confirmation (" + message.getSubscribeURL() + ") Return value: " + sb.toString());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handleNotification(SNSMessage message) {
        if (message == null) {
            return false;
        }
        // Do something with the Message and Subject. Just log the subject (if it exists) and the message.
        String logMsgAndSubject = ">>Notification received from topic " + message.getTopicArn();
        if (message.getSubject() != null) {
            logMsgAndSubject += " Subject: " + message.getSubject();
        }
        logMsgAndSubject += " Message: " + message.getMessage();
        if (logger.isInfoEnabled()) {
            logger.info(logMsgAndSubject);
        }

        if (notificationHandlerList != null && notificationHandlerList.size() > 0) {
            ExecutorService executorService = Executors.newCachedThreadPool();
            for (INotificationHandler<SNSMessage, Boolean> notificationHandler : notificationHandlerList) {
                executorService.execute(new NotificationHandlerThread(notificationHandler, message));
            }
            executorService.shutdown();
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unsubscribeConfirmation(SNSMessage message) {
        if (message == null) {
            return;
        }
        StringBuilder sb = SNSUtil.getHttpRequestContent(message.getUnsubscribeURL());
        if (logger.isInfoEnabled()) {
            logger.info(">>Unsubscribe confirmation (" + message.getSubscribeURL() + ") Return value: " + sb.toString());
        }
        if (logger.isInfoEnabled()) {
            logger.info(">>Unsubscribe confirmation: " + message.getMessage());
        }
    }
}

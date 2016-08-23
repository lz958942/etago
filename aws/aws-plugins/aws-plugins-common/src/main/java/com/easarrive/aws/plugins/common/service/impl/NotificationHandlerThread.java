/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : NotificationHandlerThread.java
 * @Package : net.lizhaoweb.aws.plugin.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月29日
 * @Time : 下午5:05:15
 */
package com.easarrive.aws.plugins.common.service.impl;

import com.easarrive.aws.plugins.common.exception.AWSPluginException;
import com.easarrive.aws.plugins.common.model.NotificationHandleResult;
import com.easarrive.aws.plugins.common.service.INotificationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年6月29日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class NotificationHandlerThread<M, NHR> implements Runnable {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private INotificationHandler<M, NHR> notificationHandler;

    private M message;

    public NotificationHandlerThread(INotificationHandler<M, NHR> notificationHandler, M message) {
        this.notificationHandler = notificationHandler;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            List<NotificationHandleResult<M, NHR>> aaa = notificationHandler.handle(message);
            if (logger.isInfoEnabled()) {
                logger.info("Message({}) handle result is {}", message, aaa);
            }
        } catch (AWSPluginException e) {
            e.printStackTrace();
        }
    }
}

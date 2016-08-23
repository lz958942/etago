/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.aws.plugins.common.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 13:57
 */
package com.easarrive.aws.plugins.common.service.impl;

import com.easarrive.aws.plugins.common.exception.AWSPluginException;
import com.easarrive.aws.plugins.common.model.NotificationHandleResult;
import com.easarrive.aws.plugins.common.service.INotificationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月23日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class NotificationHandlerCallable<M, NHR> implements Callable<List<NotificationHandleResult<M, NHR>>> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private INotificationHandler<M, NHR> notificationHandler;

    private M message;

    public NotificationHandlerCallable(INotificationHandler<M, NHR> notificationHandler, M message) {
        this.notificationHandler = notificationHandler;
        this.message = message;
    }

    @Override
    public List<NotificationHandleResult<M, NHR>> call() throws Exception {
        try {
            return notificationHandler.handle(message);
        } catch (Exception e) {
            throw new AWSPluginException(e.getMessage(), e);
        }
    }
}

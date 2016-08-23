/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.image.thumbor.executer.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 12:38
 */
package com.easarrive.image.thumbor.executer.impl;

import com.amazonaws.services.sqs.model.Message;
import com.easarrive.aws.plugins.common.exception.AWSPluginException;
import com.easarrive.aws.plugins.common.model.NotificationHandleResult;
import com.easarrive.aws.plugins.common.service.ISQSNotificationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年08月02日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
class MessageCallable implements Callable<Map<String, Object>> {

    public static final String KEY_RESULT = MessageCallable.class.getName() + ".KEY_RESULT";

    public static final String KEY_MESSAGE = MessageCallable.class.getName() + ".KEY_MESSAGE";

    private ISQSNotificationService notificationService;

    private Message message;

    MessageCallable(ISQSNotificationService notificationService, Message message) {
        this.notificationService = notificationService;
        this.message = message;
    }

    @Override
    public Map<String, Object> call() throws Exception {
        try {
            if (message == null) {
                throw new AWSPluginException("The message is null");
            }
            List<NotificationHandleResult<Message, Boolean>> resultList = notificationService.handleNotification(message);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put(KEY_RESULT, resultList);
            resultMap.put(KEY_MESSAGE, message);
            return resultMap;
        } catch (Exception e) {
            throw new AWSPluginException(e.getMessage(), e);
        }
    }
}
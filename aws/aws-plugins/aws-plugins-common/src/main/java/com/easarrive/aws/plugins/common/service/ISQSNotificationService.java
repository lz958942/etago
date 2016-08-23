/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.aws.plugins.common.service
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 12:13
 */
package com.easarrive.aws.plugins.common.service;

import com.amazonaws.services.sqs.model.Message;
import com.easarrive.aws.plugins.common.exception.AWSPluginException;
import com.easarrive.aws.plugins.common.model.NotificationHandleResult;

import java.util.List;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月23日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public interface ISQSNotificationService<T> {

    /**
     * 验证签名版本是否有效。
     *
     * @param message 消息对象。
     */
    boolean isSignatureMessageValid(Message message);

    /**
     * 处理消息。
     *
     * @param message 消息对象。
     */
    List<NotificationHandleResult<Message, T>> handleNotification(Message message) throws AWSPluginException;
}

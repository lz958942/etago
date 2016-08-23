/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : INotificationHandler.java
 * @Package : net.lizhaoweb.aws.plugin.service
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月28日
 * @Time : 下午10:06:34
 */
package com.easarrive.aws.plugins.common.service;

import com.easarrive.aws.plugins.common.exception.AWSPluginException;
import com.easarrive.aws.plugins.common.model.NotificationHandleResult;

import java.util.List;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年6月28日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public interface INotificationHandler<M, NHR> {

    /**
     * 处理消息。
     *
     * @param message 消息对象。
     * @return 返回处理结果。
     * @throws AWSPluginException 异常
     */
    List<NotificationHandleResult<M, NHR>> handle(M message) throws AWSPluginException;
}

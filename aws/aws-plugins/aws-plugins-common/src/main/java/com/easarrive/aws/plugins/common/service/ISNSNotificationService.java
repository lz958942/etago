/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : ISNSNotificationService.java
 * @Package : net.lizhaoweb.aws.plugin.service
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月4日
 * @Time : 下午8:35:26
 */
package com.easarrive.aws.plugins.common.service;

import com.easarrive.aws.plugins.common.model.SNSMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月4日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public interface ISNSNotificationService {

    /**
     * 获取消息对象。
     *
     * @param request 请求对象。
     * @return
     */
    SNSMessage getMessage(HttpServletRequest request);

    /**
     * 获取消息对象。
     *
     * @param json Json字符串。
     * @return
     */
    SNSMessage getMessage(String json);

    /**
     * 验证签名版本是否有效。
     *
     * @param message 消息对象。
     */
    boolean isSignatureVersionValid(SNSMessage message);

    /**
     * 确认订阅.
     *
     * @param message 消息对象。
     */
    void subscriptionConfirmation(SNSMessage message);

    /**
     * 处理消息。
     *
     * @param message 消息对象。
     */
    boolean handleNotification(SNSMessage message);

    /**
     * 退订消息。
     *
     * @param message 消息对象。
     */
    void unsubscribeConfirmation(SNSMessage message);
}

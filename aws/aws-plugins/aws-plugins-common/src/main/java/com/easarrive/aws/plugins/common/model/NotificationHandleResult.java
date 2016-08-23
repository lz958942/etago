/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.aws.plugins.common.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 19:09
 */
package com.easarrive.aws.plugins.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年08月01日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationHandleResult<M, T> {

    /**
     * 消息标识
     */
    private String messageId;

    /**
     * 消息
     */
    private M message;

    /**
     * 结果数据
     */
    private T data;

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(NotificationHandleResult.class.getSimpleName());
        stringBuffer.append("(");
        stringBuffer.append("messageId=").append(messageId).append(",");
        stringBuffer.append("data=").append(data);
        stringBuffer.append(")");
        return stringBuffer.toString();
    }
}

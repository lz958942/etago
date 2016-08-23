/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : YiDa
 * @Package : net.lizhaoweb.aws.plugin.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 14:19
 */
package com.easarrive.aws.plugins.cloudsearch.service.impl;

import net.lizhaoweb.datasource.mysql.etago.model.Reply4CloudSearch;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月14日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class Replys4CloudSearchDataCallBacker extends AbstractAWSDataCallBacker<Reply4CloudSearch> {

    public Replys4CloudSearchDataCallBacker(List<Map<String, Object>> dbList) {
        this.analysis(dbList);
    }

    @Override
    protected String readIdFromMap(Map bean) {
        Object idObject = bean.get("id");
        if (idObject == null) {
            return null;
        }
        return idObject + "";
    }

    @Override
    protected Long readUpdateTimeFromMap(Map bean) {
        long updateTime = -1L;
        Object replyUpdateTimeObject = bean.get("replyUpdateTime");
        if (replyUpdateTimeObject != null && replyUpdateTimeObject instanceof Long) {
            updateTime = (long) replyUpdateTimeObject;
        }
        Object conversationUpdateTimeObject = bean.get("conversationUpdateTime");
        if (conversationUpdateTimeObject != null && conversationUpdateTimeObject instanceof Long && (long) conversationUpdateTimeObject > updateTime) {
            updateTime = (long) conversationUpdateTimeObject;
        }
        Object userUpdateTimeObject = bean.get("userUpdateTime");
        if (userUpdateTimeObject != null && userUpdateTimeObject instanceof Long && (long) userUpdateTimeObject > updateTime) {
            updateTime = (long) userUpdateTimeObject;
        }
        return updateTime;
    }
}

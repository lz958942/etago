/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : YiDa
 * @Package : net.lizhaoweb.aws.plugin.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 14:12
 */
package com.easarrive.aws.plugins.cloudsearch.service.impl;

import net.lizhaoweb.datasource.mysql.etago.model.Good4CloudSearch;

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
public class Goods4CloudSearchDataCallBacker extends AbstractAWSDataCallBacker<Good4CloudSearch> {

    public Goods4CloudSearchDataCallBacker(List<Map<String, Object>> dbList) {
        this.analysis(dbList);
    }

    @Override
    public boolean validSourceData(Good4CloudSearch sourceData) {
        if (sourceData.getLatitude() == null || sourceData.getLatitude() < -90 || sourceData.getLatitude() > 90) {
            return false;
        }
        if (sourceData.getLongitude() == null || sourceData.getLongitude() < -180 || sourceData.getLongitude() > 180) {
            return false;
        }
        return true;
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
        Object goodsUpdateTimeObject = bean.get("goodsUpdateTime");
        if (goodsUpdateTimeObject != null && goodsUpdateTimeObject instanceof Long) {
            updateTime = (long) goodsUpdateTimeObject;
        }
        Object goodTagsUpdateTimeObject = bean.get("goodTagsUpdateTime");
        if (goodTagsUpdateTimeObject != null && goodTagsUpdateTimeObject instanceof Long && (long) goodTagsUpdateTimeObject > updateTime) {
            updateTime = (long) goodTagsUpdateTimeObject;
        }
        Object goodImgsUpdateTimeObject = bean.get("goodImgsUpdateTime");
        if (goodImgsUpdateTimeObject != null && goodImgsUpdateTimeObject instanceof Long && (long) goodImgsUpdateTimeObject > updateTime) {
            updateTime = (long) goodImgsUpdateTimeObject;
        }
        Object commentDataUpdateTimeObject = bean.get("commentDataUpdateTime");
        if (commentDataUpdateTimeObject != null && commentDataUpdateTimeObject instanceof Long && (long) commentDataUpdateTimeObject > updateTime) {
            updateTime = (long) commentDataUpdateTimeObject;
        }
        return updateTime;
    }
}

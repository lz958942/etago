/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : YiDa
 * @Package : net.lizhaoweb.aws.plugin.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 13:56
 */
package com.easarrive.aws.plugins.cloudsearch.service.impl;

import com.easarrive.aws.plugins.cloudsearch.service.AWSDataCallBacker;
import net.lizhaoweb.common.util.base.StringUtil;
import net.lizhaoweb.datasource.mysql.etago.model.Abstract4CloudSearch;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月14日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public abstract class AbstractAWSDataCallBacker<T extends Abstract4CloudSearch> implements AWSDataCallBacker<T> {

    private Long updateTime = null;

    private Set<String> dbIdSet = null;

    @Override
    public boolean validSourceData(T sourceData) {
        return true;
    }

    //解析数据
    protected void analysis(List<Map<String, Object>> dbList) {
        if (dbList == null) {
            return;
        }
        if (dbList.isEmpty() || dbList.size() < 1) {
            return;
        }
        dbIdSet = new HashSet<String>();
        for (Map<String, Object> bean : dbList) {
            if (bean == null) {
                continue;
            }
            String id = this.readIdFromMap(bean);
            /*
            if (StringUtil.isNotEmpty(id) && id.matches("^\\d+$")) {
                dbIdSet.add(Long.valueOf(id));
            }
            */
            if (StringUtil.isNotEmpty(id)) {
                dbIdSet.add(id);
            }
            Long updateTime = this.readUpdateTimeFromMap(bean);
            if (this.updateTime == null && updateTime != null) {
                this.updateTime = updateTime;
            } else if (updateTime != null && updateTime > this.updateTime) {
                this.updateTime = updateTime;
            }
        }
        if (this.updateTime == null) {
            this.updateTime = -1L;
        }
    }

    @Override
    public long getUpdateTimeFromCloudSearch() {
        return this.updateTime == null ? -1 : this.updateTime;
    }

    @Override
    public Set<String> getIds2CloudSearch4Upload() {
        return this.dbIdSet;
    }

    protected abstract String readIdFromMap(Map<String, Object> bean);

    protected abstract Long readUpdateTimeFromMap(Map<String, Object> bean);
}

/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : YiDa
 * @Package : net.lizhaoweb.aws.plugin.service
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 11:29
 */
package com.easarrive.aws.plugins.cloudsearch.service;

import net.lizhaoweb.datasource.mysql.etago.model.Abstract4CloudSearch;

import java.util.Set;

/**
 * 亚马逊数据处理回调器。
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月14日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public interface AWSDataCallBacker<T extends Abstract4CloudSearch> {

    /**
     * 是否有效的数据。
     *
     * @param sourceData 数据。
     * @return 返回TRUE或FALSE.
     */
    boolean validSourceData(T sourceData);

    long getUpdateTimeFromCloudSearch();

    Set<String> getIds2CloudSearch4Upload();
}

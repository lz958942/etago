/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : YiDa
 * @Package : net.lizhaoweb.aws.plugin.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 13:57
 */
package com.easarrive.aws.plugins.cloudsearch.service.impl;

import net.lizhaoweb.datasource.mysql.etago.model.Abstract4CloudSearch;

import java.util.Map;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月14日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class DefaultAWSDataCallBacker<T extends Abstract4CloudSearch> extends AbstractAWSDataCallBacker<T> {

    @Override
    protected String readIdFromMap(Map bean) {
        return null;
    }

    @Override
    protected Long readUpdateTimeFromMap(Map bean) {
        return null;
    }
}

/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.image.thumbor.executer.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 23:53
 */
package com.easarrive.image.thumbor.executer.service.impl;

import com.easarrive.datasource.redis.etago.model.ThumborConfigure;
import com.easarrive.datasource.redis.etago.read.IThumborReadDao;
import com.easarrive.datasource.redis.etago.util.Constant.Thumbor.System.Config;
import com.easarrive.datasource.redis.etago.write.IThumborWriteDao;
import com.easarrive.image.thumbor.executer.service.IThumborConfigureService;
import lombok.Setter;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年08月16日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class ThumborConfigureService implements IThumborConfigureService {

    @Setter
    private IThumborWriteDao<String, ThumborConfigure> writeDao;

    @Setter
    private IThumborReadDao<String, ThumborConfigure> readDao;

    @Override
    public void save(ThumborConfigure bean) {
        writeDao.save(Config.FEILD_JSON, bean);
    }
}

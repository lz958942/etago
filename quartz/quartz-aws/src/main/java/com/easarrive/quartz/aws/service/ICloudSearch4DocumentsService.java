/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.quartz.aws.service
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 11:00
 */
package com.easarrive.quartz.aws.service;

import com.amazonaws.services.cloudsearchdomain.model.UploadDocumentsResult;

/**
 * <h1>接口 - 云搜索任务服务</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月21日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public interface ICloudSearch4DocumentsService extends ITaskService {

    /**
     * 更新云搜索数据。
     *
     * @return 返回操作结果。
     */
    UploadDocumentsResult uploadDocuments4Domain();

    /**
     * 删除云搜索数据。
     *
     * @return 返回操作结果。
     */
    UploadDocumentsResult deleteDocuments4Domain();
}

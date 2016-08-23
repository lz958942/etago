/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.quartz.aws.api
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 16:04
 */
package com.easarrive.quartz.aws.api;

import com.amazonaws.services.cloudsearchdomain.model.UploadDocumentsResult;
import com.amazonaws.services.sns.model.DeleteTopicResult;
import com.easarrive.aws.plugins.common.model.SNSMessage;
import com.easarrive.aws.plugins.common.service.ISNSNotificationService;
import com.easarrive.aws.plugins.common.util.Constant;
import com.easarrive.quartz.aws.service.ICloudSearch4DocumentsService;
import lombok.Setter;
import net.lizhaoweb.spring.mvc.core.bean.DataDeliveryWrapper;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <h1>控制器 - 云搜索API</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月21日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@RequestMapping("/cloud-search")
public class CloudSearchApi {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Setter
    private Map<String, ICloudSearch4DocumentsService> cloudSearch4DocumentsServiceMap;

    /**
     * 清空索引。
     *
     * @param searchKey 索引库类型。
     * @return 返回操作信息。
     */
    @RequestMapping(value = "/api", method = {RequestMethod.DELETE})
    @ResponseBody
    public DataDeliveryWrapper<UploadDocumentsResult> delete(@RequestHeader("searchType") String searchKey) {
        try {
            ICloudSearch4DocumentsService cloudSearch4DocumentsService = cloudSearch4DocumentsServiceMap.get(searchKey);
            if (cloudSearch4DocumentsService == null) {
                String errorMessage = "搜索类型不存在";
                logger.error(errorMessage);
                return new DataDeliveryWrapper<UploadDocumentsResult>(HttpStatus.SC_FORBIDDEN, errorMessage, null);
            }
            UploadDocumentsResult result = cloudSearch4DocumentsService.deleteDocuments4Domain();
            return new DataDeliveryWrapper<UploadDocumentsResult>(HttpStatus.SC_OK, "清空索引成功", result);
        } catch (Exception e) {
            String errorMessage = "清空索引失败";
            logger.error(errorMessage, e);
            return new DataDeliveryWrapper<UploadDocumentsResult>(HttpStatus.SC_INTERNAL_SERVER_ERROR, errorMessage, null);
        }
    }
}

/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.quartz.aws.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 11:04
 */
package com.easarrive.quartz.aws.service.impl;

import com.amazonaws.services.cloudsearchdomain.model.UploadDocumentsResult;
import com.easarrive.aws.plugins.cloudsearch.service.impl.Users4CloudSearchDataCallBacker;
import com.easarrive.aws.plugins.common.exception.AWSPluginException;
import com.easarrive.aws.plugins.common.model.CloudSearchOptionType;
import net.lizhaoweb.common.util.base.StringUtil;
import net.lizhaoweb.datasource.mysql.etago.mapper.read.IUser4CloudSearchReadMapper;
import net.lizhaoweb.datasource.mysql.etago.model.User4CloudSearch;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月21日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class UsersCloudSearch4DocumentsService extends AbstractCloudSearch4DocumentsService {

    @Autowired
    private IUser4CloudSearchReadMapper user4CloudSearchReadMapper;

    public UsersCloudSearch4DocumentsService(String regionName, String searchEndpoint, String docEndpoint, String idFieldName, String indexTimeFieldName) {
        super(regionName, searchEndpoint, docEndpoint, idFieldName, indexTimeFieldName);
    }

    @Override
    public UploadDocumentsResult uploadDocuments4Domain() {
        System.out.println("开始更新索引(用户)……");
        try {
            if (this.getRegion() == null) {
                throw new AWSPluginException("The region is null for CloudSearch");
            }
            if (StringUtil.isEmpty(this.getSearchEndpoint())) {
                throw new AWSPluginException("The search endpoint is null for CloudSearch");
            }
            if (StringUtil.isEmpty(this.getIndexTimeFieldName())) {
                throw new AWSPluginException("The 'indexTimeFieldName' is null for CloudSearch");
            }
            if (StringUtil.isEmpty(this.getDocEndpoint())) {
                throw new AWSPluginException("The document endpoint is null for CloudSearch");
            }
            //获取上次索引更新时间。
            long lastUpdateTime = this.getUpdateTime(this.getIndexTimeFieldName(), this.getSearchEndpoint());
            if (logger.isDebugEnabled()) {
                logger.debug("The last update time is {} from search endpoint ({})", lastUpdateTime, this.getSearchEndpoint());
            }

            List<Map<String, Object>> dbMap = user4CloudSearchReadMapper.getUserIds4Update(lastUpdateTime);
            Users4CloudSearchDataCallBacker cloudSearchDataCallBacker = new Users4CloudSearchDataCallBacker(dbMap);

            long indexTime = cloudSearchDataCallBacker.getUpdateTimeFromCloudSearch();
            if (logger.isDebugEnabled()) {
                logger.debug("The update time is {} for doc endpoint ({})", indexTime, this.getDocEndpoint());
            }

            Set<String> idSet = cloudSearchDataCallBacker.getIds2CloudSearch4Upload();
            if (logger.isDebugEnabled()) {
                logger.debug("The ids is {} for doc endpoint ({})", idSet, this.getDocEndpoint());
            }

            //验证集合是否为空
            if (this.isEmptyCollection4AmazonCloudSearch(idSet, this.getDocEndpoint())) {
                if (logger.isInfoEnabled()) {
                    logger.info("The ids is null for doc endpoint ({})", this.getDocEndpoint());
                }
                return null;
            }

            List<User4CloudSearch> user4CloudSearchList = user4CloudSearchReadMapper.getAll(idSet.toArray(new String[0]));
            this.uploadData2AWSCloudSearchAdd(this.getRegion(), indexTime, CloudSearchOptionType.ADD, this.getDocEndpoint(), user4CloudSearchList, cloudSearchDataCallBacker);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }
}

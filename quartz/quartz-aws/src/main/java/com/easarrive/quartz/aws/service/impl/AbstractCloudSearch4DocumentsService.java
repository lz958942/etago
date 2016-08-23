/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.quartz.aws.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 12:09
 */
package com.easarrive.quartz.aws.service.impl;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudsearchdomain.model.UploadDocumentsResult;
import com.easarrive.aws.client.cloudsearch.model.Hit;
import com.easarrive.aws.plugins.cloudsearch.service.impl.AbstractCloudSearch2UploadService;
import com.easarrive.aws.plugins.common.exception.AWSPluginException;
import com.easarrive.quartz.aws.service.ICloudSearch4DocumentsService;
import lombok.Getter;
import net.lizhaoweb.common.util.base.StringUtil;

import java.util.List;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月21日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public abstract class AbstractCloudSearch4DocumentsService extends AbstractCloudSearch2UploadService implements ICloudSearch4DocumentsService {

    /**
     * 区域
     */
    @Getter
    private Region region;

    /**
     * 搜索节点。
     */
    @Getter
    private String searchEndpoint;

    /**
     * 文档节点。
     */
    @Getter
    private String docEndpoint;

    /**
     * 云搜索中ID字段名。
     */
    @Getter
    private String idFieldName;

    /**
     * 云搜索中索引时间字段名。
     */
    @Getter
    private String indexTimeFieldName;

    public AbstractCloudSearch4DocumentsService(String regionName, String searchEndpoint, String docEndpoint, String idFieldName, String indexTimeFieldName) {
        Regions regions = Regions.fromName(regionName);
        Region region = Region.getRegion(regions);
        this.region = region;
        this.searchEndpoint = searchEndpoint;
        this.docEndpoint = docEndpoint;
        this.idFieldName = idFieldName;
        this.indexTimeFieldName = indexTimeFieldName;
    }

    @Override
    public void executeTask() {
        try {
            this.uploadDocuments4Domain();
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public UploadDocumentsResult deleteDocuments4Domain() {
        try {
            if (StringUtil.isEmpty(this.getSearchEndpoint())) {
                throw new AWSPluginException("The search endpoint is null or empty for CloudSearch");
            }
            if (StringUtil.isEmpty(this.getIdFieldName())) {
                throw new AWSPluginException("The 'idFieldName' is null or empty for CloudSearch");
            }
            List<Hit> hitList = this.getIds(this.getIdFieldName(), this.getSearchEndpoint());
            if (hitList == null || hitList.size() < 1) {
                if (logger.isDebugEnabled()) {
                    logger.debug("The hit list is {} from search endpoint ({})", hitList, this.getSearchEndpoint());
                }
                return null;
            }
            return this.uploadData2AWSCloudSearchDelete(this.getRegion(), this.getDocEndpoint(), hitList);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }
}

/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : YiDa
 * @Package : net.lizhaoweb.aws.plugin.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 11:28
 */
package com.easarrive.aws.plugins.cloudsearch.service.impl;

import com.amazonaws.regions.Region;
import com.amazonaws.services.cloudsearchdomain.AmazonCloudSearchDomain;
import com.amazonaws.services.cloudsearchdomain.model.UploadDocumentsResult;
import com.easarrive.aws.client.cloudsearch.AmazonCloudSearchClient;
import com.easarrive.aws.client.cloudsearch.AmazonCloudSearchQuery;
import com.easarrive.aws.client.cloudsearch.AmazonCloudSearchResult;
import com.easarrive.aws.client.cloudsearch.model.Hit;
import com.easarrive.aws.client.cloudsearch.model.Hits;
import com.easarrive.aws.client.cloudsearch.model.QueryParser;
import com.easarrive.aws.client.cloudsearch.model.SortOrder;
import com.easarrive.aws.plugins.cloudsearch.service.AWSDataCallBacker;
import com.easarrive.aws.plugins.common.exception.AWSPluginException;
import com.easarrive.aws.plugins.common.model.CloudSearchDocument;
import com.easarrive.aws.plugins.common.model.CloudSearchOptionType;
import com.easarrive.aws.plugins.common.service.ICloudSearchService;
import lombok.Setter;
import net.lizhaoweb.datasource.mysql.etago.model.Abstract4CloudSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <h1>业务层(实现) - 亚马逊云搜索上传数据</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月14日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public abstract class AbstractCloudSearch2UploadService {

    public static final String SET_IDS = AbstractCloudSearch2UploadService.class.getName() + ".SET_IDS";

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Setter
    private ICloudSearchService cloudSearchService;

    //上传数据到亚马逊 CloudSearch (新增)
    protected <T extends Abstract4CloudSearch> UploadDocumentsResult uploadData2AWSCloudSearchAdd(
            Region region,
            long indexTime,
            CloudSearchOptionType optionType,
            String docEndpoint,
            List<T> dbList4CloudSearch,
            AWSDataCallBacker callBacker
    ) throws AWSPluginException {
        try {
            List<CloudSearchDocument<T>> cloudSearchDocumentList = this.preparingData4AWSCloudSearchAddOrUpdate(indexTime, CloudSearchOptionType.ADD, dbList4CloudSearch, callBacker);
            if (cloudSearchDocumentList == null) {
                throw new AWSPluginException("Upload data is null for doc endpoint (%s)", docEndpoint);
            }
            if (cloudSearchDocumentList.isEmpty()) {
                throw new AWSPluginException("No data to upload for doc endpoint (%s)", docEndpoint);
            }
            if (logger.isInfoEnabled()) {
                logger.info("The data count is {} for doc endpoint ({})", cloudSearchDocumentList.size(), docEndpoint);
            }
            AmazonCloudSearchDomain domainClient = cloudSearchService.getAmazonCloudSearchDomainClient(region);
            UploadDocumentsResult result = cloudSearchService.uploadDocumentsByJson(domainClient, docEndpoint, cloudSearchDocumentList);
            if (logger.isInfoEnabled()) {
                logger.info("The result is {} for doc endpoint ({}) to upload", result, docEndpoint);
            }
            return result;
        } catch (Exception e) {
            throw new AWSPluginException(e.getMessage(), e);
        }
    }

    //上传数据到亚马逊 CloudSearch (删除)
    protected UploadDocumentsResult uploadData2AWSCloudSearchDelete(
            Region region,
            String docEndpoint,
            List<Hit> hitList4CloudSearch
    ) throws AWSPluginException {
        try {
            List<CloudSearchDocument<Object>> cloudSearchDocumentList = this.preparingData4AWSCloudSearchDelete(hitList4CloudSearch);
            if (cloudSearchDocumentList == null) {
                throw new AWSPluginException("Upload data is NULL for doc endpoint (%s)", docEndpoint);
            }
            if (cloudSearchDocumentList.isEmpty()) {
                throw new AWSPluginException("No data to upload for doc endpoint (%s)", docEndpoint);
            }
            if (logger.isInfoEnabled()) {
                logger.info("The data count is {} for doc endpoint ({})", cloudSearchDocumentList.size(), docEndpoint);
            }
            AmazonCloudSearchDomain domainClient = cloudSearchService.getAmazonCloudSearchDomainClient(region);
            UploadDocumentsResult result = cloudSearchService.uploadDocumentsByJson(domainClient, docEndpoint, cloudSearchDocumentList);
            if (logger.isInfoEnabled()) {
                logger.info("The result is {} for doc endpoint ({}) to upload", result, docEndpoint);
            }
            return result;
        } catch (Exception e) {
            throw new AWSPluginException(e.getMessage(), e);
        }
    }

    //准备上传亚马逊 CloudSearch 的数据(新增或更新)。
    protected <T extends Abstract4CloudSearch> List<CloudSearchDocument<T>> preparingData4AWSCloudSearchAddOrUpdate(
            long indexTime,
            CloudSearchOptionType optionType,
            List<T> dbList4CloudSearch,
            AWSDataCallBacker callBacker
    ) {
        List<CloudSearchDocument<T>> cloudSearchDocumentList = new ArrayList<CloudSearchDocument<T>>();
        if (dbList4CloudSearch != null && dbList4CloudSearch.size() > 0) {
            for (T bean4CloudSearch : dbList4CloudSearch) {
                if (bean4CloudSearch == null) {
                    continue;
                }
                if (callBacker != null && !callBacker.validSourceData(bean4CloudSearch)) {
                    continue;
                }
                bean4CloudSearch.setIndexTime(indexTime);
                CloudSearchDocument<T> cloudSearchDocument = new CloudSearchDocument<T>(bean4CloudSearch.getId() + "", optionType.getValue(), bean4CloudSearch);
                cloudSearchDocumentList.add(cloudSearchDocument);
            }
        }
        return cloudSearchDocumentList;
    }

    //准备上传亚马逊 CloudSearch 的数据(删除)。
    protected List<CloudSearchDocument<Object>> preparingData4AWSCloudSearchDelete(
            List<Hit> list4CloudSearch
    ) {
        List<CloudSearchDocument<Object>> cloudSearchDocumentList = new ArrayList<CloudSearchDocument<Object>>();
        if (list4CloudSearch != null && list4CloudSearch.size() > 0) {
            for (Hit bean4CloudSearch : list4CloudSearch) {
                if (bean4CloudSearch == null) {
                    continue;
                }
                CloudSearchDocument<Object> cloudSearchDocument = new CloudSearchDocument<Object>(bean4CloudSearch.getId(), CloudSearchOptionType.DELETE.getValue(), null);
                cloudSearchDocumentList.add(cloudSearchDocument);
            }
        }
        return cloudSearchDocumentList;
    }

    //获取上次索引更新时间。
    protected long getUpdateTime(
            String indexTimeFieldName,
            String searchEndpoint
    ) throws AWSPluginException {
        try {
            AmazonCloudSearchClient client = new AmazonCloudSearchClient(searchEndpoint);
            AmazonCloudSearchQuery query = new AmazonCloudSearchQuery().withQuery("*").withQueryParser(QueryParser.LUCENE).withPage(1, 1).withReturnFields(indexTimeFieldName).addSort(indexTimeFieldName, SortOrder.DESC);
            List<Hit> hitList = this.getHitList(client, query, searchEndpoint);
            if (hitList == null || hitList.size() < 1) {
                if (logger.isDebugEnabled()) {
                    logger.debug("The hit list is {} from search endpoint ({})", hitList, searchEndpoint);
                }
                return -1L;
            }
            Hit hit = hitList.get(0);
            if (hit == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("The hit is {} from search endpoint ({})", hit, searchEndpoint);
                }
                return -1L;
            }
            Long updateTime = hit.getLongField(indexTimeFieldName);
            if (updateTime == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("The last update time is {} from search endpoint ({})", updateTime, searchEndpoint);
                }
                return -1L;
            }
            if (logger.isInfoEnabled()) {
                logger.info("Last index time is {} from search endpoint ({})", updateTime, searchEndpoint);
            }
            return updateTime;
        } catch (Exception e) {
            throw new AWSPluginException(e.getMessage(), e);
        }
    }

    //验证集合是否为空
    protected boolean isEmptyCollection4AmazonCloudSearch(
            Collection<?> idCollection,
            String docEndpoint
    ) {
        if (idCollection == null || idCollection.isEmpty() || idCollection.size() < 1) {
            if (logger.isInfoEnabled()) {
                logger.info("Non rows to upload for doc endpoint ({})", docEndpoint);
            }
            return true;
        }
        if (logger.isInfoEnabled()) {
            logger.info("The upload data id count is {} for doc endpoint ({})", idCollection.size(), docEndpoint);
        }
        return false;
    }

    //获取 CloudSearch 中所有的ID。
    protected List<Hit> getIds(
            String idFieldName,
            String searchEndpoint
    ) throws AWSPluginException {
        try {
            AmazonCloudSearchClient client = new AmazonCloudSearchClient(searchEndpoint);
            AmazonCloudSearchQuery query = new AmazonCloudSearchQuery().withQuery("*").withQueryParser(QueryParser.LUCENE).withSize(10000).withReturnFields(idFieldName);
            List<Hit> hitList = this.getHitList(client, query, searchEndpoint);
            return hitList;
        } catch (Exception e) {
            throw new AWSPluginException(e.getMessage(), e);
        }
    }

    //获取 CloudSearch 查询结果。
    private List<Hit> getHitList(
            AmazonCloudSearchClient client,
            AmazonCloudSearchQuery query,
            String searchEndpoint
    ) throws AWSPluginException {
        try {
            AmazonCloudSearchResult searchResult = client.search(query);
            if (searchResult == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("The search result is {} from search endpoint ({})", searchResult, searchEndpoint);
                }
                return null;
            }
            Hits hits = searchResult.getHits();
            if (hits == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("The hits is {} from search endpoint ({})", hits, searchEndpoint);
                }
                return null;
            }
            List<Hit> hitList = hits.getHitList();
            return hitList;
        } catch (Exception e) {
            throw new AWSPluginException(e.getMessage(), e);
        }
    }
}

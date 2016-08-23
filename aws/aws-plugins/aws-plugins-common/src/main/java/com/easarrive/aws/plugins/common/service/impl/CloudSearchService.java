/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : CloudSearchService.java
 * @Package : net.lizhaoweb.aws.plugin.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月29日
 * @Time : 下午9:35:44
 */
package com.easarrive.aws.plugins.common.service.impl;

import com.amazonaws.regions.Region;
import com.amazonaws.services.cloudsearchdomain.AmazonCloudSearchDomain;
import com.amazonaws.services.cloudsearchdomain.model.ContentType;
import com.amazonaws.services.cloudsearchdomain.model.UploadDocumentsRequest;
import com.amazonaws.services.cloudsearchdomain.model.UploadDocumentsResult;
import com.amazonaws.services.cloudsearchv2.AmazonCloudSearch;
import com.amazonaws.services.cloudsearchv2.model.*;
import com.easarrive.aws.plugins.common.service.ICloudSearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Setter;
import net.lizhaoweb.common.util.base.IOUtil;
import net.lizhaoweb.common.util.base.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年6月29日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class CloudSearchService implements ICloudSearchService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Setter
    private AmazonCloudSearch amazonCloudSearch;

    @Setter
    private AmazonCloudSearchDomain amazonCloudSearchDomain;

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateDomainResult createDomain(AmazonCloudSearch searchClient, String domainName) {
        CreateDomainRequest createDomainRequest = new CreateDomainRequest();
        createDomainRequest.setDomainName(domainName);

        // IndexDocumentsRequest indexDocumentsRequest = new
        // IndexDocumentsRequest();
        // indexDocumentsRequest.withDomainName(domainName);
        // amazonCloudSearch.indexDocuments(indexDocumentsRequest);

        CreateDomainResult result = searchClient.createDomain(createDomainRequest);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeleteDomainResult deleteDomain(AmazonCloudSearch searchClient, String domainName) {
        DeleteDomainRequest deleteDomainRequest = new DeleteDomainRequest();
        deleteDomainRequest.setDomainName(domainName);

        DeleteDomainResult result = searchClient.deleteDomain(deleteDomainRequest);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UpdateServiceAccessPoliciesResult updateServiceAccessPolicies(AmazonCloudSearch searchClient, String domainName, String accessPolicies) {
        UpdateServiceAccessPoliciesRequest updateServiceAccessPoliciesRequest = new UpdateServiceAccessPoliciesRequest();
        updateServiceAccessPoliciesRequest.withDomainName(domainName).withAccessPolicies(accessPolicies);

        UpdateServiceAccessPoliciesResult result = searchClient.updateServiceAccessPolicies(updateServiceAccessPoliciesRequest);
        return result;
    }

    // public UpdateScalingParametersResult updateScalingParameters(Region
    // region, String domainName) {
    // AmazonCloudSearch amazonCloudSearch =
    // this.getAmazonCloudSearchClient(region);
    //
    // UpdateScalingParametersRequest updateScalingParametersRequest = new
    // UpdateScalingParametersRequest();
    // ScalingParameters scalingParameters = new ScalingParameters();
    // updateScalingParametersRequest.withScalingParameters(scalingParameters);
    //
    // UpdateScalingParametersResult result =
    // amazonCloudSearch.updateScalingParameters(updateScalingParametersRequest);
    // return result;
    // }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadDocumentsResult uploadDocumentsByJson(AmazonCloudSearchDomain domainClient, String endpoint, InputStream documents, Long contentLength) {
        domainClient.setEndpoint(endpoint);

        UploadDocumentsRequest uploadDocumentsRequest = new UploadDocumentsRequest();
        uploadDocumentsRequest.withDocuments(documents).withContentType(ContentType.Applicationjson).withContentLength(contentLength);

        UploadDocumentsResult result = domainClient.uploadDocuments(uploadDocumentsRequest);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadDocumentsResult uploadDocumentsByXML(AmazonCloudSearchDomain domainClient, String domainName, InputStream documents, Long contentLength) {
        domainClient.setEndpoint(domainName);

        UploadDocumentsRequest uploadDocumentsRequest = new UploadDocumentsRequest();
        uploadDocumentsRequest.withDocuments(documents).withContentType(ContentType.Applicationxml).withContentLength(contentLength);

        UploadDocumentsResult result = domainClient.uploadDocuments(uploadDocumentsRequest);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadDocumentsResult uploadDocumentsByJson(AmazonCloudSearchDomain domainClient, String endpoint, List<?> cloudSearchDocumentList) throws JsonProcessingException {
        byte[] jsonByteArray = JsonUtil.toBytes(cloudSearchDocumentList);
        UploadDocumentsResult result = this.uploadDocumentsByJson(domainClient, endpoint, jsonByteArray);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadDocumentsResult uploadDocumentsByJson(AmazonCloudSearchDomain domainClient, String endpoint, byte[] jsonByteArray) {
        InputStream documents = null;
        UploadDocumentsResult result = null;
        try {
            documents = new ByteArrayInputStream(jsonByteArray);
            result = this.uploadDocumentsByJson(domainClient, endpoint, documents, (long) jsonByteArray.length);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        } finally {
            IOUtil.close(documents);
        }
        return result;
    }

    @Override
    public AmazonCloudSearch getAmazonCloudSearchClient(Region region) {
        amazonCloudSearch.setRegion(region);
        return amazonCloudSearch;
    }

    @Override
    public AmazonCloudSearchDomain getAmazonCloudSearchDomainClient(Region region) {
        amazonCloudSearchDomain.setRegion(region);
        return amazonCloudSearchDomain;
    }
}

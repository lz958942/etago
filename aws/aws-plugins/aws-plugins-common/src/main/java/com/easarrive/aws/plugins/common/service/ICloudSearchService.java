/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : ICloudSearchService.java
 * @Package : net.lizhaoweb.aws.plugin.service
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月29日
 * @Time : 下午9:35:17
 */
package com.easarrive.aws.plugins.common.service;

import com.amazonaws.regions.Region;
import com.amazonaws.services.cloudsearchdomain.AmazonCloudSearchDomain;
import com.amazonaws.services.cloudsearchdomain.model.UploadDocumentsResult;
import com.amazonaws.services.cloudsearchv2.AmazonCloudSearch;
import com.amazonaws.services.cloudsearchv2.model.CreateDomainResult;
import com.amazonaws.services.cloudsearchv2.model.DeleteDomainResult;
import com.amazonaws.services.cloudsearchv2.model.UpdateServiceAccessPoliciesResult;
import com.fasterxml.jackson.core.JsonProcessingException;

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
public interface ICloudSearchService {

    CreateDomainResult createDomain(AmazonCloudSearch searchClient, String domainName);

    DeleteDomainResult deleteDomain(AmazonCloudSearch searchClient, String domainName);

    UpdateServiceAccessPoliciesResult updateServiceAccessPolicies(AmazonCloudSearch searchClient, String domainName, String accessPolicies);

    UploadDocumentsResult uploadDocumentsByJson(AmazonCloudSearchDomain domainClient, String domainName, InputStream documents, Long contentLength);

    UploadDocumentsResult uploadDocumentsByXML(AmazonCloudSearchDomain domainClient, String domainName, InputStream documents, Long contentLength);

    UploadDocumentsResult uploadDocumentsByJson(AmazonCloudSearchDomain domainClient, String endpoint, List<?> cloudSearchDocumentList) throws JsonProcessingException;

    UploadDocumentsResult uploadDocumentsByJson(AmazonCloudSearchDomain domainClient, String endpoint, byte[] jsonByteArray);

    AmazonCloudSearch getAmazonCloudSearchClient(Region region);

    AmazonCloudSearchDomain getAmazonCloudSearchDomainClient(Region region);
}

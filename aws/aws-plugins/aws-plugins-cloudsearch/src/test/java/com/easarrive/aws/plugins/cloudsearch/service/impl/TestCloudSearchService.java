/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : TestCloudSearchService.java
 * @Package : net.lizhaoweb.aws.plugin.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月29日
 * @Time : 下午10:15:46
 */
package com.easarrive.aws.plugins.cloudsearch.service.impl;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudsearchdomain.AmazonCloudSearchDomain;
import com.amazonaws.services.cloudsearchdomain.model.UploadDocumentsResult;
import com.amazonaws.services.cloudsearchv2.AmazonCloudSearch;
import com.amazonaws.services.cloudsearchv2.model.CreateDomainResult;
import com.amazonaws.services.cloudsearchv2.model.DefineIndexFieldRequest;
import com.amazonaws.services.cloudsearchv2.model.DeleteDomainResult;
import com.amazonaws.services.cloudsearchv2.model.IndexField;
import com.amazonaws.services.cloudsearchv2.model.transform.DefineIndexFieldRequestMarshaller;
import com.easarrive.aws.plugins.common.model.CloudSearchDocument;
import com.easarrive.aws.plugins.common.model.CloudSearchOptionType;
import com.easarrive.aws.plugins.common.service.ICloudSearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import net.lizhaoweb.common.util.base.IOUtil;
import net.lizhaoweb.common.util.base.JsonUtil;
import net.lizhaoweb.datasource.mysql.etago.mapper.read.IGood4CloudSearchReadMapper;
import net.lizhaoweb.datasource.mysql.etago.mapper.read.IReply4CloudSearchReadMapper;
import net.lizhaoweb.datasource.mysql.etago.mapper.read.IUser4CloudSearchReadMapper;
import net.lizhaoweb.datasource.mysql.etago.model.Good4CloudSearch;
import net.lizhaoweb.datasource.mysql.etago.model.Reply4CloudSearch;
import net.lizhaoweb.datasource.mysql.etago.model.User4CloudSearch;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年6月29日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/schema/spring/spring-mysql_etago-model.xml", "classpath*:/schema/spring/spring-mysql_etago-datasource.xml", "classpath*:/schema/spring/spring-mysql_etago-mapper.xml", "classpath*:/schema/spring/spring-aws_plugin-model.xml", "classpath*:/schema/spring/spring-aws_plugin-service.xml"})
public class TestCloudSearchService extends AbstractCloudSearch2UploadService {

    @Autowired
    private ICloudSearchService cloudSearchService;

    @Autowired
    private IUser4CloudSearchReadMapper user4CloudSearchReadMapper;

    @Autowired
    private IGood4CloudSearchReadMapper good4CloudSearchReadMapper;

    @Autowired
    private IReply4CloudSearchReadMapper reply4CloudSearchReadMapper;

    private AmazonCloudSearch searchClient;
    private AmazonCloudSearchDomain domainClient;

    private String goodsDomainName;
    private String goodsSearchEndpoint;
    private String goodsDocEndpoint;

    private String usersDomainName;
    private String usersSearchEndpoint;
    private String usersDocEndpoint;

    private String replysDomainName;
    private String replysSearchEndpoint;
    private String replysDocEndpoint;

    // 初始化
    @Before
    public void init() {
        Regions regions = Regions.fromName("us-west-2");
        Region region = Region.getRegion(regions);

        this.searchClient = cloudSearchService.getAmazonCloudSearchClient(region);
        this.domainClient = cloudSearchService.getAmazonCloudSearchDomainClient(region);

        this.usersDomainName = "etago-user";
        this.usersSearchEndpoint = "search-etago-users-lrwim7hxtiisqzmdideswmhqe4.us-west-2.cloudsearch.amazonaws.com";
        this.usersDocEndpoint = "doc-etago-users-lrwim7hxtiisqzmdideswmhqe4.us-west-2.cloudsearch.amazonaws.com";

        this.goodsDomainName = "etago-goods";
        this.goodsSearchEndpoint = "search-etago-goods-iqsbfpheme4kul6mndtd5vrd4y.us-west-2.cloudsearch.amazonaws.com";
        this.goodsDocEndpoint = "doc-etago-goods-iqsbfpheme4kul6mndtd5vrd4y.us-west-2.cloudsearch.amazonaws.com";

        this.replysDomainName = "etago-replys";
        this.replysSearchEndpoint = "search-etago-replys-wa3umrskjst44asqjidj4dz44u.us-west-2.cloudsearch.amazonaws.com";
        this.replysDocEndpoint = "doc-etago-replys-wa3umrskjst44asqjidj4dz44u.us-west-2.cloudsearch.amazonaws.com";
    }

    @Test
    public void createDomain() {
        try {
            CreateDomainResult result = cloudSearchService.createDomain(searchClient, goodsDomainName);
            System.out.println(JsonUtil.toJson(result));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteDomain() {
        try {
            DeleteDomainResult result = cloudSearchService.deleteDomain(searchClient, goodsDomainName);
            System.out.println(JsonUtil.toJson(result));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void uploadDocumentsByJson4User() {
        try {
            //获取上次索引更新时间。
            long updateTime = this.getUpdateTime("index_time", this.usersSearchEndpoint);

            List<Map<String, Object>> dbMap = user4CloudSearchReadMapper.getUserIds4Update(updateTime);
            Users4CloudSearchDataCallBacker cloudSearchDataCallBacker = new Users4CloudSearchDataCallBacker(dbMap);
            long indexTime = cloudSearchDataCallBacker.getUpdateTimeFromCloudSearch();
            Set<String> userIdSet = cloudSearchDataCallBacker.getIds2CloudSearch4Upload();

            //验证集合是否为空
            if (this.isEmptyCollection4AmazonCloudSearch(userIdSet, usersDocEndpoint)) {
                return;
            }

            List<User4CloudSearch> user4CloudSearchList = user4CloudSearchReadMapper.getAll(userIdSet.toArray(new String[0]));
            List<CloudSearchDocument<User4CloudSearch>> cloudSearchDocumentList = this.preparingData4AWSCloudSearchAddOrUpdate(indexTime, CloudSearchOptionType.ADD, user4CloudSearchList, cloudSearchDataCallBacker);

            byte[] jsonByteArray = JsonUtil.toBytes(cloudSearchDocumentList);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(String.format("D:/%s.json", this.usersDomainName));
                fileOutputStream.write(jsonByteArray);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtil.close(fileOutputStream);
            }

            UploadDocumentsResult result = cloudSearchService.uploadDocumentsByJson(domainClient, this.usersDocEndpoint, jsonByteArray);
            System.out.println(JsonUtil.toJson(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void uploadDocumentsByJson4Goods() {
        try {
            //获取上次索引更新时间。
            long updateTime = this.getUpdateTime("index_time", this.goodsSearchEndpoint);

            List<Map<String, Object>> dbMap = good4CloudSearchReadMapper.getGoodIds4Update(updateTime);
            Goods4CloudSearchDataCallBacker cloudSearchDataCallBacker = new Goods4CloudSearchDataCallBacker(dbMap);
            long indexTime = cloudSearchDataCallBacker.getUpdateTimeFromCloudSearch();
            Set<String> goodIdSet = cloudSearchDataCallBacker.getIds2CloudSearch4Upload();

            //验证集合是否为空
            if (this.isEmptyCollection4AmazonCloudSearch(goodIdSet, goodsDocEndpoint)) {
                return;
            }

            List<Good4CloudSearch> good4CloudSearchList = good4CloudSearchReadMapper.getAll(goodIdSet.toArray(new String[0]));
            List<CloudSearchDocument<Good4CloudSearch>> cloudSearchDocumentList = this.preparingData4AWSCloudSearchAddOrUpdate(indexTime, CloudSearchOptionType.ADD, good4CloudSearchList, cloudSearchDataCallBacker);

            byte[] jsonByteArray = JsonUtil.toBytes(cloudSearchDocumentList);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(String.format("D:/%s.json", this.goodsDomainName));
                fileOutputStream.write(jsonByteArray);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtil.close(fileOutputStream);
            }

            UploadDocumentsResult result = cloudSearchService.uploadDocumentsByJson(domainClient, this.goodsDocEndpoint, jsonByteArray);
            System.out.println(JsonUtil.toJson(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void uploadDocumentsByJson4Replys() {
        try {
            //获取上次索引更新时间。
            long updateTime = this.getUpdateTime("index_time", this.replysSearchEndpoint);

            List<Map<String, Object>> dbMap = reply4CloudSearchReadMapper.getReplyIds4Update(updateTime);
            Replys4CloudSearchDataCallBacker cloudSearchDataCallBacker = new Replys4CloudSearchDataCallBacker(dbMap);
            long indexTime = cloudSearchDataCallBacker.getUpdateTimeFromCloudSearch();
            Set<String> replyIdSet = cloudSearchDataCallBacker.getIds2CloudSearch4Upload();

            //验证集合是否为空
            if (this.isEmptyCollection4AmazonCloudSearch(replyIdSet, replysDocEndpoint)) {
                return;
            }

            List<Reply4CloudSearch> reply4CloudSearchList = reply4CloudSearchReadMapper.getAll(replyIdSet.toArray(new String[0]));
            List<CloudSearchDocument<Reply4CloudSearch>> cloudSearchDocumentList = this.preparingData4AWSCloudSearchAddOrUpdate(indexTime, CloudSearchOptionType.ADD, reply4CloudSearchList, new DefaultAWSDataCallBacker<Reply4CloudSearch>());

            byte[] jsonByteArray = JsonUtil.toBytes(cloudSearchDocumentList);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(String.format("D:/%s.json", this.replysDomainName));
                fileOutputStream.write(jsonByteArray);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtil.close(fileOutputStream);
            }

            UploadDocumentsResult result = cloudSearchService.uploadDocumentsByJson(domainClient, this.replysDocEndpoint, jsonByteArray);
            System.out.println(JsonUtil.toJson(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void aaaa() {
        DefineIndexFieldRequestMarshaller defineIndexFieldRequestMarshaller = new DefineIndexFieldRequestMarshaller();
        DefineIndexFieldRequest defineIndexFieldRequest = new DefineIndexFieldRequest();
        IndexField indexField = new IndexField();
        defineIndexFieldRequest.withIndexField(indexField);
        defineIndexFieldRequestMarshaller.marshall(defineIndexFieldRequest);

        // IndexDocumentsRequest indexDocumentsRequest = new
        // IndexDocumentsRequest();
    }
}

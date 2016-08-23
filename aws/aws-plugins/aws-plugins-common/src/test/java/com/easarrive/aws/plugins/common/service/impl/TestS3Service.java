/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : TestS3Service.java
 * @Package : net.lizhaoweb.aws.plugin.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月29日
 * @Time : 上午11:38:09
 */
package com.easarrive.aws.plugins.common.service.impl;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.easarrive.aws.plugins.common.service.IS3Service;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
public class TestS3Service {

    @Autowired
    private IS3Service s3Service;

    private AmazonS3 client;
    private String bucketName;
    private String key;
    private File file;

    // 初始化
    @Before
    public void init() {
        Region region = Region.getRegion(Regions.US_WEST_2);

        this.client = s3Service.getAmazonS3Client(region);
        this.bucketName = "etago-app-dev";
        this.key = "images/source/goods/1324b4b40bab264f551e6c99fe1eecf0698f3ca81468287253.jpg";
        this.file = new File("D:/我的图片/33.jpg");
    }

    @Test
    public void createBucket() {
        Bucket bucket = s3Service.createBucket(client, bucketName);
        System.out.println(bucket);
    }

    @Test
    public void deleteBucket() {
        s3Service.deleteBucket(client, bucketName);
    }

    @Test
    public void putObject() {
        PutObjectResult result = s3Service.putObjectAllRW(client, bucketName, key, file);
        System.out.println(result);
    }

    @Test
    public void getObject() {
        S3Object object = s3Service.getObject(client, bucketName, key);
        System.out.println(object);
        URI uri = object.getObjectContent().getHttpRequest().getURI();
        String url = uri.toASCIIString();
        System.out.println(url);
        String url2 = uri.toString();
        System.out.println(url2);
    }

    @Test
    public void deleteObject() {
        s3Service.deleteObject(client, bucketName, key);
    }

    @Test
    public void deleteObjects() {
        List<String> keyList = new ArrayList<String>();
        keyList.add(key);
        DeleteObjectsResult result = s3Service.deleteObjects(client, bucketName, keyList);
        System.out.println(result);
    }
}

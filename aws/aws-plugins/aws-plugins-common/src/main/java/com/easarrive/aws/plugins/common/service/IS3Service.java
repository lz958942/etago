/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : IS3Service.java
 * @Package : net.lizhaoweb.aws.plugin.service
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月29日
 * @Time : 上午10:14:21
 */
package com.easarrive.aws.plugins.common.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;

import java.io.File;
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
public interface IS3Service {

    /**
     * 创建桶。
     *
     * @param client     连接客户端
     * @param bucketName 桶名
     * @return 返回桶对象。
     */
    Bucket createBucket(AmazonS3 client, String bucketName);

    /**
     * 获取所有桶列表。
     *
     * @param client 连接客户端
     * @return 返回桶列表。
     */
    List<Bucket> getBucketList(AmazonS3 client);

    /**
     * 删除桶。
     *
     * @param client     连接客户端
     * @param bucketName 桶名
     */
    void deleteBucket(AmazonS3 client, String bucketName);

    /**
     * 上传对象。
     *
     * @param client     连接客户端
     * @param bucketName 桶名
     * @param key        键
     * @param file       文件
     * @return 返回操作结果。
     */
    PutObjectResult putObject(AmazonS3 client, String bucketName, String key, File file);

    /**
     * 上传对象并授权。
     *
     * @param client     连接客户端
     * @param bucketName 桶名
     * @param key        键
     * @param file       文件
     * @param grantee    受让人
     * @param permission 许可
     * @return 返回操作结果。
     */
    PutObjectResult putObject(AmazonS3 client, String bucketName, String key, File file, Grantee grantee, Permission permission);

    /**
     * 上传对象并授权。
     *
     * @param client       连接客户端
     * @param bucketName   桶名
     * @param key          键
     * @param file         文件
     * @param grantsVarArg 授权列表
     * @return 返回操作结果。
     */
    PutObjectResult putObject(AmazonS3 client, String bucketName, String key, File file, Grant... grantsVarArg);

    /**
     * 上传对象并授权所有人读写。
     *
     * @param client     连接客户端
     * @param bucketName 桶名
     * @param key        键
     * @param file       文件
     * @return 返回操作结果。
     */
    PutObjectResult putObjectAllRW(AmazonS3 client, String bucketName, String key, File file);

    /**
     * 上传对象。
     *
     * @param client     连接客户端
     * @param bucketName 桶名
     * @param key        键
     * @param input      输入流
     * @param metadata   对象类型
     * @return 返回操作结果。
     */
    PutObjectResult putObject(AmazonS3 client, String bucketName, String key, InputStream input, ObjectMetadata metadata);

    /**
     * 上传对象并授权。
     *
     * @param client     连接客户端
     * @param bucketName 桶名
     * @param key        键
     * @param input      输入流
     * @param metadata   对象类型
     * @param grantee    受让人
     * @param permission 许可
     * @return 返回操作结果。
     */
    PutObjectResult putObject(AmazonS3 client, String bucketName, String key, InputStream input, ObjectMetadata metadata, Grantee grantee, Permission permission);

    /**
     * 上传对象并授权。
     *
     * @param client       连接客户端
     * @param bucketName   桶名
     * @param key          键
     * @param input        输入流
     * @param metadata     对象类型
     * @param grantsVarArg 授权列表
     * @return 返回操作结果。
     */
    PutObjectResult putObject(AmazonS3 client, String bucketName, String key, InputStream input, ObjectMetadata metadata, Grant... grantsVarArg);

    /**
     * 上传对象并授权所有人读写。
     *
     * @param client     连接客户端
     * @param bucketName 桶名
     * @param key        键
     * @param input      输入流
     * @param metadata   对象类型
     * @return 返回操作结果。
     */
    PutObjectResult putObjectAllRW(AmazonS3 client, String bucketName, String key, InputStream input, ObjectMetadata metadata);

    /**
     * 获取对象。
     *
     * @param client     连接客户端
     * @param bucketName 桶名
     * @param key        键
     * @return 返回对象。
     */
    S3Object getObject(AmazonS3 client, String bucketName, String key);

    /**
     * 获取对象列表。
     *
     * @param client     连接客户端
     * @param bucketName 桶名
     * @param prefix     前缀
     * @return 返回对象列表。
     */
    ObjectListing getObjectList(AmazonS3 client, String bucketName, String prefix);

    /**
     * 删除对象。
     *
     * @param client     连接客户端
     * @param bucketName 桶名
     * @param key        键
     */
    void deleteObject(AmazonS3 client, String bucketName, String key);

    /**
     * 批量删除对象。
     *
     * @param client     连接客户端
     * @param bucketName 桶名
     * @param keyList    键列表
     * @return 返回操作结果。
     */
    DeleteObjectsResult deleteObjects(AmazonS3 client, String bucketName, List<String> keyList);

    /**
     * 批量删除对象。
     *
     * @param client     连接客户端
     * @param bucketName 桶名
     * @param keyList    键列表
     * @return 返回操作结果。
     */
    DeleteObjectsResult deleteObjects2(AmazonS3 client, String bucketName, List<KeyVersion> keyList);

    /**
     * 创建目录。
     *
     * @param client     连接客户端
     * @param bucketName 桶名
     * @param folderName 文件夹名
     * @return 返回操作结果。
     */
    PutObjectResult createFolder(AmazonS3 client, String bucketName, String folderName);

    /**
     * 批量删除对象。
     *
     * @param client     连接客户端
     * @param bucketName 桶名
     * @param prefix     前缀
     * @return 返回操作结果。
     */
    DeleteObjectsResult deleteObjectsWithPrefix(AmazonS3 client, String bucketName, String prefix);

    /**
     * 获取 AWS S3 连接客户端。
     *
     * @param region 区域
     * @return 返回 AWS S3 连接客户端。
     */
    AmazonS3 getAmazonS3Client(com.amazonaws.regions.Region region);
}

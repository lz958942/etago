/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : S3Service.java
 * @Package : net.lizhaoweb.aws.plugin.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月29日
 * @Time : 上午10:14:38
 */
package com.easarrive.aws.plugins.common.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.easarrive.aws.plugins.common.service.IS3Service;
import lombok.Setter;
import net.lizhaoweb.common.util.base.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
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
public class S3Service implements IS3Service {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Setter
    private AmazonS3 amazonS3;

    // @Setter
    // private String credentialsPath;

    /**
     * {@inheritDoc}
     */
    @Override
    public Bucket createBucket(AmazonS3 client, String bucketName) {
        if (client == null) {
            return null;
        } else if (StringUtil.isEmpty(bucketName)) {
            return null;
        }
        Bucket bucket = null;
        if (!client.doesBucketExist(bucketName)) {
            bucket = client.createBucket(bucketName);
        }
        return bucket;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Bucket> getBucketList(AmazonS3 client) {
        if (client == null) {
            return null;
        }
        List<Bucket> bucketList = client.listBuckets();
        return bucketList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteBucket(AmazonS3 client, String bucketName) {
        if (client == null) {
            return;
        } else if (StringUtil.isEmpty(bucketName)) {
            return;
        }
        if (client.doesBucketExist(bucketName)) {
            client.deleteBucket(bucketName);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PutObjectResult putObject(AmazonS3 client, String bucketName, String key, File file) {
        if (client == null) {
            return null;
        } else if (StringUtil.isEmpty(bucketName)) {
            return null;
        } else if (StringUtil.isEmpty(key)) {
            return null;
        } else if (file == null) {
            return null;
        }
        PutObjectResult result = null;
        if (!client.doesObjectExist(bucketName, key)) {
            result = client.putObject(bucketName, key, file);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PutObjectResult putObject(AmazonS3 client, String bucketName, String key, File file, Grantee grantee, Permission permission) {
        return this.putObject(client, bucketName, key, file, grantee, permission);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PutObjectResult putObject(AmazonS3 client, String bucketName, String key, File file, Grant... grantsVarArg) {
        return this.putObject(client, bucketName, key, file, null, null, grantsVarArg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PutObjectResult putObjectAllRW(AmazonS3 client, String bucketName, String key, File file) {
        Grant readGrant = new Grant(GroupGrantee.AllUsers, Permission.Read);
        Grant writeGrant = new Grant(GroupGrantee.AllUsers, Permission.Write);
        return this.putObject(client, bucketName, key, file, readGrant, writeGrant);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PutObjectResult putObject(AmazonS3 client, String bucketName, String key, InputStream input, ObjectMetadata metadata) {
        if (client == null) {
            return null;
        } else if (StringUtil.isEmpty(bucketName)) {
            return null;
        } else if (StringUtil.isEmpty(key)) {
            return null;
        } else if (input == null) {
            return null;
        }
        if (metadata == null) {
            metadata = new ObjectMetadata();
        }
        PutObjectResult result = null;
        if (!client.doesObjectExist(bucketName, key)) {
            result = client.putObject(bucketName, key, input, metadata);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PutObjectResult putObject(AmazonS3 client, String bucketName, String key, InputStream input, ObjectMetadata metadata, Grantee grantee, Permission permission) {
        return this.putObject(client, bucketName, key, input, metadata, grantee, permission);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PutObjectResult putObject(AmazonS3 client, String bucketName, String key, InputStream input, ObjectMetadata metadata, Grant... grantsVarArg) {
        return this.putObject(client, bucketName, key, input, metadata, null, null, grantsVarArg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PutObjectResult putObjectAllRW(AmazonS3 client, String bucketName, String key, InputStream input, ObjectMetadata metadata) {
        Grant readGrant = new Grant(GroupGrantee.AllUsers, Permission.Read);
        Grant writeGrant = new Grant(GroupGrantee.AllUsers, Permission.Write);
        return this.putObject(client, bucketName, key, input, metadata, readGrant, writeGrant);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public S3Object getObject(AmazonS3 client, String bucketName, String key) {
        if (client == null) {
            return null;
        } else if (StringUtil.isEmpty(bucketName)) {
            return null;
        } else if (StringUtil.isEmpty(key)) {
            return null;
        }
        S3Object object = null;
        if (client.doesObjectExist(bucketName, key)) {
            object = client.getObject(bucketName, key);
        }
        return object;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectListing getObjectList(AmazonS3 client, String bucketName, String prefix) {
        if (client == null) {
            return null;
        } else if (StringUtil.isEmpty(bucketName)) {
            return null;
        } else if (StringUtil.isEmpty(prefix)) {
            return null;
        }
        if (!client.doesObjectExist(bucketName, prefix)) {
            return null;
        }
        ObjectListing objectListing = null;
        if (prefix == null) {
            objectListing = client.listObjects(bucketName);
        } else {
            objectListing = client.listObjects(bucketName, prefix);
        }
        return objectListing;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteObject(AmazonS3 client, String bucketName, String key) {
        if (client == null) {
            return;
        } else if (StringUtil.isEmpty(bucketName)) {
            return;
        } else if (StringUtil.isEmpty(key)) {
            return;
        }
        if (client.doesObjectExist(bucketName, key)) {
            client.deleteObject(bucketName, key);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeleteObjectsResult deleteObjects(AmazonS3 client, String bucketName, List<String> keyList) {
        if (client == null) {
            return null;
        } else if (StringUtil.isEmpty(bucketName)) {
            return null;
        } else if (keyList == null || keyList.size() < 1) {
            return null;
        }
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName);
        List<KeyVersion> keys = new ArrayList<KeyVersion>();
        for (String key : keyList) {
            KeyVersion keyVersion = new KeyVersion(key);
            keys.add(keyVersion);
        }
        deleteObjectsRequest.setKeys(keys);
        DeleteObjectsResult result = client.deleteObjects(deleteObjectsRequest);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public DeleteObjectsResult deleteObjects2(AmazonS3 client, String bucketName, List<KeyVersion> keyList) {
        if (client == null) {
            return null;
        } else if (StringUtil.isEmpty(bucketName)) {
            return null;
        } else if (keyList == null || keyList.size() < 1) {
            return null;
        }
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName);
        deleteObjectsRequest.setKeys(keyList);
        DeleteObjectsResult result = client.deleteObjects(deleteObjectsRequest);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PutObjectResult createFolder(AmazonS3 client, String bucketName, String folderName) {
        if (client == null) {
            return null;
        } else if (StringUtil.isEmpty(bucketName)) {
            return null;
        } else if (StringUtil.isEmpty(folderName)) {
            return null;
        }
        // Create metadata for my folder & set content-length to 0
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);
        // Create empty content
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
        if (!folderName.endsWith("/")) {
            folderName += "/";
        }
        return this.putObject(client, bucketName, folderName, emptyContent, metadata);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeleteObjectsResult deleteObjectsWithPrefix(AmazonS3 client, String bucketName, String prefix) {
        if (client == null) {
            return null;
        } else if (StringUtil.isEmpty(bucketName)) {
            return null;
        } else if (StringUtil.isEmpty(prefix)) {
            return null;
        }
        int pre_len = prefix.length();
        ObjectListing objectListing = this.getObjectList(client, bucketName, prefix);
        List<KeyVersion> keyList = new ArrayList<KeyVersion>();
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            String key = objectSummary.getKey();
            int len = key.length();
            if (len < pre_len)
                continue;
            int i;
            for (i = 0; i < pre_len; i++)
                if (key.charAt(i) != prefix.charAt(i))
                    break;
            if (i < pre_len)
                continue;
            KeyVersion keyVersion = new KeyVersion(key);
            keyList.add(keyVersion);
        }
        return this.deleteObjects2(client, bucketName, keyList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AmazonS3 getAmazonS3Client(com.amazonaws.regions.Region region) {
        // if (StringUtil.isEmpty(credentialsPath)) {
        // credentialsPath = "config/AwsCredentials.properties";
        // }
        // AWSCredentialsProvider provider = new
        // ClasspathPropertiesFileCredentialsProvider(credentialsPath);
        // AmazonS3Client amazonS3Client = new AmazonS3Client(provider);
        amazonS3.setRegion(region);
        return amazonS3;
    }

    private PutObjectResult putObject(AmazonS3 client, String bucketName, String key, File file, Grantee grantee, Permission permission, Grant... grantsVarArg) {
        if (client == null) {
            return null;
        } else if (StringUtil.isEmpty(bucketName)) {
            return null;
        } else if (StringUtil.isEmpty(key)) {
            return null;
        } else if (file == null) {
            return null;
        } else if ((grantee == null || permission == null) && (grantsVarArg == null || grantsVarArg.length < 1)) {
            return null;
        }
        PutObjectResult result = null;
        AccessControlList accessControlList = new AccessControlList();
        if (grantee != null && permission != null) {
            accessControlList.grantPermission(grantee, permission);
        }
        if (grantsVarArg != null && grantsVarArg.length > 0) {
            accessControlList.grantAllPermissions(grantsVarArg);
        }
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file).withAccessControlList(accessControlList);
        result = client.putObject(putObjectRequest);
        return result;
    }

    private PutObjectResult putObject(AmazonS3 client, String bucketName, String key, InputStream input, ObjectMetadata metadata, Grantee grantee, Permission permission, Grant... grantsVarArg) {
        if (client == null) {
            return null;
        } else if (StringUtil.isEmpty(bucketName)) {
            return null;
        } else if (StringUtil.isEmpty(key)) {
            return null;
        } else if (input == null) {
            return null;
        } else if (metadata == null) {
            return null;
        } else if ((grantee == null || permission == null) && (grantsVarArg == null || grantsVarArg.length < 1)) {
            return null;
        }
        PutObjectResult result = null;
        AccessControlList accessControlList = new AccessControlList();
        if (grantee != null && permission != null) {
            accessControlList.grantPermission(grantee, permission);
        }
        if (grantsVarArg != null && grantsVarArg.length > 0) {
            accessControlList.grantAllPermissions(grantsVarArg);
        }
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, input, metadata).withAccessControlList(accessControlList);
        result = client.putObject(putObjectRequest);
        return result;
    }
}

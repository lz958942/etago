/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : SNSNotificationHandlerForCloudSearch.java
 * @Package : net.lizhaoweb.aws.plugin.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月29日
 * @Time : 下午5:20:35
 */
package net.lizhaoweb.aws.api.service.impl;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.easarrive.aws.model.s3.*;
import com.easarrive.aws.plugins.common.exception.AWSPluginException;
import com.easarrive.aws.plugins.common.model.NotificationHandleResult;
import com.easarrive.aws.plugins.common.model.SNSMessage;
import com.easarrive.aws.plugins.common.service.INotificationHandler;
import com.easarrive.aws.plugins.common.service.IS3Service;
import com.easarrive.aws.plugins.common.util.Constant.SNS.HTTP4AWS.Response.Message.AWSEvent.Name;
import com.easarrive.aws.plugins.common.util.Constant.SNS.HTTP4AWS.Response.Message.AWSEvent.Source;
import com.easarrive.aws.plugins.common.util.Constant.SNS.HTTP4AWS.Response.Message.AWSEvent.Version;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Setter;
import net.lizhaoweb.common.util.base.JsonUtil;
import net.lizhaoweb.common.util.base.StringUtil;
import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
public class SNSNotificationHandlerForCloudSearch implements INotificationHandler<SNSMessage, Boolean> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Setter
    private String topicArn;

    @Setter
    private IS3Service s3Service;

    @Override
    public List<NotificationHandleResult<SNSMessage, Boolean>> handle(SNSMessage message) throws AWSPluginException {
        List<NotificationHandleResult<SNSMessage, Boolean>> resultList = new ArrayList<NotificationHandleResult<SNSMessage, Boolean>>();
        try {
            if (StringUtil.isEmpty(topicArn)) {
                throw new AWSPluginException("The topicArn is null");
            }
            if (!topicArn.equals(message.getTopicArn())) {
                throw new AWSPluginException("The topicArn is empty");
            }
            S3EventMessageContent messageContent = this.getMessageContent(message.getMessage());
            if (messageContent == null) {
                throw new AWSPluginException("The S3EventMessageContent is null");
            }
            List<S3EventMessageRecord> records = messageContent.getRecords();
            if (records == null) {
                throw new AWSPluginException("The S3EventMessageRecord list is null");
            }
            if (records.isEmpty()) {
                throw new AWSPluginException("The S3EventMessageRecord list is empty");
            }
            for (S3EventMessageRecord snsMessageRecord : records) {
                if (!this.isRecordValid(snsMessageRecord)) {
                    continue;
                }
                Region region = this.getRegion(snsMessageRecord);
                if (region == null) {
                    continue;
                }
                String bucketName = this.getBucketName(snsMessageRecord);
                if (StringUtil.isEmpty(bucketName)) {
                    continue;
                }
                String key = this.getObjectKey(snsMessageRecord);
                if (StringUtil.isEmpty(key)) {
                    continue;
                }
                AmazonS3 client = s3Service.getAmazonS3Client(region);
                S3Object s3Object = s3Service.getObject(client, bucketName, key);
                if (s3Object == null) {
                    continue;
                }
                String accessUrl = this.getAccessUrl(s3Object);
                System.out.println(accessUrl);
            }
            resultList.add(new NotificationHandleResult<SNSMessage, Boolean>(message.getMessageId(), message, true));
            return resultList;
        } catch (Exception e) {
            throw new AWSPluginException(e.getMessage(), e);
        }
    }

    private String getAccessUrl(S3Object s3Object) {
        if (s3Object == null) {
            return null;
        }
        S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
        if (s3ObjectInputStream == null) {
            return null;
        }
        HttpRequestBase httpRequestBase = s3ObjectInputStream.getHttpRequest();
        if (httpRequestBase == null) {
            return null;
        }
        URI uri = httpRequestBase.getURI();
        if (uri == null) {
            return null;
        }
        String accessUrl = uri.toString();
        return accessUrl;
    }

    private String getObjectKey(S3EventMessageRecord snsMessageRecord) {
        if (snsMessageRecord == null) {
            return null;
        }
        S3EventMessageS3 messageS3 = snsMessageRecord.getS3();
        if (messageS3 == null) {
            return null;
        }
        S3EventMessageS3Object messageS3Object = messageS3.getObject();
        if (messageS3Object == null) {
            return null;
        }
        String key = messageS3Object.getKey();
        return key;
    }

    private String getBucketName(S3EventMessageRecord snsMessageRecord) {
        if (snsMessageRecord == null) {
            return null;
        }
        S3EventMessageS3 messageS3 = snsMessageRecord.getS3();
        if (messageS3 == null) {
            return null;
        }
        S3EventMessageS3Bucket bucket = messageS3.getBucket();
        if (bucket == null) {
            return null;
        }
        String bucketName = bucket.getName();
        return bucketName;
    }

    private Region getRegion(S3EventMessageRecord snsMessageRecord) {
        if (snsMessageRecord == null) {
            return null;
        }
        String awsRegion = snsMessageRecord.getAwsRegion();
        if (StringUtil.isEmpty(awsRegion)) {
            return null;
        }
        Regions regions = null;
        try {
            regions = Regions.fromName(awsRegion);
        } catch (Exception e) {
            return null;
        }
        Region region = Region.getRegion(regions);
        return region;
    }

    private boolean isRecordValid(S3EventMessageRecord snsMessageRecord) {
        boolean result = false;
        if (snsMessageRecord == null) {
            return false;
        }
        Version version = Version.fromValue(snsMessageRecord.getEventVersion());
        Source source = Source.fromValue(snsMessageRecord.getEventSource());
        Name name = Name.fromValue(snsMessageRecord.getEventName());
        if (Version.V2_0 == version && Source.S3 == source && Name.ObjectCreatedPut == name) {
            result = true;
        }
        return result;
    }

    private S3EventMessageContent getMessageContent(String message) {
        S3EventMessageContent s3EventMessageContent = null;
        try {
            if (StringUtil.isNotEmpty(message)) {
                s3EventMessageContent = JsonUtil.toBean(message, S3EventMessageContent.class);
            }
        } catch (JsonProcessingException e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
        return s3EventMessageContent;
    }
}

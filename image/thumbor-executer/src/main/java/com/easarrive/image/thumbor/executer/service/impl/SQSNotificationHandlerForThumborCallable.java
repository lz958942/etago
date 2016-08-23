/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.image.thumbor.executer.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 12:39
 */
package com.easarrive.image.thumbor.executer.service.impl;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.model.Message;
import com.easarrive.aws.model.s3.S3EventMessageRecord;
import com.easarrive.aws.model.s3.S3EventMessageS3;
import com.easarrive.aws.model.s3.S3EventMessageS3Bucket;
import com.easarrive.aws.model.s3.S3EventMessageS3Object;
import com.easarrive.aws.plugins.common.exception.AWSPluginException;
import com.easarrive.aws.plugins.common.model.NotificationHandleResult;
import com.easarrive.image.thumbor.IGeneratePicture;
import com.easarrive.image.thumbor.bean.ThumborRequest;
import com.easarrive.image.thumbor.executer.util.Constant;
import net.lizhaoweb.common.util.base.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年08月02日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
class SQSNotificationHandlerForThumborCallable implements Callable<NotificationHandleResult<Message, Boolean>> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private Message message;

    private S3EventMessageRecord s3EventMessageRecord;

    private IGeneratePicture generatePicture;

    SQSNotificationHandlerForThumborCallable(Message message, S3EventMessageRecord s3EventMessageRecord, IGeneratePicture generatePicture) {
        this.message = message;
        this.s3EventMessageRecord = s3EventMessageRecord;
        this.generatePicture = generatePicture;
    }

    @Override
    public NotificationHandleResult call() throws Exception {
        try {
            String messageId = message.getMessageId();
            if (!this.isRecordValid(s3EventMessageRecord)) {
                throw new AWSPluginException("The message {%s} is not valid", s3EventMessageRecord);
            }

            Region region = this.getRegion(s3EventMessageRecord);
            if (region == null) {
                throw new AWSPluginException("The S3 region is null");
            }

            String bucketName = this.getBucketName(s3EventMessageRecord);
            if (StringUtil.isEmpty(bucketName)) {
                throw new AWSPluginException("The S3 bucket name is null");
            }

            String key = this.getObjectKey(s3EventMessageRecord);
            if (StringUtil.isEmpty(key)) {
                throw new AWSPluginException("The S3 object key is null");
            }

            String accessUrl = String.format(Constant.SQS.S3.URL.Access.FORMAT, bucketName, key);

            String kindId = key.substring(0, key.lastIndexOf('/'));

            ThumborRequest thumborRequest = new ThumborRequest(messageId, accessUrl, kindId);
            boolean success = generatePicture.generate(thumborRequest);
            return new NotificationHandleResult(messageId, message, success);
        } catch (Exception e) {
            throw new AWSPluginException(e.getMessage(), e);
        }
    }

    private String getObjectKey(S3EventMessageRecord snsMessageRecord) throws Exception {
        S3EventMessageS3 messageS3 = snsMessageRecord.getS3();
        S3EventMessageS3Object messageS3Object = messageS3.getObject();
        String key = messageS3Object.getKey();
        return key;
    }

    private String getBucketName(S3EventMessageRecord snsMessageRecord) throws Exception {
        S3EventMessageS3 messageS3 = snsMessageRecord.getS3();
        S3EventMessageS3Bucket bucket = messageS3.getBucket();
        String bucketName = bucket.getName();
        return bucketName;
    }

    private Region getRegion(S3EventMessageRecord snsMessageRecord) throws Exception {
        String awsRegion = snsMessageRecord.getAwsRegion();
        Regions regions = Regions.fromName(awsRegion);
        Region region = Region.getRegion(regions);
        return region;
    }

    private boolean isRecordValid(S3EventMessageRecord snsMessageRecord) throws Exception {
        if (snsMessageRecord == null) {
            return false;
        }
        if (!Constant.S3EventMessage.VERSION.equals(snsMessageRecord.getEventVersion())) {
            return false;
        }
        if (!Constant.S3EventMessage.Source.S3.equals(snsMessageRecord.getEventSource())) {
            return false;
        }
        if (!Constant.S3EventMessage.EventName.OBJECT_PUT.equals(snsMessageRecord.getEventName())) {
            return false;
        }
        return true;
    }
}

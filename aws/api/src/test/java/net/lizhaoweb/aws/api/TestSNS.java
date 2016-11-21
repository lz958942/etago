/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Api
 * @Title : TestSNS.java
 * @Package : net.lizhaoweb.aws.api
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月28日
 * @Time : 下午9:34:20
 */
package net.lizhaoweb.aws.api;

import java.io.IOException;

import com.easarrive.aws.model.s3.S3EventMessageContent;
import com.easarrive.aws.plugins.common.model.SNSMessage;
import net.lizhaoweb.common.util.base.JsonUtil;

import org.junit.Test;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.ListSubscriptionsByTopicRequest;
import com.amazonaws.services.sns.model.ListSubscriptionsByTopicResult;
import com.amazonaws.services.sns.model.SetTopicAttributesRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年6月28日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class TestSNS {

    // 创建SNS主题
    @Test
    public void createTopic() {
        AWSCredentials credentials = new BasicAWSCredentials("AKIAIDPJMKK4UHLE3OVA", "A+cn+TT3tUs6xbto5k1IKkWwPLBq995aOkqKxZNY");
        AmazonSNSClient amazonSNSClient = new AmazonSNSClient(credentials);
        Region region = Region.getRegion(Regions.US_WEST_2);
        amazonSNSClient.setRegion(region);
        CreateTopicRequest ctr = new CreateTopicRequest("image-queuea");
        CreateTopicResult result = amazonSNSClient.createTopic(ctr);

        SetTopicAttributesRequest tar = new SetTopicAttributesRequest(result.getTopicArn(), "DisplayName", "MessageBoard");
        System.out.println(amazonSNSClient.setTopicAttributes(tar));
    }

    // 订阅主题
    @Test
    public void subscribingTopic() {
        AWSCredentials credentials = new BasicAWSCredentials("AKIAIDPJMKK4UHLE3OVA", "A+cn+TT3tUs6xbto5k1IKkWwPLBq995aOkqKxZNY");
        AmazonSNSClient amazonSNSClient = new AmazonSNSClient(credentials);
        Region region = Region.getRegion(Regions.US_WEST_2);
        amazonSNSClient.setRegion(region);

        SubscribeRequest sr = new SubscribeRequest("arn:aws:sns:us-west-2:286828993912:image-queue", "http", "http://lsavor.f3322.net:3084/mvc/test/sns");
        System.out.println(amazonSNSClient.subscribe(sr));
    }

    // 监听订阅
    @Test
    public void listingSubscribers() {
        AWSCredentials credentials = new BasicAWSCredentials("AKIAIDPJMKK4UHLE3OVA", "A+cn+TT3tUs6xbto5k1IKkWwPLBq995aOkqKxZNY");
        AmazonSNSClient amazonSNSClient = new AmazonSNSClient(credentials);
        Region region = Region.getRegion(Regions.US_WEST_2);
        amazonSNSClient.setRegion(region);

        ListSubscriptionsByTopicRequest ls = new ListSubscriptionsByTopicRequest("arn:aws:sns:us-west-2:286828993912:image-queue");
        ListSubscriptionsByTopicResult response = amazonSNSClient.listSubscriptionsByTopic(ls);
        System.out.println(response.getSubscriptions());
    }

    @Test
    public void result() {
        String json = "{  \"Type\" : \"Notification\",  \"MessageId\" : \"6e6104d7-d5f4-52f5-bbfe-4b3d39070b3e\",  \"TopicArn\" : \"arn:aws:sns:us-west-2:286828993912:image-queue\",  \"Subject\" : \"Amazon S3 Notification\",  \"Message\" : \"{\\\"Records\\\":[{\\\"eventVersion\\\":\\\"2.0\\\",\\\"eventSource\\\":\\\"aws:s3\\\",\\\"awsRegion\\\":\\\"us-west-2\\\",\\\"eventTime\\\":\\\"2016-06-27T08:58:27.815Z\\\",\\\"eventName\\\":\\\"ObjectCreated:Put\\\",\\\"userIdentity\\\":{\\\"principalId\\\":\\\"AFRDGXV7XRMK5\\\"},\\\"requestParameters\\\":{\\\"sourceIPAddress\\\":\\\"124.205.19.130\\\"},\\\"responseElements\\\":{\\\"x-amz-request-id\\\":\\\"E9F48FE4B472C7C9\\\",\\\"x-amz-id-2\\\":\\\"bkmsdl+ajZ2yeRcT7Gm/4eynK5ssk1efUcc/KKXMgNtB3il18B/7M3s6URhzKKTy\\\"},\\\"s3\\\":{\\\"s3SchemaVersion\\\":\\\"1.0\\\",\\\"configurationId\\\":\\\"test\\\",\\\"bucket\\\":{\\\"name\\\":\\\"etago-app-dev\\\",\\\"ownerIdentity\\\":{\\\"principalId\\\":\\\"AFRDGXV7XRMK5\\\"},\\\"arn\\\":\\\"arn:aws:s3:::etago-app-dev\\\"},\\\"object\\\":{\\\"key\\\":\\\"users/image/22.jpg\\\",\\\"size\\\":4006886,\\\"eTag\\\":\\\"229f0eddc267a22e02d938e294ebd7e5\\\",\\\"sequencer\\\":\\\"005770EAB3AAFFF321\\\"}}}]}\",  \"Timestamp\" : \"2016-06-27T08:58:27.931Z\",  \"SignatureVersion\" : \"1\",  \"Signature\" : \"DwqCL4/Z61n+KE3Nh9MsRGffsSgNvpwEt3tUStzo1660Kx5zz2PMo4ZJkpYAtG6jQe0Mc6V5z5FSD458BUYLsSYFKyP7QEvDyNfVp2J5B+LkIa4Nfz0ce1hRiG+R/zyd4aTkQHAmCnQqq9XN0eZcwjOLcqMbZdMgldZ5dZ+TG+SKZe3aOf38hJs519tbPF6yEXYgDZZL6/k94M21RPYRlh6bslfhCImVJmuo2phy4qzoTF9COuN1IUGfXJAQcNWJryB5IfGaa7+rmjKtq87rUNTtXSd0k3Yh5fI2VPxIJpGE0t1XbABwvAlQqtg5N22uq5kZ6cIxdO5pJYs2iE9fkw==\",  \"SigningCertURL\" : \"https://sns.us-west-2.amazonaws.com/SimpleNotificationService-bb750dd426d95ee9390147a5624348ee.pem\",  \"UnsubscribeURL\" : \"https://sns.us-west-2.amazonaws.com/?Action=Unsubscribe&SubscriptionArn=arn:aws:sns:us-west-2:286828993912:image-queue:4884bd88-309f-4ce2-aa0c-38ea65839d36\"}";
        try {
            SNSMessage message = JsonUtil.toBean(json, SNSMessage.class);
            System.out.println(message);

            S3EventMessageContent content = JsonUtil.toBean(message.getMessage(), S3EventMessageContent.class);
            System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

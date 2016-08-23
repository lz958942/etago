/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : TestSNSService.java
 * @Package : net.lizhaoweb.aws.plugin.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月28日
 * @Time : 下午9:28:45
 */
package com.easarrive.aws.plugins.common.service.impl;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.DeleteTopicResult;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeResult;
import com.easarrive.aws.plugins.common.model.SNSPublish2TopicRequest;
import com.easarrive.aws.plugins.common.model.SNSSubscribingTopicRequest;
import com.easarrive.aws.plugins.common.model.SNSTopicRequest;
import com.easarrive.aws.plugins.common.service.ISNSService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.lizhaoweb.common.util.base.JsonUtil;
import net.lizhaoweb.common.util.base.StringUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年6月28日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/schema/spring/spring-mysql_etago-model.xml", "classpath*:/schema/spring/spring-mysql_etago-datasource.xml", "classpath*:/schema/spring/spring-mysql_etago-mapper.xml", "classpath*:/schema/spring/spring-aws_plugin-model.xml", "classpath*:/schema/spring/spring-aws_plugin-service.xml"})
public class TestSNSService {

    @Autowired
    private ISNSService snsService;

    private String snsName;
    private String displayName;
    private String displayNameValue;
    private String topicArn;
    private String protocol;
    private String endpoint;
    private String message;
    private AmazonSNS client;

    // 初始化
    @Before
    public void init() {
        Region region = Region.getRegion(Regions.US_WEST_2);

        this.client = snsService.getAmazonSNSClient(region);
        this.snsName = "image-queuea";
        this.displayName = "DisplayName";
        this.displayNameValue = "MessageBoard";
        this.topicArn = "arn:aws:sns:us-west-2:286828993912:image-queuea";
        this.protocol = "http";
        this.endpoint = "http://lsavor.f3322.net:3084/mvc/test/sns";
        this.message = "My text published to SNS topic with email endpoint";
    }

    // 创建主题
    @Test
    public void createTopic() {
        SNSTopicRequest snsRequestTopic = new SNSTopicRequest(snsName, displayName, displayNameValue);
        CreateTopicResult result = snsService.createTopic(client, snsRequestTopic);
        System.out.println(result);
    }

    // 订阅主题
    @Test
    public void subscribingTopic() {
        SNSSubscribingTopicRequest subscribingTopicRequest = new SNSSubscribingTopicRequest(topicArn, protocol, endpoint);
        SubscribeResult result = snsService.subscribingTopic(client, subscribingTopicRequest);
        System.out.println(result);
    }

    // 向主题发布消息
    @Test
    public void publish2Topic() {
        SNSPublish2TopicRequest publish2TopicRequest = new SNSPublish2TopicRequest(topicArn, message);
        PublishResult result = snsService.publish2Topic(client, publish2TopicRequest);
        System.out.println(result);
    }

    // 删除主题
    @Test
    public void deleteTopic() {
        SNSTopicRequest snsRequestTopic = new SNSTopicRequest(topicArn);
        DeleteTopicResult result = snsService.deleteTopic(client, snsRequestTopic);
        System.out.println(result);
    }

    @Test
    public void aaa() {
        // String message =
        // "{\"Records\":[{\"eventVersion\":\"2.0\",\"eventSource\":\"aws:s3\",\"awsRegion\":\"us-west-2\",\"eventTime\":\"2016-06-28T12:59:55.700Z\",\"eventName\":\"ObjectCreated:Put\",\"userIdentity\":{\"principalId\":\"AFRDGXV7XRMK5\"},\"requestParameters\":{\"sourceIPAddress\":\"124.205.19.130\"},\"responseElements\":{\"x-amz-request-id\":\"CA17C6B0CA3B9D5B\",\"x-amz-id-2\":\"8OnEzpN3a49R+oHXc+BXDRGEaLRjgM9sygcuNhg+G2g6FNWxK9CkE1vaxJhc+WiW\"},\"s3\":{\"s3SchemaVersion\":\"1.0\",\"configurationId\":\"test\",\"bucket\":{\"name\":\"etago-app-dev\",\"ownerIdentity\":{\"principalId\":\"AFRDGXV7XRMK5\"},\"arn\":\"arn:aws:s3:::etago-app-dev\"},\"object\":{\"key\":\"users/image/22.jpg\",\"size\":4006886,\"eTag\":\"229f0eddc267a22e02d938e294ebd7e5\",\"sequencer\":\"00577274CB93A5D229\"}}}]}";
        String message = "{\"Service\":\"Amazon S3\",\"Event\":\"s3:TestEvent\",\"Time\":\"2016-06-29T09:35:00.087Z\",\"Bucket\":\"etago-app-dev\",\"RequestId\":\"3AFC2B9075E9D4F1\",\"HostId\":\"aNL0geUz8N1uvZwQZ9mEdphu3wOYpvYCt9FiHIDTKzAEUjF6xXuOzybqRItfVtLc\"}";
        ObjectMapper mapper = JsonUtil.getInstance();
        try {
            if (StringUtil.isEmpty(message)) {
                return;
            }
            JsonNode jsonNode = mapper.readTree(message);
            if (jsonNode == null) {
                return;
            }
            JsonNode recordsJsonNode = jsonNode.get("Records");
            if (recordsJsonNode == null) {
                return;
            }
            Iterator<JsonNode> recordsJsonNodeI = recordsJsonNode.elements();
            if (recordsJsonNodeI == null) {
                return;
            }
            while (recordsJsonNodeI.hasNext()) {
                JsonNode recordJsonNode = recordsJsonNodeI.next();
                if (recordJsonNode == null) {
                    continue;
                }
                JsonNode eventVersion = recordJsonNode.get("eventVersion");
                System.out.println(eventVersion.asText());
                JsonNode eventSource = recordJsonNode.get("eventSource");
                System.out.println(eventSource.asText());
                JsonNode eventName = recordJsonNode.get("eventName");
                System.out.println(eventName.asText());

            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

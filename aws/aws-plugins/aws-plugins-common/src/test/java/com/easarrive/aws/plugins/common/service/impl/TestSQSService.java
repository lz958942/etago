/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : TestSQSService.java
 * @Package : net.lizhaoweb.aws.plugin.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月1日
 * @Time : 下午12:52:25
 */
package com.easarrive.aws.plugins.common.service.impl;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.easarrive.aws.plugins.common.service.IS3Service;
import com.easarrive.aws.plugins.common.service.ISQSService;
import net.lizhaoweb.common.util.base.HttpClientUtil;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月1日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/schema/spring/spring-mysql_etago-model.xml", "classpath*:/schema/spring/spring-mysql_etago-datasource.xml", "classpath*:/schema/spring/spring-mysql_etago-mapper.xml", "classpath*:/schema/spring/spring-aws_plugin-model.xml", "classpath*:/schema/spring/spring-aws_plugin-service.xml"})
public class TestSQSService {

    @Autowired
    private ISQSService sqsService;

    @Autowired
    private IS3Service s3Service;

    private AmazonSQS client;
    private String queueUrl;
    private int maxNumberOfMessages;

    @Before
    public void init() {
        Region region = Region.getRegion(Regions.US_WEST_2);

        this.client = sqsService.getAmazonSQSClient(region);
        this.queueUrl = "user-avatars";
        this.maxNumberOfMessages = 10;
    }

    @Test
    public void testA() {
        try {
            String accessUrl = "http://imgsrc.baidu.com/forum/pic/item/c77a71cf3bc79f3d5268d8c7baa1cd11738b2906.jpg";
            String saveImageResize = "500x400";
            String url = String.format("http://192.168.1.15:5277/unsafe/%s/%s", saveImageResize, accessUrl);
            // String url =
            // String.format("http://192.168.199.139:5277/unsafe/%s/%s",
            // saveImageResize, accessUrl);

            String saveImageMainName = accessUrl.substring(accessUrl.lastIndexOf('/') + 1, accessUrl.lastIndexOf('.'));
            String saveImageExeName = accessUrl.substring(accessUrl.lastIndexOf('.') + 1);

            List<Header> userHeaderList = new ArrayList<Header>();
            userHeaderList.add(new BasicHeader("X-THUMBOR-STRONG-AWS-S3", String.format("images/small/user/%s_%s.%s", saveImageMainName, saveImageResize, saveImageExeName)));
            HttpResponse userHttpResponse = HttpClientUtil.head(url, userHeaderList);
            System.out.println(userHttpResponse.getStatusLine().getStatusCode());

            List<Header> goodsHeaderList = new ArrayList<Header>();
            goodsHeaderList.add(new BasicHeader("X-THUMBOR-STRONG-AWS-S3", String.format("images/small/goods/%s_%s.%s", saveImageMainName, saveImageResize, saveImageExeName)));
            HttpResponse httpResponse = HttpClientUtil.head(url, goodsHeaderList);
            System.out.println(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void receiveMessage() {
        boolean execute = true;
        while (execute) {
            ReceiveMessageResult result = sqsService.receiveMessage(client, queueUrl, maxNumberOfMessages);
            if (result == null) {
                execute = false;
            } else {
                List<Message> messageList = result.getMessages();
                if (messageList == null || messageList.isEmpty()) {
                    execute = false;
                } else {
                    for (Message message : messageList) {
                        //abc(message);
                    }
                }
            }
        }
    }
}

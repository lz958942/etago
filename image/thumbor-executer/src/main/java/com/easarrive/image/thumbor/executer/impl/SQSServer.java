/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor Image Thumbor Executer
 * @Title : SQSServer.java
 * @Package : com.easarrive.image.thumbor.executer.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月4日
 * @Time : 下午4:47:52
 */
package com.easarrive.image.thumbor.executer.impl;

import com.amazonaws.services.sqs.AmazonSQS;
import com.easarrive.aws.plugins.common.service.ISQSNotificationService;
import com.easarrive.aws.plugins.common.service.ISQSService;
import com.easarrive.image.thumbor.exception.ThumborException;
import com.easarrive.image.thumbor.executer.bean.Status;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月4日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class SQSServer extends AbstractSQSServer {

    @Setter
    private ISQSService sqsService;

    @Setter
    private ISQSNotificationService notificationService;

    private AmazonSQS client;

    @Override
    public void start() {
        this.setServerStatus(this.getServerStatus(), Status.START);
        client = sqsService.getAmazonSQSClient(region);
        this.setServerStatus(this.getServerStatus(), Status.RUNNING);
    }

    @Override
    public void stop() {
        this.setServerStatus(this.getServerStatus(), Status.STOP);
        client.shutdown();
    }

    @Override
    public void run() {
        boolean run = true;
        while (run) {
            switch (this.getServerStatus()) {
                case STARTING://正在启动
                    this.start();
                    break;
                case RUNNING:
                    //接收消息
                    this.receiveSQS(client);
                    break;
                case STOPPING://正在停止
                    this.stop();
                    run = false;
                    break;
                default:
                    break;
            }
        }
        this.destroy();
    }

    //接收消息
    private void receiveSQS(AmazonSQS client) {
        try {

            //获取 SQS 可见的消息数
            Long visibilityMessageCount = sqsService.getQueueApproximateNumberOfMessages(client, queueUrl);
            if (visibilityMessageCount == null) {
                throw new ThumborException("The SQS messge visibility count is null");
            }
            if (visibilityMessageCount == 0) {
                return;
            }

            //计算请求 SQS 的线程数
            int threadCountRequestSQS = 1;
            int remainder = (int) (visibilityMessageCount % maxNumberOfMessages);
            int multiple = (int) (visibilityMessageCount / maxNumberOfMessages);
            if (multiple < 1) {//位数小于1
                threadCountRequestSQS = 1;
            } else if (multiple >= maxThreadCountRequestSQS) {//倍数大于等于最大线程数
                threadCountRequestSQS = maxThreadCountRequestSQS;
            } else if (remainder > 0) {//位数大于0且小于最大线程数，并且余数大于0
                threadCountRequestSQS = multiple + 1;
            } else {//位数大于0且小于最大线程数，并且余数等于0
                threadCountRequestSQS = multiple;
            }

            //多线程读取 SQS 消息
            ExecutorService executorService = Executors.newCachedThreadPool();
            List<Future<Boolean>> resultList = new ArrayList<Future<Boolean>>();
            for (int index = 0; index < threadCountRequestSQS; index++) {
                Future<Boolean> future = executorService.submit(new RequestSQSCallable(sqsService, notificationService, region, queueUrl, maxNumberOfMessages, visibilityTimeout));
                resultList.add(future);
            }

            //遍历任务的结果
            for (Future<Boolean> fs : resultList) {
                try {
                    Boolean success = fs.get();
                    if (logger.isDebugEnabled()) {
                        logger.debug("\n\t|=======- The SQS message request result to handle is {}\n", success);
                    }
                } catch (Exception e) {
                    if (logger.isErrorEnabled()) {
                        logger.error(e.getMessage(), e);
                    }
                } finally {
                    //启动一次顺序关闭，执行以前提交的任务，但不接受新任务。如果已经关闭，则调用没有其他作用。
                    executorService.shutdown();
                }
            }

        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
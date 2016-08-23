/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.image.thumbor.executer.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 12:42
 */
package com.easarrive.image.thumbor.executer.impl;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.easarrive.image.thumbor.exception.ThumborException;
import com.easarrive.image.thumbor.executer.IServer;
import com.easarrive.image.thumbor.executer.bean.Status;
import com.easarrive.image.thumbor.executer.util.Constant;
import lombok.AccessLevel;
import lombok.Getter;
import net.lizhaoweb.common.util.base.StringUtil;
import net.lizhaoweb.spring.mvc.config.PropertyConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年08月02日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
abstract class AbstractSQSServer implements IServer {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 服务器状态
     */
    @Getter(value = AccessLevel.PROTECTED)
    private Status serverStatus;

    /**
     * 区域。
     */
    protected Region region;

    /**
     * 队列地址。
     */
    protected String queueUrl;

    /**
     * 最大接收消息数量。
     */
    protected Integer maxNumberOfMessages;

    /**
     * 处理消息最大线程数量。
     */
    protected Integer maxThreadCountRequestSQS;

    /**
     * SQS 读取消息时，隐藏时间(单位：s)
     */
    protected Integer visibilityTimeout;

    private void setServerStatus(Status serverStatus) {
        switch (serverStatus) {
            case INIT:
                if (logger.isInfoEnabled()) {
                    logger.info("Init SQSServer ...");
                }
                this.serverStatus = serverStatus;
                break;
            case STARTING:
                if (logger.isInfoEnabled()) {
                    logger.info("Starting SQSServer ...");
                }
                this.serverStatus = serverStatus;
                break;
            case START:
                if (logger.isInfoEnabled()) {
                    logger.info("Start SQSServer ...");
                }
                this.serverStatus = serverStatus;
                break;
            case RUNNING:
                if (logger.isInfoEnabled()) {
                    logger.info("Running SQSServer ...");
                }
                this.serverStatus = serverStatus;
                break;
            case STOP:
                if (logger.isInfoEnabled()) {
                    logger.info("Stop SQSServer ...");
                }
                this.serverStatus = serverStatus;
                break;
            case STOPPING:
                if (logger.isInfoEnabled()) {
                    logger.info("Stopping SQSServer ...");
                }
                this.serverStatus = serverStatus;
                break;
            case DESTROY:
                if (logger.isInfoEnabled()) {
                    logger.info("Destroy SQSServer ...");
                }
                break;
            default:
                if (logger.isInfoEnabled()) {
                    logger.info("Unknow server status");
                }
                break;
        }
    }

    protected void setServerStatus(Status curStatus, Status tarStatus) {
        switch (curStatus) {
            case INIT:
                if (Status.STARTING == tarStatus) {
                    this.setServerStatus(Status.STARTING);
                } else {
                    this.setServerStatus(Status.CANNOTSET, tarStatus);
                }
                break;
            case STARTING:
                if (Status.START == tarStatus) {
                    this.setServerStatus(Status.START);
                } else {
                    this.setServerStatus(Status.CANNOTSET, tarStatus);
                }
                break;
            case START:
                if (Status.RUNNING == tarStatus) {
                    this.setServerStatus(Status.RUNNING);
                } else {
                    this.setServerStatus(Status.CANNOTSET, tarStatus);
                }
                break;
            case RUNNING:
                if (Status.STOPPING == tarStatus) {
                    this.setServerStatus(Status.STOPPING);
                } else {
                    this.setServerStatus(Status.CANNOTSET, tarStatus);
                }
                break;
            case STOPPING:
                if (Status.STOP == tarStatus) {
                    this.setServerStatus(Status.STOP);
                } else {
                    this.setServerStatus(Status.CANNOTSET, tarStatus);
                }
                break;
            case STOP:
                if (Status.DESTROY == tarStatus) {
                    this.setServerStatus(Status.DESTROY);
                } else if (Status.STARTING == tarStatus) {
                    this.setServerStatus(Status.STARTING);
                } else {
                    this.setServerStatus(Status.CANNOTSET, tarStatus);
                }
                break;
            case DESTROY:
                this.setServerStatus(Status.DESTROY);
                break;
            case CANNOTSET:
                if (logger.isInfoEnabled()) {
                    logger.info("Can not set SQSServer status to {}", tarStatus);
                }
                break;
            default:
                if (logger.isInfoEnabled()) {
                    logger.info("Unknow server status ({})", curStatus);
                }
                break;
        }
    }

    @Override
    public void init() {
        this.setServerStatus(Status.INIT);
        try {
            // 设置区域
            String regionString = PropertyConfigurer.getPropertyValue(Constant.SQS.Property.REGION.getKey());
            if (StringUtil.isEmpty(regionString)) {
                throw new ThumborException("SQS region is null or empty");
            }
            Regions regions = Regions.fromName(regionString);
            Region region = Region.getRegion(regions);

            // 设置SQS名称
            String queueUrlString = PropertyConfigurer.getPropertyValue(Constant.SQS.Property.QUEUE_URL.getKey());
            if (StringUtil.isEmpty(queueUrlString)) {
                throw new ThumborException("SQS queueUrl is null or empty");
            }

            // 设置接收消息的最大数量
            String maxNumberOfMessagesString = PropertyConfigurer.getPropertyValue(Constant.SQS.Property.MAX_NUMBER_OF_MESSAGES.getKey());
            if (StringUtil.isEmpty(maxNumberOfMessagesString) || !maxNumberOfMessagesString.matches("^\\d+$")) {
                maxNumberOfMessagesString = Constant.SQS.Property.MAX_NUMBER_OF_MESSAGES.getValue();
            }
            Integer maxNumberOfMessages = Integer.valueOf(maxNumberOfMessagesString);

            // 设置处理消息最大线程数量
            String maxThreadOfMessagesString = PropertyConfigurer.getPropertyValue(Constant.SQS.Property.MAX_THREAD_OF_MESSAGES.getKey());
            if (StringUtil.isEmpty(maxThreadOfMessagesString) || !maxThreadOfMessagesString.matches("^\\d+$")) {
                maxThreadOfMessagesString = Constant.SQS.Property.MAX_THREAD_OF_MESSAGES.getValue();
            }
            Integer maxThreadCountRequestSQS = Integer.valueOf(maxThreadOfMessagesString);

            // SQS 读取消息时，隐藏时间(单位：s)
            String visibilityTimeoutString = PropertyConfigurer.getPropertyValue(Constant.SQS.Property.VISIBILITY_TIMEOUT.getKey());
            if (StringUtil.isEmpty(maxThreadOfMessagesString) || !maxThreadOfMessagesString.matches("^\\d+$")) {
                visibilityTimeoutString = Constant.SQS.Property.VISIBILITY_TIMEOUT.getValue();
            }
            Integer visibilityTimeout = Integer.valueOf(visibilityTimeoutString);

            this.region = region;
            this.queueUrl = queueUrlString;
            this.maxNumberOfMessages = maxNumberOfMessages;
            this.maxThreadCountRequestSQS = maxThreadCountRequestSQS;
            this.visibilityTimeout = visibilityTimeout;
            this.setServerStatus(this.getServerStatus(), Status.STARTING);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            System.exit(0);
        }
    }

    @Override
    public void destroy() {
        this.setServerStatus(Status.DESTROY);
    }
}

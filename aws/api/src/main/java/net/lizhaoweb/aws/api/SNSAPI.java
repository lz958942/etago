/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Api
 * @Title : SNSAPI.java
 * @Package : net.lizhaoweb.aws.api
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月28日
 * @Time : 下午9:31:12
 */
package net.lizhaoweb.aws.api;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.*;
import com.easarrive.aws.plugins.common.model.*;
import com.easarrive.aws.plugins.common.service.ISNSNotificationService;
import com.easarrive.aws.plugins.common.service.ISNSService;
import com.easarrive.aws.plugins.common.util.Constant.SNS.HTTP4AWS.Request.Header.HeaderKey;
import com.easarrive.aws.plugins.common.util.Constant.SNS.HTTP4AWS.Response.Message.Type;
import lombok.Setter;
import net.lizhaoweb.aws.api.util.Constant.API.SpringMVC.PathVariable.PathVariableKey;
import net.lizhaoweb.common.util.base.StringUtil;
import net.lizhaoweb.spring.mvc.core.bean.DataDeliveryWrapper;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年6月28日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@RequestMapping("/sns")
public class SNSAPI {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Setter
    private ISNSService snsService;

    @Setter
    private ISNSNotificationService snsNotificationService;

    /**
     * 创建主题。
     *
     * @param regionString 区域，
     * @param topicRequest 请求。
     * @return 返回操作信息。
     */
    @RequestMapping(value = "/{" + PathVariableKey.REGION + "}/topic", method = {RequestMethod.POST})
    @ResponseBody
    public DataDeliveryWrapper<CreateTopicResult> createTopic(@PathVariable(PathVariableKey.REGION) String regionString, @ModelAttribute("topic") SNSTopicRequest topicRequest) {
        if (StringUtil.isEmpty(regionString)) {
            return new DataDeliveryWrapper<CreateTopicResult>(HttpStatus.SC_NOT_FOUND, "区域为空", null);
        } else if (StringUtil.isEmpty(topicRequest.getSnsName())) {
            return new DataDeliveryWrapper<CreateTopicResult>(HttpStatus.SC_NOT_FOUND, "主题名为空", null);
        }
        Regions regions = null;
        try {
            regions = Regions.fromName(regionString);
        } catch (Exception e) {
            return new DataDeliveryWrapper<CreateTopicResult>(HttpStatus.SC_FORBIDDEN, "区域错误", null);
        }
        if (topicRequest.getAttributeName() == null) {
            topicRequest.setAttributeName("");
        }
        if (topicRequest.getAttributeValue() == null) {
            topicRequest.setAttributeValue("");
        }
        try {
            Region region = Region.getRegion(regions);
            topicRequest.setRegion(region);
            AmazonSNS client = snsService.getAmazonSNSClient(region);
            CreateTopicResult result = snsService.createTopic(client, topicRequest);
            return new DataDeliveryWrapper<CreateTopicResult>(HttpStatus.SC_OK, "创建主题成功", result);
        } catch (Exception e) {
            String errorMessage = "创建主题失败";
            logger.error(errorMessage, e);
            return new DataDeliveryWrapper<CreateTopicResult>(HttpStatus.SC_INTERNAL_SERVER_ERROR, errorMessage, null);
        }
    }

    /**
     * 订阅主题。
     *
     * @param regionString            区域，
     * @param subscribingTopicRequest 请求。
     * @return 返回操作信息。
     */
    @RequestMapping(value = "/{" + PathVariableKey.REGION + "}/topic/subscribing", method = {RequestMethod.POST})
    @ResponseBody
    public DataDeliveryWrapper<SubscribeResult> subscribingTopic(@PathVariable(PathVariableKey.REGION) String regionString, @ModelAttribute("subscribingTopic") SNSSubscribingTopicRequest subscribingTopicRequest) {
        if (StringUtil.isEmpty(regionString)) {
            return new DataDeliveryWrapper<SubscribeResult>(HttpStatus.SC_NOT_FOUND, "区域为空", null);
        } else if (StringUtil.isEmpty(subscribingTopicRequest.getTopicArn())) {
            return new DataDeliveryWrapper<SubscribeResult>(HttpStatus.SC_NOT_FOUND, "主题资源为空", null);
        } else if (StringUtil.isEmpty(subscribingTopicRequest.getProtocol())) {
            return new DataDeliveryWrapper<SubscribeResult>(HttpStatus.SC_NOT_FOUND, "订阅协议为空", null);
        } else if (StringUtil.isEmpty(subscribingTopicRequest.getEndpoint())) {
            return new DataDeliveryWrapper<SubscribeResult>(HttpStatus.SC_NOT_FOUND, "消息接收节点为空", null);
        }
        Regions regions = null;
        try {
            regions = Regions.fromName(regionString);
        } catch (Exception e) {
            return new DataDeliveryWrapper<SubscribeResult>(HttpStatus.SC_FORBIDDEN, "区域错误", null);
        }
        try {
            Region region = Region.getRegion(regions);
            subscribingTopicRequest.setRegion(region);
            AmazonSNS client = snsService.getAmazonSNSClient(region);
            SubscribeResult result = snsService.subscribingTopic(client, subscribingTopicRequest);
            return new DataDeliveryWrapper<SubscribeResult>(HttpStatus.SC_OK, "订阅主题成功", result);
        } catch (Exception e) {
            String errorMessage = "订阅主题失败";
            logger.error(errorMessage, e);
            return new DataDeliveryWrapper<SubscribeResult>(HttpStatus.SC_INTERNAL_SERVER_ERROR, errorMessage, null);
        }
    }

    /**
     * 向主题发布消息。
     *
     * @param regionString         区域，
     * @param publish2TopicRequest 请求。
     * @return 返回操作信息。
     */
    @RequestMapping(value = "/{" + PathVariableKey.REGION + "}/topic", method = {RequestMethod.PUT})
    @ResponseBody
    public DataDeliveryWrapper<PublishResult> publish2Topic(@PathVariable(PathVariableKey.REGION) String regionString, @ModelAttribute("publish2Topic") SNSPublish2TopicRequest publish2TopicRequest) {
        if (StringUtil.isEmpty(regionString)) {
            return new DataDeliveryWrapper<PublishResult>(HttpStatus.SC_NOT_FOUND, "区域为空", null);
        } else if (StringUtil.isEmpty(publish2TopicRequest.getTopicArn())) {
            return new DataDeliveryWrapper<PublishResult>(HttpStatus.SC_NOT_FOUND, "主题资源为空", null);
        } else if (StringUtil.isEmpty(publish2TopicRequest.getMessage())) {
            return new DataDeliveryWrapper<PublishResult>(HttpStatus.SC_NOT_FOUND, "消息内容为空", null);
        }
        Regions regions = null;
        try {
            regions = Regions.fromName(regionString);
        } catch (Exception e) {
            return new DataDeliveryWrapper<PublishResult>(HttpStatus.SC_FORBIDDEN, "区域错误", null);
        }
        try {
            Region region = Region.getRegion(regions);
            publish2TopicRequest.setRegion(region);
            AmazonSNS client = snsService.getAmazonSNSClient(region);
            PublishResult result = snsService.publish2Topic(client, publish2TopicRequest);
            return new DataDeliveryWrapper<PublishResult>(HttpStatus.SC_OK, "向主题发布消息成功", result);
        } catch (Exception e) {
            String errorMessage = "向主题发布消息失败";
            logger.error(errorMessage, e);
            return new DataDeliveryWrapper<PublishResult>(HttpStatus.SC_INTERNAL_SERVER_ERROR, errorMessage, null);
        }
    }

    /**
     * 删除主题。
     *
     * @param regionString 区域，
     * @param topicRequest 请求。
     * @return 返回操作信息。
     */
    @RequestMapping(value = "/{" + PathVariableKey.REGION + "}/topic", method = {RequestMethod.DELETE})
    @ResponseBody
    public DataDeliveryWrapper<DeleteTopicResult> deleteTopic(@PathVariable(PathVariableKey.REGION) String regionString, @ModelAttribute("topic") SNSTopicRequest topicRequest) {
        if (StringUtil.isEmpty(regionString)) {
            return new DataDeliveryWrapper<DeleteTopicResult>(HttpStatus.SC_NOT_FOUND, "区域为空", null);
        } else if (StringUtil.isEmpty(topicRequest.getTopicArn())) {
            return new DataDeliveryWrapper<DeleteTopicResult>(HttpStatus.SC_NOT_FOUND, "主题资源为空", null);
        }
        Regions regions = null;
        try {
            regions = Regions.fromName(regionString);
        } catch (Exception e) {
            return new DataDeliveryWrapper<DeleteTopicResult>(HttpStatus.SC_FORBIDDEN, "区域错误", null);
        }
        try {
            Region region = Region.getRegion(regions);
            topicRequest.setRegion(region);
            // DeleteTopicResult deleteTopicResult =
            AmazonSNS client = snsService.getAmazonSNSClient(region);
            snsService.deleteTopic(client, topicRequest);
            return new DataDeliveryWrapper<DeleteTopicResult>(HttpStatus.SC_OK, "删除主题成功", null);
        } catch (Exception e) {
            String errorMessage = "删除主题失败";
            logger.error(errorMessage, e);
            return new DataDeliveryWrapper<DeleteTopicResult>(HttpStatus.SC_INTERNAL_SERVER_ERROR, errorMessage, null);
        }
    }

    /**
     * 删除主题。
     *
     * @param regionString            区域，
     * @param platformEndpointRequest 平台节点。
     * @return 返回操作信息。
     */
    @RequestMapping(value = "/{" + PathVariableKey.REGION + "}/platform_endpoint", method = {RequestMethod.POST})
    @ResponseBody
    public DataDeliveryWrapper<CreatePlatformEndpointResult> deleteTopic(@PathVariable(PathVariableKey.REGION) String regionString, @ModelAttribute("platformEndpoint") SNSPlatformEndpointRequest platformEndpointRequest) {
        if (StringUtil.isEmpty(regionString)) {
            return new DataDeliveryWrapper<CreatePlatformEndpointResult>(HttpStatus.SC_NOT_FOUND, "区域为空", null);
        } else if (StringUtil.isEmpty(platformEndpointRequest.getToken())) {
            return new DataDeliveryWrapper<CreatePlatformEndpointResult>(HttpStatus.SC_NOT_FOUND, "令牌为空", null);
        } else if (StringUtil.isEmpty(platformEndpointRequest.getPlatformApplicationArn())) {
            return new DataDeliveryWrapper<CreatePlatformEndpointResult>(HttpStatus.SC_NOT_FOUND, "资源名称为空", null);
        }
        Regions regions = null;
        try {
            regions = Regions.fromName(regionString);
        } catch (Exception e) {
            return new DataDeliveryWrapper<CreatePlatformEndpointResult>(HttpStatus.SC_FORBIDDEN, "区域错误", null);
        }
        try {
            Region region = Region.getRegion(regions);
            platformEndpointRequest.setRegion(region);
            AmazonSNS client = snsService.getAmazonSNSClient(region);
            // DeleteTopicResult deleteTopicResult =
            CreatePlatformEndpointResult result = snsService.createPlatformEndpoint(client, platformEndpointRequest);
            return new DataDeliveryWrapper<CreatePlatformEndpointResult>(HttpStatus.SC_OK, "创建平台终点成功", result);
        } catch (Exception e) {
            String errorMessage = "创建平台终点失败";
            logger.error(errorMessage, e);
            return new DataDeliveryWrapper<CreatePlatformEndpointResult>(HttpStatus.SC_INTERNAL_SERVER_ERROR, errorMessage, null);
        }
    }

    /**
     * 监听 SNS 消息。
     *
     * @param messageTypeFromHeader 消息类型。
     * @param bodyJson              请求体。
     * @return 返回操作信息。
     */
    @RequestMapping(value = "/topic/monitoring", method = {RequestMethod.POST})
    @ResponseBody
    public DataDeliveryWrapper<?> monitoringTopic(@RequestHeader(HeaderKey.X_AMZ_SNS_MESSAGE_TYPE) String messageTypeFromHeader, @RequestBody String bodyJson) {
        // If message doesn't have the message type header, don't process it.
        Type messageType = Type.fromName(messageTypeFromHeader);
        if (messageType == Type.UNKOWN) {
            return new DataDeliveryWrapper<DeleteTopicResult>(HttpStatus.SC_NOT_FOUND, "未知消息类型", null);
        }

        SNSMessage msg = snsNotificationService.getMessage(bodyJson);
        if (msg == null) {
            return new DataDeliveryWrapper<DeleteTopicResult>(HttpStatus.SC_NOT_FOUND, "无法获取消息对象", null);
        }

        // 验证签名版本是否有效。
        if (!snsNotificationService.isSignatureVersionValid(msg)) {
            return new DataDeliveryWrapper<DeleteTopicResult>(HttpStatus.SC_FORBIDDEN, "非法请求", null);
        }

        try {
            // Process the message based on type.
            switch (messageType) {
                case NOTIFICATION:
                    snsNotificationService.handleNotification(msg);
                    break;
                case SUBSCRIPTION_CONFIRMATION:
                    snsNotificationService.subscriptionConfirmation(msg);
                    break;
                case UNSUBSCRIBE_CONFIRMATION:
                    snsNotificationService.unsubscribeConfirmation(msg);
                    break;
                default:
                    // Handle unknown message type.
                    if (logger.isInfoEnabled()) {
                        logger.info(">>Unknown message type.");
                    }
            }
            if (logger.isInfoEnabled()) {
                logger.info(">>Done processing message: " + msg.getMessageId());
            }
            return new DataDeliveryWrapper<DeleteTopicResult>(HttpStatus.SC_OK, "成功接收消息", null);
        } catch (Exception e) {
            String errorMessage = "消息监听异常";
            if (logger.isErrorEnabled()) {
                logger.error(errorMessage, e);
            }
            return new DataDeliveryWrapper<DeleteTopicResult>(HttpStatus.SC_INTERNAL_SERVER_ERROR, errorMessage, null);
        }
    }
}

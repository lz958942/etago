/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor Image Thumbor
 * @Title : ConfigureFactory.java
 * @Package : com.easarrive.image.thumbor
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月21日
 * @Time : 下午12:49:48
 */
package com.easarrive.image.thumbor;

import com.easarrive.image.thumbor.bean.*;
import com.easarrive.image.thumbor.exception.ThumborException;
import net.lizhaoweb.common.util.base.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>工厂 - 配置</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年6月21日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class ConfigureFactory {

    private static Logger logger = LoggerFactory.getLogger(ConfigureFactory.class);

    /**
     * 配置对象
     */
    private static ThumborConfigure configure;

    /**
     * 配置加载器
     */
    private static IConfigureLoader configureLoader;

    //图片配置上下文字典
    private static ThumborConfigureMap thumborConfigureMap;

//    /**
//     * 获得配置对象。
//     *
//     * @return
//     */
//    public synchronized static ThumborConfigure getConfigure() {
//        try {
//            if (configure == null) {
//                configure = loadConfigure(configureLoader);
//                thumborConfigureMap = initThumborContextMap(configure);
//            }
//            if (logger.isInfoEnabled()) {
//                logger.info("The configure : {}", configure);
//            }
//            return configure;
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage(), e);
//        }
//    }

    /**
     * 获得配置对象。
     *
     * @return ThumborConfigureMap
     */
    public synchronized static ThumborConfigureMap getConfigureMap() {
        try {
            if (thumborConfigureMap == null) {
                configure = loadConfigure(configureLoader);
                thumborConfigureMap = initThumborContextMap(configure);
            }
            if (logger.isInfoEnabled()) {
                logger.info("The thumborConfigureMap : {}", thumborConfigureMap);
            }
            return thumborConfigureMap;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * @param configureLoader the configureLoader to set
     */
    public void setConfigureLoader(IConfigureLoader configureLoader) {
        if (logger.isDebugEnabled()) {
            logger.debug("Set configureLoader : {}", configureLoader);
        }
        ConfigureFactory.configureLoader = configureLoader;
    }

    /**
     * 清除
     */
    public static void clear() {
        thumborConfigureMap.clear();
        configure = null;
    }

    //加载并获取图片配置对象
    private static ThumborConfigure loadConfigure(IConfigureLoader configureLoader) {
        ThumborConfigure configure = null;
        configureLoader.load();
        configure = configureLoader.getConfigure();
        return configure;
    }

    //初始化图片配置上下文字典
    private static ThumborConfigureMap initThumborContextMap(ThumborConfigure configure) throws ThumborException {
        ThumborConfigureMap thumborContextKindMap = new ThumborConfigureMap();
        if (configure == null) {//配置对象不存在
            throw new ThumborException("The configure is null for message");
        }
        if (configure.getServer() == null) {//默认图片处理服务器对象
            throw new ThumborException("The thumbor server is null for message");
        }
        if (StringUtil.isEmpty(configure.getServer().getUrl())) {//默认图片处理服务器地址
            throw new ThumborException("The thumbor server url is null for message");
        }
        ThumborContext thumborContext = new ThumborContext();
        thumborContext.setServer(configure.getServer());
        thumborContext.setWatermark(configure.getWatermark());
        thumborContext.setAlign(configure.getAlign());
        thumborContext.setMeta(configure.isMeta());
        thumborContext.setSmart(configure.isSmart());
        thumborContext.setTrim(configure.isTrim());
        for (ThumborConfigureKind thumborConfigureKind : configure.getKindList()) {
            ThumborContext thumborContextKind = thumborContext.clone();
            String kindId = thumborConfigureKind.getId();
            Map<String, ThumborContext> thumborContextSeparateMap = initThumborContextKindMap(thumborConfigureKind, thumborContextKind);
            thumborContextKindMap.put(kindId, thumborContextSeparateMap);
        }
        return thumborContextKindMap;
    }

    //种类
    private static Map<String, ThumborContext> initThumborContextKindMap(ThumborConfigureKind thumborConfigureKind, ThumborContext thumborContextKind) {
        Map<String, ThumborContext> thumborContextSeparateMap = new HashMap<String, ThumborContext>();
        if (thumborConfigureKind == null) {
            return thumborContextSeparateMap;
        }
        if (thumborContextKind == null) {
            return thumborContextSeparateMap;
        }
//        thumborContextKind.setId();
        if (thumborConfigureKind.getServer() != null && StringUtil.isNotEmpty(thumborConfigureKind.getServer().getUrl())) {
            thumborContextKind.setServer(thumborConfigureKind.getServer());
        }
        if (thumborConfigureKind.getWatermark() != null && StringUtil.isNotEmpty(thumborConfigureKind.getWatermark().getUrl())) {
            thumborContextKind.setWatermark(thumborConfigureKind.getWatermark());
        }
        thumborContextKind.setSource(thumborConfigureKind.getSource());
        thumborContextKind.setTarget(thumborConfigureKind.getTarget());
        for (ThumborConfigureSeparate thumborConfigureSeparate : thumborConfigureKind.getSeparateList()) {
            ThumborContext thumborContextSeparate = thumborContextKind.clone();
            String separateId = String.format("%dx%d", thumborConfigureSeparate.getWidth(), thumborConfigureSeparate.getHeight());
//            String separateId = String.format("%dx%d_%s", thumborConfigureSeparate.getWidth(), thumborConfigureSeparate.getHeight(), thumborConfigureSeparate.getSuffix());
            thumborContextSeparate.setWidth(thumborConfigureSeparate.getWidth());
            thumborContextSeparate.setHeight(thumborConfigureSeparate.getHeight());
            thumborContextSeparate.setFormat(thumborConfigureSeparate.getFormat());
            thumborContextSeparate.setQuality(thumborConfigureSeparate.getQuality());
            thumborContextSeparate.setRoundCorner(thumborConfigureSeparate.getRoundCorner());
            thumborContextSeparate.setSuffix(thumborConfigureSeparate.getSuffix());
            thumborContextSeparateMap.put(separateId, thumborContextSeparate);
        }
        return thumborContextSeparateMap;
    }
}

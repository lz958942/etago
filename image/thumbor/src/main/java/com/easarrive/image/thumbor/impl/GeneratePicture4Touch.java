/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor Image Thumbor
 * @Title : GeneratePicture4Touch.java
 * @Package : com.easarrive.image.thumbor.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月2日
 * @Time : 下午8:05:27
 */
package com.easarrive.image.thumbor.impl;

import com.easarrive.image.thumbor.ConfigureFactory;
import com.easarrive.image.thumbor.IGeneratePicture;
import com.easarrive.image.thumbor.IPictureKeeper;
import com.easarrive.image.thumbor.bean.RquestThumborServerCallable;
import com.easarrive.image.thumbor.bean.ThumborConfigureMap;
import com.easarrive.image.thumbor.bean.ThumborContext;
import com.easarrive.image.thumbor.bean.ThumborRequest;
import com.easarrive.image.thumbor.exception.ThumborException;
import com.easarrive.image.thumbor.util.Constant;
import com.squareup.pollexor.Thumbor;
import com.squareup.pollexor.ThumborUrlBuilder;
import lombok.Setter;
import net.lizhaoweb.common.util.base.StringUtil;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月2日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class GeneratePicture4Touch implements IGeneratePicture {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    private static ThumborConfigure configure;

    private static ThumborConfigureMap configureMap;

    @Setter
    private IPictureKeeper pictureKeeper;

//    static {
//        configure = ConfigureFactory.getConfigure();
//    }

    public GeneratePicture4Touch() {
        configureMap = ConfigureFactory.getConfigureMap();
    }

//    @Override
//    public boolean generate(ThumborRequest request) {
//        long generateStart = System.currentTimeMillis();
//        if (logger.isInfoEnabled()) {
//            logger.info("\n=========================== {} START ===========================", request.getSerialNO4Thread());
//        }
//
//        boolean result = false;
//        try {
//            if (configure == null) {//配置对象不存在
//                throw new ThumborException("The configure is null for message (ID : %s)", request.getSerialNO4Thread());
//            }
//            if (configure.getServer() == null) {//默认图片处理服务器对象
//                throw new ThumborException("The thumbor server is null for message (ID : %s)", request.getSerialNO4Thread());
//            }
//            if (StringUtil.isEmpty(configure.getServer().getUrl())) {//默认图片处理服务器地址
//                throw new ThumborException("The thumbor server url is null for message (ID : %s)", request.getSerialNO4Thread());
//            }
//
//            //遍历种类列表
//            result = this.ergodicThumborConfigureKind(generateStart, configure, pictureKeeper, request);
//        } catch (Exception e) {
//            if (logger.isErrorEnabled()) {
//                logger.error(e.getMessage(), e);
//            }
//            result = false;
//        }
//        if (logger.isInfoEnabled()) {
//            logger.info("The file({}) generate time consuming is {} millisecond for message (ID : {})", request.getSimpleSourceName(), System.currentTimeMillis() - generateStart, request.getSerialNO4Thread());
//            logger.info("\n=========================== {} END ===========================", request.getSerialNO4Thread());
//        }
//        return result;
//    }

    @Override
    public boolean generate(ThumborRequest request) {
        long generateStart = System.currentTimeMillis();
        if (logger.isInfoEnabled()) {
            logger.info("\n=========================== {} START ===========================", request.getSerialNO4Thread());
        }

        boolean result = false;
        try {
            //遍历种类列表
            result = this.ergodicThumborConfigureKind(generateStart, configureMap, pictureKeeper, request);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            result = false;
        }
        if (logger.isInfoEnabled()) {
            logger.info("The file({}) generate time consuming is {} millisecond for message (ID : {})", request.getSimpleSourceName(), System.currentTimeMillis() - generateStart, request.getSerialNO4Thread());
            logger.info("\n=========================== {} END ===========================", request.getSerialNO4Thread());
        }
        return result;
    }

    @Override
    public boolean generate(ThumborRequest request, String separateId) {
        long generateStart = System.currentTimeMillis();
        if (logger.isInfoEnabled()) {
            logger.info("\n=========================== {} START ONE ===========================", request.getSerialNO4Thread());
        }

        boolean result = false;
        try {
            ThumborContext context = configureMap.getThumborContext(request.getKind(), separateId);

            String thumborAccessURL = this.buildThumborAccessURL(context, request);
            if (StringUtil.isEmpty(thumborAccessURL)) {
                throw new ThumborException("The thumbor access URL is %s for message (ID : %s)", thumborAccessURL, request.getSerialNO4Thread());
            }
            if (thumborAccessURL.length() > Constant.URL.MAXIMUM_LENGTH) {
                throw new ThumborException("The maximum length({}) of thumbor access URL is {} for message (ID : %s)", thumborAccessURL.length(), Constant.URL.MAXIMUM_LENGTH, request.getSerialNO4Thread());
            }
            RquestThumborServerCallable rquestThumborServerCallable = new RquestThumborServerCallable(context, pictureKeeper, request, thumborAccessURL);
            Map<String, HttpResponse> responseMap = rquestThumborServerCallable.call();
            if (logger.isDebugEnabled()) {
                logger.debug("The File({}) time consuming : {} millisecond for message (ID : {})", request.getSimpleSourceName(), System.currentTimeMillis() - generateStart, request.getSerialNO4Thread());
            }
            if (responseMap == null || responseMap.size() < 1) {
                throw new ThumborException("The map to handle is null for message (ID : %s)", request.getSerialNO4Thread());
            }

            result = this.handleResponse(request, responseMap);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            result = false;
        }
        if (logger.isInfoEnabled()) {
            logger.info("The file({}) generate time consuming is {} millisecond for message (ID : {})", request.getSimpleSourceName(), System.currentTimeMillis() - generateStart, request.getSerialNO4Thread());
            logger.info("\n=========================== {} END ONE ===========================", request.getSerialNO4Thread());
        }
        return result;
    }

//    //遍历种类列表
//    private boolean ergodicThumborConfigureKind(long generateStart, ThumborConfigure configure, IPictureKeeper pictureKeeper, ThumborRequest request) throws ThumborException {
//        List<ThumborConfigureKind> thumborConfigureKindList = configure.getKindList();
//        if (thumborConfigureKindList == null) {
//            throw new ThumborException("The thumbor configure kind list is null for message (ID : %s)", request.getSerialNO4Thread());
//        }
//        if (thumborConfigureKindList.size() < 1) {
//            throw new ThumborException("The thumbor configure kind list is empty for message (ID : %s)", request.getSerialNO4Thread());
//        }
//
//        //遍历配置种类
//        for (ThumborConfigureKind kind : configure.getKindList()) {
//            try {
//                if (StringUtil.isEmpty(request.getKind())) {
//                    throw new ThumborException("Rquest kind is %s for message (ID : %s)", request.getKind(), request.getSerialNO4Thread());
//                }
//                if (!request.getKind().equals(kind.getId())) {
//                    continue;
//                }
//
//                //遍历规格列表
//                boolean success = this.ergodicThumborConfigureSeparate(generateStart, configure, request, pictureKeeper, kind);
//                if (!success) {
//                    return false;
//                }
//            } catch (Exception e) {
//                if (logger.isErrorEnabled()) {
//                    logger.error(e.getMessage(), e);
//                }
//                return false;
//            }
//        }
//        return true;
//    }

    //遍历种类列表
    private boolean ergodicThumborConfigureKind(long generateStart, ThumborConfigureMap configureMap, IPictureKeeper pictureKeeper, ThumborRequest request) throws ThumborException {
        if (configureMap == null) {
            throw new ThumborException("The thumbor configure kind map is null for message (ID : %s)", request.getSerialNO4Thread());
        }
        if (configureMap.size() < 1) {
            throw new ThumborException("The thumbor configure kind map is empty for message (ID : %s)", request.getSerialNO4Thread());
        }
        if (StringUtil.isEmpty(request.getKind())) {
            throw new ThumborException("Rquest kind is %s for message (ID : %s)", request.getKind(), request.getSerialNO4Thread());
        }
        Map<String, ThumborContext> kindMap = configureMap.getKind(request.getKind());

        //遍历规格列表
        boolean success = this.ergodicThumborConfigureSeparate(generateStart, kindMap, request, pictureKeeper);
        if (!success) {
            return false;
        }
        return true;
    }

//    //遍历规格列表
//    private boolean ergodicThumborConfigureSeparate(long generateStart, ThumborConfigure configure, ThumborRequest request, IPictureKeeper pictureKeeper, ThumborConfigureKind kind) throws ThumborException {
//        List<ThumborConfigureSeparate> separateList = kind.getSeparateList();
//        if (separateList == null) {
//            throw new ThumborException("The thumbor configure separate list is null for message (ID : %s)", request.getSerialNO4Thread());
//        }
//        if (separateList.size() < 1) {
//            throw new ThumborException("The thumbor configure separate list is empty for message (ID : %s)", request.getSerialNO4Thread());
//        }
//
//        //利用多线程，调用 Thumbor 处理图片。
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        List<Future<Map<String, HttpResponse>>> resultList = new ArrayList<Future<Map<String, HttpResponse>>>();
//        for (ThumborConfigureSeparate separate : separateList) {
//            try {
//                if (separate == null) {
//                    throw new ThumborException("The thumbor configure separate is null for message (ID : %s)", request.getSerialNO4Thread());
//                }
//
//                ThumborContext context = this.getContext(configure, kind, separate);
//                if (context == null) {
//                    throw new ThumborException("The thumbor context is null for message (ID : %s)", request.getSerialNO4Thread());
//                }
//
//                String thumborAccessURL = this.buildThumborAccessURL(context, request);
//                if (StringUtil.isEmpty(thumborAccessURL)) {
//                    throw new ThumborException("The thumbor access URL is %s for message (ID : %s)", thumborAccessURL, request.getSerialNO4Thread());
//                }
//                if (thumborAccessURL.length() > Constant.URL.MAXIMUM_LENGTH) {
//                    throw new ThumborException("The maximum length({}) of thumbor access URL is {} for message (ID : %s)", thumborAccessURL.length(), Constant.URL.MAXIMUM_LENGTH, request.getSerialNO4Thread());
//                }
//                Future<Map<String, HttpResponse>> future = executorService.submit(new RquestThumborServerCallable(context, pictureKeeper, request, thumborAccessURL));
//                resultList.add(future);
//            } catch (Exception e) {
//                if (logger.isErrorEnabled()) {
//                    logger.error(e.getMessage(), e);
//                }
//            }
//        }

    //遍历规格列表
    private boolean ergodicThumborConfigureSeparate(long generateStart, Map<String, ThumborContext> kindMap, ThumborRequest request, IPictureKeeper pictureKeeper) throws ThumborException {
        if (kindMap == null) {
            throw new ThumborException("The thumbor configure kind map is null for message (ID : %s)", request.getSerialNO4Thread());
        }
        if (kindMap.size() < 1) {
            throw new ThumborException("The thumbor configure kind map is empty for message (ID : %s)", request.getSerialNO4Thread());
        }

        //利用多线程，调用 Thumbor 处理图片。
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<Map<String, HttpResponse>>> resultList = new ArrayList<Future<Map<String, HttpResponse>>>();
        for (ThumborContext context : kindMap.values()) {
            try {
                if (context == null) {
                    throw new ThumborException("The thumbor context is null for message (ID : %s)", request.getSerialNO4Thread());
                }

                String thumborAccessURL = this.buildThumborAccessURL(context, request);
                if (StringUtil.isEmpty(thumborAccessURL)) {
                    throw new ThumborException("The thumbor access URL is %s for message (ID : %s)", thumborAccessURL, request.getSerialNO4Thread());
                }
                if (thumborAccessURL.length() > Constant.URL.MAXIMUM_LENGTH) {
                    throw new ThumborException("The maximum length({}) of thumbor access URL is {} for message (ID : %s)", thumborAccessURL.length(), Constant.URL.MAXIMUM_LENGTH, request.getSerialNO4Thread());
                }
                Future<Map<String, HttpResponse>> future = executorService.submit(new RquestThumborServerCallable(context, pictureKeeper, request, thumborAccessURL));
                resultList.add(future);
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

        //遍历任务的结果
        for (Future<Map<String, HttpResponse>> fs : resultList) {
            try {
                Map<String, HttpResponse> responseMap = fs.get();
                if (logger.isDebugEnabled()) {
                    logger.debug("The File({}) time consuming : {} millisecond for message (ID : {})", request.getSimpleSourceName(), System.currentTimeMillis() - generateStart, request.getSerialNO4Thread());
                }
                if (responseMap == null || responseMap.size() < 1) {
                    throw new ThumborException("The map to handle is null for message (ID : %s)", request.getSerialNO4Thread());
                }

                boolean success = this.handleResponse(request, responseMap);
                if (!success) {
                    return false;
                }
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
                return false;
            } finally {
                //启动一次顺序关闭，执行以前提交的任务，但不接受新任务。如果已经关闭，则调用没有其他作用。
                executorService.shutdown();
            }
        }
        return true;
    }

    //处理返回结果
    private boolean handleResponse(ThumborRequest request, Map<String, HttpResponse> responseMap) {
        for (Map.Entry<String, HttpResponse> entry : responseMap.entrySet()) {
            String key = entry.getKey();
            HttpResponse httpResponse = entry.getValue();
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (logger.isInfoEnabled()) {
                logger.info("\n\t\t\t\t\t\t<<======| The file({}) hold status is {} for message (ID : {})", key, statusCode, request.getSerialNO4Thread());
            }
            if (statusCode != HttpStatus.SC_OK) {
                return false;
            }
        }
        return true;
    }

    // 构建Thumbor请求路径
    private String buildThumborAccessURL(ThumborContext context, ThumborRequest request) {
        Thumbor thumbor = Thumbor.create(context.getServer().getUrl());

        // =================== 过滤器 =================== \\
        List<String> filterList = new ArrayList<String>();

        // 图片生成质量
        if (context.getQuality() >= Constant.Thumbor.Quality.MINIMUM && context.getQuality() <= Constant.Thumbor.Quality.MAXIMUM) {
            String qualityFilter = ThumborUrlBuilder.quality(context.getQuality());
            filterList.add(qualityFilter);
        }

        // 图片圆角度
        if (context.getRoundCorner() > Constant.Thumbor.RoundCorner.DEFAULT) {
            String roundCornerFilter = ThumborUrlBuilder.roundCorner(context.getRoundCorner());
            filterList.add(roundCornerFilter);
        }

        // 图片水印
        if (StringUtil.isNotEmpty(context.getWatermark().getUrl())) {
            String watermarkFilter = ThumborUrlBuilder.watermark(context.getWatermark().getUrl(), context.getWatermark().getX() == null ? Constant.Thumbor.Watermark.X.MINIMUM : context.getWatermark().getX(), context.getWatermark().getY() == null ? Constant.Thumbor.Watermark.Y.MINIMUM : context.getWatermark().getY(), context.getWatermark().getTransparency() == null ? Constant.Thumbor.Watermark.Transparency.MAXIMUM : context.getWatermark().getTransparency());
            filterList.add(watermarkFilter);
        }

        // 图片格式
        if (context.getFormat() != null) {
            String formatFilter = ThumborUrlBuilder.format(context.getFormat());
            filterList.add(formatFilter);
        }

        // =================== 构建URL ===================\\
        ThumborUrlBuilder thumborUrlBuilder = thumbor.buildImage(request.getSourcePicture()).filter(filterList.toArray(new String[0]));

        // 指定处理尺寸
        thumborUrlBuilder.resize(context.getWidth(), context.getHeight());

        // 去除空白
        if (context.isTrim()) {
            thumborUrlBuilder.trim();
        }

        // 图片信息
        if (context.isMeta()) {
            thumborUrlBuilder.toMeta();
        }

        // 智能
        if (context.isSmart()) {
            thumborUrlBuilder.smart();
        }

        // 位置
        if (context.getAlign() != null) {
            // 水平位置
            if (context.getAlign().getHorizontal() != null) {
                thumborUrlBuilder.align(context.getAlign().getHorizontal());
            }
            // 垂直位置
            if (context.getAlign().getVertical() != null) {
                thumborUrlBuilder.align(context.getAlign().getVertical());
            }
        }

        // 生成目标图片并保存
        String thumborAccessURL = thumborUrlBuilder.toUrl();

        return thumborAccessURL;
    }

//    // 构建Thumbor上下文
//    private ThumborContext getContext(ThumborConfigure configure, ThumborConfigureKind kind, ThumborConfigureSeparate separate) {
//        if (configure == null || kind == null || separate == null) {
//            return null;
//        }
//        if (configure.getServer() == null) {
//            return null;
//        }
//        if (StringUtil.isEmpty(configure.getServer().getUrl())) {
//            return null;
//        }
//
//        ThumborContext context = new ThumborContext();
//
//        context.setSmart(configure.isSmart());
//        context.setTrim(configure.isTrim());
//        context.setMeta(configure.isMeta());
//        context.setAlign(configure.getAlign());
//
//        context.setId(kind.getId());
//        context.setSource(kind.getSource());
//        context.setTarget(kind.getTarget());
//
//        context.setFormat(separate.getFormat());
//        context.setSuffix(separate.getSuffix());
//        context.setQuality(separate.getQuality());
//        context.setRoundCorner(separate.getRoundCorner());
//        context.setWidth(separate.getWidth());
//        context.setHeight(separate.getHeight());
//
//        String serverUrl = null;
//        if (kind.getServer() == null) {
//            serverUrl = configure.getServer().getUrl();
//        } else if (StringUtil.isEmpty(kind.getServer().getUrl())) {
//            serverUrl = configure.getServer().getUrl();
//        } else {
//            serverUrl = kind.getServer().getUrl();
//        }
//        context.setServer(new ThumborConfigureServer(serverUrl));
//
//        // 图片水印
//        String watermarkURL = null;
//        Integer x = 0, y = 0, transparency = 0;
//        if (kind.getWatermark() == null) {
//            if (configure.getWatermark() != null) {
//                watermarkURL = configure.getWatermark().getUrl();
//                x = configure.getWatermark().getX();
//                y = configure.getWatermark().getY();
//                transparency = configure.getWatermark().getTransparency();
//            }
//        } else {
//            watermarkURL = kind.getWatermark().getUrl();
//            x = kind.getWatermark().getX();
//            y = kind.getWatermark().getY();
//            transparency = kind.getWatermark().getTransparency();
//            if (StringUtil.isEmpty(watermarkURL)) {
//                if (configure.getWatermark() != null) {
//                    watermarkURL = configure.getWatermark().getUrl();
//                }
//            }
//            if (x == null) {
//                if (configure.getWatermark() != null) {
//                    x = configure.getWatermark().getX();
//                }
//            }
//            if (y == null) {
//                if (configure.getWatermark() != null) {
//                    y = configure.getWatermark().getY();
//                }
//            }
//            if (transparency == null) {
//                if (configure.getWatermark() != null) {
//                    transparency = configure.getWatermark().getTransparency();
//                }
//            }
//        }
//        context.setWatermark(new ThumborConfigureWatermark(watermarkURL, x, y, transparency));
//
//        return context;
//    }
}

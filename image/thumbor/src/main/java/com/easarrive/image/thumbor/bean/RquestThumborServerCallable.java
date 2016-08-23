/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.image.thumbor
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 14:14
 */
package com.easarrive.image.thumbor.bean;

import com.easarrive.image.thumbor.IPictureKeeper;
import com.easarrive.image.thumbor.exception.ThumborException;
import com.easarrive.image.thumbor.util.Constant;
import net.lizhaoweb.common.util.base.HttpClientUtil;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * <h1>线程回调 - 请求Thumbor服务器</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月23日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class RquestThumborServerCallable implements Callable<Map<String, HttpResponse>> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private IPictureKeeper pictureKeeper;

    private ThumborContext context;

    private ThumborRequest request;

    private String thumborAccessURL;

    public RquestThumborServerCallable(ThumborContext context, IPictureKeeper pictureKeeper, ThumborRequest request, String thumborAccessURL) {
        this.context = context;
        this.pictureKeeper = pictureKeeper;
        this.request = request;
        this.thumborAccessURL = thumborAccessURL;
    }

    @Override
    public Map<String, HttpResponse> call() throws Exception {
        try {
            List<Header> headerList = new ArrayList<Header>();
            String xThumborStrongAWSS3 = pictureKeeper.getTargetPicturePath(context, request);
            String contentType = pictureKeeper.getTargetPictureContentType(context, request);

            Map<String, HttpResponse> result = new HashMap<String, HttpResponse>();
            headerList.add(new BasicHeader(Constant.Thumbor.Strong.HTTP.Header.Key.S3.PATH, xThumborStrongAWSS3));
            headerList.add(new BasicHeader(Constant.Thumbor.Strong.HTTP.Header.Key.S3.CONTENT_TYPE, contentType));
            if (logger.isInfoEnabled()) {
                logger.info("\n\t\t\t\t\t\t|======>> The thumbor headers is {} to URL({}) for message (ID : {})", headerList, thumborAccessURL, request.getSerialNO4Thread());
            }
            HttpResponse httpResponse = HttpClientUtil.head(thumborAccessURL, headerList);
            result.put(xThumborStrongAWSS3, httpResponse);
            return result;
        } catch (Exception e) {
            String errorMessage = String.format("The exception {%s} for message (ID : %s)", e.getMessage(), request.getSerialNO4Thread());
            throw new ThumborException(errorMessage, e);
        }
    }
}

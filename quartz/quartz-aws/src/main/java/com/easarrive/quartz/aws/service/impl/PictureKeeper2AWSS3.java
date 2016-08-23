/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor Image Thumbor Executer
 * @Title : PictureKeeper2AWSS3.java
 * @Package : com.easarrive.image.thumbor.executer.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月3日
 * @Time : 上午1:03:23
 */
package com.easarrive.quartz.aws.service.impl;

import com.amazonaws.services.s3.internal.Mimetypes;
import com.easarrive.image.thumbor.IPictureKeeper;
import com.easarrive.image.thumbor.bean.ThumborContext;
import com.easarrive.image.thumbor.bean.ThumborRequest;
import com.squareup.pollexor.ThumborUrlBuilder;

/**
 * <h1>实现 - S3图片存储器</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月3日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class PictureKeeper2AWSS3 implements IPictureKeeper {

    @Override
    public String getTargetPicturePath(ThumborContext context, ThumborRequest request) {
        if (context == null) {
            return null;
        }
        if (request == null) {
            return null;
        }
        if (request.getSourcePicture() == null) {
            return null;
        }
        int startIndex = request.getSourcePicture().lastIndexOf('/'), endIndex = request.getSourcePicture().lastIndexOf('.');
        if (startIndex >= endIndex) {
            return null;
        }
        if (startIndex < 0) {
            startIndex = 0;
        } else {
            startIndex += 1;
        }
        if (endIndex < 0) {
            endIndex = request.getSourcePicture().length() - 1;
        }
        String saveImageMainName = request.getSourcePicture().substring(startIndex, endIndex);
        return String.format("%s/%s_%dx%d%s", context.getTarget(), saveImageMainName, context.getWidth(), context.getHeight(), context.getSuffix());
    }

    @Override
    public String getTargetPictureContentType(ThumborContext context, ThumborRequest request) {
        ThumborUrlBuilder.ImageFormat imageFormat = context.getFormat();
        String contentType = Mimetypes.getInstance().getMimetype(String.format("x.%s", imageFormat.toString()));
        return contentType;
    }
}

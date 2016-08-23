/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor Image Thumbor
 * @Title : ThumborConfigureSeparate.java
 * @Package : com.easarrive.image.thumbor.bean
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月6日
 * @Time : 下午7:55:55
 */
package com.easarrive.image.thumbor.bean;

import com.squareup.pollexor.ThumborUrlBuilder.ImageFormat;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月6日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@Data
public class ThumborConfigureSeparate implements Serializable {

    private static final long serialVersionUID = -7109536261968153101L;

    /**
     * 图片格式
     */
    private String format;

    /**
     * 文件后缀
     */
    private String suffix;

    /**
     * 图片质量。取值:0-100
     */
    private int quality = -1;

    /**
     * 圆角度
     */
    private int roundCorner;

    /**
     * 宽
     */
    private int width;

    /**
     * 高
     */
    private int height;

    public ImageFormat getFormat() {
        ImageFormat imageFormat = null;
        if (this.format != null && this.format.matches("^(?i)gif|jpeg|png|webp$")) {
            imageFormat = ImageFormat.valueOf(this.format.toUpperCase());
        }
        return imageFormat;
    }
}

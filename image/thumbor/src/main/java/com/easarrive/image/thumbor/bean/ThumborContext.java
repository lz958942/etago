/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor Image Thumbor
 * @Title : ThumborContext.java
 * @Package : com.easarrive.image.thumbor.bean
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月6日
 * @Time : 下午8:35:13
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
public class ThumborContext implements Serializable, Cloneable {

    private static final long serialVersionUID = 6648224409061498537L;

    /**
     * 唯一标识
     */
    private String id;

    /**
     * 处理服务器
     */
    private ThumborConfigureServer server;

    /**
     * 水印
     */
    private ThumborConfigureWatermark watermark;

    /**
     * 是否智能
     */
    private boolean smart;

    /**
     * 是否去除空白
     */
    private boolean trim;

    /**
     * 是否输出图片信息
     */
    private boolean meta;

    /**
     * 位置
     */
    private ThumborConfigureAlign align;

    /**
     * 源目录
     */
    private String source;

    /**
     * 目标目录
     */
    private String target;

    /**
     * 图片格式
     */
    private ImageFormat format;

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

    public ThumborContext clone() {
        ThumborContext clone = null;
        try {
            clone = (ThumborContext) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}

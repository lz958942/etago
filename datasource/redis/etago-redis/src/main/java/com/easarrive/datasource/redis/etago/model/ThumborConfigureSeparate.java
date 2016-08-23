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
package com.easarrive.datasource.redis.etago.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <h1>模型 - Thumbor配置 - 规格</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月6日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}

/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor Image Thumbor
 * @Title : ThumborConfigureWatermark.java
 * @Package : com.easarrive.image.thumbor.bean
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月2日
 * @Time : 下午5:48:07
 */
package com.easarrive.datasource.redis.etago.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <h1>模型 - Thumbor配置 - 水印</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月2日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThumborConfigureWatermark implements Serializable {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = -8191011709175283331L;

    /**
     * 水印图片路径
     */
    private String url;

    /**
     * 横坐标
     */
    private Integer x;

    /**
     * 纵坐标
     */
    private Integer y;

    /**
     * 透明度
     */
    private Integer transparency;
}

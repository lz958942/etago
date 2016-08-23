/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor Image Thumbor
 * @Title : ThumborConfigure.java
 * @Package : com.easarrive.image.thumbor.bean
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月2日
 * @Time : 下午5:54:49
 */
package com.easarrive.datasource.redis.etago.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <h1>模型 - Thumbor配置</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月2日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ThumborConfigure extends AbstractThumborConfigure {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = -3360918912028094175L;

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
     * 种类
     */
    @JsonProperty("kind-list")
    private List<ThumborConfigureKind> kindList;

    public ThumborConfigure() {
        super();
        align = new ThumborConfigureAlign();
    }
}

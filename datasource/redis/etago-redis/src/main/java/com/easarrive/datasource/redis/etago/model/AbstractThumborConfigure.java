/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor Image Thumbor
 * @Title : AbstractThumborConfigure.java
 * @Package : com.easarrive.image.thumbor.bean
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月2日
 * @Time : 下午5:11:37
 */
package com.easarrive.datasource.redis.etago.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月2日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@Data
public abstract class AbstractThumborConfigure implements Serializable {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = -5565337444613388865L;

    /**
     * 处理服务器
     */
    private ThumborConfigureServer server;

    /**
     * 水印
     */
    private ThumborConfigureWatermark watermark;

    public AbstractThumborConfigure() {
        server = new ThumborConfigureServer("");
        watermark = new ThumborConfigureWatermark();
    }
}

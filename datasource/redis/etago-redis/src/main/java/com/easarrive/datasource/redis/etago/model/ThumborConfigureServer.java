/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor Image Thumbor
 * @Title : ThumborConfigureServer.java
 * @Package : com.easarrive.image.thumbor.bean
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月2日
 * @Time : 下午5:49:26
 */
package com.easarrive.datasource.redis.etago.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <h1>模型 - Thumbor配置 - 处理服务器</h1>
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
public class ThumborConfigureServer implements Serializable {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 5558372187530399684L;

    /**
     * 图片处理器路径
     */
    private String url;
}

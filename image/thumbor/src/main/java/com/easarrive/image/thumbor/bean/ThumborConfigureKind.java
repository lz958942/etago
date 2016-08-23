/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor Image Thumbor
 * @Title : ThumborConfigureKind.java
 * @Package : com.easarrive.image.thumbor.bean
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月2日
 * @Time : 下午5:52:27
 */
package com.easarrive.image.thumbor.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月2日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ThumborConfigureKind extends AbstractThumborConfigure {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = -8425001606221737558L;

    /**
     * 唯一标识。
     */
    private String id;

    /**
     * 源目录
     */
    private String source;

    /**
     * 目标目录
     */
    private String target;

    /**
     * 缩放列表
     */
    @JsonProperty("separate-list")
    private List<ThumborConfigureSeparate> separateList;
}

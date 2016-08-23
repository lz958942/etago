/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : S3EventMessageS3Object.java
 * @Package : net.lizhaoweb.aws.plugin.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月1日
 * @Time : 下午2:37:48
 */
package com.easarrive.aws.model.s3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月1日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@Data
public class S3EventMessageS3Object implements Serializable {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 1595210571508359171L;

    /**
     * 键
     */
    private String key;

    /**
     * 大小
     */
    private String size;

    /**
     * 标签
     */
    @JsonProperty(value = "eTag")
    private String eTag;

    /**
     * 排序器
     */
    private String sequencer;
}

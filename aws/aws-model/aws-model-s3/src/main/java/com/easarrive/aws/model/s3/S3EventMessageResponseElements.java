/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : S3EventMessageResponseElements.java
 * @Package : net.lizhaoweb.aws.plugin.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月1日
 * @Time : 下午2:44:15
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
public class S3EventMessageResponseElements implements Serializable {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = -5068846339464835110L;

    /**
     * 亚马逊响应标识
     */
    @JsonProperty(value = "x-amz-request-id")
    private String xAMZRequestId;

    /**
     * 亚马逊标识2
     */
    @JsonProperty(value = "x-amz-id-2")
    private String xAMZId2;
}

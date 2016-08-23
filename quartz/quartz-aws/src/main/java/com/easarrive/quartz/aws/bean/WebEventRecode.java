/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.quartz.aws.bean
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 21:04
 */
package com.easarrive.quartz.aws.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <h1>模型 - 网站事件记录</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月21日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@Data
public class WebEventRecode implements Serializable {

    /**
     * 索引库类型。
     */
    @JsonProperty(value = "type")
    private String type;

    /**
     * 操作类型。
     */
    @JsonProperty(value = "option")
    private String option;

    /**
     * 记录ID列表。
     */
    @JsonProperty(value = "ids")
    private List<String> idList;
}

/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.quartz.aws.bean
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 21:02
 */
package com.easarrive.quartz.aws.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * <h1>模型 - 网站事件消息</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月21日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class WebEventMessage implements Serializable {

    /**
     * 版本号。
     */
    @JsonProperty(value = "version")
    private String version;

    /**
     * 记录列表。
     */
    @JsonProperty(value = "recodes")
    private List<WebEventRecode> recodeList;
}

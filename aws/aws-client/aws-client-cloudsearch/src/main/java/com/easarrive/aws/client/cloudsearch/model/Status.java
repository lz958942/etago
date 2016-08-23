/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : YiDa
 * @Package : com.easarrive.aws.client.cloudsearch.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 20:05
 */
package com.easarrive.aws.client.cloudsearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@Data
public class Status {

    /**
     * 请求标识。
     */
    @JsonProperty("rid")
    private String requestId;

    /**
     * 响应毫秒数。
     */
    @JsonProperty("time-ms")
    private long responseMillisecond;
}

/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : YiDa
 * @Package : com.easarrive.aws.client.cloudsearch.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 19:58
 */
package com.easarrive.aws.client.cloudsearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@Data
public class Hits {

    /**
     * 查询到的记录数。
     */
    @JsonProperty("found")
    private int found;

    /**
     * 查询起始索引。
     */
    @JsonProperty("start")
    private int start;

    /**
     * 查询到的记录列表。
     */
    @JsonProperty("hit")
    private List<Hit> hitList;
}

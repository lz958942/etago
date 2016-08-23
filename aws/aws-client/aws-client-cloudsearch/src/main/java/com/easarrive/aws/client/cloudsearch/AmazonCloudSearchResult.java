/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : YiDa
 * @Package : com.easarrive.aws.client.cloudsearch
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 18:17
 */
package com.easarrive.aws.client.cloudsearch;

import com.easarrive.aws.client.cloudsearch.model.Hits;
import com.easarrive.aws.client.cloudsearch.model.Status;
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
public class AmazonCloudSearchResult {

    /**
     * 状态。
     */
    @JsonProperty("status")
    private Status status;

    /**
     * 查询结果。
     */
    @JsonProperty("hits")
    private Hits hits;
}

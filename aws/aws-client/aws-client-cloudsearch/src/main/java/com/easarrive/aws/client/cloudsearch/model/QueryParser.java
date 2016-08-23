/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : YiDa
 * @Package : com.easarrive.aws.client.cloudsearch.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 20:53
 */
package com.easarrive.aws.client.cloudsearch.model;

import lombok.Getter;

/**
 * 查询分析器
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年07月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public enum QueryParser {
    SIMPLE("simple"),
    STRUCTURED("structured"),
    LUCENE("lucene"),
    DISMAX("dismax"),
    UNKNOWN("unknown");

    @Getter
    private String value;

    private QueryParser(String value) {
        this.value = value;
    }

    public static QueryParser fromValue(String value) {
        QueryParser result = null;
        try {
            result = valueOf(value);
        } catch (Exception e) {
            result = UNKNOWN;
        }
        return result;
    }
}

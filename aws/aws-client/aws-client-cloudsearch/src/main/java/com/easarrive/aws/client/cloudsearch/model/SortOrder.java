/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : YiDa
 * @Package : com.easarrive.aws.client.cloudsearch.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 21:05
 */
package com.easarrive.aws.client.cloudsearch.model;

import lombok.Getter;

/**
 * 排列秩序
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public enum SortOrder {
    //倒序
    DESC("desc"),
    //正序
    ASC("asc"),
    //未知
    UNKNOWN("unknown");

    @Getter
    private String value;

    private SortOrder(String value) {
        this.value = value;
    }

    public static SortOrder fromValue(String value) {
        SortOrder result = null;
        try {
            result = valueOf(value);
        } catch (Exception e) {
            result = UNKNOWN;
        }
        return result;
    }
}

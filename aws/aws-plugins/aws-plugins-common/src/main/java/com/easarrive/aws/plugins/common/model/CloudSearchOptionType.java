/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : YiDa
 * @Package : net.lizhaoweb.aws.plugin.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 10:20
 */
package com.easarrive.aws.plugins.common.model;

import lombok.Getter;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月14日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public enum CloudSearchOptionType {
    ADD("add"),
    UPDATE("update"),
    DELETE("delete"),
    UNKNOWN("unknown");

    @Getter
    private String value;

    private CloudSearchOptionType(String value) {
        this.value = value;
    }

    public static CloudSearchOptionType fromValue(String value) {
        CloudSearchOptionType result = null;
        try {
            result = valueOf(value);
        } catch (Exception e) {
            result = UNKNOWN;
        }
        return result;
    }
}

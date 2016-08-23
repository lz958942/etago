/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : YiDa
 * @Package : com.easarrive.aws.client.cloudsearch.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 18:17
 */
package com.easarrive.aws.client.cloudsearch.model;

import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@Data
public class Hit {

    /**
     * 记录的标识。
     */
    @JsonProperty("id")
    private String id;

    /**
     * 字段列表。
     */
    @JsonProperty("fields")
    private Map<String, Object> fields = new HashMap<String, Object>();

    public Integer getIntegerField(String field) {
        Integer rlt = null;
        if (fields.containsKey(field)) {
            Object value = fields.get(field);
            if (value instanceof Integer) {
                rlt = Integer.parseInt(value.toString());
            } else if (value instanceof String && ((String) value).matches("^\\d+$")) {
                rlt = Integer.parseInt(value.toString());
            }
        }
        return rlt;
    }

    public Long getLongField(String field) {
        Long rlt = null;
        if (fields.containsKey(field)) {
            Object value = fields.get(field);
            if (value instanceof Long) {
                rlt = Long.parseLong(value.toString());
            } else if (value instanceof String && ((String) value).matches("^\\d+$")) {
                rlt = Long.parseLong(value.toString());
            }
        }
        return rlt;
    }

    public Double getDoubleField(String field) {
        Double rlt = null;
        if (fields.containsKey(field)) {
            Object value = fields.get(field);
            if (value instanceof Double) {
                rlt = Double.parseDouble(value.toString());
            } else if (value instanceof String && ((String) value).matches("^\\d+(\\.\\d+)?$")) {
                rlt = Double.parseDouble(value.toString());
            }
        }
        return rlt;
    }

    public String getField(String field) {
        String result = null;
        if (fields.get(field) != null) {
            result = fields.get(field) + "";
        }
        return result;
    }

    public List<String> getListField(String field) {
        List<String> rlt = null;

        if (fields.containsKey(field)) {
            Object value = fields.get(field);
            if (value instanceof String) {
                JsonNode jsonNode = Jackson.jsonNodeOf((String) value);
                if (jsonNode.isArray()) {
                    rlt = new ArrayList<String>();
                    for (int index = 0; index < jsonNode.size(); index++) {
                        rlt.add(jsonNode.get(index).asText());
                    }
                }
            }
        }

        return rlt;
    }
}

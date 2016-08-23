/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor DataSource Mysql Etago
 * @Title : UserLoginLog.java
 * @Package : net.lizhaoweb.datasource.mysql.etago.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月30日
 * @Time : 下午7:03:46
 */
package net.lizhaoweb.datasource.mysql.etago.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <h1>模型 - 用户登录日志</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年6月30日.<br>
 * Revision of last commit:$Revision$.<br>
 * Author of last commit:$Author$.<br>
 * Date of last commit:$Date$.<br>
 *
 */
@Data
public class UserLoginLog {

    // 主键ID
    @JsonProperty(value = "id")
    private Integer id;

    // 用户id
    @JsonProperty(value = "user_id")
    private Integer userId;

    // 设备号
    @JsonProperty(value = "deviceid")
    private String deviceid;

    // 登录来源0:iphone1:Android
    @JsonProperty(value = "where_from")
    private int whereFrom;

    //
    @JsonProperty(value = "traceinfo")
    private String traceinfo;

    // 当前位置经度
    @JsonProperty(value = "longitude")
    private String longitude;

    // 当前位置维度
    @JsonProperty(value = "latitude")
    private String latitude;

    // ip地址
    @JsonProperty(value = "ip")
    private String ip;

    // 登录时间
    @JsonProperty(value = "addtime")
    private Long addTime;
}

/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor DataSource Mysql Etago
 * @Title : User.java
 * @Package : net.lizhaoweb.datasource.mysql.etago.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月30日
 * @Time : 下午7:03:28
 */
package net.lizhaoweb.datasource.mysql.etago.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <h1>模型 - 用户</h1>
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
public class User {

    // 用户ID,主键
    @JsonProperty(value = "id")
    private Integer id;

    // 用户姓名
    @JsonProperty(value = "username")
    private String username;

    // 登录密码
    @JsonProperty(value = "password")
    private String password;

    // 国家、地区代码
    @JsonProperty(value = "region_code")
    private int regionCode;

    // 用户昵称
    @JsonProperty(value = "nickname")
    private String nickName;

    // 手机号码
    @JsonProperty(value = "mobile")
    private String mobile;

    // Email地址
    @JsonProperty(value = "email")
    private String email;

    // 用户头像
    @JsonProperty(value = "face")
    private String face;

    // 性别:0, 保密; 1, 男; 2, 女;
    @JsonProperty(value = "gender")
    private int gender;

    // 用户类型1：普通用户2：官方账号
    @JsonProperty(value = "type")
    private int type;

    // 注册时间
    @JsonProperty(value = "reg_time")
    private Long regTime;

    // 用户来源1:iphone 2:Android 3:后台添加
    @JsonProperty(value = "where_from")
    private int whereFrom;

    // 用户来源描述
    @JsonProperty(value = "where_from_extra")
    private String whereFromExtra;

    // 修改时间
    @JsonProperty(value = "update_time")
    private Long updateTime;

    // 删除标识,默认为0,未删除; 1, 已删除;
    @JsonProperty(value = "del_flag")
    private int delFlag;
}

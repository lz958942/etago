/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor DataSource Mysql Etago
 * @Title : GoodsTags.java
 * @Package : net.lizhaoweb.datasource.mysql.etago.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月30日
 * @Time : 下午3:41:22
 */
package net.lizhaoweb.datasource.mysql.etago.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <h1>模型 - 产品标签</h1>
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
public class GoodsTags {

    @JsonProperty(value = "id")
    private Integer id;

    // 用户id
    @JsonProperty(value = "user_id")
    private Integer userId;

    // 商品id
    @JsonProperty(value = "goods_id")
    private Integer goodsId;

    // 标签
    @JsonProperty(value = "tag")
    private String tag;

    // 添加时间
    @JsonProperty(value = "add_time")
    private Long addTime;

    //
    @JsonProperty(value = "del_flag")
    private int delFlag;
}

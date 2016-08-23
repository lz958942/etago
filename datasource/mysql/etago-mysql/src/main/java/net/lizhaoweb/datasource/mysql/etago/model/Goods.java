/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor DataSource Mysql Etago
 * @Title : Goods.java
 * @Package : net.lizhaoweb.datasource.mysql.etago.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月30日
 * @Time : 下午3:09:49
 */
package net.lizhaoweb.datasource.mysql.etago.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * <h1>模型 - 产品</h1>
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
public class Goods {

    @JsonProperty(value = "id")
    private Integer id;

    // 标题
    @JsonProperty(value = "title")
    private String title;

    // 内容信息
    @JsonProperty(value = "content")
    private String content;

    // 价格
    @JsonProperty(value = "price")
    private Double price;

    // 发布城市
    @JsonProperty(value = "city_id")
    private Integer cityId;

    // 销售类型1不销售|2销售
    @JsonProperty(value = "sale_type")
    private int saleType;

    // 配送类型1送货上门|2自取
    @JsonProperty(value = "delivery_type")
    private int deliveryType;

    // 地址
    @JsonProperty(value = "address")
    private String address;

    // 支付类型1线上支付|2线下支付
    @JsonProperty(value = "pay_type")
    private int payType;

    // 服务类型1Ta来我这2我去ta那
    @JsonProperty(value = "service_type")
    private int serviceType;

    // 产品类型0未知1商品2服务
    @JsonProperty(value = "type")
    private int type;

    // 商品来源1普通 2官方
    @JsonProperty(value = "source")
    private int source;

    // 计价单位1件数|2个数|3小时|4次数
    @JsonProperty(value = "price_unit")
    private int priceUnit;

    // 购买人数
    @JsonProperty(value = "buynum")
    private Integer buynum;

    // 点赞个数
    @JsonProperty(value = "likenum")
    private Integer likenum;

    // 当前位置经度
    @JsonProperty(value = "longitude")
    private String longitude;

    // 当前位置维度
    @JsonProperty(value = "latitude")
    private String latitude;

    // 用户id
    @JsonProperty(value = "user_id")
    private Integer userId;

    // 增加时间
    @JsonProperty(value = "add_time")
    private Long addTime;

    // 更新时间
    @JsonProperty(value = "update_time")
    private Long updateTime;

    //
    @JsonProperty(value = "del_flag")
    private int delFlag;

    //
    @JsonProperty(value = "tags")
    private List<GoodsTags> tagList;
}

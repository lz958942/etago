/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor DataSource Mysql Etago
 * @Title : Good4CloudSearch.java
 * @Package : net.lizhaoweb.datasource.mysql.etago.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月5日
 * @Time : 下午5:43:05
 */
package net.lizhaoweb.datasource.mysql.etago.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <h1>模型(云搜索) - 产品</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年7月5日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Good4CloudSearch extends Abstract4CloudSearch {

    private static final long serialVersionUID = 8455560348321175486L;

    /**
     * 标题
     */
    @JsonProperty(value = "title")
    private String title;

    /**
     * 内容信息
     */
    @JsonProperty(value = "content")
    private String content;

    /**
     * 价格
     */
    @JsonProperty(value = "price")
    private Double price;

    /**
     * 发布城市标识
     */
    @JsonProperty(value = "city_id")
    private Integer cityId;

    /**
     * 发布城市名称
     */
    @JsonProperty(value = "city_name")
    private String cityName;

    /**
     * 发布国家标识
     */
    @JsonProperty(value = "country_id")
    private Integer countryId;

    /**
     * 发布国家名称
     */
    @JsonProperty(value = "country_name")
    private String countryName;

    /**
     * 发布时区
     */
    @JsonProperty(value = "time_zone")
    private String timeZone;

    /**
     * 距离单位
     */
    @JsonProperty(value = "distance_unit")
    private String distanceUnit;

    /**
     * 货币代码
     */
    @JsonProperty(value = "currency_code")
    private String currencyCode;

    /**
     * 货币符号
     */
    @JsonProperty(value = "currency_mark")
    private String currencySymbol;

    /**
     * 销售类型1不销售|2销售
     */
    @JsonProperty(value = "sale_type")
    private int saleType;

    /**
     * 配送类型1送货上门|2自取
     */
    @JsonProperty(value = "delivery_type")
    private int deliveryType;

    /**
     * 地址
     */
    @JsonProperty(value = "address")
    private String address;

    /**
     * 支付类型1线上支付|2线下支付
     */
    @JsonProperty(value = "pay_type")
    private int payType;

    /**
     * 服务类型1Ta来我这2我去ta那
     */
    @JsonProperty(value = "service_type")
    private int serviceType;

    /**
     * 产品类型0未知1商品2服务
     */
    @JsonProperty(value = "type")
    private int type;

    /**
     * 商品来源1普通 2官方
     */
    @JsonProperty(value = "source")
    private int source;

    /**
     * 计价单位1件数|2个数|3小时|4次数
     */
    @JsonProperty(value = "price_unit")
    private int priceUnit;

    /**
     * 购买人数
     */
    @JsonProperty(value = "buy_count")
    private Integer buyCount;

    /**
     * 点赞数
     */
    @JsonProperty(value = "like_count")
    private Integer likeCount;

    /**
     * 评论数
     */
    @JsonProperty(value = "comment_count")
    private int commentCount;

    /**
     * 当前位置经度
     */
    @JsonIgnore
    private Double longitude;

    /**
     * 当前位置维度
     */
    @JsonIgnore
    private Double latitude;

    /**
     * 用户id
     */
    @JsonProperty(value = "user_id")
    private Integer userId;

    /**
     * 增加时间
     */
    @JsonProperty(value = "add_time")
    private Long addTime;

    /**
     * 更新时间
     */
    @JsonProperty(value = "update_time")
    private Long updateTime;

    /**
     * 删除标志
     */
    @JsonProperty(value = "del_flag")
    private int delFlag;

    /**
     * 标签列表
     */
    @JsonProperty(value = "tags")
    private List<String> tagList;

    /**
     * 产品图片列表
     */
    @JsonProperty(value = "images")
    private List<String> imageList;

    /**
     * 位置
     */
    @JsonProperty("location")
    public String getLocation() {
        String location = "";
        if (this.latitude != null && this.longitude != null) {
            location = String.format("%s, %s", this.latitude, this.longitude);
        }
        return location;
    }
}

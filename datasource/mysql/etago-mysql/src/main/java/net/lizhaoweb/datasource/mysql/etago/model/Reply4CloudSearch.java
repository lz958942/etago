/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : YiDa
 * @Package : net.lizhaoweb.datasource.mysql.etago.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 12:55
 */
package net.lizhaoweb.datasource.mysql.etago.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lizhaoweb.common.util.base.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>模型(云搜索) - 用户回复</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年07月12日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Reply4CloudSearch extends Abstract4CloudSearch {

    /**
     * 发送用户标识
     */
    @JsonProperty(value = "user_id")
    private Long userId;

    /**
     * 已读取用户 ID 列表。
     */
    @JsonProperty(value = "reader_ids")
    private Long[] readerIds;

    /**
     * 已删除用户 ID 列表。
     */
    @JsonProperty(value = "deleter_ids")
    private Long[] deleterIds;

    /**
     * 会话内容
     */
    @JsonProperty(value = "content")
    private String content;

    /**
     * 消息类型。1：文本；2：产品；3：图片；4：媒体。
     */
    @JsonProperty(value = "type")
    private Integer type;

    /**
     * 会话标识
     */
    @JsonProperty(value = "conversation_id")
    private Long conversationId;

    /**
     * 创建时间
     */
    @JsonProperty(value = "add_time")
    private Long addTime;

    /**
     * 更新时间
     */
    @JsonProperty(value = "update_time")
    private Long updateTime;

    /**
     * 是否删除
     */
    @JsonProperty(value = "del_flag")
    private Integer delFlag;

    /**
     * 成员 ID 列表。
     */
    @JsonProperty(value = "member_ids")
    private Long[] memberIds;

    /**
     * 会话成员标识
     */
    @JsonProperty(value = "is_group")
    private Integer isGroup;

    /**
     * 发送用户昵称
     */
    @JsonProperty(value = "nickname")
    private String nickname;

    /**
     * 发送用户头像
     */
    @JsonProperty(value = "face")
    private String face;

    /**
     * 发送用户对象
     */
    @JsonIgnore
    private User user;

    /**
     * 已读用户列表
     */
    @JsonIgnore
    private List<User> readerList;

    /**
     * 已删除用户列表
     */
    @JsonIgnore
    private List<User> deleterList;

    /**
     * 会话成员列表
     */
    @JsonIgnore
    private List<User> memberList;

    /**
     * 发送用户 JSON 对象
     *
     * @return 发送用户 JSON 对象
     */
    @JsonProperty("user_object")
    public String getUserObject() {
        String result = null;
        result = JsonUtil.toJson(user);
        return result;
    }


    /**
     * 已读用户 JSON 列表
     *
     * @return 已读用户 JSON 列表
     */
    @JsonProperty("reader_list")
    public List<String> getReaderList() {
        List<String> result = new ArrayList<String>();
        for (User reader : readerList) {
            try {
                String readerString = JsonUtil.toJson(reader);
                result.add(readerString);
            } catch (Exception e) {
            }
        }
        return result;
    }

    /**
     * 已删除用户 JSON 列表
     *
     * @return 已删除用户 JSON 列表
     */
    @JsonProperty("deleter_list")
    public List<String> getDeleterList() {
        List<String> result = new ArrayList<String>();
        for (User deleter : deleterList) {
            try {
                String deleterString = JsonUtil.toJson(deleter);
                result.add(deleterString);
            } catch (Exception e) {
            }
        }
        return result;
    }

    /**
     * 会话成员 JSON 列表
     *
     * @return 会话成员 JSON 列表
     */
    @JsonProperty("member_list")
    public List<String> getMemberList() {
        List<String> result = new ArrayList<String>();
        for (User member : memberList) {
            try {
                String memberString = JsonUtil.toJson(member);
                result.add(memberString);
            } catch (Exception e) {
            }
        }
        return result;
    }
}

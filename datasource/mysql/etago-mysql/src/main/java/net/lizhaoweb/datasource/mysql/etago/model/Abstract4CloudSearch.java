/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : YiDa
 * @Package : net.lizhaoweb.datasource.mysql.etago.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 9:55
 */
package net.lizhaoweb.datasource.mysql.etago.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月14日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@Data
public abstract class Abstract4CloudSearch implements Serializable {

    /**
     * 唯一标识
     */
    @JsonProperty(value = "id")
    private Long id;

    /**
     * 创建索引的时间
     */
    @JsonProperty(value = "index_time")
    private Long indexTime;
}

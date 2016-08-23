/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : SNSRequest.java
 * @Package : net.lizhaoweb.aws.plugin.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月28日
 * @Time : 下午9:20:46
 */
package com.easarrive.aws.plugins.common.model;

import com.amazonaws.regions.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version
 * @notes Created on 2016年6月28日<br>
 *        Revision of last commit:$Revision$<br>
 *        Author of last commit:$Author$<br>
 *        Date of last commit:$Date$<br>
 *        <p />
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SNSRequest implements Serializable {

    /** 序列化标识 */
    private static final long serialVersionUID = 5041712233799978034L;

    /** 访问密钥 */
    private String accessKey;

    /** 权限密钥 */
    private String secretKey;

    /** 区域 */
    private Region region;

    public SNSRequest(Region region) {
        super();
        this.setRegion(region);
    }
}

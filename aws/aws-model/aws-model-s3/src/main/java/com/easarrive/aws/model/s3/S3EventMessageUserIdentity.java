/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : S3EventMessageUserIdentity.java
 * @Package : net.lizhaoweb.aws.plugin.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月1日
 * @Time : 下午2:41:31
 */
package com.easarrive.aws.model.s3;

import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月1日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@Data
public class S3EventMessageUserIdentity implements Serializable {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 2587958739767871798L;

    /**
     * 主要标识
     */
    private String principalId;
}

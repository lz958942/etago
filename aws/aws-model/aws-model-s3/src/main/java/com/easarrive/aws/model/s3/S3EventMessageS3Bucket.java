/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : S3EventMessageS3Bucket.java
 * @Package : net.lizhaoweb.aws.plugin.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月1日
 * @Time : 下午2:36:16
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
public class S3EventMessageS3Bucket implements Serializable {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = -8828990950549116228L;

    /**
     * 名称
     */
    private String name;

    /**
     * 资源
     */
    private String arn;

    /**
     * 所有者标识
     */
    private S3EventMessageS3BucketOwnerIdentity ownerIdentity;
}

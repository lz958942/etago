/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor Image Thumbor
 * @Title : ThumborRequest.java
 * @Package : com.easarrive.image.thumbor.bean
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月2日
 * @Time : 下午6:19:16
 */
package com.easarrive.image.thumbor.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月2日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThumborRequest {

    /**
     * 线程流水号
     */
    private String serialNO4Thread;

    /**
     * 源图片路径
     */
    private String sourcePicture;

    /**
     * 种类
     */
    private String kind;

    public String getSimpleSourceName() {
        String simpleSourceName = sourcePicture.substring(sourcePicture.lastIndexOf("/") + 1);
        return simpleSourceName;
    }
}

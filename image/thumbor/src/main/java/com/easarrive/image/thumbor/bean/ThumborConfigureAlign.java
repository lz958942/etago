/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor Image Thumbor
 * @Title : ThumborConfigureAlign.java
 * @Package : com.easarrive.image.thumbor.bean
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月2日
 * @Time : 下午5:50:25
 */
package com.easarrive.image.thumbor.bean;

import com.squareup.pollexor.ThumborUrlBuilder.HorizontalAlign;
import com.squareup.pollexor.ThumborUrlBuilder.VerticalAlign;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月2日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@Data
public class ThumborConfigureAlign implements Serializable {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 6586701617988001591L;

    /**
     * 水平位置
     */
    private String horizontal;

    /**
     * 垂直位置
     */
    private String vertical;

    public HorizontalAlign getHorizontal() {
        HorizontalAlign horizontalAlign = null;
        if (this.horizontal != null && this.horizontal.matches("^(?i)left|center|right$")) {
            horizontalAlign = HorizontalAlign.valueOf(this.horizontal.toUpperCase());
        }
        return horizontalAlign;
    }

    public VerticalAlign getVertical() {
        VerticalAlign verticalAlign = null;
        if (this.vertical != null && this.vertical.matches("^(?i)top|middle|bottom$")) {
            verticalAlign = VerticalAlign.valueOf(this.vertical.toUpperCase());
        }
        return verticalAlign;
    }
}

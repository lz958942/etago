/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor Image Thumbor
 * @Title : IGeneratePicture.java
 * @Package : com.easarrive.image.thumbor
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月2日
 * @Time : 下午6:16:23
 */
package com.easarrive.image.thumbor;

import com.easarrive.image.thumbor.bean.ThumborRequest;

/**
 * <h1>接口 - 图片生成器</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月2日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public interface IGeneratePicture {

    /**
     * 生成图片
     *
     * @param request 图片处理请求对象
     * @return boolean
     */
    boolean generate(ThumborRequest request);

    /**
     * 生成图片
     *
     * @param request    图片处理请求对象
     * @param separateId 规格标识
     * @return boolean
     */
    boolean generate(ThumborRequest request, String separateId);
}

/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor Image Thumbor
 * @Title : IPictureKeeper.java
 * @Package : com.easarrive.image.thumbor
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月3日
 * @Time : 上午12:56:07
 */
package com.easarrive.image.thumbor;

import com.easarrive.image.thumbor.bean.ThumborContext;
import com.easarrive.image.thumbor.bean.ThumborRequest;

/**
 * <h1>接口 - 图片存储器</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月3日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public interface IPictureKeeper {

    String getTargetPicturePath(ThumborContext context, ThumborRequest request);

    String getTargetPictureContentType(ThumborContext context, ThumborRequest request);
}

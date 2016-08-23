/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.image.thumbor.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 23:07
 */
package com.easarrive.aws.plugins.common.service.impl;

import com.amazonaws.services.s3.internal.Mimetypes;
import org.junit.Test;

import javax.activation.MimetypesFileTypeMap;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年08月01日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class TestAA {

    @Test
    public void contentType() {
        String aaa = new MimetypesFileTypeMap().getContentType("a.png");
        System.out.println(aaa);
    }

    @Test
    public void mimetypes() {
        String aaa = Mimetypes.getInstance().getMimetype("a.png");
        System.out.println(aaa);
    }
}

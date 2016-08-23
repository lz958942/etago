/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @Project : Savor Image Thmbor
 * @Title : IConfigureLoader.java
 * @Package : com.easarrive.image.thumbor
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月21日
 * @Time : 上午11:33:00
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package com.easarrive.image.thumbor;

import com.easarrive.image.thumbor.bean.ThumborConfigure;

/**
 * <h1>接口 - 配置加载器</h1>
 * 
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年6月21日<br>
 *        Revision of last commit:$Revision$<br>
 *        Author of last commit:$Author$<br>
 *        Date of last commit:$Date$<br>
 *        <p />
 *
 */
public interface IConfigureLoader {

	/** 加载配置 */
	void load();

	/** 获取配置对象 */
	ThumborConfigure getConfigure();
}

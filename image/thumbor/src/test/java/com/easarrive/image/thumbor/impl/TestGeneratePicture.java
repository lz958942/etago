/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @Project : Savor Image Thumbor
 * @Title : TestGeneratePicture.java
 * @Package : com.easarrive.image.thumbor.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月21日
 * @Time : 下午6:01:46
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
package com.easarrive.image.thumbor.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.easarrive.image.thumbor.IGeneratePicture;
import com.easarrive.image.thumbor.bean.ThumborRequest;

/**
 * 
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version
 * @notes Created on 2016年6月21日<br>
 *        Revision of last commit:$Revision$<br>
 *        Author of last commit:$Author$<br>
 *        Date of last commit:$Date$<br>
 *        <p />
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/schema/spring/spring-thumbor-model.xml", "classpath*:/schema/spring/spring-thumbor-service.xml" })
public class TestGeneratePicture {

	@Autowired
	private IGeneratePicture generatePicture;

	@Test
	public void generate() {
		ThumborRequest parameter = new ThumborRequest();
		parameter.setSourcePicture("http://olpic.tgbusdata.cn/uploads/oltgbuspic/2013216/new/1361004162_e70e6bbe.jpg");
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1; i++) {
			generatePicture.generate(parameter);
		}
		System.out.println(TestGeneratePicture.class.getName() + "总耗时 : " + (System.currentTimeMillis() - start) + "毫秒");
	}
}

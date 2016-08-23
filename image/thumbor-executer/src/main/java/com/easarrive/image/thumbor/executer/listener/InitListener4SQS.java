/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @Project : Savor Image Thumbor Executer
 * @Title : InitListener4SQS.java
 * @Package : com.easarrive.image.thumbor.executer.listener
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月4日
 * @Time : 下午1:44:10
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
package com.easarrive.image.thumbor.executer.listener;

import javax.servlet.ServletContext;

import lombok.Setter;
import net.lizhaoweb.spring.mvc.core.listener.AbstractListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.context.WebApplicationContext;

import com.easarrive.image.thumbor.executer.IServer;

/**
 * 
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version
 * @notes Created on 2016年7月4日<br>
 *        Revision of last commit:$Revision$<br>
 *        Author of last commit:$Author$<br>
 *        Date of last commit:$Date$<br>
 *        <p />
 *
 */
public class InitListener4SQS extends AbstractListener {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Setter
	private IServer server;

	@Override
	protected void onApplicationLoad(ApplicationEvent event) {
		if (logger.isInfoEnabled()) {
			logger.info("Web loadding server ...");
		}
		server.init();
		Thread serverThread = new Thread(server);
		serverThread.start();
		if (logger.isInfoEnabled()) {
			logger.info("Server is loaded from web!");
		}
	}

	@Override
	protected void onApplicationLoad(WebApplicationContext context) {
	}

	@Override
	protected void onApplicationLoad(ServletContext context) {
	}
}

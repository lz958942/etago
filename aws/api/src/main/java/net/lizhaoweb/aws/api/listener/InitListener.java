/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Api
 * @Title : InitListener.java
 * @Package : net.lizhaoweb.aws.api.listener
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月28日
 * @Time : 下午9:32:50
 */
package net.lizhaoweb.aws.api.listener;

import net.lizhaoweb.spring.mvc.core.listener.AbstractListener;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年6月28日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class InitListener extends AbstractListener {

    @Override
    protected void onApplicationLoad(ApplicationEvent event) {
    }

    @Override
    protected void onApplicationLoad(WebApplicationContext context) {
    }

    @Override
    protected void onApplicationLoad(ServletContext context) {
    }
}

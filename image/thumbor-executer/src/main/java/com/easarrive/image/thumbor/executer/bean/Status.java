/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor Image Thumbor Executer
 * @Title : Status.java
 * @Package : com.easarrive.image.thumbor.executer.bean
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月4日
 * @Time : 下午4:49:15
 */
package com.easarrive.image.thumbor.executer.bean;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月4日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public enum Status {

    /**
     * 初始化
     */
    INIT,

    /**
     * 开始启动
     */
    START,

    /**
     * 正在启动
     */
    STARTING,

    /**
     * 正在运行
     */
    RUNNING,

    /**
     * 开始停止
     */
    STOP,

    /**
     * 正在停止
     */
    STOPPING,

    /**
     * 销毁
     */
    DESTROY,

    /**
     * 未知
     */
    UNKNOW,

    /**
     * 不能设置
     */
    CANNOTSET
}

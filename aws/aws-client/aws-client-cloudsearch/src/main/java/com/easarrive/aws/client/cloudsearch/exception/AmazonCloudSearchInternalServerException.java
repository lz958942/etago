/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : YiDa
 * @Package : com.easarrive.aws.client.cloudsearch.exception
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 19:11
 */
package com.easarrive.aws.client.cloudsearch.exception;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class AmazonCloudSearchInternalServerException extends Exception {

    public AmazonCloudSearchInternalServerException(Throwable t) {
        super(t);
    }

    public AmazonCloudSearchInternalServerException(String message, Throwable t) {
        super(message, t);
    }

    public AmazonCloudSearchInternalServerException(String message) {
        super(message);
    }
}

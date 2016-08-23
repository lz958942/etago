/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : YiDa
 * @Package : com.easarrive.aws.client.cloudsearch.exception
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 19:12
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
public class AmazonCloudSearchRequestException extends Exception {

    public String response;

    public String request;

    public AmazonCloudSearchRequestException(Throwable t) {
        super(t);
    }

    public AmazonCloudSearchRequestException(String message, Throwable t) {
        super(message, t);
    }

    public AmazonCloudSearchRequestException(String message) {
        super(message);
    }

    public AmazonCloudSearchRequestException(String request, String response) {
        super(response);
        this.request = request;
        this.response = response;
    }
}

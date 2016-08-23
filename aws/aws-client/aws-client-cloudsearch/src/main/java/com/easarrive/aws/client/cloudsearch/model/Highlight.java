/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : YiDa
 * @Package : com.easarrive.aws.client.cloudsearch.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 18:10
 */
package com.easarrive.aws.client.cloudsearch.model;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class Highlight {

    public String field;
    /**
     * specifies the format of the data in the text field: text or html. When data is returned as HTML, all non-alphanumeric characters are encoded. The default is html.
     */
    public String format;
    /**
     * specifies the maximum number of occurrences of the search term(s) you want to highlight. By default, the first occurrence is highlighted.
     */
    public Integer maxPhrases;
    /**
     * specifies the string to prepend to an occurrence of a search term. The default for HTML highlights is <em>. The default for text highlights is *.
     */
    public String preTag;
    /**
     * specifies the string to append to an occurrence of a search term. The default for HTML highlights is </em>. The default for text highlights is *.
     */
    public String postTag;
}

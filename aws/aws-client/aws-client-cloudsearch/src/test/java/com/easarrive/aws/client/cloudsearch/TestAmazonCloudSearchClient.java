/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : YiDa
 * @Package : com.easarrive.aws.client.cloudsearch
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 20:39
 */
package com.easarrive.aws.client.cloudsearch;

import com.easarrive.aws.client.cloudsearch.exception.AmazonCloudSearchInternalServerException;
import com.easarrive.aws.client.cloudsearch.exception.AmazonCloudSearchRequestException;
import com.easarrive.aws.client.cloudsearch.model.QueryParser;
import com.easarrive.aws.client.cloudsearch.model.SortOrder;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class TestAmazonCloudSearchClient {

    private String searchEndpoint;

    @Before
    public void init() {
        this.searchEndpoint = "search-etago-goods-iqsbfpheme4kul6mndtd5vrd4y.us-west-2.cloudsearch.amazonaws.com";
    }

    @Test
    public void get() {
        AmazonCloudSearchClient client = new AmazonCloudSearchClient(searchEndpoint);
        AmazonCloudSearchQuery query = new AmazonCloudSearchQuery().withQuery("*")
                .withQueryParser(QueryParser.LUCENE).withPage(1, 2).addSort("id", SortOrder.DESC);
        try {
            AmazonCloudSearchResult result = client.search(query);
            System.out.println(result);
        } catch (AmazonCloudSearchRequestException e) {
            e.printStackTrace();
        } catch (AmazonCloudSearchInternalServerException e) {
            e.printStackTrace();
        }
    }
}

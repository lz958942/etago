/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : YiDa
 * @Package : com.easarrive.aws.client.cloudsearch
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 19:02
 */
package com.easarrive.aws.client.cloudsearch;

import com.amazonaws.util.json.Jackson;
import com.easarrive.aws.client.cloudsearch.exception.AmazonCloudSearchInternalServerException;
import com.easarrive.aws.client.cloudsearch.exception.AmazonCloudSearchRequestException;
import lombok.Getter;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class AmazonCloudSearchClient {

    @Getter
    private String searchEndpoint;

    public AmazonCloudSearchClient(String searchEndpoint) {
        super();
        this.searchEndpoint = searchEndpoint;
    }

    /**
     * Execute a search and return result.
     *
     * @param query search query to be executed.
     * @return result of the search.
     * @throws AmazonCloudSearchRequestException
     * @throws IllegalStateException
     * @throws AmazonCloudSearchInternalServerException
     */
    public AmazonCloudSearchResult search(AmazonCloudSearchQuery query) throws IllegalStateException, AmazonCloudSearchRequestException, AmazonCloudSearchInternalServerException {
        if (this.getSearchEndpoint() == null || this.getSearchEndpoint().trim().length() < 1) {
            throw new AmazonCloudSearchRequestException("URI is null.");
        }
        AmazonCloudSearchResult result = null;
        String responseBody = null;
        try {
            Response response = Request.Get("https://" + this.getSearchEndpoint() + "/2013-01-01/search?" + query.build())
                    .useExpectContinue()
                    .version(HttpVersion.HTTP_1_1)
                    .addHeader("Content-Type", ContentType.APPLICATION_JSON.getMimeType())
                    .addHeader("Accept", ContentType.APPLICATION_JSON.getMimeType())
                    .execute();

            HttpResponse resp = response.returnResponse();
            int statusCode = resp.getStatusLine().getStatusCode();
            int statusType = statusCode / 100;
            if (statusType == 4) {
                throw new AmazonCloudSearchRequestException(statusCode + "", responseBody);
            } else if (statusType == 5) {
                throw new AmazonCloudSearchInternalServerException("Internal Server Error. Please try again as this might be a transient error condition.");
            }

            responseBody = inputStreamToString(resp.getEntity().getContent());
            result = Jackson.fromJsonString(responseBody, AmazonCloudSearchResult.class);
        } catch (ClientProtocolException e) {
            throw new AmazonCloudSearchInternalServerException(e);
        } catch (IOException e) {
            throw new AmazonCloudSearchInternalServerException(e);
        } catch (Exception e) {
            throw new AmazonCloudSearchInternalServerException(e);
        }

        return result;
    }

    private String inputStreamToString(InputStream in) throws IOException {
        StringWriter output = new StringWriter();
        InputStreamReader input = new InputStreamReader(in);
        char[] buffer = new char[1024 * 4];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toString();
    }
}

/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : YiDa
 * @Package : com.easarrive.aws.client.cloudsearch
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 18:01
 */
package com.easarrive.aws.client.cloudsearch;

import com.easarrive.aws.client.cloudsearch.model.Facet;
import com.easarrive.aws.client.cloudsearch.model.Highlight;
import com.easarrive.aws.client.cloudsearch.model.QueryParser;
import com.easarrive.aws.client.cloudsearch.model.SortOrder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年07月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class AmazonCloudSearchQuery extends AmazonAbstractCloudSearchQuery {

    /**
     * 增加表达式。
     *
     * @param name
     * @param expression
     * @return
     */
    public AmazonCloudSearchQuery addExpression(String name, String expression) {
        this.getExpressions().put(name, expression);
        return this;
    }

    /**
     * 增加排序条件。
     *
     * @param fieldOrExpr
     * @param direction
     * @return
     */
    public AmazonCloudSearchQuery addSort(String fieldOrExpr, SortOrder direction) {
        this.getSort().put(fieldOrExpr, direction);
        return this;
    }

    /**
     * 增加排序条件。
     *
     * @param fieldOrExpr
     * @return
     */
    public AmazonCloudSearchQuery addSort(String fieldOrExpr) {
        this.addSort(fieldOrExpr, SortOrder.ASC);
        return this;
    }

    /**
     * 设置默认操作。
     *
     * @param operator
     * @return
     */
    public AmazonCloudSearchQuery setDefaultOperator(String operator) {
        this.getQueryOptions().put("defaultOperator", operator);
        return this;
    }

    public AmazonCloudSearchQuery setFields(String... fields) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < fields.length; i++) {
            builder.append("'").append(fields[i]).append("'");
            if (i != fields.length - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
        this.getQueryOptions().put("fields", builder.toString());
        return this;
    }

    public AmazonCloudSearchQuery setPhraseFields(String... fields) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < fields.length; i++) {
            builder.append("'").append(fields[i]).append("'");
            if (i != fields.length - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
        this.getQueryOptions().put("phraseFields", builder.toString());
        return this;
    }

    public AmazonCloudSearchQuery setPhraseSlop(int phraseSlop) {
        this.getQueryOptions().put("phraseSlop", String.valueOf(phraseSlop));
        return this;
    }

    public AmazonCloudSearchQuery setExplicitPhraseSlop(int explicitPhraseSlop) {
        this.getQueryOptions().put("explicitPhraseSlop", String.valueOf(explicitPhraseSlop));
        return this;
    }

    public AmazonCloudSearchQuery setTieBreaker(double tieBreaker) {
        this.getQueryOptions().put("tieBreaker", String.valueOf(tieBreaker));
        return this;
    }

    public AmazonCloudSearchQuery disableOperators(String... operators) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < operators.length; i++) {
            builder.append("'").append(operators[i]).append("'");
            if (i != operators.length - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
        this.getQueryOptions().put("operators", builder.toString());
        return this;
    }

    public AmazonCloudSearchQuery setReturnFields(String... returnFields) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < returnFields.length; i++) {
            builder.append(returnFields[i]);
            if (i != returnFields.length - 1) {
                builder.append(",");
            }
        }
        builder.append("]");

        this.setReturnFields(builder.toString());
        return this;
    }

    public AmazonCloudSearchQuery withQuery(String query) {
        this.setQuery(query);
        return this;
    }

    public AmazonCloudSearchQuery withQueryParser(QueryParser queryParser) {
        this.setQueryParser(queryParser);
        return this;
    }

    public AmazonCloudSearchQuery withCursor(String cursor) {
        this.setCursor(cursor);
        return this;
    }

    public AmazonCloudSearchQuery withStructuredQuery(String structuredQuery) {
        this.setStructuredQuery(structuredQuery);
        return this;
    }

    public AmazonCloudSearchQuery withPartial(boolean partial) {
        this.setPartial(partial);
        return this;
    }

    public AmazonCloudSearchQuery withPretty(boolean pretty) {
        this.setPretty(pretty);
        return this;
    }

    public AmazonCloudSearchQuery withReturnFields(String... returnFields) {
        if (returnFields != null && returnFields.length > 0) {
            StringBuffer returnFieldsBuffer = new StringBuffer();
            for (String returnField : returnFields) {
                returnFieldsBuffer.append(returnField).append(",");
            }
            this.setReturnFields(returnFieldsBuffer.deleteCharAt(returnFieldsBuffer.length() - 1).toString());
        }
        return this;
    }

    public AmazonCloudSearchQuery withSize(int size) {
        if (size < 1) {
            size = 1;
        }
        this.setSize(size);
        return this;
    }

    public AmazonCloudSearchQuery withStart(int start) {
        if (start < 0) {
            start = 0;
        }
        this.setStart(start);
        return this;
    }

    public AmazonCloudSearchQuery withPage(int pageNo, int pageSize) {
        if (pageNo < 0) {
            pageNo = 0;
        }
        if (pageSize < 1) {
            pageSize = 1;
        }
        this.setStart(pageNo - 1);
        this.setSize(pageSize);
        return this;
    }

    public String build() throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();

        if (this.getCursor() != null) {
            builder.append("cursor=").append(URLEncoder.encode(this.getCursor(), CHARSET_UTF8));
        }

        if (this.getExpressions().size() > 0) {
            for (Map.Entry<String, String> entry : this.getExpressions().entrySet()) {
                if (builder.length() > 0) {
                    builder.append("&");
                }
                builder.append("expr.").append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), CHARSET_UTF8));
            }
        }

        if (this.getFacets().size() > 0) {
            for (Facet facet : this.getFacets()) {
                if (builder.length() > 0) {
                    builder.append("&");
                }
                builder.append("facet.").append(facet.field).append("=");

                StringBuilder value = new StringBuilder();
                value.append("{");
                if (facet.sort != null) {
                    value.append("sort:\"").append(facet.sort).append("\"");
                }
                if (facet.buckets != null) {
                    value.append("buckets:").append(facet.buckets);
                }
                if (facet.size != null) {
                    value.append("size:").append(facet.size);
                }

                value.append("}");

                builder.append(URLEncoder.encode(value.toString(), CHARSET_UTF8));
            }
        }


        if (this.getStructuredQuery() != null) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append("fq=").append(this.getStructuredQuery());
        }

        if (this.getHighlights().size() > 0) {
            for (Highlight highlight : this.getHighlights()) {
                if (builder.length() > 0) {
                    builder.append("&");
                }
                builder.append("highlight.").append(highlight.field).append("=");

                StringBuilder value = new StringBuilder();
                value.append("{");
                if (highlight.format != null) {
                    value.append("format:'").append(highlight.format).append("'");
                }
                if (highlight.maxPhrases != null) {
                    value.append("max_phrases:").append(highlight.maxPhrases);
                }
                if (highlight.preTag != null) {
                    value.append("pre_tag:'").append(highlight.preTag).append("'");
                }
                if (highlight.postTag != null) {
                    value.append("post_tag:'").append(highlight.postTag).append("'");
                }
                value.append("}");

                builder.append(URLEncoder.encode(value.toString(), CHARSET_UTF8));

            }
        }

        if (this.getPartial() != null) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append("partial=").append(URLEncoder.encode(this.getPartial() + "", CHARSET_UTF8));
        }

        if (this.getPretty() != null) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append("pretty=").append(URLEncoder.encode(this.getPretty() + "", CHARSET_UTF8));
        }

        if (this.getQuery() != null) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append("q=").append(URLEncoder.encode(this.getQuery(), CHARSET_UTF8));
        }

        if (this.getQueryOptions().size() > 0) {
            if (builder.length() > 0) {
                builder.append("&");
            }

            builder.append("q.options=");

            StringBuilder value = new StringBuilder();
            value.append("{");
            Iterator<Map.Entry<String, String>> i = this.getQueryOptions().entrySet().iterator();
            while (i.hasNext()) {
                Map.Entry<String, String> entry = i.next();

                if (isArray(entry.getValue())) {
                    value.append(entry.getKey()).append(":").append(entry.getValue());
                } else {
                    value.append(entry.getKey()).append(":'").append(entry.getValue()).append("'");
                }

                if (i.hasNext()) {
                    value.append(",");
                }
            }
            value.append("}");

            builder.append(URLEncoder.encode(value.toString(), CHARSET_UTF8));
        }

        if (this.getQueryParser() != null && this.getQueryParser() != QueryParser.UNKNOWN) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append("q.parser=").append(URLEncoder.encode(this.getQueryParser().getValue(), CHARSET_UTF8));
        }

        if (this.getReturnFields() != null) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append("return=").append(URLEncoder.encode(this.getReturnFields(), CHARSET_UTF8));
        }

        if (this.getSize() != null) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append("size=").append(URLEncoder.encode(this.getSize() + "", CHARSET_UTF8));
        }

        if (this.getSort().size() > 0) {
            if (builder.length() > 0) {
                builder.append("&");
            }

            builder.append("sort=");

            StringBuilder value = new StringBuilder();
            int i = 0;
            for (Map.Entry<String, SortOrder> entry : this.getSort().entrySet()) {
                value.append(entry.getKey()).append(" ").append(entry.getValue().getValue());
                if (i != this.getSort().size() - 1) {
                    value.append(",");

                }
            }

            builder.append(URLEncoder.encode(value.toString(), CHARSET_UTF8));
        }

        if (this.getStart() != null) {
            if (builder.length() > 0) {

                builder.append("&");
            }
            builder.append("start=").append(URLEncoder.encode(this.getStart() + "", CHARSET_UTF8));
        }

        return builder.toString();
    }

    private boolean isArray(String value) {
        return value != null && value.length() > 1 && value.charAt(0) == '[' && value.charAt(value.length() - 1) == ']';
    }
}

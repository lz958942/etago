/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor Image Thumbor Executer
 * @Title : Constant.java
 * @Package : com.easarrive.image.thumbor.executer.util
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年7月4日
 * @Time : 下午7:09:05
 */
package com.easarrive.image.thumbor.executer.util;

import lombok.Getter;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年7月4日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class Constant {

    /**
     * SQS
     *
     * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
     * @version Constant
     * @notes Created on 2016年7月4日<br>
     * Revision of last commit:$Revision$<br>
     * Author of last commit:$Author$<br>
     * Date of last commit:$Date$<br>
     *
     */
    public static class SQS {

        /**
         * 属性
         *
         * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
         * @version Constant.SQS
         * @notes Created on 2016年7月4日<br>
         * Revision of last commit:$Revision$<br>
         * Author of last commit:$Author$<br>
         * Date of last commit:$Date$<br>
         *
         */
        public static enum Property {
            /**
             * 区域
             */
            REGION("com.easarrive.image.thumbor.executer.aws.sqs.region", null),
            /**
             * 队列名称
             */
            QUEUE_URL("com.easarrive.image.thumbor.executer.aws.sqs.queueUrl", null),
            /**
             * 每次读取消息的最大数量
             */
            MAX_NUMBER_OF_MESSAGES("com.easarrive.image.thumbor.executer.aws.sqs.maxNumberOfMessages", "1"),
            /**
             * 处理消息最大线程数量
             */
            MAX_THREAD_OF_MESSAGES("com.easarrive.image.thumbor.executer.aws.sqs.maxThreadOfMessages", "3"),
            /**
             * 处理消息最大线程数量
             */
            VISIBILITY_TIMEOUT("com.easarrive.image.thumbor.executer.aws.sqs.visibilityTimeout", "30"),
            /**
             * 未知
             */
            UNKNOWN("", null);

            @Getter
            private String key;

            @Getter
            private String value;

            private Property(String key, String value) {
                this.key = key;
                this.value = value;
            }

            public static Property fromKey(String key) {
                Property result = null;
                try {
                    result = valueOf(key);
                } catch (Exception e) {
                    result = UNKNOWN;
                }
                return result;
            }
        }

        public static class S3 {
            public static class URL {
                public static class Access {
                    public static final String FORMAT = "http://%s.s3.amazonaws.com/%s";
                }
            }
        }
    }

    public static class S3EventMessage {
        public static final String VERSION = "2.0";

        public static class Source {
            public static final String S3 = "aws:s3";
        }

        public static class EventName {
            public static final String OBJECT_PUT = "ObjectCreated:Put";
        }
    }
}

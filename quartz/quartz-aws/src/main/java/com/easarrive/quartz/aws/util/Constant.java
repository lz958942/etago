/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.quartz.aws.util
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 16:08
 */
package com.easarrive.quartz.aws.util;

import lombok.Getter;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月21日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class Constant {

    /**
     * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
     * @version Constant
     * @notes Created on 2016年7月4日<br>
     * Revision of last commit:$Revision$<br>
     * Author of last commit:$Author$<br>
     * Date of last commit:$Date$<br>
     *
     */
    public static final class API {
        /**
         * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
         * @version Constant.API
         * @notes Created on 2016年7月4日<br>
         * Revision of last commit:$Revision$<br>
         * Author of last commit:$Author$<br>
         * Date of last commit:$Date$<br>
         *
         */
        public static final class SpringMVC {
            /**
             * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
             * @version Constant.API.SpringMVC
             * @notes Created on 2016年7月4日<br>
             * Revision of last commit:$Revision$<br>
             * Author of last commit:$Author$<br>
             * Date of last commit:$Date$<br>
             *
             */
            public static enum PathVariable {
                REGION(PathVariableKey.REGION, "us-west-2"), UNKOWN("", null);

                @Getter
                private String name;

                @Getter
                private String value;

                private PathVariable(String name, String defaultValue) {
                    this.name = name;
                    this.value = defaultValue;
                }

                public static PathVariable fromName(String name) {
                    PathVariable result = null;
                    try {
                        result = PathVariable.valueOf(name);
                    } catch (Exception e) {
                        result = UNKOWN;
                    }
                    return result;
                }

                public static final class PathVariableKey {
                    public static final String REGION = "awsRegion";
                }
            }
        }
    }

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
                    public static class Goods {
                        public static final String SOURCE_FORMAT = "http://%s.s3.amazonaws.com/%s";
                        public static final String TARGET_FORMAT = "http://%s.s3.amazonaws.com/images/small/goods/%s";
                    }

                    public static class Users {
                        public static final String SOURCE_FORMAT = "http://%s.s3.amazonaws.com/%s";
                        public static final String TARGET_FORMAT = "http://%s.s3.amazonaws.com/images/small/user/%s";
                    }
                }
            }
        }
    }
}

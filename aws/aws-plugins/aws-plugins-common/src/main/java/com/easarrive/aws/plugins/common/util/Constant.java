/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : Constant.java
 * @Package : net.lizhaoweb.aws.plugin.util
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月28日
 * @Time : 下午9:23:46
 */
package com.easarrive.aws.plugins.common.util;

import lombok.Getter;

/**
 * <h1>常量</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年6月28日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class Constant {

    /**
     * 消息队列。
     *
     * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
     * @version Constant
     * @notes Created on 2016年6月27日<br>
     * Revision of last commit:$Revision$<br>
     * Author of last commit:$Author$<br>
     * Date of last commit:$Date$<br>
     *
     */
    public static class SNS {
        /**
         * HTTP
         *
         * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
         * @version Constant.SNS
         * @notes Created on 2016年6月27日<br>
         * Revision of last commit:$Revision$<br>
         * Author of last commit:$Author$<br>
         * Date of last commit:$Date$<br>
         *
         */
        public static class HTTP4AWS {
            /**
             * 请求
             *
             * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
             * @version Constant.SNS.HTTP
             * @notes Created on 2016年6月27日<br>
             * Revision of last commit:$Revision$<br>
             * Author of last commit:$Author$<br>
             * Date of last commit:$Date$<br>
             *
             */
            public static class Request {
                /**
                 * 头
                 *
                 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
                 * @version Constant.SNS.HTTP.Request
                 * @notes Created on 2016年6月27日<br>
                 * Revision of last commit:$Revision$<br>
                 * Author of last commit:$Author$<br>
                 * Date of last commit:$Date$<br>
                 *
                 */
                public static enum Header {
                    /**
                     * 亚马逊消息类型
                     */
                    X_AMZ_SNS_MESSAGE_TYPE(HeaderKey.X_AMZ_SNS_MESSAGE_TYPE, null),
                    /**
                     * 未知
                     */
                    UNKOWN("UNKOWN", null);

                    @Getter
                    private String name;

                    @Getter
                    private String value;

                    // 构造方法
                    private Header(String name, String defaultValue) {
                        this.name = name;
                        this.value = defaultValue;
                    }

                    public static Header fromName(String name) {
                        Header result = null;
                        try {
                            result = Header.valueOf(name);
                        } catch (Exception e) {
                            result = UNKOWN;
                        }
                        return result;
                    }

                    public String getHeaderName() {
                        return this.name;
                    }

                    /**
                     * 键
                     *
                     * @author <a
                     *         href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
                     * @version Constant.SNS.HTTP.Request.Header
                     * @notes Created on 2016年7月4日<br>
                     * Revision of last commit:$Revision$<br>
                     * Author of last commit:$Author$<br>
                     * Date of last commit:$Date$<br>
                     *
                     */
                    public static class HeaderKey {
                        /**
                         * 亚马逊消息类型
                         */
                        public static final String X_AMZ_SNS_MESSAGE_TYPE = "x-amz-sns-message-type";
                    }
                }
            }

            /**
             * 响应
             *
             * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
             * @version Constant.SNS.HTTP
             * @notes Created on 2016年6月27日<br>
             * Revision of last commit:$Revision$<br>
             * Author of last commit:$Author$<br>
             * Date of last commit:$Date$<br>
             *
             */
            public static class Response {

                /**
                 * 消息
                 *
                 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
                 * @version Constant.SNS.HTTP.Request
                 * @notes Created on 2016年6月27日<br>
                 * Revision of last commit:$Revision$<br>
                 * Author of last commit:$Author$<br>
                 * Date of last commit:$Date$<br>
                 *
                 */
                public static class Message {
                    /**
                     * 签名版本号
                     */
                    public static enum SIGNATURE_VERSION {
                        /**
                         * 版本1
                         */
                        V1("1"),
                        /**
                         * 未知版本
                         */
                        UNKOWN("UNKOWN");

                        @Getter
                        private String value;

                        // 构造方法
                        private SIGNATURE_VERSION(String value) {
                            this.value = value;
                        }

                        public static SIGNATURE_VERSION fromValue(String value) {
                            SIGNATURE_VERSION result = null;
                            try {
                                result = SIGNATURE_VERSION.valueOf(value);
                            } catch (Exception e) {
                                result = UNKOWN;
                            }
                            return result;
                        }
                    }

                    /**
                     * 类型
                     *
                     * @author <a
                     *         href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
                     * @version Constant.SNS.HTTP.Request.Message
                     * @notes Created on 2016年6月27日<br>
                     * Revision of last commit:$Revision$<br>
                     * Author of last commit:$Author$<br>
                     * Date of last commit:$Date$<br>
                     *
                     */
                    public static enum Type {
                        /**
                         * 消息
                         */
                        NOTIFICATION("Notification"),
                        /**
                         * 订阅确认
                         */
                        SUBSCRIPTION_CONFIRMATION("SubscriptionConfirmation"),
                        /**
                         * 退订
                         */
                        UNSUBSCRIBE_CONFIRMATION("UnsubscribeConfirmation"),
                        /**
                         * 未知
                         */
                        UNKOWN("UNKOWN");

                        @Getter
                        private String name;

                        // 构造方法
                        private Type(String name) {
                            this.name = name;
                        }

                        public static Type fromName(String name) {
                            Type result = null;
                            try {
                                result = Type.valueOf(name);
                            } catch (Exception e) {
                                result = UNKOWN;
                            }
                            return result;
                        }
                    }

                    /**
                     * 亚马逊事件
                     *
                     * @author <a
                     *         href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
                     * @version Constant.SNS.HTTP4AWS.Response.Message
                     * @notes Created on 2016年7月4日<br>
                     * Revision of last commit:$Revision$<br>
                     * Author of last commit:$Author$<br>
                     * Date of last commit:$Date$<br>
                     *
                     */
                    public static class AWSEvent {
                        /**
                         * 版本
                         *
                         * @author <a
                         *         href="http://www.lizhaoweb.cn">李召(John.Lee)
                         *         </a>
                         * @version Constant.SNS.HTTP4AWS.Response.Message.AWSEvent
                         * @notes Created on 2016年7月4日<br>
                         * Revision of last commit:$Revision$<br>
                         * Author of last commit:$Author$<br>
                         * Date of last commit:$Date$<br>
                         *
                         */
                        public static enum Version {
                            V2_0("2.0"),
                            /**
                             * 未知
                             */
                            UNKOWN("UNKOWN");

                            @Getter
                            private String value;

                            // 构造方法
                            private Version(String value) {
                                this.value = value;
                            }

                            public static Version fromValue(String value) {
                                Version result = null;
                                try {
                                    result = Version.valueOf(value);
                                } catch (Exception e) {
                                    result = UNKOWN;
                                }
                                return result;
                            }
                        }

                        /**
                         * 来源
                         *
                         * @author <a
                         *         href="http://www.lizhaoweb.cn">李召(John.Lee)
                         *         </a>
                         * @version Constant.SNS.HTTP4AWS.Response.Message.AWSEvent
                         * @notes Created on 2016年7月4日<br>
                         * Revision of last commit:$Revision$<br>
                         * Author of last commit:$Author$<br>
                         * Date of last commit:$Date$<br>
                         *
                         */
                        public static enum Source {
                            S3("aws:s3"),
                            /**
                             * 未知
                             */
                            UNKOWN("UNKOWN");

                            @Getter
                            private String value;

                            // 构造方法
                            private Source(String value) {
                                this.value = value;
                            }

                            public static Source fromValue(String value) {
                                Source result = null;
                                try {
                                    result = Source.valueOf(value);
                                } catch (Exception e) {
                                    result = UNKOWN;
                                }
                                return result;
                            }
                        }

                        /**
                         * 名称
                         *
                         * @author <a
                         *         href="http://www.lizhaoweb.cn">李召(John.Lee)
                         *         </a>
                         * @version Constant.SNS.HTTP4AWS.Response.Message.AWSEvent
                         * @notes Created on 2016年7月4日<br>
                         * Revision of last commit:$Revision$<br>
                         * Author of last commit:$Author$<br>
                         * Date of last commit:$Date$<br>
                         *
                         */
                        public static enum Name {
                            ObjectCreatedPut("ObjectCreated:Put"),
                            /**
                             * 未知
                             */
                            UNKOWN("UNKOWN");

                            @Getter
                            private String value;

                            // 构造方法
                            private Name(String value) {
                                this.value = value;
                            }

                            public static Name fromValue(String value) {
                                Name result = null;
                                try {
                                    result = Name.valueOf(value);
                                } catch (Exception e) {
                                    result = UNKOWN;
                                }
                                return result;
                            }
                        }
                    }
                }
            }
        }
    }

    public static class SQS {
        public static class Attribute {
            public static final String ALL = "All";
            public static final String DELAY_SECONDS = "DelaySeconds";
            public static final String MESSAGE_RETENTION_PERIOD = "MessageRetentionPeriod";
            public static final String MAXIMUM_MESSAGE_SIZE = "MaximumMessageSize";
            public static final String RECEIVE_MESSAGE_WAIT_TIME_SECONDS = "ReceiveMessageWaitTimeSeconds";
            public static final String LAST_MODIFIED_TIMESTAMP = "LastModifiedTimestamp";
            public static final String CREATED_TIMESTAMP = "CreatedTimestamp";
            public static final String VISIBILITY_TIMEOUT = "VisibilityTimeout";
            public static final String APPROXIMATE_NUMBER_OF_MESSAGES = "ApproximateNumberOfMessages";
            public static final String APPROXIMATE_NUMBER_OF_MESSAGES_NOT_VISIBLE = "ApproximateNumberOfMessagesNotVisible";
            public static final String QUEUE_ARN = "QueueArn";
            public static final String APPROXIMATE_NUMBER_OF_MESSAGES_DELAYED = "ApproximateNumberOfMessagesDelayed";
        }
    }
}

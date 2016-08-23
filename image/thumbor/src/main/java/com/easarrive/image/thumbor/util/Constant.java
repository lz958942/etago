/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor Image Thumbor
 * @Title : Constant.java
 * @Package : com.easarrive.image.thumbor.util
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月21日
 * @Time : 下午9:12:27
 */
package com.easarrive.image.thumbor.util;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年6月21日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class Constant {

    public class Key {
        public static final String IMAGE_THUMBOR_CONFIG_AWS_S3 = "com.easarrive.image.thumbor.aws.s3.config";
    }

    public static class URL {
        public static final int MAXIMUM_LENGTH = 1024;
    }

    public static class Thumbor {
        public static class Quality {
            public static final int MINIMUM = 0;
            public static final int MAXIMUM = 100;
        }

        public static class RoundCorner {
            public static final int DEFAULT = 0;
        }

        public static class Watermark {
            public static class X {
                public static final int MINIMUM = 0;
            }

            public static class Y {
                public static final int MINIMUM = 0;
            }

            public static class Transparency {
                public static final int MINIMUM = 0;
                public static final int MAXIMUM = 100;
            }
        }

        public static class Strong {
            public static class HTTP {
                public static class Header {
                    public static class Key {
                        public static class S3 {
                            public static final String PATH = "X-THUMBOR-STRONG-AWS-S3";
                            public static final String CONTENT_TYPE = "X-THUMBOR-STRONG-AWS-S3-CONTENT-TYPE";
                        }
                    }
                }
            }
        }
    }
}

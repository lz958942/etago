/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.datasource.redis.etago.util
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 20:51
 */
package com.easarrive.datasource.redis.etago.util;

/**
 * <h1>工具 - 常量</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月25日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class Constant {

    public static class Thumbor {
        public static class System {
            public static class Config {
                public static final String NAMESPACE = "etago:system:config:image:thumbor";
                public static final String FEILD_JSON = "JSON";
            }
        }

        public static class Path {
            public static class Image {
                public static final String GOODS = "etago:api:images:goods";
                public static final String USERS = "etago:api:images:user";
            }
        }
    }
}

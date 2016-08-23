/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Api
 * @Title : Constant.java
 * @Package : net.lizhaoweb.aws.api.util
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月28日
 * @Time : 下午9:32:16
 */
package net.lizhaoweb.aws.api.util;

import lombok.Getter;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年6月28日<br>
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
}

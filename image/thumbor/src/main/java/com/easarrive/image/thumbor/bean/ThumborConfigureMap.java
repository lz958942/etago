/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.image.thumbor.bean
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 14:57
 */
package com.easarrive.image.thumbor.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年08月05日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class ThumborConfigureMap extends HashMap<String, Map<String, ThumborContext>> {

    public Map<String, ThumborContext> getKind(String kindId) {
        return this.get(kindId);
    }

    public ThumborContext getThumborContext(String kindId, String separateId) {
        Map<String, ThumborContext> separateMap = this.get(kindId);
        if (separateMap != null) {
            return separateMap.get(separateId);
        }
        return null;
    }
}

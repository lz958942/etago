/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : PACKAGE_NAME
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 13:33
 */

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年08月06日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class TestRegex {

    @Test
    public void pic_size() {
        String redisKey = "32851183ec83b22e49a3343b21db9737abc260de1470384822_1080x1080.png";

        Pattern pattern = Pattern.compile("^[^_]+_(\\d+x\\d+)\\.\\w+$");
        Matcher matcher = pattern.matcher(redisKey);
        String separateId = "";
        if (matcher.find()) {
            separateId = matcher.group(1);
        }
        System.out.println(separateId);
    }

    @Test
    public void bbb() {
        try {
            String aaa = "\ud83c\udc00";
            String aab = URLEncoder.encode(aaa, "UTF-8");
            System.out.println(aab);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

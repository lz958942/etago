/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : PACKAGE_NAME
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 16:27
 */

import net.lizhaoweb.common.util.base.HttpClientUtil;
import net.lizhaoweb.common.util.base.StringUtil;
import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月28日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class TestS {

    private static final String REGEX_CONTENT_TYPE_CHARSET = "<meta http-equiv=\"Content-type\" content=\"text/html; charset=(.+)\"/>";

    private static final String REG_URL = "<a.+href=\"(http(?:s)?:[^\"]+)\"(?: )?(?:/)?>";

    private static final int MAX_COUNT = 100;

    private String serverURL;

    private String souorceURL;

    private Map<Integer, String> infoMap = new HashMap<Integer, String>();

    @Before
    public void init() {
        this.serverURL = "http://192.168.199.139:8080";
        this.souorceURL = "http://bj.tuanche.com";

        this.initInfo();
    }

    @Test
    public void thead() {
        long startTiem = System.currentTimeMillis();
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<String>> resultList = new ArrayList<Future<String>>();
        for (int count = 0; count < MAX_COUNT; count++) {
            final String info = infoMap.get(count);
            Future<String> future = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    try {
                        String url = String.format("%s?q=%s", serverURL, info);
                        String result = getHttpContent(url);
                        return result;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });
            resultList.add(future);
        }

        //遍历任务的结果
        for (Future<String> fs : resultList) {
            try {
                String result = fs.get();
                System.out.println(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } finally {
                //启动一次顺序关闭，执行以前提交的任务，但不接受新任务。如果已经关闭，则调用没有其他作用。
                executorService.shutdown();
            }
        }
        String timeInfo = String.format("总执行时间： %d 毫秒", System.currentTimeMillis() - startTiem);
        System.out.println(timeInfo);
    }

    protected void initInfo() {
        Queue<String> urlQueue = new LinkedBlockingQueue<>();
        urlQueue.add(this.souorceURL);
        for (int index = 0; index < MAX_COUNT && urlQueue.size() > 0; index++) {
            String searchURL = urlQueue.poll();
            String info = this.getHttpContent(searchURL);
//            String info = "名字的简称，或是其它。aa一种是指吃饭几个人个付个的款人名亚历山德拉·安布罗西奥（Alessandra%20Ambrosio），维多利亚的秘密(Victorias%20Secret)品牌的签约模特。乐队/组合Asking%20Alexandria，一支英国的后硬核金属乐队。%20Double%20A%20(AA)，...";
            if (StringUtil.isEmpty(info)) {
                index--;
                continue;
            }
            infoMap.put(index, info);
            Queue<String> searchURLQueue = this.analysisURL(info);
            urlQueue.addAll(searchURLQueue);
        }
    }

    protected Queue<String> analysisURL(String content) {
        if (StringUtil.isEmpty(content)) {
            return null;
        }
        while (content.indexOf("\n\r") > -1) {
            content = content.replace("\n\r", " ");
        }
        while (content.indexOf("\n") > -1) {
            content = content.replace("\n", " ");
        }
        while (content.indexOf("\r") > -1) {
            content = content.replace("\r", " ");
        }
        while (content.indexOf("\t") > -1) {
            content = content.replace("\t", " ");
        }
        while (content.indexOf("  ") > -1) {
            content = content.replace("  ", " ");
        }
        while (content.indexOf("> <") > -1) {
            content = content.replace("> <", "><");
        }
        Queue<String> urlQueue = new LinkedBlockingQueue<>();
        Pattern pattern = Pattern.compile(REG_URL);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            urlQueue.add(matcher.group(1));
        }
        return urlQueue;
    }

    protected String getHttpContent(String url) {
        try {
            HttpResponse httpResponse = HttpClientUtil.get(url, null);
            String result = HttpClientUtil.getHttpResponseBody(httpResponse);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.datasource.redis.etago.write.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 16:38
 */
package com.easarrive.datasource.redis.etago.write.impl;

import com.easarrive.datasource.redis.etago.model.*;
import com.easarrive.datasource.redis.etago.read.IThumborReadDao;
import com.easarrive.datasource.redis.etago.write.IThumborWriteDao;
import net.lizhaoweb.common.util.base.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年07月25日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/schema/spring/spring-redis_etago-datasource.xml", "classpath*:/schema/spring/spring-redis_etago-mapper.xml"})
public class TestThumborConfigureDao {

    @Autowired
    private IThumborWriteDao<String, ThumborConfigure> writeDao;

    @Autowired
    private IThumborReadDao<String, ThumborConfigure> readDao;

    private Queue<ThumborCallBackURL> callURLQueue;

    private int maxCallCount;

    private long maxTimeInterval;

    @Before
    public void init() {
        this.callURLQueue = new LinkedBlockingQueue<ThumborCallBackURL>();
        this.callURLQueue.add(new ThumborCallBackURL(
                "http://127.0.0.1:3081/thumbor-executer-1.0.0.0.1-SNAPSHOT/api/config/reload",
                0,
                System.currentTimeMillis()
        ));

        this.maxCallCount = 3;
        this.maxTimeInterval = 5000;
    }

    @Test
    public void save() {
        ThumborConfigure configure = this.buildThumborConfigure();
        writeDao.save(com.easarrive.datasource.redis.etago.util.Constant.Thumbor.System.Config.FEILD_JSON, configure);
        //this.callBack(configure);
    }

    @Test
    public void get() {
        ThumborConfigure configure = readDao.get(com.easarrive.datasource.redis.etago.util.Constant.Thumbor.System.Config.FEILD_JSON);
        System.out.println(configure);
    }

    private ThumborConfigure buildThumborConfigure() {

        //处理服务器对象
        ThumborConfigureServer server = new ThumborConfigureServer("http://172.31.41.222:5277");

        //水印对象。ThumborConfigureWatermark(水印图片地址, 水平坐标, 垂直坐标, 透明度)
        ThumborConfigureWatermark watermark = new ThumborConfigureWatermark(null, 0, 0, 50);


        List<ThumborConfigureKind> kindList = new ArrayList<ThumborConfigureKind>();
        // ====================================== 产品图片缩放规格列表 ======================================
        List<ThumborConfigureSeparate> goodsSeparateList = this.getGoodsSeparateList();
        ThumborConfigureKind goodsKind = new ThumborConfigureKind("images/source/goods", "images/source/goods", "images/small/goods", goodsSeparateList);
        kindList.add(goodsKind);


        // ====================================== 用户图片缩放规格列表 ======================================
        List<ThumborConfigureSeparate> usersSeparateList = this.getUsersSeparateList();
        ThumborConfigureKind usersKind = new ThumborConfigureKind("images/source/user", "images/source/user", "images/small/user", usersSeparateList);
        usersKind.setServer(server);
        usersKind.setWatermark(watermark);
        kindList.add(usersKind);


        // ====================================== 种类列表 ======================================

        //ThumborConfigureKind(种类标识, 源目录, 目标目录, 规格列表)


        //处理图片位置 ThumborConfigureAlign(水平, 垂直)
        ThumborConfigureAlign align = new ThumborConfigureAlign("CENTER", "MIDDLE");

        //ThumborConfigure(是否智能, 是否去空白, 是否打印信息, 图片位置对象, 种类列表)
        ThumborConfigure configure = new ThumborConfigure(true, true, false, align, kindList);
        configure.setServer(server);
        configure.setWatermark(watermark);

        return configure;
    }

    //产品图片缩放规格列表
    private List<ThumborConfigureSeparate> getGoodsSeparateList() {
        List<ThumborConfigureSeparate> goodsSeparateList = new ArrayList<ThumborConfigureSeparate>();

        //ThumborConfigureSeparate(图片处理格式, 处理后文件后缀, 处理质量, 圆角度, 宽度, 高度)

        //png_1080X1080
        ThumborConfigureSeparate goodsSeparate_png_1080X1080 = new ThumborConfigureSeparate("png", ".png", 100, 0, 1080, 1080);
        goodsSeparateList.add(goodsSeparate_png_1080X1080);

        //png_750X750
        ThumborConfigureSeparate goodsSeparate_png_750X750 = new ThumborConfigureSeparate("png", ".png", 100, 0, 750, 750);
        goodsSeparateList.add(goodsSeparate_png_750X750);

        //png_250X250
        ThumborConfigureSeparate goodsSeparate_png_250X250 = new ThumborConfigureSeparate("png", ".png", 100, 0, 250, 250);
        goodsSeparateList.add(goodsSeparate_png_250X250);

        //png_160X160
        ThumborConfigureSeparate goodsSeparate_png_160X160 = new ThumborConfigureSeparate("png", ".png", 100, 0, 160, 160);
        goodsSeparateList.add(goodsSeparate_png_160X160);

        //png_124X124
        ThumborConfigureSeparate goodsSeparate_png_124X124 = new ThumborConfigureSeparate("png", ".png", 100, 0, 124, 124);
        goodsSeparateList.add(goodsSeparate_png_124X124);

        return goodsSeparateList;
    }

    //用户图片缩放规格列表
    private List<ThumborConfigureSeparate> getUsersSeparateList() {
        List<ThumborConfigureSeparate> usersSeparateList = new ArrayList<ThumborConfigureSeparate>();

        //ThumborConfigureSeparate(图片处理格式, 处理后文件后缀, 处理质量, 圆角度, 宽度, 高度)

        //png_200X200
        ThumborConfigureSeparate usersSeparate_png_200X200 = new ThumborConfigureSeparate("png", ".png", 100, 0, 200, 200);
        usersSeparateList.add(usersSeparate_png_200X200);

        //png_160X160
        ThumborConfigureSeparate usersSeparate_png_160X160 = new ThumborConfigureSeparate("png", ".png", 100, 0, 160, 160);
        usersSeparateList.add(usersSeparate_png_160X160);

        //png_80X80
        ThumborConfigureSeparate usersSeparate_png_80X80 = new ThumborConfigureSeparate("png", ".png", 100, 0, 80, 80);
        usersSeparateList.add(usersSeparate_png_80X80);

        return usersSeparateList;
    }

    private void callBack(ThumborConfigure configure) {
        if (callURLQueue == null) {
            return;
        }

        byte[] jsonByteArray = null;
        try {
            jsonByteArray = JsonUtil.toBytes(configure);
        } catch (Exception e) {
            return;
        }

        String sign = DigestUtils.md5Hex(jsonByteArray);

        List<Header> headerList = new ArrayList<Header>();
        headerList.add(new BasicHeader("VERSION", "1.0.0.0.1"));
        headerList.add(new BasicHeader("CHARSET", "UTF-8"));
        headerList.add(new BasicHeader("DATA_SIGN", sign));

        ByteArrayEntity entity = new ByteArrayEntity(jsonByteArray, ContentType.APPLICATION_JSON);

        boolean run = true;
        while (run) {
            //队列是否空了
            if (callURLQueue == null || callURLQueue.size() < 1) {
                run = false;
                continue;
            }

            //从队列中获取数据
            ThumborCallBackURL callBackURL = callURLQueue.poll();
            if (callBackURL == null) {
                continue;
            }

            //获取回调地址
            String callURL = callBackURL.getUrl();
            if (StringUtil.isEmpty(callURL)) {
                continue;
            }

            //判断是否超出最大回调次数
            int callCount = callBackURL.getCallCount();
            if (callCount >= this.maxCallCount) {
                continue;
            }

            //判断是否满足回调的时间间隔
            long lastRequestTime = callBackURL.getLastRequestTime();
            if (lastRequestTime + this.maxTimeInterval > System.currentTimeMillis()) {
                callURLQueue.add(callBackURL);//移动到队尾
                continue;
            }

            try {
                //开始回调
                HttpResponse httpResponse = HttpClientUtil.post(callURL, headerList, entity);
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                Header contentEncodingHeader = httpResponse.getEntity().getContentEncoding();
                String charset = null;
                if (contentEncodingHeader == null) {
                    charset = Constant.Charset.UTF8;
                } else {
                    charset = contentEncodingHeader.getValue();
                }
                if (StringUtil.isEmpty(charset)) {
                    charset = Constant.Charset.UTF8;
                }
                String content = IOUtil.getString(httpResponse.getEntity().getContent(), charset);

                System.out.println(httpResponse.getStatusLine().getReasonPhrase());
                System.out.println(httpResponse.getEntity().getContentType());
                System.out.println(httpResponse.getEntity().getContentEncoding());
                System.out.println(content);
                if (statusCode != 200) {
                    //回调失败
                    callBackURL.setCallCount(callCount + 1);
                    callBackURL.setLastRequestTime(System.currentTimeMillis());
                    callURLQueue.add(callBackURL);//到队尾重新排队
                }
            } catch (IOException e) {
                //回调失败
                callBackURL.setCallCount(callCount + 1);
                callBackURL.setLastRequestTime(System.currentTimeMillis());
                callURLQueue.add(callBackURL);//到队尾重新排队
            }
        }
    }
}

/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : parent
 * @Package : com.easarrive.image.thumbor.executer.controller
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 23:18
 */
package com.easarrive.image.thumbor.executer.controller;

import com.easarrive.datasource.redis.etago.model.ThumborConfigure;
import com.easarrive.image.thumbor.ConfigureFactory;
import com.easarrive.image.thumbor.bean.ThumborConfigureMap;
import com.easarrive.image.thumbor.executer.service.IThumborConfigureService;
import lombok.Setter;
import net.lizhaoweb.common.util.base.Constant;
import net.lizhaoweb.common.util.base.StringUtil;
import net.lizhaoweb.spring.mvc.core.bean.DataDeliveryWrapper;
import net.lizhaoweb.spring.mvc.core.controller.AbstractController;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年08月16日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
@RequestMapping("/thumbor/configure")
public class ThumborConfigureController extends AbstractController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String MODEL = "thumbor/configure";

    @Setter
    private IThumborConfigureService service;

    /**
     * 展示页面
     *
     * @param forward 跳转的页面
     * @return 返回视图。
     */
    @RequestMapping("/show/{forward}")
    public ModelAndView show(@PathVariable("forward") String forward) {
        String forwardS = String.format("/%s/%s", MODEL, forward);
        ModelAndView view = new ModelAndView(forwardS, "bean", new ThumborConfigure());
        return view;
    }

    /**
     * @param request  请求对象
     * @param response 响应对象
     * @param model    请求参数
     * @param bean     实例对象
     * @param result   验证结果
     * @return 返回视图。
     */
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public String save(
            HttpServletRequest request,
            HttpServletResponse response,
            ModelMap model,
            @ModelAttribute("bean") @Valid ThumborConfigure bean,
            BindingResult result
    ) {
        System.out.println(bean);
//        if (!result.hasErrors()) {
//            service.save(bean);
//        }
        return String.format("/%s/save", MODEL);
    }

    /**
     * 重新加载配置
     *
     * @param request  请求对象
     * @param response 响应对象
     * @return 返回操作信息。
     */
    @RequestMapping(value = "/reload", method = {RequestMethod.POST})
    @ResponseBody
    public DataDeliveryWrapper<Boolean> reloadConfig(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        //获取请求头信息
        String acceptEncoding = this.getAcceptEncoding(request);
        try {
            String version = request.getHeader("VERSION");
            String sign = request.getHeader("DATA_SIGN");
            String charset = request.getHeader("CHARSET");

            //获取请求体信息
            String dataJson = this.getRequestBody(request);

            //获取字符集
            if (StringUtil.isEmpty(charset)) {
                charset = request.getCharacterEncoding();
            }
            if (StringUtil.isEmpty(charset)) {
                charset = Constant.Charset.UTF8;
            }

            //校验
            if (StringUtil.isEmpty(version)) {
                return new DataDeliveryWrapper<Boolean>(HttpStatus.SC_NOT_FOUND, this.encode("版本为空", acceptEncoding), false);
            } else if (!"1.0.0.0.1".equals(version)) {
                return new DataDeliveryWrapper<Boolean>(HttpStatus.SC_NOT_FOUND, this.encode("版本号不正确", acceptEncoding), false);
            } else if (StringUtil.isEmpty(sign)) {
                return new DataDeliveryWrapper<Boolean>(HttpStatus.SC_NOT_FOUND, this.encode("签名为空", acceptEncoding), false);
            } else if (StringUtil.isEmpty(dataJson)) {
                return new DataDeliveryWrapper<Boolean>(HttpStatus.SC_NOT_FOUND, this.encode("数据为空", acceptEncoding), false);
            }

            String signTemp = DigestUtils.md5Hex(dataJson);
            if (!sign.equals(signTemp)) {
                return new DataDeliveryWrapper<Boolean>(HttpStatus.SC_FORBIDDEN, this.encode("数据不正确", acceptEncoding), false);
            }

            ConfigureFactory.clear();
//            ThumborConfigure configure = ConfigureFactory.getConfigure();
//            if (logger.isInfoEnabled()) {
//                logger.info("重新加载配置文件为 ： {}", configure);
//            }
            ThumborConfigureMap configureMap = ConfigureFactory.getConfigureMap();
            if (logger.isInfoEnabled()) {
                logger.info("重新加载配置文件为 ： {}", configureMap);
            }
            return new DataDeliveryWrapper<Boolean>(HttpStatus.SC_OK, this.encode("Thumbor 重新加载配置成功", acceptEncoding), true);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("Thumbor 配置设置异常", e);
            }
            return new DataDeliveryWrapper<Boolean>(HttpStatus.SC_INTERNAL_SERVER_ERROR, this.encode("Thumbor 重新加载配置异常", acceptEncoding), false);
        }
    }
}

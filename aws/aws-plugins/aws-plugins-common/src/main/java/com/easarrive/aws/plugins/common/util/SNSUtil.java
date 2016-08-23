/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : Savor AWS Plugin
 * @Title : SNSUtil.java
 * @Package : net.lizhaoweb.aws.plugin.util
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Date : 2016年6月28日
 * @Time : 下午9:24:30
 */
package com.easarrive.aws.plugins.common.util;

import com.easarrive.aws.plugins.common.model.SNSMessage;
import net.lizhaoweb.common.util.base.IOUtil;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Scanner;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @notes Created on 2016年6月28日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 *
 */
public class SNSUtil {

    /**
     * Build the string to sign for SubscriptionConfirmation and
     * UnsubscribeConfirmation messages.
     *
     * @param msg
     * @return
     */
    public static String buildSubscriptionStringToSign(SNSMessage msg) {
        String stringToSign = null;
        // Build the string to sign from the values in the message.
        // Name and values separated by newline characters
        // The name value pairs are sorted by name
        // in byte sort order.
        stringToSign = "Message\n";
        stringToSign += msg.getMessage() + "\n";
        stringToSign += "MessageId\n";
        stringToSign += msg.getMessageId() + "\n";
        stringToSign += "SubscribeURL\n";
        stringToSign += msg.getSubscribeURL() + "\n";
        stringToSign += "Timestamp\n";
        stringToSign += msg.getTimestamp() + "\n";
        stringToSign += "Token\n";
        stringToSign += msg.getToken() + "\n";
        stringToSign += "TopicArn\n";
        stringToSign += msg.getTopicArn() + "\n";
        stringToSign += "Type\n";
        stringToSign += msg.getType() + "\n";
        return stringToSign;
    }

    /**
     * Build the string to sign for Notification messages.
     *
     * @param msg
     * @return
     */
    public static String buildNotificationStringToSign(SNSMessage msg) {
        String stringToSign = null;

        // Build the string to sign from the values in the message.
        // Name and values separated by newline characters
        // The name value pairs are sorted by name
        // in byte sort order.
        stringToSign = "Message\n";
        stringToSign += msg.getMessage() + "\n";
        stringToSign += "MessageId\n";
        stringToSign += msg.getMessageId() + "\n";
        if (msg.getSubject() != null) {
            stringToSign += "Subject\n";
            stringToSign += msg.getSubject() + "\n";
        }
        stringToSign += "Timestamp\n";
        stringToSign += msg.getTimestamp() + "\n";
        stringToSign += "TopicArn\n";
        stringToSign += msg.getTopicArn() + "\n";
        stringToSign += "Type\n";
        stringToSign += msg.getType() + "\n";
        return stringToSign;
    }

    public static boolean isMessageSignatureValid(SNSMessage msg) {
        try {
            URL url = new URL(msg.getSigningCertURL());
            InputStream inStream = url.openStream();
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
            inStream.close();

            Signature sig = Signature.getInstance("SHA1withRSA");
            sig.initVerify(cert.getPublicKey());
            sig.update(getMessageBytesToSign(msg));
            return sig.verify(Base64.decodeBase64(msg.getSignature()));
        } catch (Exception e) {
            throw new SecurityException("Verify method failed.", e);
        }
    }

    public static byte[] getMessageBytesToSign(SNSMessage msg) {
        byte[] bytesToSign = null;
        if (msg.getType().equals("Notification"))
            bytesToSign = buildNotificationStringToSign(msg).getBytes();
        else if (msg.getType().equals("SubscriptionConfirmation") || msg.getType().equals("UnsubscribeConfirmation"))
            bytesToSign = buildSubscriptionStringToSign(msg).getBytes();
        return bytesToSign;
    }

    public static StringBuilder getHttpRequestContent(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        StringBuilder builder = null;
        try {
            builder = getHttpRequestContent(request.getInputStream());
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return builder;
    }

    public static StringBuilder getHttpRequestContent(URL url) {
        if (url == null) {
            return null;
        }
        StringBuilder builder = null;
        try {
            builder = getHttpRequestContent(url.openStream());
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return builder;
    }

    public static StringBuilder getHttpRequestContent(String urlString) {
        if (StringUtils.isEmpty(urlString)) {
            return null;
        }
        StringBuilder builder = null;
        try {
            builder = getHttpRequestContent(new URL(urlString).openStream());
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return builder;
    }

    public static StringBuilder getHttpRequestContent(InputStream inputStream) {
        Scanner scan = null;
        StringBuilder builder = new StringBuilder();
        try {
            scan = new Scanner(inputStream);
            while (scan.hasNextLine()) {
                builder.append(scan.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(scan);
        }
        return builder;
    }
}

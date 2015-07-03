package com.ehome.baidu.push.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * 短信发送
 */
public class SmsUtil {
    private static Logger logger = Logger.getLogger(SmsUtil.class);

    /**
     * 发送短信
     *
     * @param mobile 手机号
     * @param msg    短信内容
     */
    public static boolean sendMsg(String mobile, String msg) {
        if (mobile == null || "".equals(mobile)) {
            logger.error("手机号为空！！！");
            return false;
        }
        if (msg == null || "".equals(msg)) {
            logger.error("短信内容为空！！！");
            return false;
        }

        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("http://203.81.21.34/send/gsend.asp");
        postMethod.setParameter("name", "zzncp");
        postMethod.setParameter("pwd", "zzncp890");
        postMethod.setParameter("dst", mobile);
        postMethod.setParameter("longmsg", "1");
        postMethod.setParameter("sno", "");
        postMethod.setParameter("msg", msg);

        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(1000 * 60); // 设置超时60s
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=GBK");
        String entitys = "";
        try {
            int statusCode = httpClient.executeMethod(postMethod);
            if (statusCode != HttpStatus.SC_OK) {
                logger.error("远程返回失败: " + postMethod.getStatusLine());
            }
            entitys = postMethod.getResponseBodyAsString();
            if (logger.isDebugEnabled()) {
                logger.debug("发送短信结果:" + entitys);
            }
            entitys = entitys.toLowerCase();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }

        if (!"".equals(entitys)) {
            String[] result = entitys.split("&");
            String num = result[0].split("=")[1];
            if (Integer.valueOf(num) > 0) {
                String succMobile = result[1].split("=")[1];
                logger.debug("发送消息成功，用户[" + succMobile + "]数量：" + num + ":" + msg);
                return true;
            } else {
                logger.error("发送消息失败，用户[]数量：" + num + ":" + msg);
                return false;
            }
        } else return false;

    }
}

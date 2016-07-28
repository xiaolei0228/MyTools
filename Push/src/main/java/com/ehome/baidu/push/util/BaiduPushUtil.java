package com.ehome.baidu.push.util;

import com.alibaba.fastjson.JSONObject;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * trace
 *
 * @author: 郝晓雷
 * @date: 2015-06-02 15:58
 * @desc: 百度推送工具类
 */
public class BaiduPushUtil {
    static Logger logger = Logger.getLogger(BaiduPushUtil.class);

    /**
     * 单播推送消息
     *
     * @param msgMap title：标题。description：消息内容。channelId：指定的设备Id号
     *
     * @throws PushClientException
     * @throws PushServerException
     */
    public static void pushSingleDeviceMsg(Map<String, String> msgMap) throws PushClientException, PushServerException {
        String apiKey = "btKNLhRHV7gqt5PdGjnRQ233";
        String secretKey = "9v8BXCuwBEalLwkziLrKPhGWWFmDRMDo";
        PushKeyPair pair = new PushKeyPair(apiKey, secretKey);

        BaiduPushClient pushClient = new BaiduPushClient(pair, BaiduPushConstants.CHANNEL_REST_URL);

        pushClient.setChannelLogHandler(new YunLogHandler() {
            public void onHandle(YunLogEvent event) {
                //System.out.println(event.getMessage());
            }
        });

        try {
            // 4. specify request arguments
            //创建 Android的通知
            JSONObject notification = new JSONObject();

            notification.put("title", msgMap.get("title"));
            notification.put("description", msgMap.get("description"));
            notification.put("notification_builder_id", 0);
            notification.put("notification_basic_style", 4);

            //notification.put("open_type", 1);
            //notification.put("url", "http://push.baidu.com");

            //JSONObject jsonCustormCont = new JSONObject();
            //jsonCustormCont.put("key", "value"); //自定义内容，key-value
            //notification.put("custom_content", jsonCustormCont);

            PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest().
                    addChannelId(msgMap.get("channelId")).
                    addMsgExpires(3600 * 24).   // message有效时间
                    addMessageType(1).          // 1：通知,0:透传消息. 默认为0 注：IOS只有通知.
                    addMessage(notification.toString()).
                    addDeviceType(3);           // deviceType => 3:android, 4:ios
            // 5. http request
            PushMsgToSingleDeviceResponse response = pushClient.pushMsgToSingleDevice(request);
            // Http请求结果解析打印
            logger.debug("msgId: " + response.getMsgId() + ",sendTime: " + response.getSendTime());
        } catch (PushClientException e) {
            //ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,'true' 表示抛出, 'false' 表示捕获。
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                e.printStackTrace();
            }
        } catch (PushServerException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                logger.error(String.format("requestId: %d, errorCode: %d, errorMessage: %s", e.getRequestId(), e.getErrorCode(), e.getErrorMsg() + " 请检测channelId是否真的存在!"));
            }
        }
    }
}

import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.ehome.baidu.push.util.BaiduPushUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * MyTools
 *
 * @author: haoxiaolei
 * @date: 2016-08-16 09:53
 * @desc: 百度推送测试类
 */
public class BaiduPushUtilsTest {

    @Test
    public void push() throws PushClientException, PushServerException {
        Map<String, String> pushMap = new HashMap<>();
        pushMap.put("channelId", "4309651502633916384");
        pushMap.put("title", "我是消息标题");
        pushMap.put("description", "我是测试推送消息的内容......");

        BaiduPushUtil.pushSingleDeviceMsg(pushMap);
    }
}

package com.ehome.baidu.push.servlet;

import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.ehome.baidu.push.util.BaiduPushUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 郝晓雷
 * @date: 2015-06-25 17:56
 * @desc: 百度推送Servlet
 */
public class MsgServlet extends HttpServlet implements Serializable {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");              // 标题
        String description = req.getParameter("description");  // 消息内容
        String channelId = req.getParameter("channelId");      // 指定的设备Id

        Map<String, String> msgMap = new HashMap<>();
        msgMap.put("title", title);
        msgMap.put("description", description);
        msgMap.put("channelId", channelId);

        System.out.println("channelId:::::" + channelId);
        try {
            BaiduPushUtil.pushSingleDeviceMsg(msgMap);
            resp.getWriter().print("向[" + channelId + "][" + description + "]推送消息成功^_^");
        } catch (PushClientException | PushServerException e) {
            e.printStackTrace();
            resp.getWriter().print("向[" + channelId + "][" + description + "]推送消息失败，请检查网络或传值参数！");
        }
    }
}

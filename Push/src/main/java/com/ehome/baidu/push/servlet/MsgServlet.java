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
 * @author: ������
 * @date: 2015-06-25 17:56
 * @desc: ����Servlet
 */
public class MsgServlet extends HttpServlet implements Serializable {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");               // ����
        String description = req.getParameter("description");  // ��Ϣ����
        String channelId = req.getParameter("channelId");      // ָ�����豸Id��

        Map<String, String> msgMap = new HashMap<>();
        msgMap.put("title", title);
        msgMap.put("description", description);
        msgMap.put("channelId", channelId);

        try {
            BaiduPushUtil.pushSingleDeviceMsg(msgMap);
        } catch (PushClientException | PushServerException e) {
            e.printStackTrace();
        }
    }
}
package com.ehome.baidu.push.servlet;

import com.ehome.baidu.push.util.SmsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author: 郝晓雷
 * @date: 2015-06-26 09:50
 * @desc: 发送短信Servlet
 */
public class SmsServlet extends HttpServlet implements Serializable {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if ("send".equals(action)) {
            String mobile = req.getParameter("mobile");     // 手机号
            String msg = req.getParameter("msg");           // 短信内容
            // 短信内容字数多于63个字就分开发
            double divider = msg.length() / 63d;
            int split = msg.length() / 63;
            int timeNum = divider > 1 ? split + 1 : 1;  // 分几条短信发
            String splitMsg;
            for (int i = 0; i < timeNum; i++) {
                splitMsg = msg.substring(i * 63, ((i * 63 + 63) > msg.length() ? msg.length() : (i * 63 + 63)));
                boolean result = SmsUtil.sendMsg(mobile, splitMsg);
                if (result) {
                    resp.getWriter().print("向[" + mobile + "][" + msg + "]发送短信成功^_^");
                } else {
                    resp.getWriter().print("向[" + mobile + "][" + msg + "]发送短信失败，请检测网络原因！");
                }
            }
        } else if ("receive".equals(action)) {
            String receiveMsg = SmsUtil.receiveMsg();
            resp.getWriter().print(receiveMsg);
        }
    }
}

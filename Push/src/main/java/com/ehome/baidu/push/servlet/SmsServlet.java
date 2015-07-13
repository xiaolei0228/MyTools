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
        String mobile = req.getParameter("mobile");     // 手机号
        String msg = req.getParameter("msg");           // 短信内容

        boolean result = SmsUtil.sendMsg(mobile, msg);
        if (result) {
            resp.getWriter().print("向[" + mobile + "]发送短信成功^_^");
        } else {
            resp.getWriter().print("向[" + mobile + "]发送短信失败，请检测网络原因！");
        }
    }
}

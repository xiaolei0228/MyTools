package com.ehome.baidu.push.servlet;

import com.ehome.baidu.push.util.SmsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author: ������
 * @date: 2015-06-26 09:50
 * @desc: ���Ͷ���Servlet
 */
public class SmsServlet extends HttpServlet implements Serializable {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mobile = req.getParameter("mobile");     // �ֻ���
        String msg = req.getParameter("msg");           // ��������

        SmsUtil.sendMsg(mobile, msg);
    }
}

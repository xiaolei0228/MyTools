package com.ehome.file.servlet;

import com.ehome.file.util.RemoteFileUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * trace
 *
 * @author: ºÂÏþÀ×
 * @date: 2015-07-02 09:45
 * @desc: É¾³ýÎÄ¼þµÄservlet
 */
public class DeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 5826533312047626907L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        RemoteFileUtil.deleteFile(request);
    }


}

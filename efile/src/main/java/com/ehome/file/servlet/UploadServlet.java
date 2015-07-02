package com.ehome.file.servlet;

import com.ehome.file.util.FileUploadUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @date: 2015-7-1 15:22
 * @desc: 文件上传Servlet
 */
@WebServlet(name = "upload")
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = -8473571070728362228L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        FileUploadUtil.uploadFile(request);
    }


}
package com.ehome.uploaddownload.servlet;

import com.ehome.uploaddownload.util.PropertiesFileUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by xiaolei on 2015-03-22 23:34
 */
public class DownloadServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        boolean result = false;
        result = downloadFile(request, response);
        if (result) {
//		this.getServletContext().getRequestDispatcher("/success.jsp").forward(request, response);
            System.out.println("下载完成!");
        }
    }

    // 下载文件
    public boolean downloadFile(HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        String requestDownloadFileName = request.getParameter("fileName");
        String uploadPath = PropertiesFileUtil.getValue("upload.path");
        String realPath = uploadPath.startsWith("/") ? request.getServletContext().getRealPath("") : "";
        File file = new File(realPath + uploadPath + "/" + requestDownloadFileName);
        if (file.exists()) {
            String fileName = file.getName();
            response.setContentType("application/x-msdownload");
            response.setContentLength((int) file.length());
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                // 从response对象中得到输出流,准备下载
                response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso-8859-1"));
                response.setHeader("windows-Target", "_blank");
                // 读出文件到i/o流
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);

                byte[] buffer = new byte[5000];// 相当于我们的缓存
                long readBytes = 0;// 该值用于计算当前实际下载了多少字节

                OutputStream myout = response.getOutputStream();

                // 开始循环下载
                while (readBytes < file.length()) {
                    int tempBytes = bis.read(buffer, 0, 5000);
                    readBytes += tempBytes;
                    // 将buffer中的数据写到客户端的内存
                    myout.write(buffer, 0, tempBytes);
                }
                // 将写入到客户端的内存的数据,刷新到磁盘
                myout.flush();

                result = true;
            } catch (IOException e) {
                e.getMessage();
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (bis != null) {
                        bis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("文件不存在，或者路径不正确!");
        }

        return result;
    }
}

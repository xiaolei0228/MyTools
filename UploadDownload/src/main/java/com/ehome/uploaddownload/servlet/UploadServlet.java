package com.ehome.uploaddownload.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xiaolei on 2015-03-23 0:08
 * 文件上传
 */
public class UploadServlet extends HttpServlet {

    long _pBytesRead;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        String serverDir = System.getProperty("java.io.tmpdir") + "/upload"; // 文件上传到服务器上的目录
        String tempDir = System.getProperty("java.io.tmpdir"); 				// 服务器上的临时目录

        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // Set factory constraints
        factory.setSizeThreshold(22000);
        factory.setRepository(new File(tempDir)); // 设置临时目录

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Parse the request
        List items = null;
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        if (items != null) {
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (!item.isFormField()) { // 判断是否是一个简单的表单域
                    String fieldName = item.getFieldName();
                    String fullName = item.getName();
                    String contentType = item.getContentType();
                    boolean isInMemory = item.isInMemory();
                    long sizeInBytes = item.getSize();

                    if (fullName != null && !"".equals(fullName)) {
                        File fullFile = new File(fullName);
                        if (!new File(serverDir).exists()) new File(serverDir).mkdirs(); // 如果目录不存在就创建
                        System.out.println("文件存放地址： " + serverDir);
                        File fileOnServer = new File(serverDir, fullFile.getName()); // 服务器上的文件地址
                        try {
                            item.write(fileOnServer);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        System.out.println(fieldName + "------" + fullName + "-----" + contentType + "------" + isInMemory + "-----" + sizeInBytes);
                    }
                }
            }
        }

        response.getWriter().println(_pBytesRead);

        System.out.println(isMultipart);
    }

    // 获取读取的字节数
    private long getProgressBytes(long pBytesRead, long pContentLength) {
        getServletContext().setAttribute("pBytesRead", pBytesRead);
        _pBytesRead = pBytesRead;
        return pBytesRead;
    }
}

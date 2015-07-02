package com.ehome.file.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * trace
 *
 * @author: 郝晓雷
 * @date: 2015-07-01 19:22
 * @desc: 文件上传工具类(接收来自httpclient传递过来的文件参数)
 */
public class FileUploadUtil {
    static Logger logger = Logger.getLogger(FileUploadUtil.class);

    private static String uploadPath = PropertiesFileUtil.getValue("upload.path");

    /**
     * 上传文件
     */
    public static void uploadFile(HttpServletRequest request) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            String filePath = uploadPath;
            File rootDirFile = new File(filePath);
            if (!rootDirFile.exists()) {
                rootDirFile.mkdirs();
            }

            DiskFileItemFactory factory = new DiskFileItemFactory(1024 * 10, new File(filePath));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");
            upload.setSizeMax(1024 * 1024 * 500);   // 设置允许上传的最大文件大小 500M
            List<FileItem> fileItems;
            try {
                FileItem fileData = null;
                fileItems = upload.parseRequest(request);
                Iterator<FileItem> iter = fileItems.iterator();
                String name = "";
                while (iter.hasNext()) {
                    FileItem item = iter.next();
                    if (!item.isFormField()) {      // 如果item是文件上传表单域
                        fileData = item;
                        name = item.getFieldName();
                        System.out.println(name);
                    }

                    if (fileData != null) {
                        filePath = filePath + name;
                        String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1, filePath.length());
                        filePath = filePath.substring(0, filePath.lastIndexOf(File.separator));
                        File dirFile = new File(filePath);
                        if (!dirFile.exists()) {
                            dirFile.mkdirs();
                        }
                        File fileOnServer = new File(filePath + File.separator + fileName);
                        fileData.write(fileOnServer);
                        logger.debug("文件" + fileOnServer.getName() + "上传成功");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteFile(HttpServletRequest request) {

    }
}

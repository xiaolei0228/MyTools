<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>   
    <title>文件上传</title>
  </head>
 
  <body>
    <form action="upload" enctype="multipart/form-data" method=post name="fileForm">
        请选择上传的文件:<br/>
        <input type="file" name="filename1"/>
        <br/>
        <input type="file" name="filename2"/>
        <br/>
        <input type="file" name="filename3"/>
        <br/>
        <input type="file" name="filename4"/>
        <br/>
        <input type="submit" value="开始上传"/>
    </form>
  </body>
</html>
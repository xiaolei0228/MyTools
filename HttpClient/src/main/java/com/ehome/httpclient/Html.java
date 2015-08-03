package com.ehome.httpclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 同一session下模拟登录抓取页面源代码
 */
public class Html {
    /*登录页面*/
    private static final String loginURL = "http://218.28.18.2:9090/scm_login.asp";
    /*订单打印主页面*/
    private static String forwardURL = "http://218.28.18.2:9090/scm_sup2_poh_print.asp";

    public static String getForwardURL() {
        return forwardURL;
    }

    public static void setForwardURL(String forwardURL) {
        Html.forwardURL = forwardURL;
    }

    public static URL url;
    public static HttpURLConnection conn;



    /**
     * 获取登录页面请求
     *
     * @param loginUrl登录URL
     * @param params登录用户名/密码参数
     *
     * @throws Exception
     */
    public static String createHtml(String... params) throws Exception {
        url = new URL(loginURL);
        conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        loginHtml(conn, params);
        return forwardHtml(conn, url, forwardURL);
    }

    /**
     * 登录页面
     *
     * @param conn
     * @param params登录用户名/密码参数
     *
     * @throws Exception
     */
    private static void loginHtml(HttpURLConnection conn, String... params) throws Exception {
        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "GBK");
        StringBuffer buff = new StringBuffer();
        buff.append("Name=" + URLEncoder.encode(params[0], "UTF-8"));//页面用户名
        buff.append("&Password=" + URLEncoder.encode(params[1], "UTF-8"));//页面密码
        out.write(buff.toString());//填充参数
        out.flush();
        out.close();
    }

    /**
     * 转向到定向的页面
     *
     * @param conn连接对象
     * @param url重新定向请求URL
     * @param toUrl定向到页面请求URL
     *
     * @throws Exception
     */
    public static String forwardHtml(HttpURLConnection conn, URL toUrl, String forwardURL) throws Exception {
        //重新打开一个连接
        String cookieVal = conn.getHeaderField("Set-Cookie");
        toUrl = new URL(forwardURL);
        conn = (HttpURLConnection) toUrl.openConnection();
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Foxy/1; .NET CLR 2.0.50727;MEGAUPLOAD 1.0)");
        conn.setFollowRedirects(false);//置此类是否应该自动执行 HTTP 重定向
        // 取得cookie,相当于记录了身份,供下次访问时使用
        if (cookieVal != null) {
            //发送cookie信息上去,以表明自己的身份,否则会被认为没有权限
            conn.setRequestProperty("Cookie", cookieVal);
        }
        conn.connect();
        InputStream in = conn.getInputStream();
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(in, "GBK"));
        String line = null;
        String content = "";
        while ((line = buffReader.readLine()) != null) {
            content += "\n" + line;
        }
        //IOUtils.write(result, new FileOutputStream("d:/index.html"),"GBK");
        write(content, "g:/hidden_file/desk/order_print.html");
        buffReader.close();
        return content;
    }

    /**
     * 写源码...
     *
     * @param content
     * @param htmlPath
     *
     * @return
     */
    public static boolean write(String content, String htmlPath) {
        boolean flag = true;
        try {
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlPath), "GBK"));
            out.write("\n" + content);
            out.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return flag;
    }

}

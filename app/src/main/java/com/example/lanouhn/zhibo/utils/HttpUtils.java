package com.example.lanouhn.zhibo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 网络请求类工具
 * Created by lanouhn on 16/6/25.
 */
public class HttpUtils {

    /**
     * 访问网络
     *
     * @param path 网络地址
     * @return
     */
    public static String GetNet(String path) {

        String result = "";

        try {
            URL url = new URL(path);
            HttpURLConnection open = (HttpURLConnection) url.openConnection();
            open.setRequestMethod("GET");
            open.setConnectTimeout(5000);
            open.setReadTimeout(5000);

            if (open.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = open.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader bdr = new BufferedReader(isr);

                String temp = "";

                if ((temp = bdr.readLine()) != null) {
                    result += temp;
                }
                bdr.close();
                isr.close();
                is.close();

                if (open != null) {
                    open.disconnect();
                } else {
                    result = "请求失败，错误码：" + open.getResponseCode();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Bitmap GetBitmap(String path) {

        Bitmap bitmap = null;

        try {
            URL url = new URL(path);
            HttpURLConnection open = (HttpURLConnection) url.openConnection();
            open.setRequestMethod("GET");
            open.setConnectTimeout(5000);
            open.setReadTimeout(5000);

            if (open.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = open.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);

                is.close();
                if (open != null) {
                    open.disconnect();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    //使用PUT方式获取网络数据
    public static String Put(String path, String params) {
        String result = "";
        URL url = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (url != null) {
            try {
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                urlConn.setRequestProperty("content-type", "application/json");
                urlConn.setDoInput(true);
                urlConn.setDoOutput(true);
                urlConn.setConnectTimeout(5 * 1000);
                //设置请求方式为 PUT
                urlConn.setRequestMethod("PUT");

                urlConn.setRequestProperty("Content-Type", "application/json");
                urlConn.setRequestProperty("Accept", "application/json");

                urlConn.setRequestProperty("Charset", "UTF-8");


                DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
                //写入请求参数
                dos.writeBytes(params);
                dos.flush();
                dos.close();

                if (urlConn.getResponseCode() == 200) {
                    InputStreamReader isr = new InputStreamReader(urlConn.getInputStream());
                    BufferedReader br = new BufferedReader(isr);
                    String inputLine = null;
                    while ((inputLine = br.readLine()) != null) {
                        result += inputLine;
                    }
                    isr.close();
                    urlConn.disconnect();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public static String doPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);

            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

}

package com.example.will.parking_share;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Will on 6/7/17.
 */

public class SendPost {
    public static String sendPost(String ip, String model, Map<String, String> info) {
        String IP="http://192.168.43.247:5000/";
        int TIME_OUT = 10 * 10000000; //超时时间
        String CHARSET = "utf-8"; //设置编码
        String PREFIX = "--", LINE_END = "\r\n";
        String BOUNDARY = "----WebKitFormBoundary7MA4YWxkTrZu0gW"; //边界标识 随机生成
        String CONTENT_TYPE = "multipart/form-data"; //内容类型
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(IP + model);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); //允许输入流
            conn.setDoOutput(true); //允许输出流
            conn.setUseCaches(false); //不允许使用缓存
            conn.setRequestMethod("POST"); //请求方式
            conn.setRequestProperty("Charset", CHARSET);
            //设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + "; boundary=" + BOUNDARY);
            OutputStream outputSteam = conn.getOutputStream();
            DataOutputStream dos = new DataOutputStream(outputSteam);
            StringBuffer sb = new StringBuffer();
            for (Map.Entry<String, String> thisentry : info.entrySet()) {
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                sb.append("Content-Disposition: form-data; name=\"" + thisentry.getKey() + "\"" + LINE_END);
                sb.append(LINE_END);
                sb.append(thisentry.getValue() + LINE_END);
            }
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(PREFIX);
            dos.write(sb.toString().getBytes());
            dos.flush();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
            return "Fail to post the server,please check the Internet！";

        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String getAdressFromGoogle(double latitude,double longitude) throws Exception{
        URL url=new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng="+latitude+","+longitude+"&sensor=true_or_false");
        Scanner scan = new Scanner(url.openStream());
        String result = new String();
        while (scan.hasNext())
            result += scan.nextLine();
        scan.close();
        System.out.print(result);
        JSONObject obj=new JSONObject(result);
        if (! obj.getString("status").equals("OK"))
            return "Error!";
        JSONObject jsonobject=obj.getJSONArray("results").getJSONObject(0);
        String address=jsonobject.optString("formatted_address");
        return address;

    }

    public static String[][] parkingToList(String parking) throws Exception{
        int len=parking.charAt(0)-'0';
        if (len==0)
            return null;
        String[][] result=new String[len][4];
        String json=new String(parking.substring(1));
        JSONArray obj=new JSONArray(json);
        for(int i=0;i<len;i++){
            result[i][0]=obj.getJSONObject(i).optString("NAME");
            result[i][1]=obj.getJSONObject(i).optString("DES");
            result[i][2]=obj.getJSONObject(i).optString("LAT");
            result[i][3]=obj.getJSONObject(i).optString("LON");
        }
        return result;
    }


}

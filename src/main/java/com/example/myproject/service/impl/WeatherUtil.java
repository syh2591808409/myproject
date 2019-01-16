package com.example.myproject.service.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class WeatherUtil {
    public static void main(String[] args) {

//        System.out.println(WeatherUtil.getCityCode("苍南"));
        String subData=WeatherUtil.sendGet("http://t.weather.sojson.com/api/weather/city/"+WeatherUtil.getCityCode("苍南"));
        System.out.println(subData);
        JSONObject jsonData = JSONObject.fromObject(subData);
        JSONObject cityInfo = (JSONObject) jsonData.get("cityInfo");
        JSONObject data = (JSONObject) jsonData.get("data");
        JSONObject yesterday = (JSONObject) data.get("yesterday");
        JSONArray forecastlist = (JSONArray) data.get("forecast");
        System.out.println(cityInfo.get("city").toString());
        System.out.println(cityInfo.get("cityId").toString());
        System.out.println(data.get("shidu").toString());
        System.out.println(yesterday.get("high").toString());
        for(int i=0;i<forecastlist.size();i++){
            JSONObject forecast = JSONObject.fromObject(forecastlist.get(i));
            System.out.println(forecast.get("high"));
        }
    }

    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader
                    (new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    public static String getCityCode(String cityName){
        String pathname = "C:\\Users\\suyuheng\\IdeaProjects\\myproject\\src\\main\\resources\\static\\txt\\cityCode.txt"; // 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
        //不关闭文件会导致资源的泄露，读写文件都同理
        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
        String result="";
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname),"UTF-8"));
            String line;
            String[] s;
            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                if(line.equals(""))
                    continue;
                s=line.split("=");

                if(s[1].equals(cityName)){
                    result=s[0];
                    break;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

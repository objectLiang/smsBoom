package com.liang.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class demo {
	public static String number = "13640814941";
	
	public static testThread ts;
	
	public static void main(String[] args) throws Exception {
		String str = doGet("http://web.lxgps.com/servlet/getVehicleMessage?type=kclocus&declarationId=2019040800001&positionType=original");
		
		JSONArray json = JSONArray.fromObject(str); // 首先把字符串转成 JSONArray  对象
		List<Object> ar = new ArrayList<Object>();
		if(json.size()>0){
		  for(int i=0;i<json.size();i++){
		    JSONObject job = json.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
		    System.out.println(job) ;  // 得到 每个对象中的属性值
		    ar.add(job);
		  }
		}
		
		System.out.println(ar);
			
		//System.out.println(requestPOSTUtil("https://xueqiu.com/account/sms/send_verification_code.json","telephone="+number+"&areacode=86"));//时间限制60秒
		//System.out.println(doGet("http://web.lxgps.com/servlet/getVehicleMessage?type=kclocus&declarationId=2019040800001&positionType=original"));//60秒
		//requestPOSTUtil("https://pay.lxgps.com/wechat/sendMessage","message=发送信息&areacode=86");
		//System.out.println(requestPOSTUtil("http://newsso.forex.com.cn/ssouser/sendPhoneRegisterValicode.do","phone="+number+"&"));
		//ts = new testThread();
		//ts.start();
	}
	
	public static class testThread extends Thread{
		@Override
		public void run() {
			System.out.println(1);
			try {
				sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(2);
		}
	}
	
	/**
	 * 
	 * @param url
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public static String requestPOSTUtil(String url,String query) throws Exception{
        URL restURL = new URL(url);
        /*
         * 此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类 的子类HttpURLConnection
         */
        HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
        //请求方式
        conn.setRequestMethod("POST");
        //设置是否从httpUrlConnection读入，默认情况下是true; httpUrlConnection.setDoInput(true);
        conn.setDoOutput(true);
        //allowUserInteraction 如果为 true，则在允许用户交互（例如弹出一个验证对话框）的上下文中对此 URL 进行检查。
        conn.setAllowUserInteraction(false);

        PrintStream ps = new PrintStream(conn.getOutputStream());
        ps.print(query);

        ps.close();

        BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line,resultStr="";

        while(null != (line=bReader.readLine()))
        {
        resultStr +=line;
        }
        bReader.close();

        return resultStr;

    }
	
	
	/**
	 * java query string parameters GET请求
	 * @param httpurl
	 * @return
	 */
	public static String doGet(String httpurl) {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// 返回结果字符串
        try {
            // 创建远程url连接对象
            URL url = new URL(httpurl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // 存放数据
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
 
            if (null != is) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
 
            connection.disconnect();// 关闭远程连接
        }
 
        return result;
    }

	
	
	

}

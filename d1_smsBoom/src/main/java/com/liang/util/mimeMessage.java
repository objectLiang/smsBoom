package com.liang.util;
import java.util.Date;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class mimeMessage {
	public static String myEmailAccount="2090045385@qq.com";
	public static String myEmailPassword="pjveigzjjvdiefch";
	public static String myEmailSMTPHost="smtp.qq.com";
	
	/**
	 * 
	 * @param emailContext 内容
	 * @param receiveMailAccount 收件人
	 * @param emailTheme 标题
	 * @throws Exception
	 */
	public static void setMessageContext(String emailContext,String receiveMailAccount,String emailTheme) throws Exception{
		Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        
        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);  
        // 3. 创建一封邮件
        MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount,emailContext,emailTheme);

        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();

        transport.connect(myEmailAccount, myEmailPassword);

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());

        // 7. 关闭连接
        transport.close();
	}
	public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,String emailContext,String emailTheme) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, "加密邮件", "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "验证用邮箱", "UTF-8"));
        // 4. Subject: 邮件主题
        message.setSubject(""+emailTheme, "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(emailContext, "text/html;charset=UTF-8");

        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }
}
package com.javamail.util;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.javamail.constant.MailConstant;
import com.javamail.pojo.MailObject;
import com.javamail.pojo.MailProperties;
/**
 * 邮件发送工具类
 * @file_comment 
 * @author zhangxing
 * @date 2018年10月29日
 */
@Component
public class MailUtil {
	
	static Logger log = LoggerFactory.getLogger(MailUtil.class);
	/**
	 * 初始化邮件接收人信息
	 * @param
	 * @param session
	 * @param mailObject
	 * @return
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	private static MimeMessage initFromTo(MailObject mailObject,Session session) throws Exception{
		// 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        message.setFrom(new InternetAddress(session.getProperty("mail.from"),new String(session.getProperty("mail.user").getBytes("ISO8859-1"), "UTF-8"),"UTF-8"));
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        //收件人
        if(null != mailObject.getToEmailAddresses() && mailObject.getToEmailAddresses().size() > 0){
        	InternetAddress[] internetAddressTO = new InternetAddress[mailObject.getToEmailAddresses().size()];
            for (int i = 0; i < mailObject.getToEmailAddresses().size(); i++) {
            	internetAddressTO[i] = new InternetAddress(mailObject.getToEmailAddresses().get(i).toString());
            }
        	message.setRecipients(MimeMessage.RecipientType.TO, internetAddressTO);
        }
        //抄送人
        if(null != mailObject.getCcEmailAddresses() && mailObject.getCcEmailAddresses().size() > 0){
       	 InternetAddress[] internetAddressCc = new InternetAddress[mailObject.getCcEmailAddresses().size()];
            for (int i = 0; i < mailObject.getCcEmailAddresses().size(); i++) {
            	internetAddressCc[i] = new InternetAddress(mailObject.getCcEmailAddresses().get(i).toString());
            }
            message.setRecipients(MimeMessage.RecipientType.CC, internetAddressCc);
        }
        //密送人
        if(null != mailObject.getBccEmailAddresses() && mailObject.getBccEmailAddresses().size() > 0){
          	 InternetAddress[] internetAddressBcc = new InternetAddress[mailObject.getBccEmailAddresses().size()];
               for (int i = 0; i < mailObject.getBccEmailAddresses().size(); i++) {
            	   internetAddressBcc[i] = new InternetAddress(mailObject.getBccEmailAddresses().get(i).toString());
               }
               message.setRecipients(MimeMessage.RecipientType.CC, internetAddressBcc);
        }
        return message;
	}
	/**
	 * 创建一封只包含文本的简单邮件
	 * @param <T>
	 * @param session 和服务器交互的会话
	 * @param mailObject 邮件对象
	 * @return
	 * @throws Exception
	 */
    public void sendSimpleMessage(MailObject mailObject){
    	log.info("开始发送一封简单文本邮件...参数有：" + mailObject.toString());
    	Session session = getSession();
    	try {
    		MimeMessage message = initFromTo(mailObject,session);
			message.setSubject(mailObject.getMailTitle(), "UTF-8");
			message.setText(mailObject.getMailContent(), "UTF-8");
			Boolean boo = SendMail(message, session);
	        if(boo){
		        log.info("邮件发送成功......simple");
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("邮件发送失败......");
		}
    }
    
	/**
     * 创建一封html邮件
     * @param session
     * @param mailObject
     * @return
     * @throws Exception
     */
    public static void sendHtmlMessage(MailObject mailObject) {
    	log.info("开始发送一封简单html超文本邮件...参数有：" + mailObject.toString());
    	Session session = getSession();
		try {
			MimeMessage message = initFromTo(mailObject,session);
			message.setSubject(mailObject.getMailTitle(), "UTF-8");
			message.setContent(mailObject.getMailContent(),"text/html;charset=UTF-8");
			Boolean boo = SendMail(message, session);
	        if(boo){
		        log.info("邮件发送成功......html");
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("邮件发送失败......");
		}
    }
    
    /**
     * 创建一封带内嵌图片的html邮件(是否带附件)
     * @param session
     * @param mailObject
     * @return
     * @throws Exception
     */
    public static void sendImageHtmlMessage(MailObject mailObject){
    	log.info("开始发送一封内嵌图片的html超文本邮件(可带附件)...参数有：" + mailObject.toString());
    	Session session = getSession();
		try {
			MimeMessage message = initFromTo(mailObject,session);
			message.setSubject(mailObject.getMailTitle(),"UTF-8");
	        MimeMultipart mm_text_image = new MimeMultipart();
	        // （1）. 创建文本“节点”
	        MimeBodyPart text = new MimeBodyPart();
	        //这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
	        //text.setContent("这是一张图片<br/><img src='cid:image_fairy_tail'/>", "text/html;charset=UTF-8");
	        text.setContent(mailObject.getMailContent(),"text/html;charset=UTF-8");
	        mm_text_image.addBodyPart(text);
	        
	        // （2） 创建图片“节点”
	        if(null != mailObject.getCids() && mailObject.getCids().size() > 0){
	        	for(int i = 0; i < mailObject.getCids().size() ; i++){
	        		MimeBodyPart image = new MimeBodyPart();
	        		image.setDataHandler(new DataHandler(new FileDataSource(mailObject.getCids().get(i))));
	        		image.setContentID(mailObject.getCids().get(i));// 为“节点”设置一个唯一编号（在文本“节点”将引用该ID）
	        		mm_text_image.addBodyPart(image);
	        	}
	        }
	        
	        //（3）关联关系  文本节点和多个图片节点关联起来
	        mm_text_image.setSubType("related");

	        //文本嵌入图片节点
	        MimeBodyPart text_image = new MimeBodyPart();
	        text_image.setContent(mm_text_image,"text/html;charset=UTF-8");
	        
	        //加入返回数据中
	        MimeMultipart mm = new MimeMultipart();
	        mm.addBodyPart(text_image);
	        
	        // 附件部分(可以是图片路径，也可以是word等路径)--判断是否需要带附件
	        if(null != mailObject.getMailAttachPathes() && mailObject.getMailAttachPathes().size() > 0){
	        	mailObject.getMailAttachPathes().forEach(t->{
	        	    try {
	        	    	BodyPart messageBodyPart = new MimeBodyPart();
	        	    	// FileDataSource用于读取文件数据，并返回代表数据的输入输出流和数据的MIME类型
	        	    	// DataHandler类用于封装FileDataSource对象，并为应用程序提供访问数据的接口
	        	    	messageBodyPart.setDataHandler(new DataHandler(new FileDataSource(t)));
	        	    	// 设置附件名称,MimeUtility.encodeText可以处理乱码问题
	        	    	messageBodyPart.setFileName(MimeUtility.encodeWord(t.contains("/") ? t.substring(t.lastIndexOf("/")) : t.contains("\\") ? t.substring(t.lastIndexOf("\\")) :t));
						mm.addBodyPart(messageBodyPart);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	});
	        	mm.setSubType("mixed");
	        }
	        message.setContent(mm);
	        Boolean boo = SendMail(message, session);
	        if(boo){
		        log.info("邮件发送成功......ImageHtml");
	        }
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			log.info("邮件发送失败......");
		}
    }
    
    
    /**
     * 创建一封带一个或者多个附件的邮件(正文不带图片)
     * @param session
     * @param mailObject
     * @return
     * @throws Exception
     */
    public static void sendAttachMessage(MailObject mailObject){
    	log.info("开始发送一封内嵌图片的html超文本邮件(可带附件)...参数有：" + mailObject.toString());
    	Session session = getSession();
		try {
			MimeMessage message = initFromTo(mailObject,session);
	    	// 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
	        message.setSubject(mailObject.getMailTitle(), "UTF-8");
	        // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
	        // 创建多重消息
	        Multipart multipart = new MimeMultipart();
	        //正文部分
	        if(null != mailObject.getMailContent()){
	        	 BodyPart messageBodyPart = new MimeBodyPart();
	        	 messageBodyPart.setContent(mailObject.getMailContent(),"text/html;charset=UTF-8");
	             multipart.addBodyPart(messageBodyPart);
	        }
	        // 附件部分(可以是图片路径，也可以是word等路径)
	        if(null != mailObject.getMailAttachPathes() && mailObject.getMailAttachPathes().size() > 0){
	        	mailObject.getMailAttachPathes().forEach(t->{
	        	    try {
	        	    	BodyPart messageBodyPart = new MimeBodyPart();
	        	    	// FileDataSource用于读取文件数据，并返回代表数据的输入输出流和数据的MIME类型
	        	    	// DataHandler类用于封装FileDataSource对象，并为应用程序提供访问数据的接口
	        	    	messageBodyPart.setDataHandler(new DataHandler(new FileDataSource(t)));
	        	    	// 设置附件名称,MimeUtility.encodeText可以处理乱码问题
	        	    	messageBodyPart.setFileName(MimeUtility.encodeWord(t.contains("/") ? t.substring(t.lastIndexOf("/")) : t.contains("\\") ? t.substring(t.lastIndexOf("\\")) :t));
						multipart.addBodyPart(messageBodyPart);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	});
	        }
	        // 发送完整消息
	        message.setContent(multipart);
	        Boolean boo = SendMail(message, session);
	        if(boo){
		        log.info("邮件发送成功......attach");
	        }
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			log.info("邮件发送失败......");
		}
    }
    
   /**
    * 获取邮件会话
    * @return
    */
	private static Session getSession(){
		Session session = Session.getInstance(MailProperties.getInstance(),new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// 登陆账号及密码，密码需要是第三方授权码
				return new PasswordAuthentication(MailConstant.MY_EMAIL_ACCOUNT,MailConstant.MY_EMAIL_PASSWORD);
			}
		});
		session.setDebug(true);
		return session;
	}
    
    /**
     * 发送邮件
     * @param message
     */
	@SuppressWarnings("finally")
	private static Boolean SendMail(MimeMessage message,Session session){
		Boolean bool = false;
		Transport transport = null;
		try{
			// 4. 根据 Session 获取邮件传输对象
			transport = session.getTransport();
			// 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
			transport.connect(session.getProperty("mail.from"),session.getProperty("mail.pass"));
			// 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
			message.setSentDate(new Date());//设置发件时间
			message.saveChanges();//保存设置
			transport.sendMessage(message, message.getAllRecipients());
			bool = true;
			// 7. 关闭连接
			transport.close();
		}catch(Exception e){
			e.printStackTrace();
			log.info("邮件发送失败......");
		}finally {
			if(null != transport){
				try {
					transport.close();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return bool;
		}
	}

}

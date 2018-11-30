package com.javamail.pojo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
/**
 * email所需基本配置信息（此处未使用，读取的配置文件MailProperties）
 * @file_comment 
 * @author zhangxing
 * @date 2018年10月29日
 */
@Component
@PropertySource("classpath:email.properties")
public class EmailConfig {
	
	//是否开启debug调试
	@Value("${mail.debug}")
	private String debug;
	//发送邮件协议名称
	@Value("${mail.transport.protocol}")
	private String protocol;
	//发件人的邮箱的 SMTP 服务器地址
	@Value("${mail.smtp.host}")
	private String host;
	//发送服务器是否需要身份验证
	@Value("${mail.smtp.auth}")
	private String auth;
	//发送邮件用户名
	@Value("${mail.user}")
	private String senderNickName;
	//发送邮件邮箱密码或者授权码
	@Value("${mail.pass}")
	private String senderPassWord;
	//发送邮件发件人
	@Value("${mail.from}")
	private String senderAddress;
	
	public String getDebug() {
		return debug;
	}

	public void setDebug(String debug) {
		this.debug = debug;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getSenderNickName() {
		return senderNickName;
	}

	public void setSenderNickName(String senderNickName) {
		this.senderNickName = senderNickName;
	}

	public String getSenderPassWord() {
		return senderPassWord;
	}

	public void setSenderPassWord(String senderPassWord) {
		this.senderPassWord = senderPassWord;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}
}

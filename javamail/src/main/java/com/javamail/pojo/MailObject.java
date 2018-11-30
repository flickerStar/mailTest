package com.javamail.pojo;
import java.io.Serializable;
import java.util.List;
import org.springframework.stereotype.Component;

import com.javamail.constant.MailConstant;

import net.sf.json.JSONObject;

/**
 * 邮件实体类
 * TODO
 * @author zhangxingxing
 * @time 2018年10月14日
 * @version 1.1
 * @param <T>
 */
@Component
public class MailObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//发件人邮箱地址
	private String senderAddress;
	//发件人昵称（随意）
	private String senderNickName;
	//收件人邮箱地址
	private List<String> toEmailAddresses;
	//密送人邮箱地址
	private List<String> bccEmailAddresses;
	//抄送人邮箱地址
	private List<String> ccEmailAddresses;
	//邮件主题
	private String mailTitle;
	//邮件正文
	private String mailContent;
	//附件路径
	private List<String> mailAttachPathes;
	//内嵌图片cids 依次放入图片路径即可
	private List<String> cids;
	
	public MailObject(List<String> toEmailAddresses, String mailTitle) {
		super();
		this.senderAddress = MailConstant.MY_EMAIL_ACCOUNT;
		this.senderNickName = MailConstant.MY_EMAIL_NICKNAME;
		this.toEmailAddresses = toEmailAddresses;
		this.mailTitle = mailTitle;
	}
	public String getSenderAddress() {
		return senderAddress;
	}
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}
	public String getSenderNickName() {
		return senderNickName;
	}
	public void setSenderNickName(String senderNickName) {
		this.senderNickName = senderNickName;
	}
	public List<String> getToEmailAddresses() {
		return toEmailAddresses;
	}
	public void setToEmailAddresses(List<String> toEmailAddresses) {
		this.toEmailAddresses = toEmailAddresses;
	}
	public List<String> getBccEmailAddresses() {
		return bccEmailAddresses;
	}
	public void setBccEmailAddresses(List<String> bccEmailAddresses) {
		this.bccEmailAddresses = bccEmailAddresses;
	}
	public List<String> getCcEmailAddresses() {
		return ccEmailAddresses;
	}
	public void setCcEmailAddresses(List<String> ccEmailAddresses) {
		this.ccEmailAddresses = ccEmailAddresses;
	}
	public String getMailTitle() {
		return mailTitle;
	}
	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}
	public String getMailContent() {
		return mailContent;
	}
	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}
	public List<String> getMailAttachPathes() {
		return mailAttachPathes;
	}
	public void setMailAttachPathes(List<String> mailAttachPathes) {
		this.mailAttachPathes = mailAttachPathes;
	}
	public List<String> getCids() {
		return cids;
	}
	public void setCids(List<String> cids) {
		this.cids = cids;
	}
	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
	
}

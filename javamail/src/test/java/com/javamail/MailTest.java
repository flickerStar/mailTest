package com.javamail;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.javamail.pojo.MailObject;
import com.javamail.util.MailUtil;
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {
	
	//简单文本邮件发送
	/*@Test
    public void sendSimpleMail() {
        MailObject mailObject = new MailObject(Arrays.asList("927190121@qq.com"), "测试简单文本发件");
        mailObject.setMailContent("11</br>22");
        new MailUtil().sendSimpleMessage(mailObject);
    }*/
	
	//简单html样式文本邮件发送
	/*@Test
	public void sendHtmlMail(){
		MailObject mailObject = new MailObject(Arrays.asList("927190121@qq.com","1762788448@qq.com"), "测试html超文本发件");
        mailObject.setMailContent("<h2>11</h2>");
        MailUtil.sendHtmlMessage(mailObject);
	}*/
	
	//带图片的html样式文本发送
	/*@Test 
	public void sendImageHtmlMail() {
		//要求拼接html文本时，前端在所有的src的链接前拼接cid:
        MailObject mailObject = new MailObject(Arrays.asList("15201031184@163.com"), "测试带图片的html样式文本发送");
        mailObject.setMailContent("lalallala我是谁<image src = 'cid:C://Users/Administrator/Desktop/11/image0.jpg'/>");
        mailObject.setCids(Arrays.asList("C://Users/Administrator/Desktop/11/image0.jpg"));
        MailUtil.sendImageHtmlMessage(mailObject);
    }*/
	
	//带附件的图片html样式文本发送
	/*@Test
	public void sendImageHtmlAttachMail() {
		//要求拼接html文本时，前端在所有的src的链接前拼接cid:
        MailObject mailObject = new MailObject(Arrays.asList("15201031184@163.com"), "带附件的图片html文本发送");
        mailObject.setMailContent("就是想发出去而已<image src = 'cid:C://Users/Administrator/Desktop/11/image0.jpg'/>");
        mailObject.setCids(Arrays.asList("C://Users/Administrator/Desktop/11/image0.jpg"));
        mailObject.setMailAttachPathes(Arrays.asList("C://Users/Administrator/Desktop/11/timg.jpg","C://Users/Administrator/Desktop/11/psb.jpg","C://Users/Administrator/Desktop/11/1 【公众号支付】验收用例.docx"));
        MailUtil.sendImageHtmlMessage(mailObject);
    }*/
	
	//带附件的html样式文本发送（不支持图片）
	@Test
	public void sendAttachMail() {
        MailObject mailObject = new MailObject(Arrays.asList("15201031184@163.com"), "带附件的html文本发送");
        mailObject.setMailContent("就是想发出去而已<image src = 'cid:C://Users/Administrator/Desktop/11/image0.jpg'/>");
        mailObject.setMailAttachPathes(Arrays.asList("C://Users/Administrator/Desktop/11/image0.jpg","C://Users/Administrator/Desktop/11/psb.jpg","C://Users/Administrator/Desktop/11/1 【公众号支付】验收用例.docx"));
        MailUtil.sendAttachMessage(mailObject);
    }
}

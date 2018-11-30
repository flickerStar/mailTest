package com.javamail.constant;
/**
 * 发件人基础信息配置，全局通用
 * @file_comment 
 * @author zhangxing
 * @date 2018年10月15日
 */
public class MailConstant {
	// 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
    // PS: 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）, 
    //     对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
    public static String MY_EMAIL_ACCOUNT = "15201031184@163.com";
    public static String MY_EMAIL_PASSWORD = "zxx927190";
    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
    // 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
    public static String MY_EMAIL_SMTP_HOST = "smtp.163.com";
    //发件人昵称
    public static String MY_EMAIL_NICKNAME = "唤不醒的星星";
}

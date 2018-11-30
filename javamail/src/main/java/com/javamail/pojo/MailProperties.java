package com.javamail.pojo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 读取email配置文件用于获取Session
 * @file_comment 
 * @author zhangxing
 * @date 2018年10月17日
 */
public class MailProperties extends Properties{
	
	private static final long serialVersionUID = 1L;
	static Logger log = LoggerFactory.getLogger(MailProperties.class);
	
	//构造方法私有化
	private MailProperties(){
		
	}
	
	public static MailProperties getInstance(){
			MailProperties props = new MailProperties();
			InputStream in = MailProperties.class.getResourceAsStream("/email.properties");
			try{
				if(null != in){
					props.load(in);
					in.close();
				}else{
					log.debug("文件不存在");
				}
			}catch (FileNotFoundException e) {
				log.debug("文件不存在："+e.toString());
			} catch (IOException e) {
				log.debug("IO 流关闭异常："+e.toString());
			} finally {
				if(in != null){
					try {
						in.close();//关闭流
					} catch (IOException e) {
						log.debug("IO 流关闭异常："+e.toString());
					}
				}
			}
		return props;
	}
}

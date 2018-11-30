package com.javamail;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
/**
 * 启动类
 * @file_comment 
 * @author zhangxing
 * @date 2018年10月15日
 */
@EnableAutoConfiguration
@SpringBootConfiguration
public class SpringBootApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootApplication.class, args);
	}
}

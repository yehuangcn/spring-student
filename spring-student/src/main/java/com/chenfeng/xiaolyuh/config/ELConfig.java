package com.chenfeng.xiaolyuh.config;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

@Configuration // 声明当前类是一个配置类，相当于Spring配置的XML文件
// 包扫描，并排除了对BeanConfig的扫描
@ComponentScan(basePackages={"com.chenfeng.xiaolyuh"}, excludeFilters={@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value={BeanConfig.class, AopConfig.class})})
@PropertySource("classpath:el-test.properties")// 指定文件地址
public class ELConfig {
	@Value("注入普通字符串")// 注入普通字符串
	private String normal;
	
	@Value("#{systemProperties['os.name']}")// 注入操作系统属性
	private String osName;
	
	@Value("#{T(java.lang.Math).random() * 100.0 }")// 注入表达式结果
	private double randomNumber; 
	
	@Value("#{demoELService.another}")// 注入其他Bean属性
	private String fromAnother;
	
	@Value("classpath:el-test.txt")// 注入文件资源
	private Resource testFile;
	
	@Value("https://www.baidu.com")// 注入网址资源
	private Resource testUrl;

	@Value("${book.name}")// 注入配置文件【注意是$符号】
	private String bookName;
	
	@Autowired// Properties可以从Environment获得
	private Environment environment;
	
//	@Bean
//	public static PropertySourcesPlaceholderConfigurer propertyConfigure() {
//		return new PropertySourcesPlaceholderConfigurer();
//	}

	@Override
	public String toString() {
		try {
			return "ELConfig [normal=" + normal 
					+ ", osName=" + osName 
					+ ", randomNumber=" + randomNumber 
					+ ", fromAnother=" + fromAnother 
					+ ", testFile=" + IOUtils.toString(testFile.getInputStream()) 
					+ ", testUrl=" + IOUtils.toString(testUrl.getInputStream()) 
					+ ", bookName=" + bookName
					+ ", environment=" + environment.getProperty("book.name") + "]";
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
package com.hosptialsys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * Hello world!
 *
 */
@SpringBootApplication
//开启事务管理
@EnableTransactionManagement
@MapperScan("com.hosptialsys.mapper")
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}

package com.rodrigo.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SpringECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringECommerceApplication.class, args);
	}

}

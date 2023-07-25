package com.study.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootMainApp {

	Logger logger = LoggerFactory.getLogger(SpringBootMainApp.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMainApp.class, args);
	}
}

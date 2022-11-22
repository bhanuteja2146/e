package com.clinistats.helpdesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@ComponentScan
@EnableEurekaClient
public class HepdeskApplication {
	public static void main(String[] args) {
		SpringApplication.run(HepdeskApplication.class, args);
		System.out.println("main ");
	}

}

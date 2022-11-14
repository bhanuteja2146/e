package com.clinistats.hepdesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HepdeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(HepdeskApplication.class, args);
	}

	@RequestMapping("/sample")
	public String getSample() {
		return "hi";
	}
	@RequestMapping("/samplen")
	public String getSampleNew() {
		return "hi1";
	}
}

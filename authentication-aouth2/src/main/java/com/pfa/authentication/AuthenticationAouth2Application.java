package com.pfa.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthenticationAouth2Application {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationAouth2Application.class, args);
	}

}

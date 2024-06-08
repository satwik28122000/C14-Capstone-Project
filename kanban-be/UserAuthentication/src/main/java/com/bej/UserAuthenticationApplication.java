package com.bej;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserAuthenticationApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(UserAuthenticationApplication.class, args);
		System.out.println("Employee Authentication service is running");
	}

}

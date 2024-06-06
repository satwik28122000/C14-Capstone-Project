package com.bej;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserAuthenticationApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(UserAuthenticationApplication.class, args);
		System.out.println("User Authentication service is running");
	}

}

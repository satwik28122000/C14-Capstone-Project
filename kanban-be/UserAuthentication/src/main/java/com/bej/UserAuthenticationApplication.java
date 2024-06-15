package com.bej;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info=@Info(title = "User-Auth-API", version = "1.0", description = "This is a User Authentication Application"))
public class UserAuthenticationApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(UserAuthenticationApplication.class, args);
		System.out.println("Employee Authentication service is running");
	}

}

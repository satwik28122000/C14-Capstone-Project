package com.bej;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class GateWayApiApplication
{

	public static void main(String[] args) {
		SpringApplication.run(GateWayApiApplication.class, args);
		System.out.println("API Gateway is running");
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder){
		return builder.routes()
				.route(p->p.path("/auth/**")
						.uri("lb://UserAuthentication/"))
<<<<<<< HEAD
				.route(p->p.path("/api/v2/**")
						.uri("lb://  ") )
						.build();
=======
//				.route(p->p.path("/api/v2/**")
//						.uri("lb://  ") )
				.build();

	}

}

package com.bej.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfiguration
{

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(p->p.path("/auth/**")
                        .uri("lb://UserAuthentication/"))

//				.route(p->p.path("/api/v2/**")
//						.uri("lb://  ") )
                .build();

    }

}

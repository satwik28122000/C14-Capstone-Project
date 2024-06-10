package com.bej;

import com.bej.filter.JwtFilter;
import com.bej.filter.JwtFilterManager;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients
@SpringBootApplication
@OpenAPIDefinition
public class KanbanBoardServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KanbanBoardServiceApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean jwtFilterBeanForEmployee() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new JwtFilter());
		filterRegistrationBean.addUrlPatterns("/api/kanban/user/*");
		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean jwtFilterBeanForManager() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new JwtFilterManager());
		filterRegistrationBean.addUrlPatterns("/api/kanban/manager/*");
		return filterRegistrationBean;
	}
}

package com.app.cloudzuul;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
public class AppConfiguration {
	
	@Bean
	public AppZuulFilter filter() {
		return new AppZuulFilter();
	}
}

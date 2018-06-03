package com.app.cloudhystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class CloudHystrixApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudHystrixApplication.class, args);
	}
}

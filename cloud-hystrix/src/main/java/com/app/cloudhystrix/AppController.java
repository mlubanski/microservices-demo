package com.app.cloudhystrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
@EnableHystrix
@RefreshScope
public class AppController {

	@Value("${server.port}")
	private String port;

	@Value("${zuul.url}")
	private String zuulUrl;
	@Value("${eureka_client.path}")
	private String eurekaClientPath;

	@Autowired
	private RestTemplate rest;

	//hystrix will call method 'failure' in case when condition defined in annotation won't  meet
	//execution.isolation.thread.timeoutInMilliseconds - time limit for response
	@RequestMapping("/data/{time}")
	@HystrixCommand(fallbackMethod = "failure", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "400") })
	public String info(@PathVariable long time) throws InterruptedException {
		
		Thread.sleep(time);
		return this.rest.getForObject(zuulUrl + eurekaClientPath + "/port", String.class);
		
		
	}

	//when @HystrixCommand is without commandProperties than 'public String failure()' will have no argument
	//this method should have the same arguments as main method annotated with @HystrixCommand
	public String failure(long time) {
		return "another data";
	}
}

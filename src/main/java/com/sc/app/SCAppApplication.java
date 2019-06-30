package com.sc.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.sc.app.config.FileConfig;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableConfigurationProperties({ FileConfig.class })
public class SCAppApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SCAppApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SCAppApplication.class, args);
	}

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public RestTemplate restTemplateOther() {
		return new RestTemplate();
	}
}

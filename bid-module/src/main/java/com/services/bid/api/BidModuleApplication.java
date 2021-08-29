package com.services.bid.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.service.bid.api.interfaces.ResourceServiceInterface;
import com.service.bid.api.interfaces.UserServiceInterface;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(clients = {ResourceServiceInterface.class,UserServiceInterface.class})
public class BidModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(BidModuleApplication.class, args);
	}

}

package com.hotelres.discovery;

import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DiscoveryServerApplication.class);
		app.setDefaultProperties(Map.of(
				"server.port", "9011",
				"eureka.client.register-with-eureka", "false",
				"eureka.client.fetch-registry", "false",
				"eureka.client.service-url.defaultZone", "http://localhost:9011/eureka/"
		));
		app.run(args);
	}
}

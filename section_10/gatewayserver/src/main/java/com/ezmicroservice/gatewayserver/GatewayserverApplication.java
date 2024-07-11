package com.ezmicroservice.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

import static org.springframework.cloud.gateway.support.RouteMetadataUtils.CONNECT_TIMEOUT_ATTR;
import static org.springframework.cloud.gateway.support.RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator ezMicroserviceConfig(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
						.path("/ezmicroservice/accounts/**")
						.filters(f->f.rewritePath("/ezmicroservice/accounts/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
//								.circuitBreaker(config -> config.setName("accountsCircuitBreaker").setFallbackUri("forward:/contactSupport"))
						)
						.metadata(RESPONSE_TIMEOUT_ATTR, 5000)
						.metadata(CONNECT_TIMEOUT_ATTR, 5000)
						.uri("lb://ACCOUNTS"))
				.route(p -> p
						.path("/ezmicroservice/loans/**")
						.filters(f -> f.rewritePath("/ezmicroservice/loans/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://LOANS"))
				.route(p -> p
						.path("/ezmicroservice/cards/**")
						.filters(f -> f.rewritePath("/ezmicroservice/cards/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://CARDS"))
				.build();
	}

}
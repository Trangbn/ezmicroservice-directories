package com.ezmicroservice.gatewayserver;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import java.time.Duration;
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
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.retry(retryConfig -> retryConfig.setRetries(3)
										.setMethods(HttpMethod.GET)
										.setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000),2 ,true)))
						.uri("lb://LOANS"))
				.route(p -> p
						.path("/ezmicroservice/cards/**")
						.filters(f -> f.rewritePath("/ezmicroservice/cards/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://CARDS"))
				.build();
	}

//	@Bean
//	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer(){
//		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
//				.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
//				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build()).build()
//		);
//	}

}
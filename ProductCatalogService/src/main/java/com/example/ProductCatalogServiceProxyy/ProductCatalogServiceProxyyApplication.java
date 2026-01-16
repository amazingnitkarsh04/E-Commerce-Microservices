package com.example.ProductCatalogServiceProxyy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductCatalogServiceProxyyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductCatalogServiceProxyyApplication.class, args);
	}

}

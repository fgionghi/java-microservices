package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.domain.Product;

@Service
public class ProductService {
	@Value("${product.endpoint}")
	private String endpoint;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public Product getProduct (String productId) {
		return restTemplate.getForObject(endpoint + "/api/product/{productId}", Product.class, productId);
	}
	
	public Product bookAvailability (String productId, int quantity) {
		return restTemplate.exchange(
				endpoint + "/api/product/{productId}/availability/{quantity}",
				HttpMethod.PUT,
				null, 
				Product.class, 
				productId, 
				-quantity).getBody();
				
	}
}

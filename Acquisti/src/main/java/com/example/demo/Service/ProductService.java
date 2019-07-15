package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.domain.Product;

@Service
public class ProductService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	public Product getProducts(String productId) {
		return restTemplate.getForObject("http://catalogo/api/product/{productId}", Product.class, productId);
	}
	
	public Product bookAvailability (String productId, int quantity) {
		return restTemplate.exchange("http://catalogo/api/product/{productId}/availability/{quantity}",
				HttpMethod.PUT,
				null, 
				Product.class, 
				productId, 
				-quantity).getBody();
	}
}

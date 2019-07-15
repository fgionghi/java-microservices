package com.example.demo.Service;

import org.springframework.stereotype.Component;

import com.example.demo.domain.Product;

@Component
public class ProductServiceFallback implements ProductService {

	@Override
	public Product getProduct(String productId) {
		// TODO Auto-generated method stub
		return new Product();
	}

	@Override
	public Product bookAvailability(String productId, int quantity) {
		// TODO Auto-generated method stub
		return new Product();
	}
	
	

}

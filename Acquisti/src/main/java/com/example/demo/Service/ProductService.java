package com.example.demo.Service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.demo.domain.Product;

//ottenere i metododi del microservizio product
@FeignClient("catalogo")
public interface ProductService {
	
	@GetMapping("/api/product/{productId}")
	public Product getProduct(@PathVariable String productId);
	
	@PutMapping("/api/product/{productId}/availability/{quantity}")
	public Product bookAvailability(
			@PathVariable String productId,
			@PathVariable int quantity);

}

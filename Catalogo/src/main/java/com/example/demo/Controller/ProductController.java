package com.example.demo.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;

@RestController
@Api(tags = "Catalog",description = "Product Catalog API")
public class ProductController {
	
	@Autowired
	private ProductService service;

	@ApiOperation(value = "Lista Prodotti", notes = "Lista prodotti disponibili con impaginazione")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 500, message = "Brutto errore server")
	})
	@GetMapping("/api/products")
	public @ResponseBody Page<Product> listProducts(Pageable pageRequest) {
		return service.getProducts(pageRequest);
	}
	
	@GetMapping("/api/product/{productId}")
	public @ResponseBody Product getProduct(@PathVariable String productId) {
		return service.getProduct(productId);
	}
	
	@GetMapping("/api/products/category/{category}")
	public @ResponseBody Page<Product> listProducts(@PathVariable String category, Pageable pageRequest) {
		return service.getProductsByCategory(category, pageRequest);
	}
	
	@PostMapping("/api/products")
	public @ResponseBody Product create(@RequestBody Product product) {
		return service.create(product);
	}
	
	@PutMapping("/api/products/{productId}/availability/{value}") 
	public @ResponseBody Product changeAvailability(@PathVariable String productId, @PathVariable int value) {
		return service.changeAvailability(productId, value);
	}
	
	
}

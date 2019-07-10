package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo.data.ProductRepository;
import com.example.demo.domain.Product;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repo;
	
	@Autowired
	private MongoTemplate template;
	
	public Page<Product> getProducts(Pageable pageRequest) {
		return repo.findAll(pageRequest);
	}
	
	public Product getProduct(String productId) {
		//meglio usare optional
		return repo.findById(productId).orElse(null);
	}
	
	public Page<Product> getProductsByCategory(String category, Pageable pageRequest) {
		return repo.findByCategory(category, pageRequest);
	}
	
	public Product create(Product product) {
		return repo.save(product);
	}
	
	//senza transazionalita', usando ProductRepository e quindi MongoRepository e quindi metodi di alto livello come findById
	public Product changeAvailabiltyWithoutTransaz(String productId, int change) {
		Product product = repo.findById(productId).orElse(null);
		if (product != null) {
			product.setAvailability(product.getAvailability() + change);
			repo.save(product);
		}
		return product;
	}
	
	public Product changeAvailability(String productId, int change) {
		//usiamo template e non repo perche mongotemplate ha metodi di piu basso livello che MongoRepository. updateFirst 'e un operazione sola per la tranzias
		template.updateFirst(
				Query.query(Criteria.where("id").is(productId)),
				new Update().inc("availability", change),
				Product.class);
		return getProduct(productId);
	}
}

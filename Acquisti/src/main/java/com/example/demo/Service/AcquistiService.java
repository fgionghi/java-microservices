package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.data.AcquistiRepository;
import com.example.demo.domain.Acquisti;
import com.example.demo.domain.AcquistiRequest;
import com.example.demo.domain.Product;

@Service
public class AcquistiService {
	
	@Autowired 
	private AcquistiRepository repo;
	
	@Autowired
	private ProductService productService;
	
	//acquisti di quell'utente
	public Page<Acquisti> getUserPurchases(String userId, Pageable pageRequest) {
		return repo.findByUserId(userId, pageRequest);
	}
	
	//per id di acquisto
	public Acquisti getUserPurchase(String purchaseId) {
		return repo.findById(purchaseId).orElse(null);
	}
	
	
	public Acquisti buy(String userId, AcquistiRequest request) {
		Acquisti acquisti = new Acquisti();
		
		Product product = productService.getProduct(request.getProductId());
		acquisti.setProductId(request.getProductId());
		acquisti.setQuantity(request.getCount());
		acquisti.setUserId(userId);
		acquisti.setProductTitle(product.getTitle());
		acquisti.setProductCategory(product.getCategory());
		acquisti.setPrice(product.getPrice());		
		
		productService.bookAvailability(request.getProductId(), request.getCount());
		return repo.save(acquisti);
	}
}









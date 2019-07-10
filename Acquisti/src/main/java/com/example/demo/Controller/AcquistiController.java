package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Service.AcquistiService;
import com.example.demo.domain.Acquisti;
import com.example.demo.domain.AcquistiRequest;

@Controller
public class AcquistiController {
	
	@Autowired
	private AcquistiService service;
	
	@GetMapping("/api/acquisti/{userId}")
	public @ResponseBody Page<Acquisti> listAcquisti(@PathVariable String userId, Pageable pageRequest) {
		return service.getUserPurchases(userId, pageRequest);
	}
	
	@GetMapping("/api/acquisto/{purchaseId}")
	public @ResponseBody Acquisti getAcquist(@PathVariable String acquistiId) {
		return service.getUserPurchase(acquistiId);
	}
	
	@PostMapping("/api/acquisti/{userId}")
	public @ResponseBody Acquisti buy(@PathVariable String userId, @RequestBody AcquistiRequest request) {
		return service.buy(userId, request);
	}
	
}

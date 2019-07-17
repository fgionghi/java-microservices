package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.data.ProductRepository;
import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;

import org.junit.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CatalogoServiceTest{
	
	@Autowired
	private ProductRepository repo;
	@Autowired
	private ProductService service;
	
	@Before
	public void tearUp() {
		repo.deleteAll();
	}
	
	@Test
	public void testService() throws Exception {
		Product product = new Product();
		product.setAvailability(10);
		product.setCategory("test");
		product.setTitle("Test title");
		product.setDescription("Test description");
		product.setPrice(100.0);
		
		//test create
		product = service.create(product);
		Assert.assertNotNull(product.getId());
		
		//test find
		product = service.getProduct(product.getId());
		Assert.assertNotNull(product);
		
		//test list
		Page<Product> page = service.getProducts(PageRequest.of(0, 10));
		Assert.assertEquals(1, page.getContent().size());
		
		//test list by category
		page = service.getProductsByCategory("test", PageRequest.of(0, 10));
		Assert.assertEquals(1, page.getContent().size());
		
		
		//change availability
		service.changeAvailability(product.getId(), -1);
		product = service.getProduct(product.getId());
		Assert.assertEquals(9, (int)product.getAvailability());
	}
}
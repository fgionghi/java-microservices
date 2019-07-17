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

import org.junit.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CatalogoDataTest{
	
	@Autowired
	private ProductRepository repo;
	
	@Before
	public void tearUp() {
		repo.deleteAll();
	}
	
	@Test
	public void testData() throws Exception {
		Product product = new Product();
		product.setAvailability(10);
		product.setCategory("test");
		product.setTitle("Test title");
		product.setDescription("Test description");
		product.setPrice(100.0);
		
		//test create
		product = repo.save(product);
		Assert.assertNotNull(product.getId());
		
		//test find
		Optional<Product> optional = repo.findById(product.getId());
		Assert.assertTrue(optional.isPresent());
		
		//test list
		List<Product> list = repo.findAll();
		Assert.assertEquals(1,list.size());
		
		//test list by category
		Page<Product> page = repo.findByCategory("test", PageRequest.of(0, 10));
		Assert.assertEquals(1, page.getContent().size());
		
		//test delete
		repo.delete(product);
		optional = repo.findById(product.getId());
		Assert.assertTrue(!optional.isPresent());
	}
}
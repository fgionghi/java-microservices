package com.example.demo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.data.ProductRepository;
import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CatalogoControllerTests {

	@Autowired
	private ProductRepository repo;
	@Autowired
	private MockMvc mockMvc;
	
	@Before
	public void tearUp() {
		repo.deleteAll();
	}
	
	@Test
	public void testCreate() throws Exception {
		Product product = new Product();
		product.setAvailability(10);
		product.setCategory("test");
		product.setTitle("Test title");
		product.setDescription("Test description");
		product.setPrice(100.0);

}
}
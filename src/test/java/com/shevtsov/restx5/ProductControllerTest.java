package com.shevtsov.restx5;

import com.shevtsov.domain.Product;
import com.shevtsov.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ProductControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ProductRepository repository;

	@Test
	public void testGetAll() {
		ResponseEntity<List<Product>> response = restTemplate
				.exchange("/products", HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {});
		List<Product> products = response.getBody();

		assertNotNull(products);

		assertEquals(products.size(), 2);
	}

	@Test
	public void testGet() {
		ResponseEntity<Product> response = restTemplate
				.getForEntity("/products/1", Product.class);
		Product product = response.getBody();

		assertNotNull(product);

		Product productFromDb = repository.findById(1L).orElseGet(null);

		assertNotNull(productFromDb);

		assertEquals(product, productFromDb);

		assertEquals(product.getCharacteristic(), productFromDb.getCharacteristic());
	}

	@Test
	public void testGetNotFound() {
		ResponseEntity<Product> forEntity = restTemplate.getForEntity("/products/3", Product.class);
		assertEquals(forEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}
}

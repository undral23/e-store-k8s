package edu.iu.sa.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.iu.sa.product.entity.Product;
import edu.iu.sa.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping
	public Product saveProduct(@RequestBody Product product) {
		log.info("Inside saveProduct of ProductController");
		return productService.saveProduct(product);
	}

	@GetMapping
	public List<Product> getAll() {
		return productService.getAll();
	}

//	@GetMapping("/{id}")
//	public ResponseTemplateVO getUserWithDepartment(@PathVariable("id") Long userId) {
//		log.info("Inside getUserWithDepartment of UserController");
//		return productService.getUserWithDepartment(userId);
//	}

}

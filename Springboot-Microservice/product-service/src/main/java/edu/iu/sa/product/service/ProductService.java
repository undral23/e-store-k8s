package edu.iu.sa.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.iu.sa.product.entity.Product;
import edu.iu.sa.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private RestTemplate restTemplate;

	public Product saveProduct(Product product) {
		log.info("Inside saveUser of UserService");
		return productRepository.save(product);
	}

	public List<Product> getAll() {
		return productRepository.findAll();
	}

//	public ResponseTemplateVO getUserWithDepartment(Long userId) {
//		log.info("Inside getUserWithDepartment of UserService");
//		ResponseTemplateVO vo = new ResponseTemplateVO();
//		Product user = productRepository.findByUserId(userId);
//
//		Department department = restTemplate
//				.getForObject("http://DEPARTMENT-SERVICE/departments/" + user.getDepartmentId(), Department.class);
//
//		vo.setUser(user);
//		vo.setDepartment(department);
//
//		return vo;
//	}
}

package edu.miu.sa.order.service;

import java.util.List;

import edu.miu.sa.order.entity.Order;
import edu.miu.sa.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RestTemplate restTemplate;

	public Order saveOrder(Order product) {
		log.info("Inside saveUser of UserService");
		return orderRepository.save(product);
	}

	public List<Order> getAll() {
		return orderRepository.findAll();
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

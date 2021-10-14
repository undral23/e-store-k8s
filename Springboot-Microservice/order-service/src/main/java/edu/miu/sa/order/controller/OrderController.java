package edu.miu.sa.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.miu.sa.order.entity.Order;
import edu.miu.sa.order.entity.PaymentInfo;
import edu.miu.sa.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	public Order placeOrder(@RequestBody Order order, Authentication auth) {
		Long userId = Long.parseLong((String) auth.getPrincipal());
		log.info("Inside saveOrder of OrderController");
		log.info("userId: " + userId);

		return orderService.placeOrder(order, userId);
	}

	@PutMapping("/{orderId}/pay")
	public Order payOrder(@PathVariable Long orderId, @RequestBody PaymentInfo paymentInfo,
			@RequestHeader("Authorization") String token) {
		log.info("Inside payOrder of OrderController");
		log.info("orderId: " + orderId);

		return orderService.payOrder(orderId, paymentInfo, token);
	}

	@GetMapping
	public List<Order> getAll() {
		return orderService.getAll();
	}

//	@GetMapping("/{id}")
//	public ResponseTemplateVO getUserWithDepartment(@PathVariable("id") Long userId) {
//		log.info("Inside getUserWithDepartment of UserController");
//		return productService.getUserWithDepartment(userId);
//	}

}

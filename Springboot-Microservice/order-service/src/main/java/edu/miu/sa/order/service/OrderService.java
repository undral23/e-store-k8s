package edu.miu.sa.order.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.miu.sa.order.entity.Order;
import edu.miu.sa.order.entity.OrderStatus;
import edu.miu.sa.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {

	final static String PAYMENT_SERVICE_URL = "http://PAYMENT-SERVICE:9191/payment/";
	final static String SHIPPING_SERVICE_URL = "http://SHIPPING-SERVICE/shipping";

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RestTemplate restTemplate;

	public Order saveOrder(Order order, Long userId, String token) {
		log.info("Inside saveUser of UserService");

		order.setUserId(userId);
		order.setDateTime(LocalDateTime.now());
		order.setStatus(OrderStatus.PLACED);
		order = orderRepository.save(order);

		// doing payment
		doPayment(order, token);

		// place of the shipment
		if (order.getStatus() == OrderStatus.PAID) {
			doShipRequest(order, token);
		}

		return order;
	}

	public List<Order> getAll() {
		return orderRepository.findAll();
	}

	public Object doPayment(Order order, String token) {
		log.info("Inside doPayment of OrderService, OrderID: " + order.getId());

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);

		order.getPaymentInfo().getData().put("amount", order.getPaymentInfo().getAmount());
		HttpEntity<Object> entityReq = new HttpEntity<Object>(order.getPaymentInfo().getData(), headers);

		ResponseEntity<Object> result;
		result = restTemplate.postForEntity(PAYMENT_SERVICE_URL + order.getPaymentInfo().getMethod(), entityReq,
				Object.class);

		if (result.getStatusCode() == HttpStatus.OK) {
			order.setStatus(OrderStatus.PAID);
			Map<String, Object> resBody = (Map<String, Object>) result.getBody();
			order.getPaymentInfo().getData().put("transactionNumber", resBody.get("transactionNumber"));

			log.info("Payment successful!");
		} else {
			order.setStatus(OrderStatus.PAYMENT_FAILED);
			log.info("Payment failed: " + result.getBody());
		}

		return orderRepository.save(order);
	}

	public Object doShipRequest(Order order, String token) {
		log.info("Inside doShipRequest of OrderService, OrderID: " + order.getId());

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);

		order.getPaymentInfo().getData().put("amount", order.getPaymentInfo().getAmount());

		Map<String, Object> reqData = new HashMap<>();
		reqData.put("orderId", order.getId());
		reqData.put("shippingAddress", order.getShippingAddress());

		HttpEntity<Object> entityReq = new HttpEntity<Object>(reqData, headers);

		ResponseEntity<Object> result;
		result = restTemplate.postForEntity(SHIPPING_SERVICE_URL, entityReq, Object.class);

		if (result.getStatusCode() == HttpStatus.OK) {
			order.setStatus(OrderStatus.PAID);
			Map<String, Object> resBody = (Map<String, Object>) result.getBody();
			order.setShippingInfo(resBody);
			log.info("Shipping request successful!");
		} else {
			order.setStatus(OrderStatus.PAYMENT_FAILED);
			log.info("Shipping request failed: " + result.getBody());
		}

		return orderRepository.save(order);
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

package edu.miu.sa.order.service;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.miu.sa.order.entity.OrderStatus;
import edu.miu.sa.order.entity.PaymentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.miu.sa.order.entity.Order;
import edu.miu.sa.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {

    final static String PAYMENT_SERVICE_URL = "http://localhost:9003/transaction/card"; // "http://TRANSACTION-CARD-SERVICE/payment/";
    final static String SHIPPING_SERVICE_URL = "http://localhost:9003/shipping"; // "http://SHIPPING-SERVICE/shipping/";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Order placeOrder(Order order, Long userId) {
        log.info("Inside placeOrder of OrderService");

        order.setUserId(userId);
        order.setDateTime(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);
        order = orderRepository.save(order);

        return order;
    }

    public Order payOrder(Long orderId, PaymentInfo paymentInfo, String token) {
        log.info("Inside saveUser of UserService");

        Order order = orderRepository.findById(orderId).get();

        // doing payment
        doPayment(order, paymentInfo, token);

        // place of the shipment
        if (order.getStatus() == OrderStatus.PAID) {
            doShipRequest(order, token);
        }

        return order;
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Object doPayment(Order order, PaymentInfo paymentInfo, String token) {
        log.info("Inside doPayment of OrderService, OrderID: " + order.getId());

        order.setPaymentInfo(paymentInfo);
        order = orderRepository.save(order);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        Map<String, String> paymentAttributes = order.getPaymentInfo().getAttributes();
        paymentAttributes.put("amount", String.valueOf(order.getPaymentInfo().getAmount()));
        HttpEntity<Object> entityReq = new HttpEntity<Object>(paymentAttributes, headers);

        ResponseEntity<Object> result;
        result = restTemplate
                .postForEntity(PAYMENT_SERVICE_URL,
                        entityReq,
                        Object.class);

        if (result.getStatusCode() == HttpStatus.OK) {
            order.setStatus(OrderStatus.PAID);
            Map<String, Object> resBody = (Map<String, Object>) result.getBody();
            order.getPaymentInfo().getAttributes().put("transactionNumber", String.valueOf(resBody.get("transactionNumber")));

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

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("orderId", order.getId());
        reqData.put("shippingAddress", order.getShippingAddress());

        HttpEntity<Object> entityReq = new HttpEntity<Object>(reqData, headers);

        ResponseEntity<Object> result;
        result = restTemplate
                .postForEntity(SHIPPING_SERVICE_URL,
                        entityReq,
                        Object.class);

        if (result.getStatusCode() == HttpStatus.OK) {
            order.setStatus(OrderStatus.SHIP_REQUESTED);
            Map<String, Object> resBody = (Map<String, Object>) result.getBody();
            order.setShippingInfo(resBody);
            log.info("Shipping request successful!");
        } else {
            order.setStatus(OrderStatus.SHIP_REQUEST_FAILED);
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

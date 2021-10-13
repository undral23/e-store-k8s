package edu.miu.sa.shipping.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import edu.miu.sa.shipping.dto.ShipRequestDTO;
import edu.miu.sa.shipping.entity.ShipmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.miu.sa.shipping.entity.Shipment;
import edu.miu.sa.shipping.repository.ShippingRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Shipment placeShipment(ShipRequestDTO shipRequestDTO) {
        log.info("Inside placeShipment of ShippingService");

        Shipment shipment = new Shipment();
        shipment.setOrderId(shipRequestDTO.getOrderId());
        shipment.setShippingAddress(shipRequestDTO.getShippingAddress());
        shipment.setStatus(ShipmentStatus.PENDING);
        shipment.setCreatedDatetime(LocalDateTime.now());

        shipment = shippingRepository.save(shipment);

        return shipment;
    }

    public Shipment shipShipment(Long shipmentId) {
        Shipment shipment = shippingRepository.findById(shipmentId).get();
        if(shipment.getStatus() == ShipmentStatus.PENDING) {
            shipment.setTrackingNumber(String.valueOf(System.currentTimeMillis()));
            shipment.setStatus(ShipmentStatus.SHIPPED);
            shipment = shippingRepository.save(shipment);
        }
        else {
            throw new RuntimeException("Already shipped item");
        }

        return shipment;
    }

    public Shipment deliveredShipment(Long shipmentId) {
        Shipment shipment = shippingRepository.findById(shipmentId).get();
        if(shipment.getStatus() == ShipmentStatus.SHIPPED) {
            shipment.setTrackingNumber(String.valueOf(System.currentTimeMillis()));
            shipment.setStatus(ShipmentStatus.DELIVERED);
            shipment = shippingRepository.save(shipment);
        }
        else {
            throw new RuntimeException("Cannot set to delivered unless status is SHIPPED");
        }

        return shipment;
    }

    public List<Shipment> getAll() {
        return shippingRepository.findAll();
    }

    public List<Shipment> getAllPending() {
        return shippingRepository.getAllPending();
    }

    public Object doPayment(Shipment order, String token) {
//        log.info("Inside doPayment of OrderService, OrderID: " + order.getId());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", token);
//
//        order.getPaymentInfo().getData().put("amount", order.getPaymentInfo().getAmount());
//        HttpEntity<Object> entityReq = new HttpEntity<Object>(order.getPaymentInfo().getData(), headers);
//
//
//        ResponseEntity<Object> result;
//        result = restTemplate
////                .postForEntity("http://TRANSACTION-CARD-SERVICE/payment/",
//                .postForEntity("http://localhost:9003/transaction/card",
//                        entityReq,
//                        Object.class);
//
//        if (result.getStatusCode() == HttpStatus.OK) {
//            order.setStatus(ShipmentStatus.PAID);
//            Map<String, Object> resBody = (Map<String, Object>) result.getBody();
//            order.getPaymentInfo().getData().put("transactionNumber", resBody.get("transactionNumber"));
//
//            log.info("Payment successful!");
//        } else {
//            order.setStatus(ShipmentStatus.PAYMENT_FAILED);
//            log.info("Payment failed: " + result.getBody());
//        }
//
//        return orderRepository.save(order);
        return null;
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

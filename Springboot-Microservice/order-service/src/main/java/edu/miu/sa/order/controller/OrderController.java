package edu.miu.sa.order.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import edu.miu.sa.order.entity.Order;
import edu.miu.sa.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order saveOrder(@RequestBody Order order, Authentication auth, @RequestHeader("Authorization") String token) {
        String userId = (String) auth.getPrincipal();
        log.info("Inside saveOrder of OrderController");
        log.info("userId: " + userId);

        return orderService.saveOrder(order, Long.parseLong(userId), token);
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

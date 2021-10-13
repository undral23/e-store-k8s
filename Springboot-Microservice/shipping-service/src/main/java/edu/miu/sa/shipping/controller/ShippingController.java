package edu.miu.sa.shipping.controller;

import java.util.List;

import edu.miu.sa.shipping.dto.ShipRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import edu.miu.sa.shipping.entity.Shipment;
import edu.miu.sa.shipping.service.ShippingService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/shipping")
@Slf4j
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @PostMapping
    public Shipment placeShipment(@RequestBody ShipRequestDTO shipRequestDTO) {
        log.info("Inside placeShipment of ShippingController");

        return shippingService.placeShipment(shipRequestDTO);
    }

    @PutMapping("/{id}/ship")
    public Shipment shipShipment(@PathVariable Long id) {
        log.info("Inside placeShipment of ShippingController");

        return shippingService.shipShipment(id);
    }

    @PutMapping("/{id}/deliver")
    public Shipment deliverShipment(@PathVariable Long id) {
        log.info("Inside placeShipment of ShippingController");

        return shippingService.deliveredShipment(id);
    }

    @GetMapping
    public List<Shipment> getAll() {
        return shippingService.getAll();
    }

    @GetMapping("?status=PENDING")
    public List<Shipment> getAllPending() {
        return shippingService.getAll();
    }

//	@GetMapping("/{id}")
//	public ResponseTemplateVO getUserWithDepartment(@PathVariable("id") Long userId) {
//		log.info("Inside getUserWithDepartment of UserController");
//		return productService.getUserWithDepartment(userId);
//	}

}

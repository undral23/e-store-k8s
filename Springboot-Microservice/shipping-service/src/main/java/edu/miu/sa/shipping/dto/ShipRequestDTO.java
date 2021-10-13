package edu.miu.sa.shipping.dto;

import edu.miu.sa.shipping.entity.Address;

import javax.validation.constraints.NotNull;

public class ShipRequestDTO {
    @NotNull
    private Long orderId;

    @NotNull
    private Address shippingAddress;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}

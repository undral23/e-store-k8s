package edu.miu.sa.shipping.entity;

public enum ShipmentStatus {
    PENDING("PENDING"),
    SHIPPED("SHIPPED"),
    DELIVERED("DELIVERED");

    private final String text;

    ShipmentStatus(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

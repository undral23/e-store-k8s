package edu.miu.sa.order.entity;

public enum OrderStatus {
    PLACED("PLACED"),
    PAID("PAID"),
    PAYMENT_FAILED("PAYMENT_FAILED"),
    SHIPPED("SHIPPED");

    private final String text;

    OrderStatus(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

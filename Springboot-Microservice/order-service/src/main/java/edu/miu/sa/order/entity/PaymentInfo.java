package edu.miu.sa.order.entity;


import javax.persistence.*;
import java.util.Map;

@Embeddable
public class PaymentInfo {
    private String method;
    private Double amount;

    @ElementCollection
    @CollectionTable(name = "PAYMENT_INFO_ATTRIBUTES", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "KEY")
    @Column(name = "VALUE")
    private Map<String, String> attributes;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}

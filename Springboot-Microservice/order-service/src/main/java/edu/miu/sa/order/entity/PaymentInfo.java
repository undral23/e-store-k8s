package edu.miu.sa.order.entity;


import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.Map;

@Embeddable
public class PaymentInfo {
    private String method;
    private Double amount;

    @Transient
    private Map<String, Object> data;

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

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}

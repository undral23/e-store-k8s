package edu.miu.sa.order.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private Long userId;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<OrderItem> items;

    @Embedded
    @Column(nullable = false)
    private Address shippingAddress;

    @Embedded
    @Column(nullable = true)
    private PaymentInfo paymentInfo;

    @Column(nullable = false)
    private OrderStatus status;

    @Transient
    private Object shippingInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Collection<OrderItem> getItems() {
        return items;
    }

    public void setItems(Collection<OrderItem> items) {
        this.items = items;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Object getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(Object shippingInfo) {
        this.shippingInfo = shippingInfo;
    }
}

package edu.miu.sa.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.miu.sa.order.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}

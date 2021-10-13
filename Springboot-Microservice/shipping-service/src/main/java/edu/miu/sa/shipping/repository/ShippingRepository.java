package edu.miu.sa.shipping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.miu.sa.shipping.entity.Shipment;

import java.util.List;

@Repository
public interface ShippingRepository extends JpaRepository<Shipment, Long> {
    @Query("from Shipment s where s.status = 'PENDING'")
    List<Shipment> getAllPending();
}

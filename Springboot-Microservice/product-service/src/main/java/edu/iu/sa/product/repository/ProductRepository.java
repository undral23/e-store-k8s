package edu.iu.sa.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.iu.sa.product.entity.Product;

@Repository
public interface ProductRepository  extends JpaRepository<Product,Long> {
    Product findByCategory(String category);
}

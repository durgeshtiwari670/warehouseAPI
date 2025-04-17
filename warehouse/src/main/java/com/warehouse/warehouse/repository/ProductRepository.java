package com.warehouse.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.warehouse.warehouse.model.Product;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsBySku(String sku);

    List<Product> findByVendor(String vendor);
}

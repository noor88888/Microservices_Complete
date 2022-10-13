package com.microservices.product.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.microservices.product.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long>{

}

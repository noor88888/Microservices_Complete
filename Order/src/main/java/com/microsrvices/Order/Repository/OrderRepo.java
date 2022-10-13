package com.microsrvices.Order.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microsrvices.Order.Entity.Order;
@Repository
public interface OrderRepo extends JpaRepository<Order, Long>{

}

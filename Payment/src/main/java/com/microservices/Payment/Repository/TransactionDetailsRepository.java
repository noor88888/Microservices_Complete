package com.microservices.Payment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.Payment.Entity.TransactionDetails;
@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails,Long>{

	TransactionDetails findByOrderId(long orderId);
}

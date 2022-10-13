package com.microservices.Payment.Service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.Payment.Entity.TransactionDetails;
import com.microservices.Payment.Model.PaymentMode;
import com.microservices.Payment.Model.PaymentRequest;
import com.microservices.Payment.Model.PaymentResponse;
import com.microservices.Payment.Repository.TransactionDetailsRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PaymentServiceImp implements PaymentService
{
	    @Autowired
	    private TransactionDetailsRepository transactionDetailsRepository;
		@Override
		public long doPayment(PaymentRequest paymentRequest)
		{
			log.info("Recording Payment Details: {}", paymentRequest);
			TransactionDetails transactionDetails
	                = TransactionDetails.builder()
	                .paymentDate(Instant.now())
	                .paymentMode(paymentRequest.getPaymentMode().name())
	                .paymentStatus("SUCCESS")
	                .orderId(paymentRequest.getOrderId())
	                .referenceNumber(paymentRequest.getReferenceNumber())
	                .amount(paymentRequest.getAmount())
	                .build();

	        transactionDetailsRepository.save(transactionDetails);
	        log.info("Transaction Completed with Id: {}", transactionDetails.getId());
	        return transactionDetails.getId();
		}
		@Override
		public PaymentResponse getPaymentDetailsByOrderId(String orderId) {
			log.info("Getting payment details for the Order Id: {}", orderId);

	        TransactionDetails transactionDetails
	                = transactionDetailsRepository.findByOrderId(Long.valueOf(orderId));

	        PaymentResponse paymentResponse
	                = PaymentResponse.builder()
	                .paymentId(transactionDetails.getId())
	                .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
	                .paymentDate(transactionDetails.getPaymentDate())
	                .orderId(transactionDetails.getOrderId())
	                .status(transactionDetails.getPaymentStatus())
	                .amount(transactionDetails.getAmount())
	                .build();

	        return paymentResponse;
		}

	
}

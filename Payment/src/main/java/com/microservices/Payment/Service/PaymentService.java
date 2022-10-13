package com.microservices.Payment.Service;

import com.microservices.Payment.Model.PaymentRequest;
import com.microservices.Payment.Model.PaymentResponse;

public interface PaymentService {

	long doPayment(PaymentRequest paymentRequest);

	PaymentResponse getPaymentDetailsByOrderId(String orderId);

}

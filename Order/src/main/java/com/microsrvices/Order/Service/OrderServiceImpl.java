package com.microsrvices.Order.Service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.microsrvices.Order.Entity.Order;
import com.microsrvices.Order.Model.OrderRequest;
import com.microsrvices.Order.Model.OrderResponse;
import com.microsrvices.Order.Repository.OrderRepo;
import com.microsrvices.Order.exception.OrderException;
import com.microsrvices.Order.external.ErrorResponse.PaymentResponse;
import com.microsrvices.Order.external.ErrorResponse.ProductResponse;
import com.microsrvices.Order.external.client.PaymentService;
import com.microsrvices.Order.external.client.ProductService;
import com.microsrvices.Order.external.request.PaymentRequest;


import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService
{
	@Autowired
	private OrderRepo orderrepo;
	@Autowired
	private ProductService productservice;
	@Autowired
    private PaymentService paymentService;
	
	@Autowired
    private RestTemplate restTemplate;
	
	@Override
	public long placeOrder(OrderRequest orderRequest)
	{
		log.info("Placing Order Request: {}", orderRequest);
        log.info("Creating Order with Status CREATED");
		productservice.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());
		
        
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

        order = orderrepo.save(order);
        
        
        log.info("Calling Payment Service to complete the payment");
        PaymentRequest paymentRequest
                = PaymentRequest.builder()
                .orderId(order.getOrdered_id())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .build();

        String orderStatus = null;
        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment done Successfully. Changing the Oder status to PLACED");
            orderStatus = "PLACED";
        } catch (Exception e) {
            log.error("Error occurred in payment. Changing order status to PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderrepo.save(order);

        log.info("Order Places successfully with Order Id: {}", order.getOrdered_id());
        return order.getOrdered_id();
	}
	@Override
	public OrderResponse getOrderDetails(long orderId) 
	{
		// TODO Auto-generated method stub
		  log.info("Get order details for Order Id : {}", orderId);

	        Order order
	                = orderrepo.findById(orderId).orElseThrow(() -> new OrderException("Order not found for the order Id:" + orderId,
	                        "NOT_FOUND",
	                        404));
	        
	        log.info("Invoking Product service to fetch the product for id: {}", order.getProductId());
	        ProductResponse productResponse
            = restTemplate.getForObject(
                    "http://PRODUCT-SERVICE/product/" + order.getProductId(),
            ProductResponse.class
            );
	        
	        log.info("Getting payment information form the payment Service");
	        PaymentResponse paymentResponse
	                = restTemplate.getForObject(
	                        "http://PAYMENT-SERVICE/payment/order/" + order.getOrdered_id(),
	                PaymentResponse.class
	                );
	        
	        OrderResponse.ProductDetails productDetails
            = OrderResponse.ProductDetails
            .builder()
            .productName(productResponse.getProductName())
            .productId(productResponse.getProductId())
            .quantity(productResponse.getQuantity())
            .price(productResponse.getPrice())
            .build();
	        
	        OrderResponse.PaymentDetails paymentDetails
            = OrderResponse.PaymentDetails
            .builder()
            .paymentId(paymentResponse.getPaymentId())
            .paymentStatus(paymentResponse.getStatus())
            .paymentDate(paymentResponse.getPaymentDate())
            .paymentMode(paymentResponse.getPaymentMode())
            .build();
	        
	        OrderResponse orderResponse
            = OrderResponse.builder()
            .orderId(order.getOrdered_id())
            .orderStatus(order.getOrderStatus())
            .amount(order.getAmount())
            .orderDate(order.getOrderDate())
            .productDetails(productDetails)
            .paymentDetails(paymentDetails)
            .build();

    return orderResponse;
	}

}

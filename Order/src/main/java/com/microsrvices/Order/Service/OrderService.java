package com.microsrvices.Order.Service;

import com.microsrvices.Order.Model.OrderRequest;
import com.microsrvices.Order.Model.OrderResponse;

public interface OrderService {

	long placeOrder(OrderRequest orderRequest);

	OrderResponse getOrderDetails(long orderId);

}

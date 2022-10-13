package com.microservices.product.service;

import com.microservices.product.model.ProductRequest;
import com.microservices.product.model.ProductResponse;

public interface ProductService {

	long addProduct(ProductRequest productrequest);

	ProductResponse getProductById(long productId);

	void reduceQuantity(long productId,long quantity);



}

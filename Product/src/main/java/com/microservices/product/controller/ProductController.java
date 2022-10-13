package com.microservices.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.product.model.ProductRequest;
import com.microservices.product.model.ProductResponse;
import com.microservices.product.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController 
{
	@Autowired
	ProductService productservice;
	@PostMapping
	public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productrequest)
	{
		long productId=productservice.addProduct(productrequest);
		return new ResponseEntity<>(productId, HttpStatus.CREATED);
	}
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId)
	{
		ProductResponse productresponse =productservice.getProductById(productId);
		return new ResponseEntity<>(productresponse,HttpStatus.OK);
	}
	@PutMapping("reduceQuantity/{id}")
	public ResponseEntity<Void> reduceQuantity(@PathVariable("id") long productId,@RequestParam long quantity)
	{
		productservice.reduceQuantity(productId,quantity);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

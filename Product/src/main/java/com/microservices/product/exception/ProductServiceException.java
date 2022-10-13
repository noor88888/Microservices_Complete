package com.microservices.product.exception;

import lombok.Data;

@Data
public class ProductServiceException extends RuntimeException
{
	String errorcode;
	public ProductServiceException  (String message,String errorcode )
	{
		super(message );
		this.errorcode=errorcode;
	}	
}

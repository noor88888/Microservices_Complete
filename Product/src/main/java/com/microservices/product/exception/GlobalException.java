package com.microservices.product.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.microservices.product.model.ErrorResponse;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler
{
	@ExceptionHandler(ProductServiceException.class)
	public ResponseEntity<ErrorResponse> handleproductService(ProductServiceException exception)
	{
		return new ResponseEntity<>(
							new ErrorResponse()
							.builder()
							.errorMessage(exception.getMessage())
							.errorCode(exception.getErrorcode())
							.build(),HttpStatus.NOT_FOUND
							);
	}
}

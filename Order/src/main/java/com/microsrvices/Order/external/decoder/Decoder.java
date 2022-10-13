package com.microsrvices.Order.external.decoder;


import com.microsrvices.Order.exception.OrderException;
import com.microsrvices.Order.external.ErrorResponse.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
@Log4j2
public class Decoder implements ErrorDecoder {

	 @Override
	    public Exception decode(String s, Response response) {
	        ObjectMapper objectMapper
	                = new ObjectMapper();

	        log.info("::{}",response.request().url());
	        log.info("::{}",response.request().headers());

	        try {
	            ErrorResponse errorResponse
	                    = objectMapper.readValue(response.body().asInputStream(),
	                    ErrorResponse.class);

	            return new OrderException(errorResponse.getErrorMessage() ,
	                    errorResponse.getErrorCode(),
	                    response.status());

	        } catch (IOException e) {
	            throw  new OrderException("Quantity exceded",
	                    "INSUFFICIENT_QUANTITY",
	                    500);
	        }
	    }

}

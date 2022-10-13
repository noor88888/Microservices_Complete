package com.microservices.product.service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.product.entity.Product;
import com.microservices.product.exception.ProductServiceException;
import com.microservices.product.model.ProductRequest;
import com.microservices.product.model.ProductResponse;
import com.microservices.product.repository.ProductRepo;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService
{
	@Autowired
	private ProductRepo productrepo;
	 @Override
	 public long addProduct(ProductRequest productRequest)
	 {
		 log.info("Adding Product..");
	     Product product
		                = Product.builder()
		                .productName(productRequest.getName())
		                .quantity(productRequest.getQuantity())
		                .price(productRequest.getPrice())
		                .build();
	        productrepo.save(product);
	        log.info("Product Created");
	        return product.getProductId();
	    }
	@Override
	public ProductResponse getProductById(long productId)
	{
		Product product=
				productrepo.findById(productId).orElseThrow(()->new ProductServiceException("product with id "+ productId+" not found","PRODUCT_NOT_FOUND"));
		
		ProductResponse productresponse=new ProductResponse();
		BeanUtils.copyProperties(product, productresponse);
		return productresponse;
	}
	@Override
	public void reduceQuantity(long productId,long quantity) 
	{
		// TODO Auto-generated method stub
		Product product=
				productrepo.findById(productId).orElseThrow(()->new ProductServiceException("product with id "+ productId+" not found","PRODUCT_NOT_FOUND"));
		if(product.getQuantity()<quantity)
		{
			throw new ProductServiceException("Quantity spefied is more than currwnt quantity", "INSUFFICIENT_QUANTITY");
		}
		product.setQuantity(product.getQuantity()-quantity);
		productrepo.save(product);
	}
}

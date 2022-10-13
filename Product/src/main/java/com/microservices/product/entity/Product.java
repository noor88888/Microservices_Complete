package com.microservices.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	/*
	 * @GenericGenerator( name="sequence-generator", strategy =
	 * "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters= {
	 * 
	 * @Parameter(name="sequence-name" ,value ="user-sequence"),
	 * 
	 * @Parameter(name="initial-value" ,value ="1"),
	 * 
	 * @Parameter(name="increment-size" ,value ="1") } )
	 */
    @Column(name = "PRODUCT_ID")
    private long productId;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRICE")
    private long price;

    @Column(name = "QUANTITY")
    private long quantity;
}

	
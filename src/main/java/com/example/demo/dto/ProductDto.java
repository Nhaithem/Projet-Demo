package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

	private String name;
	private String description;
	private double price;

}

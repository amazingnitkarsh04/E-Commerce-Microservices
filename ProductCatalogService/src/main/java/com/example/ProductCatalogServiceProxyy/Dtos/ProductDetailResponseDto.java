package com.example.ProductCatalogServiceProxyy.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDetailResponseDto {

    private Long productId;
    private String productName;
    private Double price;
    private String userEmail;
}

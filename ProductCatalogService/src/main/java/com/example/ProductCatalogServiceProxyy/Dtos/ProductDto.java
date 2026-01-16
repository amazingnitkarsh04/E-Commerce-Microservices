package com.example.ProductCatalogServiceProxyy.Dtos;

import com.example.ProductCatalogServiceProxyy.Models.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ProductDto {

    private Long id;

    private String title;

    private String description;

    private Double price;

    private String imageUrl;

    private String category;

    private RatingDto ratingDto;
}

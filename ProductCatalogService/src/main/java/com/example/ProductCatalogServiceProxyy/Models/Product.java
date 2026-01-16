package com.example.ProductCatalogServiceProxyy.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel {

    private String title;

    private String description;

    private Double price;

    private String imageUrl;

    @ManyToOne
    @JsonBackReference
    private Category category;

    private Boolean isPrime;


}

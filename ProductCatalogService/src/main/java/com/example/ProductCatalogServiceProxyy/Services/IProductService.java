package com.example.ProductCatalogServiceProxyy.Services;

import com.example.ProductCatalogServiceProxyy.Dtos.ProductDetailResponseDto;
import com.example.ProductCatalogServiceProxyy.Dtos.ProductDto;
import com.example.ProductCatalogServiceProxyy.Models.Product;

import java.util.List;

public interface IProductService {

    List<Product> getProducts();

    Product getProduct(Long ProductId);

    Product createProduct(Product product);

    Product updateProduct(Long ProductId, Product product);

    ProductDetailResponseDto getProductdetails(Long userId, Long productId);

    Void deleteProduct(Long productID);

}

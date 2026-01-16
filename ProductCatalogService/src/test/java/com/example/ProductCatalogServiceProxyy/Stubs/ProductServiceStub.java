package com.example.ProductCatalogServiceProxyy.Stubs;

import com.example.ProductCatalogServiceProxyy.Dtos.ProductDetailResponseDto;
import com.example.ProductCatalogServiceProxyy.Models.Product;
import com.example.ProductCatalogServiceProxyy.Services.IProductService;
import org.springframework.stereotype.Service;

import java.util.*;

//@Service
public class ProductServiceStub implements IProductService {

    Map<Long,Product> products;

    public ProductServiceStub(){
        products = new HashMap<Long,Product>();
    }


    @Override
    public List<Product> getProducts() {
        return List.of();
    }

    @Override
    public Product getProduct(Long ProductId) {
       return products.get(ProductId);
    }

    @Override
    public Product createProduct(Product product) {
        products.put(product.getId(), product);
        return products.get(product.getId());
    }

    @Override
    public Product updateProduct(Long ProductId, Product product) {
        products.put(ProductId,product);
        return products.get(ProductId);
    }

    @Override
    public ProductDetailResponseDto getProductdetails(Long userId, Long productId) {
        return null;
    }


    @Override
    public Void deleteProduct(Long productID) {
        return null;
    }
}

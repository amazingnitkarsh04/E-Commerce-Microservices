package com.example.ProductCatalogServiceProxyy.Services;

import com.example.ProductCatalogServiceProxyy.Dtos.ProductDetailResponseDto;
import com.example.ProductCatalogServiceProxyy.Exceptions.ProductNotFoundException;
import com.example.ProductCatalogServiceProxyy.Dtos.UserDto;
import com.example.ProductCatalogServiceProxyy.Models.Product;
import com.example.ProductCatalogServiceProxyy.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class StorageProductService implements IProductService{

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Product> getProducts() {
        List<Product> products = productRepo.findAll();
        return products;
    }

    @Override
    public Product getProduct(Long productId) {
        Product product = productRepo.findProductById(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product not found with id " + productId);
        }
        return product;
    }

    @Override
    public Product createProduct(Product product) {
        Product resultProduct = productRepo.save(product);
        return resultProduct;
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        Product existing = productRepo.findProductById(productId);
        if (existing == null) {
            throw new ProductNotFoundException("Product not found with id " + productId);
        }
        existing.setPrice(product.getPrice());
        existing.setDescription(product.getDescription());
        existing.setTitle(product.getTitle());
        existing.setCategory(product.getCategory());
        existing.setImageUrl(product.getImageUrl());
        return productRepo.save(existing);
    }


    @Override
    public ProductDetailResponseDto getProductdetails(Long userId, Long productId) {
        Product product = productRepo.findProductById(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product not found with id " + productId);
        }
        UserDto userDto;
        try{
            userDto = restTemplate.getForEntity("http://UserService/users/{id}", UserDto.class, userId).getBody();
        }catch (Exception ex) {
            throw new IllegalArgumentException("Failed to fetch user with id " + userId + ": " + ex.getMessage());
        }

        if (userDto == null) {
            throw new IllegalArgumentException("User not found with id " + userId);
        }
        ProductDetailResponseDto response = new ProductDetailResponseDto();
        response.setProductId(product.getId());
        response.setProductName(product.getTitle());
        response.setPrice(product.getPrice());
        response.setUserEmail(userDto.getEmail());
        return response;
    }


    @Override
    public Void deleteProduct(Long productId) {
        Product product = productRepo.findProductById(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product not found with id " + productId);
        }
        productRepo.delete(product);
        return null;
    }
}

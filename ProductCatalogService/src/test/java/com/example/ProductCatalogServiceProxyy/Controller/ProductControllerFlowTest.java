package com.example.ProductCatalogServiceProxyy.Controller;

import com.example.ProductCatalogServiceProxyy.Dtos.ProductDto;
import com.example.ProductCatalogServiceProxyy.Models.Category;
import com.example.ProductCatalogServiceProxyy.Models.Product;
import com.example.ProductCatalogServiceProxyy.Repository.CategoryRepo;
import com.example.ProductCatalogServiceProxyy.Services.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class ProductControllerFlowTest {

    @Autowired
    ProductController productController;

    @MockBean
    IProductService productService;

    @MockBean
    CategoryRepo categoryRepo;

    @Test
    public void Test_CreateAndFetchAndUpdate_RunsSuccessfully() {
        // Arrange
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        Product product = new Product();
        product.setId(1L);
        product.setTitle("ABC");
        product.setDescription("XYZ");
        product.setCategory(category);

        // Mock category repo and product service behavior
        when(categoryRepo.findByName("Electronics")).thenReturn(Optional.of(category));
        when(productService.createProduct(any(Product.class))).thenReturn(product);
        when(productService.getProduct(1L)).thenReturn(product);

        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setTitle("DEF");
        updatedProduct.setDescription("XYZ");
        updatedProduct.setPrice(1000.0);
        updatedProduct.setCategory(category);

        when(productService.updateProduct(any(Long.class), any(Product.class))).thenReturn(updatedProduct);
        when(productService.getProduct(1L)).thenReturn(product, updatedProduct); // first fetch returns original, second fetch returns updated

        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setTitle("ABC");
        productDto.setDescription("XYZ");
        productDto.setCategory("Electronics");

        // Act
        productController.createProduct(productDto);
        ResponseEntity<Product> productResponseEntity = productController.getProduct(1L);

        productDto.setTitle("DEF");
        productDto.setPrice(1000.0);
        productController.updateProduct(1L, productDto);

        ResponseEntity<Product> updatedProductResponseEntity = productController.getProduct(1L);

        // Assert
        assertEquals("ABC", productResponseEntity.getBody().getTitle());
        assertEquals("XYZ", productResponseEntity.getBody().getDescription());
        assertEquals("DEF", updatedProductResponseEntity.getBody().getTitle());
        assertEquals(1000.0, updatedProductResponseEntity.getBody().getPrice());
    }
}


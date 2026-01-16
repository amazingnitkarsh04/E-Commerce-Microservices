package com.example.ProductCatalogServiceProxyy.Controller;

import com.example.ProductCatalogServiceProxyy.Dtos.ProductDetailResponseDto;
import com.example.ProductCatalogServiceProxyy.Dtos.ProductDto;
import com.example.ProductCatalogServiceProxyy.Models.Category;
import com.example.ProductCatalogServiceProxyy.Models.Product;
import com.example.ProductCatalogServiceProxyy.Repository.CategoryRepo;
import com.example.ProductCatalogServiceProxyy.Services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/product")
@RestController
public class ProductController {

    @Autowired
    IProductService productService;

    @Autowired
    CategoryRepo categoryRepo;


    @GetMapping
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long productId) {
        if (productId < 1) {
            throw new IllegalArgumentException("Product ID must be greater than 0");
        }
        Product product = productService.getProduct(productId);
        return ResponseEntity.ok(product);
    }
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        Product product = getProduct(productDto);
        Product saved = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long productId, @RequestBody ProductDto productDto) {
        Product product = getProduct(productDto);
        Product updated = productService.updateProduct(productId, product);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{uid}/{pid}")
    public ResponseEntity<ProductDetailResponseDto> getProductDetails(@PathVariable Long uid, @PathVariable Long pid) {
        ProductDetailResponseDto responseDto = productService.getProductdetails(uid, pid);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
    public Product getProduct(ProductDto productDto) {

        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        Category category;
        if(productDto.getCategory() != null){
            category = categoryRepo.findByName(productDto.getCategory())
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName(productDto.getCategory());
                        return categoryRepo.save(newCategory);
                    });
            product.setCategory(category);
        }
        return product;
    }

//    public Product getProduct(ProductDto productDto){
//        Product product = new Product();
//        product.setId(productDto.getId());
//        product.setTitle(productDto.getTitle());
//        product.setDescription(productDto.getDescription());
//        product.setPrice(productDto.getPrice());
//        product.setImageUrl(productDto.getImageUrl());
//        Category category = new Category();
//        category.setName(productDto.getCategory());
//        product.setCategory(category);
//        return product;
//    }

}

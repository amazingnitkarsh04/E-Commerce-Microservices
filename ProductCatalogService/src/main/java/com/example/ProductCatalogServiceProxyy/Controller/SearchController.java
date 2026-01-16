package com.example.ProductCatalogServiceProxyy.Controller;

import com.example.ProductCatalogServiceProxyy.Dtos.ProductDto;
import com.example.ProductCatalogServiceProxyy.Dtos.SearchRequestDto;
import com.example.ProductCatalogServiceProxyy.Models.Product;
import com.example.ProductCatalogServiceProxyy.Services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping
    public List<ProductDto> searchproduct(@RequestBody SearchRequestDto searchrequestDto){
        List<Product> products = searchService.searchProduct(searchrequestDto.getQuery(),searchrequestDto.getPageNo(), searchrequestDto.getPageSize());
        List<ProductDto> searchResults = new ArrayList<>();
        for(Product product : products) {
            searchResults.add(getProductDto(product));
        }
        return searchResults;
    }

    ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(product.getCategory().getName());
        productDto.setPrice(product.getPrice());
        return productDto;
    }
}

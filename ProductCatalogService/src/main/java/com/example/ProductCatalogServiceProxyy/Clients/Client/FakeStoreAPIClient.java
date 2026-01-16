package com.example.ProductCatalogServiceProxyy.Clients.Client;

import com.example.ProductCatalogServiceProxyy.Clients.Dtos.FakeStoreProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class FakeStoreAPIClient {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    public List<FakeStoreProductDto> getProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForEntity("https://fakestoreapi.com/products", FakeStoreProductDto[].class).getBody();
        List<FakeStoreProductDto> products = new ArrayList<>();
        for(FakeStoreProductDto productDto : fakeStoreProductDtos){
            products.add(productDto);
        }
        return products;
    }

    public FakeStoreProductDto getProduct(Long ProductId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class,ProductId).getBody();
        return fakeStoreProductDto;
    }

    public FakeStoreProductDto createProduct(FakeStoreProductDto fakeStoreProductDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = restTemplate.postForEntity("https://fakestoreapi.com/products",fakeStoreProductDto,FakeStoreProductDto.class);
        return fakeStoreProductDtoResponseEntity.getBody();
    }

    public FakeStoreProductDto updateProduct(Long ProductId, FakeStoreProductDto fakeStoreProductDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDtoo = restTemplate.patchForObject("https://fakestoreapi.com/products/{id}", fakeStoreProductDto, FakeStoreProductDto.class, ProductId);
        return fakeStoreProductDtoo;
    }
}

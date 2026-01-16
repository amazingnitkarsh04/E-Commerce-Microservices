package com.example.ProductCatalogServiceProxyy.Services;

import com.example.ProductCatalogServiceProxyy.Clients.Client.FakeStoreAPIClient;
import com.example.ProductCatalogServiceProxyy.Clients.Dtos.FakeStoreProductDto;
import com.example.ProductCatalogServiceProxyy.Dtos.ProductDetailResponseDto;
import com.example.ProductCatalogServiceProxyy.Models.Category;
import com.example.ProductCatalogServiceProxyy.Models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

//@Service
public class FakeStoreProductService implements IProductService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private FakeStoreAPIClient fakeStoreAPIClient;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    @Override
    public List<Product> getProducts() {
        List<FakeStoreProductDto> fakeProduct = fakeStoreAPIClient.getProducts();
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto : fakeProduct){
            products.add(getProduct(fakeStoreProductDto));
        }
        return products;
    }

    @Override
    public Product getProduct(Long ProductId) {
        // check if product is in cache
        // if yes :
        //      read from cache
        // else :
        //     call fakestore and get result
        //     store result in cache

        //productid_epochtimestamp
        FakeStoreProductDto fakeStoreProductDto = null;
        fakeStoreProductDto = (FakeStoreProductDto) redisTemplate.opsForHash().get("PRODUCTS",ProductId);
        if(fakeStoreProductDto != null) {
            System.out.println("Read from Cache");
            return getProduct(fakeStoreProductDto);
        }

        fakeStoreProductDto = fakeStoreAPIClient.getProduct(ProductId);
        System.out.println("Read from Fakestore API");
        redisTemplate.opsForHash().put("PRODUCTS",ProductId,fakeStoreProductDto);
        return getProduct(fakeStoreProductDto);
//        return getProduct(fakeStoreAPIClient.getProduct(ProductId));
    }

    @Override
    public Product createProduct(Product product) {
        FakeStoreProductDto fakeStoreProductDto = getFakeStoreProductDto(product);
        FakeStoreProductDto response = fakeStoreAPIClient.createProduct(fakeStoreProductDto);
        return getProduct(response);
    }

    @Override
    public Product updateProduct(Long ProductId, Product product) {
//        RestTemplate restTemplate = restTemplateBuilder.build();
//        FakeStoreProductDto fakeStoreProductDto = restTemplate.patchForObject("https://fakestoreapi.com/{id}", product, FakeStoreProductDto.class, ProductId);
//        Product resultantProduct = getProduct(fakeStoreProductDto);
//        return resultantProduct;
        FakeStoreProductDto fakeStoreProductDto = getFakeStoreProductDto(product);
        FakeStoreProductDto response = fakeStoreAPIClient.updateProduct(ProductId,fakeStoreProductDto);
        return getProduct(response);
    }

    @Override
    public ProductDetailResponseDto getProductdetails(Long userId, Long productId) {
        return null;
    }


    @Override
    public Void deleteProduct(Long productID) {
        return null;
    }

    private Product getProduct(FakeStoreProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        Category category = new Category();
        category.setName(productDto.getCategory());
        product.setCategory(category);
        return product;
    }

    private FakeStoreProductDto getFakeStoreProductDto(Product product){
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setCategory(product.getCategory().getName());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setImageUrl(product.getImageUrl());
        fakeStoreProductDto.setDescription(product.getDescription());
        return fakeStoreProductDto;
    }


}

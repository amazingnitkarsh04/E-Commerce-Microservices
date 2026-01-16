package com.example.ProductCatalogServiceProxyy.Services;

import com.example.ProductCatalogServiceProxyy.Models.Product;
import com.example.ProductCatalogServiceProxyy.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private ProductRepo productRepo;

    public List<Product> searchProduct(String query, int pageNo, int pageSize){
        Sort sort = Sort.by("id").and(Sort.by("price").descending());
        return productRepo.findByTitleEquals(query, PageRequest.of(pageNo,pageSize));
    }
}

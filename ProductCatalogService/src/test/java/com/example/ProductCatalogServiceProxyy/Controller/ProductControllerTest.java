package com.example.ProductCatalogServiceProxyy.Controller;

import com.example.ProductCatalogServiceProxyy.Models.Product;
import com.example.ProductCatalogServiceProxyy.Services.IProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockBean
    private IProductService productService;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @Test
    public void Test_GetProduct_ReturnProduct() {

        when(productService.getProduct(any(Long.class))).thenReturn(new Product());

        ResponseEntity<Product> productResponseEntity = productController.getProduct(1L);

        assertNotNull(productResponseEntity);
    }

//    @Test
//    @DisplayName("dependency threw an exception ")
//    public void Test_GetProduct_InternalDependencyThrowsException() {
//        //Arrange
//        when(productService.getProduct(any(Long.class))).thenThrow(new RuntimeException("Something went very wrong"));
//
//        //Act and Assert
//        assertThrows(RuntimeException.class, ()-> productController.getProduct(1L));
//    }
//
//    @Test
//    @DisplayName("wrong id 0 lead to an exception")
//    public void Test_GetProductWithInvalidId_ThrowsException() {
//        assertThrows(IllegalArgumentException.class,()->productController.getProduct(0L));
//    }


    @Test
    public void Test_ProductControllerCallsProductServiceWithSameId() {
        //Act
        Long id = 2L;

        //Act
        productController.getProduct(id);

        //Assert
        verify(productService).getProduct(idCaptor.capture());
        assertEquals(id,idCaptor.getValue());
    }
    
}
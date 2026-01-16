package com.example.ProductCatalogServiceProxyy.Repository;

import com.example.ProductCatalogServiceProxyy.Models.Category;
import com.example.ProductCatalogServiceProxyy.Models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepoTest {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ProductRepo productRepo;

    @Test
    @Transactional
    @Rollback(value = false)
    void demonstrateLoading() {
        // Insert test data
        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("All electronic items");
        categoryRepo.save(category);

        Product product = new Product();
        product.setTitle("Phone");
        product.setDescription("Smartphone");
        product.setPrice(500.0);
        product.setCategory(category);
        category.getProducts().add(product);
        productRepo.save(product);

        // Fetch back
        Category savedCategory = categoryRepo.findById(category.getId()).get();
        System.out.println(savedCategory.getName());
        List<Product> products = savedCategory.getProducts();
        assertFalse(products.isEmpty());
        for (Product p : products) {
            System.out.println(p.getId());
        }
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void demonstrateNPlusOnePrblm() {
        // Ensure at least one category exists
        if (categoryRepo.count() == 0) {
            Category category = new Category();
            category.setName("Books");
            category.setDescription("All books");
            categoryRepo.save(category);

            Product book = new Product();
            book.setTitle("Java Book");
            book.setDescription("Programming");
            book.setPrice(200.0);
            book.setCategory(category);
            productRepo.save(book);
        }

        List<Category> categories = categoryRepo.findAll();
        for (Category category : categories) {
            List<Product> products = category.getProducts();
            if (!products.isEmpty()) {
                System.out.println(products.get(0).getPrice());
            }
        }
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void queryTest() {
        // Insert sample data
        Category cat = new Category();
        cat.setName("Clothing");
        cat.setDescription("Apparel");
        categoryRepo.save(cat);

        Product shirt = new Product();
        shirt.setTitle("Shirt");
        shirt.setDescription("Cotton Shirt");
        shirt.setPrice(300.0);
        shirt.setCategory(cat);
        productRepo.save(shirt);

        Product product = productRepo.findProductById(shirt.getId());
        assertNotNull(product);

        List<Product> products = productRepo.findProductByPriceBetween(100D, 1000D);
        assertFalse(products.isEmpty());

        String name = productRepo.getProductTitleFromId(shirt.getId());
        assertEquals("Shirt", name);

        String xyz = productRepo.getCategoryNameFromProductId(shirt.getId());
        assertEquals("Clothing", xyz);

        System.out.println("debug");
    }
}
//
//@DataJpaTest
//@ActiveProfiles("test")
//class ProductRepoTest {
//
//    @Autowired
//    private CategoryRepo categoryRepo;
//
//    @Autowired
//    private ProductRepo productRepo;
//
//    @Test
//    @Transactional
//    @Rollback(value = false)
//    void demonstrateLoading(){
//        Category category = categoryRepo.findById(2L).get();
//        System.out.println(category.getName());
//        List<Product> products = category.getProducts();
//        for(Product product : products){
//            System.out.println(product.getId());
//        }
//    }
//
//
//    @Test
//    @Transactional
//    @Rollback(value = false)
//    void demonstrateNPlusOnePrblm(){
//        List<Category> categories = categoryRepo.findAll();
//        for(Category category : categories){
//            List<Product> products = category.getProducts();
//            if(!products.isEmpty()){
//                System.out.println(products.get(0).getPrice());
//            }
//        }
//    }
//
//    @Test
//    @Transactional
//    @Rollback(value = false)
//    void queryTest(){
//        Product product = productRepo.findProductById(2L);
//        List<Product> products = productRepo.findProductByPriceBetween(100D,1000D);
//        String name = productRepo.getProductTitleFromId(1L);
//        String xyz = productRepo.getCategoryNameFromProductId(2L);
//                System.out.println("debug");
//    }
//}
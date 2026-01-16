package com.example.ProductCatalogServiceProxyy.Repository;

import com.example.ProductCatalogServiceProxyy.Models.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

    List<Product> findAll();

    Product save(Product product);

    Product findProductById(Long id);

    List<Product> findProductByPriceBetween(Double low, Double high);

    @Query("select p.title from Product p where p.id=?1")
    String getProductTitleFromId(Long id);

    @Query("select c.name from Product p join Category c on p.category.id=c.id where p.id =:id1")
    String getCategoryNameFromProductId(@Param("id1") Long id);

    List<Product> findAllByIsPrimeTrue();

    @Query("SELECT p FROM Product p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Product> findByTitleEquals(@Param("query") String query, Pageable pageable);

}

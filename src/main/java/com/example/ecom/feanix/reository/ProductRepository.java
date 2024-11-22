package com.example.ecom.feanix.reository;


import com.example.ecom.feanix.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query(value = "SELECT * FROM product WHERE description LIKE %?1%", nativeQuery = true) //get 1st data
    public Page<Product> searchAll(String searchText, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM product WHERE description LIKE %?1%", nativeQuery = true)
    public long searchCount(String searchText);
}

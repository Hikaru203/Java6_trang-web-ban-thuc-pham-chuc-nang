package com.example.jparepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
  
  
  @Query("SELECT o FROM Product o WHERE o.category.id LIKE ?1")
  List<Product> findByCategory(String cid);
}

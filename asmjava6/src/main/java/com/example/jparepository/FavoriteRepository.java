package com.example.jparepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;
import com.example.entity.Favorite;
import com.example.entity.Product;


@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    // Các phương thức truy vấn dữ liệu cho Entity Favorite
    Favorite findByAccountAndProduct(Account account,Product product);
    List<Favorite> findByAccount(Account account);
}

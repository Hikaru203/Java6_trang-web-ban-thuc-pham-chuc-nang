package com.example.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUserName(String userName);
    Account findById(int id);


    Account findByEmail(String email);
}

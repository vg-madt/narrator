package com.narrator.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.narrator.entity.Token;

@Repository
public interface TokenRepo extends JpaRepository<Token, Integer> {

    List<Token> findByDate(String date);

}

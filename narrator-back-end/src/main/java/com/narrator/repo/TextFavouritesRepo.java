package com.narrator.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.narrator.entity.TextFavourites;

@Repository
public interface TextFavouritesRepo extends JpaRepository<TextFavourites, Integer> {

    List<TextFavourites> findByUid(Integer uid);

}

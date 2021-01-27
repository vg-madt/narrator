package com.narrator.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.narrator.entity.ComicFavourites;

@Repository
public interface ComicFavouritesRepo extends JpaRepository<ComicFavourites, Integer> {

    List<ComicFavourites> findByUid(Integer uid);

}

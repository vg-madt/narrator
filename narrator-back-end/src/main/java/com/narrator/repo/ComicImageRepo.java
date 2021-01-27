package com.narrator.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.narrator.entity.ComicImage;

@Repository
public interface ComicImageRepo extends JpaRepository<ComicImage, Integer> {

    List<ComicImage> findByCsid(Integer csid);

}

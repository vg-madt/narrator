package com.narrator.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.narrator.entity.ComicChapter;

@Repository
public interface ComicChapterRepo extends JpaRepository<ComicChapter, Integer> {

    List<ComicChapter> findByCid(Integer cid);

}

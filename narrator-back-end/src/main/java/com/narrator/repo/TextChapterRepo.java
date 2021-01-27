package com.narrator.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.narrator.entity.TextChapter;

@Repository
public interface TextChapterRepo extends JpaRepository<TextChapter, Integer> {

    List<TextChapter> findByTid(Integer tid);

}

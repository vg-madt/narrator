package com.narrator.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.narrator.entity.ChapterPage;

@Repository
public interface ChapterPageRepo extends JpaRepository<ChapterPage, Integer> {

    List<ChapterPage> findByTcid(Integer tcid);

}

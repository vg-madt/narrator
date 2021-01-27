package com.narrator.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.narrator.entity.ComicStory;

@Repository
public interface ComicStoryRepo extends JpaRepository<ComicStory, Integer> {

    List<ComicStory> findByUid(Integer uid);

    List<ComicStory> findByTitleContaining(String title);

    List<ComicStory> findByDescriptionContaining(String description);

    List<ComicStory> findByGenreContaining(String genre);

    @Query("SELECT c FROM ComicStory c WHERE c.cid IN (SELECT cf.cid FROM ComicFavourites cf WHERE cf.uid=?1)")
    List<ComicStory> findFavouritesByUid(Integer uid);

}

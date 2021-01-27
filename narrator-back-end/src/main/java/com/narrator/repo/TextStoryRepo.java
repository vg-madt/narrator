package com.narrator.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.narrator.entity.TextStory;

@Repository
public interface TextStoryRepo extends JpaRepository<TextStory, Integer> {

    List<TextStory> findByUid(Integer uid);

    List<TextStory> findByTitleContaining(String title);

    List<TextStory> findByDescriptionContaining(String description);

    List<TextStory> findByGenreContaining(String genre);

    @Query("SELECT t FROM TextStory t WHERE t.tid IN (SELECT tf.tid FROM TextFavourites tf WHERE tf.uid=?1)")
    List<TextStory> findFavouritesByUid(Integer uid);

}

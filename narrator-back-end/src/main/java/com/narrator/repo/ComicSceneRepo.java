package com.narrator.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.narrator.entity.ComicScene;

@Repository
public interface ComicSceneRepo extends JpaRepository<ComicScene, Integer> {

    List<ComicScene> findByCcid(Integer ccid);

}

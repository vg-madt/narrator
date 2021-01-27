package com.narrator.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.narrator.entity.ComicDialog;

@Repository
public interface ComicDialogRepo extends JpaRepository<ComicDialog, Integer> {

    List<ComicDialog> findByCsid(Integer csid);

}
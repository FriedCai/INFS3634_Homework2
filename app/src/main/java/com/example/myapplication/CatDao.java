package com.example.myapplication;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CatDao {

    @Insert
    void insert(Cats... cats);


    @Query("SELECT * FROM cats")
    List<Cats> loadAllCats();
}

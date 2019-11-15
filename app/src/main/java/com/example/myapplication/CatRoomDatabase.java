package com.example.myapplication;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Cats
        .class}, version=1 ,exportSchema = false)
@TypeConverters({WeightBeanConvert.class})
public abstract class CatRoomDatabase extends RoomDatabase {


    public abstract CatDao catDao();
}
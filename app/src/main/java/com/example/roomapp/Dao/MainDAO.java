package com.example.roomapp.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roomapp.data.MainData;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDAO {

    @Insert(onConflict = REPLACE)
    void insert (MainData mainData);

    @Delete
    void delete (MainData mainData);

    //delete all
    @Delete
    void reset (List<MainData> mainData);

    @Query("UPDATE Datos SET text = :sText WHERE ID = :sID")
    void update(int sID, String sText);

    //get all
    @Query("SELECT * FROM  Datos")
    List<MainData> getAll();
}

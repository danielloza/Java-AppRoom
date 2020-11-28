package com.example.roomapp.data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.roomapp.Dao.MainDAO;

//add database entities
@Database(entities = {MainData.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    //Create database instance
    private static RoomDB database;

    //Name DB

    private static String  DATABASE_NAME = "RoomDatabase";

    public synchronized static RoomDB getInstance(Context context){
        if (database==null){
            //initialize
            database = Room.databaseBuilder(context.getApplicationContext()
            ,RoomDB.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build();
        }
        return database;
    }

    public abstract MainDAO mainDAO();
}

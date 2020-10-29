package com.example.gardenwarden.db.plant;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlantDao {
    @Insert
    void insertPlant(Plant plant);

    @Query("delete from plants")
    void deleteAll();

    @Query("select * from plants")
    LiveData<List<Plant>> getPlants();
}

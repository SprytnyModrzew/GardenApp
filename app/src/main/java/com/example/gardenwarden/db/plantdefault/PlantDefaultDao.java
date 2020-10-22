package com.example.gardenwarden.db.plantdefault;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.gardenwarden.db.plantdefault.PlantDefault;

import java.util.List;

@Dao
public interface PlantDefaultDao {
    @Insert
    void insertPlantDefault(PlantDefault plantDefault);

    @Query("select * from plantDefaults order by name desc")
    LiveData<List<PlantDefault>> getPlantDefaults();

    @Query("delete from plantDefaults")
    void deleteAll();

    @Query("select * from plantDefaults e join plantDefaultCategories r on e.id = r.parent_id where r.parent_id = :id order by name desc")
    LiveData<List<PlantDefaultCategory>> getPlantDefaultCategories(int id);

    @Query("select id from plantDefaults where name = :name")
    int getCurrentId(String name);

    @Insert
    void insertPlantDefaultCategory(PlantDefaultCategory plantDefaultCategory);
}

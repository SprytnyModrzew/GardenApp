package com.example.gardenwarden.db.plantdefault;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "plantDefaultCategories")
public class PlantDefaultCategory{
    @PrimaryKey(autoGenerate = true)
    int id;

    @NonNull
    String name;
    int parent_id;

    public PlantDefaultCategory(@NonNull String name, int parent_id) {
        this.name = name;
        this.parent_id = parent_id;
    }


    @NonNull
    public String getName() {
        return name;
    }
}

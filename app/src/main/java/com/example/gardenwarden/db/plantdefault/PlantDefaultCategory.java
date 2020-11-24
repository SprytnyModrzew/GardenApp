package com.example.gardenwarden.db.plantdefault;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "plantDefaultCategories")
public class PlantDefaultCategory{
    @PrimaryKey
    int id;

    @NonNull
    String name;
    int parent_id;

    public PlantDefaultCategory(@NonNull String name, int parent_id, int id) {
        this.name = name;
        this.parent_id = parent_id;
        this.id = id;
    }


    @NonNull
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}

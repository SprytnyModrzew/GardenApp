package com.example.gardenwarden.db.plantdefault;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "plantDefaults")
public class PlantDefault implements Serializable {
    @PrimaryKey
    int id;

    @NonNull
    String name;
    int defaultImage;

    public PlantDefault(int id, @NonNull String name, int defaultImage) {
        this.id = id;
        this.name = name;
        this.defaultImage = defaultImage;
    }


    @NonNull
    public String getName() {
        return name;
    }

    public int getDefaultImage() {
        return defaultImage;
    }
}

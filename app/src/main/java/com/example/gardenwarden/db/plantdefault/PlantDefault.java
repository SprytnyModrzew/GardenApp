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
    @NonNull
    @PrimaryKey
    String name;
    int defaultImage;

    public PlantDefault(@NonNull String name, int defaultImage) {
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

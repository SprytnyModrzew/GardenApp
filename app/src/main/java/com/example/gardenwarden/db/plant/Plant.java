package com.example.gardenwarden.db.plant;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "plants")
public class Plant implements Serializable {
    @PrimaryKey
    private int id;

    private String name;

    private int deviceId;
    private int waterLevel;
    private int plantCategory;


    private String waterTime;


    public Plant(int id, String name, int deviceId, int waterLevel, int plantCategory, String waterTime) {
        this.id = id;
        this.name = name;
        this.deviceId = deviceId;
        this.waterLevel = waterLevel;
        this.plantCategory = plantCategory;
        this.waterTime = waterTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public int getPlantCategory() {
        return plantCategory;
    }

    public String getWaterTime() {
        return waterTime;
    }
}

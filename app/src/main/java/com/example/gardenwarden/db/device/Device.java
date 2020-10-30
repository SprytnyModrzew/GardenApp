package com.example.gardenwarden.db.device;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "devices")
public class Device implements Serializable {
    @PrimaryKey
    private int id;

    @NonNull
    private String deviceName;

    private int privilegeLevel;

    public int getMaxPlants() {
        return maxPlants;
    }

    private int maxPlants;


    public int getId() {
        return id;
    }

    @NonNull
    public String getDeviceName() {
        return deviceName;
    }

    public int getPrivilegeLevel() {
        return privilegeLevel;
    }

    public Device(int id, @NonNull String deviceName, int privilegeLevel, int maxPlants) {
        this.id = id;
        this.deviceName = deviceName;
        this.privilegeLevel = privilegeLevel;
        this.maxPlants = maxPlants;
    }
}

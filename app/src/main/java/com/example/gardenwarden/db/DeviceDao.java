package com.example.gardenwarden.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DeviceDao {
    @Insert
    void insertDevice(Device device);

    @Query("select * from devices order by deviceName desc")
    LiveData<List<Device>> getDevices();

    @Query("delete from devices")
    void deleteAll();
}
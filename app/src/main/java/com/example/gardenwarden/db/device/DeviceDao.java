package com.example.gardenwarden.db.device;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.gardenwarden.db.device.Device;

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
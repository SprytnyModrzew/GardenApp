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

    @Query("select count(*) from devices join plants on devices.id=plants.deviceId where devices.id=:id")
    int getUsedSlots(int id);

    @Query("select * from devices d where d.maxPlants>(select count(*) from devices join plants on devices.id=plants.deviceId where devices.id=d.id) and d.privilegeLevel == 0 order by deviceName desc")
    LiveData<List<Device>> getAvailableDevices();


}
package com.example.gardenwarden.device;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gardenwarden.db.device.Device;
import com.example.gardenwarden.db.device.DeviceRepository;

import java.util.List;

public class DeviceViewModel extends AndroidViewModel {
    private DeviceRepository deviceRepository;
    private LiveData<List<Device>> devices;

    public DeviceViewModel(@NonNull Application application) {
        super(application);
        deviceRepository = new DeviceRepository(application);
        devices = deviceRepository.getDevices();
    }
    public void insert(Device device){
        deviceRepository.insertDevice(device);
    }

    public LiveData<List<Device>> getDevices(){
        return devices;
    }

    public void updateDevices(List<Device> devices) {
        deviceRepository.updateDevices(devices);
    }
}


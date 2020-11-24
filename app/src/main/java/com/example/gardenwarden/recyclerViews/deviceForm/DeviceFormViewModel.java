package com.example.gardenwarden.recyclerViews.deviceForm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gardenwarden.db.device.Device;

import java.util.List;

public class DeviceFormViewModel extends AndroidViewModel {
    private DeviceFormRepository deviceRepository;
    private LiveData<List<Device>> devices;

    public DeviceFormViewModel(@NonNull Application application) {
        super(application);
        deviceRepository = new DeviceFormRepository(application);
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


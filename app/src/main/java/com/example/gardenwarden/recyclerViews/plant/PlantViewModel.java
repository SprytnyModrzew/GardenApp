package com.example.gardenwarden.recyclerViews.plant;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gardenwarden.db.device.Device;
import com.example.gardenwarden.db.device.DeviceRepository;
import com.example.gardenwarden.db.plant.Plant;
import com.example.gardenwarden.db.plant.PlantRepository;

import java.util.List;

public class PlantViewModel extends AndroidViewModel {
    private PlantRepository deviceRepository;
    private LiveData<List<Plant>> devices;

    public PlantViewModel(@NonNull Application application) {
        super(application);
        deviceRepository = new PlantRepository(application);
        devices = deviceRepository.getPlants();
    }
    public void insert(Plant device){
        deviceRepository.insertPlant(device);
    }

    public LiveData<List<Plant>> getDevices(){
        return devices;
    }

    public void updateDevices(List<Plant> devices) throws InterruptedException {
        deviceRepository.updatePlants(devices);
    }
}

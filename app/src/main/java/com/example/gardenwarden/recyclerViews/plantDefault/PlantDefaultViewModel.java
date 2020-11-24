package com.example.gardenwarden.recyclerViews.plantDefault;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gardenwarden.db.plantdefault.PlantDefault;
import com.example.gardenwarden.db.plantdefault.PlantDefaultRepository;

import java.util.List;

public class PlantDefaultViewModel extends AndroidViewModel {
    private PlantDefaultRepository deviceRepository;
    private LiveData<List<PlantDefault>> devices;

    public PlantDefaultViewModel(@NonNull Application application) {
        super(application);
        deviceRepository = new PlantDefaultRepository(application);
        devices = deviceRepository.getPlantDefaults();
    }
    public void insert(PlantDefault device){
        deviceRepository.insertPlantDefault(device);
    }

    public LiveData<List<PlantDefault>> getPlantDefaults(){
        return devices;
    }

    public void updateDevices(List<PlantDefault> devices) throws InterruptedException {
        deviceRepository.updatePlantDefaults(devices);
    }
}


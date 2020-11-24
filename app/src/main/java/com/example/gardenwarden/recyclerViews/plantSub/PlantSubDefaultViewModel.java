package com.example.gardenwarden.recyclerViews.plantSub;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gardenwarden.db.plantdefault.PlantDefault;
import com.example.gardenwarden.db.plantdefault.PlantDefaultCategory;
import com.example.gardenwarden.db.plantdefault.PlantDefaultRepository;

import java.util.List;

public class PlantSubDefaultViewModel extends AndroidViewModel {
    private PlantDefaultRepository deviceRepository;
    private LiveData<List<PlantDefaultCategory>> devices;

    public PlantSubDefaultViewModel(@NonNull Application application) {
        super(application);
        deviceRepository = new PlantDefaultRepository(application);
    }
    public void insert(PlantDefaultCategory device){
        deviceRepository.insertPlantDefaultCategory(device);
    }
    public void setPlantSubDefaults(int id){
        devices = deviceRepository.getPlantDefaultCategories(id);
    }

    public LiveData<List<PlantDefaultCategory>> getPlantDefaults(){
        return devices;
    }

    public void updateDevices(List<PlantDefaultCategory> devices) throws InterruptedException {
        deviceRepository.updatePlantDefaultCategories(devices);
    }
}

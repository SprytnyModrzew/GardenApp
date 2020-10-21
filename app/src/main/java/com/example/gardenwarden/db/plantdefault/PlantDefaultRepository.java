package com.example.gardenwarden.db.plantdefault;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.gardenwarden.db.AppDatabase;
import com.example.gardenwarden.db.plantdefault.PlantDefault;
import com.example.gardenwarden.db.plantdefault.PlantDefaultDao;

import java.util.List;

public class PlantDefaultRepository {
    private PlantDefaultDao plantDefaultDao;
    private LiveData<List<PlantDefault>> devices;

    public PlantDefaultRepository(Application application) {
        AppDatabase deviceDatabase = AppDatabase.getInstance(application);
        plantDefaultDao = deviceDatabase.plantDefaultDao();
        devices = plantDefaultDao.getPlantDefaults();
    }

    public LiveData<List<PlantDefault>> getDevices(){
        return devices;
    }

    public void insertDevice(PlantDefault plantDefault){
        final PlantDefault plantDefault1 = plantDefault;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                plantDefaultDao.insertPlantDefault(plantDefault1);
            }
        });
        thread.start();
    }

    public void deleteDevices(){
        plantDefaultDao.deleteAll();
    }

    public void updateDevices(final List<PlantDefault> plantDefaults) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                plantDefaultDao.deleteAll();
                Log.d("hh","deleted");
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(int i=0; i<plantDefaults.size(); i++){
                    plantDefaultDao.insertPlantDefault(plantDefaults.get(i));
                }
            }
        });
        thread.start();
        thread2.start();
    }
}

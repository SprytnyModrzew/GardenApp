package com.example.gardenwarden.db.plant;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.gardenwarden.db.AppDatabase;
import com.example.gardenwarden.db.device.Device;

import java.util.List;

public class PlantRepository {
    private PlantDao plantDao;
    private LiveData<List<Plant>> plants;

    public PlantRepository(Application application) {
        AppDatabase deviceDatabase = AppDatabase.getInstance(application);
        plantDao = deviceDatabase.plantDao();
        plants = plantDao.getPlants();
    }

    public LiveData<List<Plant>> getPlants(){
        return plants;
    }

    public void insertPlant(Plant plant) {
        final Plant plant1 = plant;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                plantDao.insertPlant(plant1);
            }
        });
        thread.start();
    }

    public void deletePlants() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                plantDao.deleteAll();
            }
        });
        thread.start();
    }

    public void updatePlants(final List<Plant> devices) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                plantDao.deleteAll();
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
                for(int i=0; i<devices.size(); i++){
                    Log.d("i:", String.valueOf(devices.get(i).getWaterTime()));

                    plantDao.insertPlant(devices.get(i));
                }
            }
        });
        thread.start();
        thread2.start();
    }
}

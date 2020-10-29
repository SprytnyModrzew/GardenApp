package com.example.gardenwarden.db.plantdefault;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.gardenwarden.db.AppDatabase;

import java.util.List;

public class PlantDefaultRepository {
    private PlantDefaultDao plantDefaultDao;
    private LiveData<List<PlantDefault>> plantDefaults;

    public PlantDefaultRepository(Application application) {
        AppDatabase deviceDatabase = AppDatabase.getInstance(application);
        plantDefaultDao = deviceDatabase.plantDefaultDao();
        plantDefaults = plantDefaultDao.getPlantDefaults();
    }

    public LiveData<List<PlantDefault>> getPlantDefaults(){
        return plantDefaults;
    }

    public LiveData<List<PlantDefaultCategory>> getPlantDefaultCategories(int id) {
        return plantDefaultDao.getPlantDefaultCategories(id);
    }

    public void insertPlantDefault(PlantDefault plantDefault){
        final PlantDefault plantDefault1 = plantDefault;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                plantDefaultDao.insertPlantDefault(plantDefault1);
            }
        });
        thread.start();
    }

    public void insertPlantDefaultCategory(PlantDefaultCategory plantDefaultCategory){
        final PlantDefaultCategory plantDefault1 = plantDefaultCategory;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                plantDefaultDao.insertPlantDefaultCategory(plantDefault1);
            }
        });
        thread.start();
    }

    public int getParentId(String parentName){
        Log.e("parent_name",parentName);
        return plantDefaultDao.getCurrentId(parentName);
    }

    public void deletePlantDefaults(){
        plantDefaultDao.deleteAllDefaults();
    }

    public void deletePlantDefaultCategories(){
        plantDefaultDao.deleteAllDefaultCategories();
    }

    public void updatePlantDefaults(final List<PlantDefault> plantDefaults) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                plantDefaultDao.deleteAllDefaults();
                Log.d("hh","deleted");
            }
        });
        thread.start();
        thread.join();
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<plantDefaults.size(); i++){
                    plantDefaultDao.insertPlantDefault(plantDefaults.get(i));
                }
            }
        });
        thread2.start();
    }

    public void updatePlantDefaultCategories(final List<PlantDefaultCategory> plantDefaults) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                plantDefaultDao.deleteAllDefaultCategories();
                Log.d("hh","deleted");
            }
        });
        thread.start();
        thread.join();
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(int i=0; i<plantDefaults.size(); i++){
                    plantDefaultDao.insertPlantDefaultCategory(plantDefaults.get(i));
                }
            }
        });
        thread2.start();
    }
}

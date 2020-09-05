package com.example.gardenwarden.db;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DeviceRepository {
    private DeviceDao deviceDao;
    private LiveData<List<Device>> devices;

    public DeviceRepository(Application application) {
        AppDatabase deviceDatabase = AppDatabase.getInstance(application);
        deviceDao = deviceDatabase.deviceDao();
        devices = deviceDao.getDevices();
    }

    public LiveData<List<Device>> getDevices(){
        return devices;
    }

    public void insertDevice(Device device){
        final Device device1 = device;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                deviceDao.insertDevice(device1);
            }
        });
        thread.start();
    }

    public void updateDevices(final List<Device> devices) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                deviceDao.deleteAll();
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
                    Log.d("i:", String.valueOf(devices.get(i).getDeviceName()));

                    deviceDao.insertDevice(devices.get(i));
                }
            }
        });
        thread.start();
        thread2.start();
    }
}

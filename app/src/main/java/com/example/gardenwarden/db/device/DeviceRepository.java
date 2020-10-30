package com.example.gardenwarden.db.device;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.gardenwarden.db.AppDatabase;
import com.example.gardenwarden.db.device.Device;
import com.example.gardenwarden.db.device.DeviceDao;

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

    public int getSlots(int id) throws InterruptedException {
        final int id1 = id;
        final int[] left = new int[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                left[0] = deviceDao.getUsedSlots(id1);
            }
        });
        thread.start();
        thread.join();
        return left[0];
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

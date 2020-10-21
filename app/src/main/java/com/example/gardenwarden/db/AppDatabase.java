package com.example.gardenwarden.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.gardenwarden.db.device.Device;
import com.example.gardenwarden.db.device.DeviceDao;
import com.example.gardenwarden.db.plantdefault.PlantDefault;
import com.example.gardenwarden.db.plantdefault.PlantDefaultDao;

@androidx.room.Database(entities = {Device.class, PlantDefault.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract DeviceDao deviceDao();
    public abstract PlantDefaultDao plantDefaultDao();

    public static synchronized AppDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "appDatabase")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDB(instance).insert();
        }
    };

    private static class PopulateDB{
        private DeviceDao deviceDao;
        private PopulateDB(AppDatabase db){
            deviceDao = db.deviceDao();
        }
        protected void insert(){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                }
            });
            thread.start();
        }


    }
}
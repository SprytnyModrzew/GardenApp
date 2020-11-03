package com.example.gardenwarden.form;

import android.util.Log;

public class FormPlant {
    public int waterLevel;
    public String name;
    public String waterTime;

    public int getWaterLevel() {
        return waterLevel;
    }

    public String getName() {
        return name;
    }

    public String getWaterTime() {
        return waterTime;
    }

    public FormPlant(int waterLevel, String name, int waterMinute, int waterHour) {
        this.waterLevel = waterLevel;
        this.name = name;
        if(waterHour<9){
            this.waterTime = "0"+waterHour +":"+ (waterMinute % 2) * 3+"0";
        }
        else {
            this.waterTime = waterHour +":"+ (waterMinute % 2) * 3+"0";
        }
        Log.e("www",this.waterTime);
        Log.e("www", String.valueOf(this.waterLevel));
    }
}

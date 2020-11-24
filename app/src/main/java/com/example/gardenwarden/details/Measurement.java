package com.example.gardenwarden.details;

public class Measurement {
    String type;
    String value;

    public Measurement(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}

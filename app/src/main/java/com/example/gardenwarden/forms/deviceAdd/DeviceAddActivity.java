package com.example.gardenwarden.forms.deviceAdd;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gardenwarden.R;

public class DeviceAddActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        Button button = findViewById(R.id.device_add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView id = findViewById(R.id.device_add_id);
                String id_string = (String) id.getText().toString();
                if(!id_string.equals("")){
                    int id_int = Integer.parseInt(id_string);
                    Intent intent = new Intent();
                    intent.putExtra("id",id_int);
                    Log.e("id",String.valueOf(id_int));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}

package com.example.gardenwarden.form;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gardenwarden.R;
import com.example.gardenwarden.db.Device;

public class DeviceDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_device);
        TextView name = findViewById(R.id.device_detail_name);
        TextView id = findViewById(R.id.device_detail_id);
        TextView auth = findViewById(R.id.device_detail_ownership);

        final Intent intent = getIntent();
        final Device device = (Device) intent.getSerializableExtra("object");
        assert device != null;
        Log.e("device",device.getDeviceName());
        name.setText(device.getDeviceName());
        id.setText(String.valueOf(device.getId()));
        Button delete_button = findViewById(R.id.detail_device_button_delete);
        if(device.getPrivilegeLevel()==0){
            auth.setText(R.string.detail_label_authority_0);
            delete_button.setVisibility(View.INVISIBLE);
        }
        else{
            auth.setText(R.string.detail_label_authority_1);
            delete_button.setVisibility(View.VISIBLE);
            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra("command","erase");
                    intent.putExtra("object",device);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }
}

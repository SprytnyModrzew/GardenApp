package com.example.gardenwarden.details;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gardenwarden.R;
import com.example.gardenwarden.db.device.Device;
import com.example.gardenwarden.db.device.DeviceRepository;
import com.example.gardenwarden.db.plant.Plant;

import java.util.HashMap;
import java.util.Map;

public class DeviceDetailActivity extends AppCompatActivity{
    String oldDeviceName;
    Device device;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = this.getSharedPreferences("network content", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_detail_device);
        TextView name = findViewById(R.id.device_detail_name);
        TextView id = findViewById(R.id.device_detail_id);
        TextView auth = findViewById(R.id.device_detail_ownership);
        TextView maxPlants = findViewById(R.id.device_detail_plants);
        TextView leftPlants = findViewById(R.id.device_detail_plants2);

        final Intent intent = getIntent();
        device = (Device) intent.getSerializableExtra("object");
        assert device != null;
        Log.e("device",device.getDeviceName());
        oldDeviceName = device.getDeviceName();
        name.setText(oldDeviceName);
        id.setText(String.valueOf(device.getId()));

        DeviceRepository deviceRepository = new DeviceRepository(getApplication());
        int slots = 69;
        try {
            slots = deviceRepository.getSlots(device.getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        leftPlants.setText(String.valueOf(slots));
        maxPlants.setText(String.valueOf(device.getMaxPlants()));
        Button action_button = findViewById(R.id.detail_device_button_delete);
        if(device.getPrivilegeLevel()==0){
            action_button.setText(R.string.detail_button_desc_0);
            auth.setText(R.string.detail_label_authority_0);
            action_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteDevice(device);
                }
            });
        }
        else{
            action_button.setText(R.string.detail_button_desc_1);
            auth.setText(R.string.detail_label_authority_1);
            action_button.setOnClickListener(new View.OnClickListener() {
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

    private void makeAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DeviceDetailActivity.this);
        alertDialog.setTitle("Renaming element");
        alertDialog.setMessage("Enter the new device name");

        final EditText input = new EditText(DeviceDetailActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        //alertDialog.setIcon(R.drawable.key);

        alertDialog.setPositiveButton("Rename",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String newDeviceName = input.getText().toString();
                        if (!(newDeviceName.equals(""))) {
                            if (newDeviceName.equals(oldDeviceName)) {
                                Toast.makeText(getApplicationContext(),
                                        R.string.toast_error_names_equal, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        R.string.toast_rename, Toast.LENGTH_SHORT).show();
                                Intent intent = getIntent();
                                intent.putExtra("command","update");
                                intent.putExtra("object",device);
                                intent.putExtra("newName",newDeviceName);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        }
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }
    public void deleteDevice(final Device device){
        String url_main = sharedPref.getString("url","0");
        String url =url_main+"/delete/device";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = getIntent();
                        intent.putExtra("command","update");
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap<>();
                String token = sharedPref.getString("token","0");

                params.put("Authorization", "Token "+token);
                return params;
            }

            @Override
            protected Map<String, String> getParams(){
                Map<String, String>  params = new HashMap<>();
                params.put("device_id", String.valueOf(device.getId()));
                return params;
            }
        };
        queue.add(postRequest);
    }

}

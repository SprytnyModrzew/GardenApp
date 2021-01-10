package com.example.gardenwarden.forms.deviceAdd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gardenwarden.R;

import java.util.HashMap;
import java.util.Map;

public class DeviceAddActivity extends AppCompatActivity {
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        Button button = findViewById(R.id.device_add_button);
        final NumberPicker pickerMinute = findViewById(R.id.device_add_own_number);
        pickerMinute.setMinValue(1);
        pickerMinute.setMaxValue(10);
        sharedPref = this.getSharedPreferences("network content", Context.MODE_PRIVATE);

        Button button1 = findViewById(R.id.device_add_own_button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url_main = sharedPref.getString("url","0");
                String url =url_main+"/add/device";

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                Log.e("yay","something happend");
                                Log.e("yr",response);
                                if(response.equals("good")){
                                    Intent intent = new Intent();
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }
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
                        //TextView id = findViewById(R.id.device_own_add_id);
                        TextView name = findViewById(R.id.device_own_add_name);
                        TextView url = findViewById(R.id.device_own_add_url);
                        NumberPicker numberPicker = findViewById(R.id.device_add_own_number);

                        //params.put("id", id.getText().toString());
                        params.put("name", name.getText().toString());
                        params.put("url", url.getText().toString());
                        params.put("slots", String.valueOf(numberPicker.getValue()));

                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });

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

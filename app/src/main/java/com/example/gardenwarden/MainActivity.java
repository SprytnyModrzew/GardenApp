package com.example.gardenwarden;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gardenwarden.db.Device;
import com.example.gardenwarden.form.DeviceAddActivity;
import com.example.gardenwarden.form.DeviceDetailActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import com.example.gardenwarden.ui.main.SectionsPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements DeviceFragment.OnListFragmentInteractionListener {
    private final int requestDeviceDetail = 69;
    private final int requestDeviceAdd = 70;


    SharedPreferences sharedPref;
    final String url_main = "http://192.168.1.151:8080";
    SectionsPagerAdapter sectionsPagerAdapter;
    protected void changeToken(String token){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token",token);
        editor.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("requestcode", String.valueOf(requestCode));
        Log.d("reqddddcode", String.valueOf(resultCode));
        if(requestCode == requestDeviceDetail && resultCode == RESULT_OK){
            assert data != null;
            Log.d("woop",data.getStringExtra("command"));
            Device device = (Device) data.getSerializableExtra("object");
            String command = data.getStringExtra("command");
            assert command != null;
            if(command.equals("erase")){
                assert device != null;
                delete_watch_event(device.getId());
                Log.e("edo","do");
            }
            if(command.equals("update")){
                assert device != null;
                String name = data.getStringExtra("newName");
                update_watch_event(device.getId(), name);
                Log.e("edo","do");
            }
        }
        if(requestCode==requestDeviceAdd && resultCode == RESULT_OK){
            assert data != null;
            int id = data.getIntExtra("id",0);
            add_watch_event(id);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab_login);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
                get_devices();
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.fab_refresh);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_devices();
            }
        });

        FloatingActionButton fab3 = findViewById(R.id.fab_add);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DeviceAddActivity.class);
                startActivityForResult(intent,requestDeviceAdd);
            }
        });
    }

    @Override
    public void onListFragmentInteraction(Device item) {
        Intent intent = new Intent(this, DeviceDetailActivity.class);
        intent.putExtra("object",item);
        startActivityForResult(intent,requestDeviceDetail);
    }


    @Override
    public void onListFragmentLongClick(Device item) {
        {
        }
    }

    @Override
    public void onButtonClick(Device mItem) {

    }

    public void login(){
        String url =url_main+"/login";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Response", response);
                        try {
                            JSONObject object = new JSONObject(response);
                            Log.e("soo",object.get("token").toString());
                            changeToken(object.get("token").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.e("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("login", "superszpital");
                params.put("password", "valerie");

                return params;
            }
        };
        queue.add(postRequest);
    }

    public void get_devices(){
        String url =url_main+"/get/devices";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray good = object.getJSONArray("data");
                            List<Device> devices = new ArrayList<>();
                            for(int i = 0; i< good.length();i++)
                            {
                                Log.d("i:", String.valueOf(i));
                                JSONArray temp = good.getJSONArray(i);
                                int id = (int) temp.get(0);
                                Device device = new Device(id,temp.get(1).toString(),(int)temp.get(2));
                                devices.add(device);
                            }
                            sectionsPagerAdapter.deviceFragment.contactViewModel.updateDevices(devices);
                        } catch (JSONException e) {
                            e.printStackTrace();
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
        };
        queue.add(postRequest);
    }
    public void delete_devices(){
        String url =url_main+"/debug/delete_watch_events";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        get_devices();
                        Log.d("Response", response);
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
        };
        queue.add(postRequest);
    }


    public void add_watch_event(int id_temp){
        final int id = id_temp;
        String url =url_main+"/add/watch_event";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        get_devices();
                        Log.d("Response", response);
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
            public Map<String, String> getParams(){
                Map<String, String>  params = new HashMap<>();
                params.put("id",String.valueOf(id));
                return params;
            }
        };
        queue.add(postRequest);
    }

    public void delete_watch_event(int id){
        final int id_ = id;
        String url =url_main+"/delete/watch_event";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        get_devices();
                        Log.d("Respons22e", response);
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
            public Map<String, String> getParams(){
                Map<String, String>  params = new HashMap<>();
                params.put("id",String.valueOf(id_));
                return params;
            }
        };
        queue.add(postRequest);
    }

    public void update_watch_event(int id_temp, String name_temp){
        final int id = id_temp;
        final String name = name_temp;
        String url =url_main+"/modify/device_name";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        get_devices();
                        Log.d("Response", response);
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
            public Map<String, String> getParams(){
                Map<String, String>  params = new HashMap<>();
                Log.e("name",name);
                Log.e("id",String.valueOf(id));
                params.put("id",String.valueOf(id));
                params.put("name",name);
                return params;
            }
        };
        queue.add(postRequest);
    }
}


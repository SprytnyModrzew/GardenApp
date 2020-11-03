package com.example.gardenwarden.form;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gardenwarden.PlantSubDefaultFragment;
import com.example.gardenwarden.R;
import com.example.gardenwarden.db.device.Device;
import com.example.gardenwarden.db.plant.Plant;
import com.example.gardenwarden.db.plant.PlantRepository;
import com.example.gardenwarden.db.plantdefault.PlantDefault;
import com.example.gardenwarden.DefaultPlantFragment;
import com.example.gardenwarden.db.plantdefault.PlantDefaultCategory;
import com.example.gardenwarden.device.DeviceFormFragment;
import com.example.gardenwarden.ui.main.FormFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlantAddActivity extends AppCompatActivity implements
        DeviceFormFragment.OnListFragmentInteractionListener,
        DefaultPlantFragment.OnListFragmentInteractionListener,
        PlantSubDefaultFragment.OnListFragmentInteractionListener,
        FormFragment.OnFormFragmentInteractionListener {

    SharedPreferences sharedPref;

    private PlantDefaultCategory plantDefaultCategory;
    private Device device;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
        sharedPref = this.getSharedPreferences("network content",Context.MODE_PRIVATE);
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {

        if (fragment instanceof FormFragment) {
            FormFragment headlinesFragment = (FormFragment) fragment;
            headlinesFragment.setClickedListener(this);
            return;
        }
        super.onAttachFragment(fragment);
    }

    @Override
    public void onListFragmentInteraction(PlantDefault item) {
        Log.e("clicked","eo");
        //todo zamiast device'ów, jakieś nowe deviceAvailable zrobić
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        );

        PlantSubDefaultFragment blankFragment = new PlantSubDefaultFragment(item.getId());

        fragmentTransaction.replace(R.id.plant_add_fragment,blankFragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentLongClick(PlantDefault item) {

    }

    @Override
    public void onButtonClick(PlantDefault mItem) {

    }

    @Override
    public void onListFragmentInteraction(PlantDefaultCategory item) {
        Log.e("clicked","eo");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //todo parametryzować, tzn przekazywać z poziomu bazki tutaj
        int water_id = 0;

        FormFragment blankFragment = new FormFragment(water_id);
        fragmentTransaction.setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        );

        fragmentTransaction.replace(R.id.plant_add_fragment,blankFragment);
        fragmentTransaction.addToBackStack(null);

        plantDefaultCategory = item;

        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentLongClick(PlantDefaultCategory item) {

    }

    @Override
    public void onButtonClick(PlantDefaultCategory mItem) {

    }

    @Override
    public void onListFragmentInteraction(Device item) {
        Log.e("clicked","eo");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        );

        DefaultPlantFragment blankFragment = new DefaultPlantFragment();

        fragmentTransaction.replace(R.id.plant_add_fragment,blankFragment);
        fragmentTransaction.addToBackStack(null);

        device = item;

        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentLongClick(Device item) {

    }

    @Override
    public void onButtonClick(Device mItem) {

    }

    @Override
    public void onAddButtonClick(final FormPlant plant){
        String url_main = sharedPref.getString("url","0");
        String url =url_main+"/add/plant";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.e("yay","something happend");
                        Intent intent = new Intent();
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
                params.put("name", plant.getName());
                params.put("water_level", String.valueOf(plant.getWaterLevel()));
                params.put("water_time", plant.getWaterTime());
                params.put("device_id", String.valueOf(device.getId()));
                params.put("plant_id", plantDefaultCategory.getName());

                return params;
            }
        };
        queue.add(postRequest);
    }
}

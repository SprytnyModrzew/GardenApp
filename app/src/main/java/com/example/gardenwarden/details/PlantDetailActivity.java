package com.example.gardenwarden.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gardenwarden.R;
import com.example.gardenwarden.db.plant.Plant;
import com.example.gardenwarden.db.plantdefault.PlantDefaultRepository;
import com.example.gardenwarden.forms.plantAdd.FormPlant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class PlantDetailActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    Context context;
    Timer requestTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = this.getSharedPreferences("network content", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_detail_plant);
        Intent intent = getIntent();
        final Plant currentPlant = (Plant) intent.getSerializableExtra("object");

        assert currentPlant != null;
        TextView plantName = findViewById(R.id.detail_plant_name);
        int plantId = currentPlant.getPlantCategory();
        plantName.setText(currentPlant.getName());
        int imagePath = 0;
        String type = "";
        Log.e("plantID",String.valueOf(plantId));
        PlantDefaultRepository plantDefaultRepository = new PlantDefaultRepository(getApplication());
        try {
            type = plantDefaultRepository.getCategoryName(plantId);
            imagePath = plantDefaultRepository.getDefaultImage(plantId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        File destination = getApplicationContext().getFilesDir();
        String dest = "default"+ imagePath +".png";
        File des = new File(destination,dest);

        TextView plantType = findViewById(R.id.detail_plant_type);
        plantType.setText(type);

        context = this;

        ImageView plantImage = findViewById(R.id.detail_plant_image);
        plantImage.setImageBitmap(BitmapFactory.decodeFile(String.valueOf(des)));
        Log.e(",,",currentPlant.getName());

        getMeasurements(currentPlant);

        requestTimer = new Timer();
        requestTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getMeasurements(currentPlant);
            }
        }, 5, 30000);
    }

    public void getMeasurements(final Plant plant){
        String url_main = sharedPref.getString("url","0");
        String url =url_main+"/send/measurements";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.e("yay",response);
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("data");
                            ArrayList<Measurement> measurements = new ArrayList<>();
                            Log.e("ro","do");
                            for(int i = 0; i<jsonArray.length(); i++){
                                Log.e("eo","eo");
                                JSONObject object1 = jsonArray.getJSONObject(i);
                                measurements.add(new Measurement(object1.getString("name_of_sensor"), object1.getString("measurement")));
                            }
                            FragmentManager fg = getSupportFragmentManager();
                            MeasurementFragment fragment = (MeasurementFragment) fg.findFragmentById(R.id.detail_measurements);

                            assert fragment != null;
                            try {
                                fragment.updateRecyclerView(measurements);
                            }
                            catch(NullPointerException e){
                                Log.e("woo","woo");
                                requestTimer.cancel();
                            }

                        } catch (JSONException e) {
                            Log.e("toto","źle jest");
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

            @Override
            protected Map<String, String> getParams(){
                Map<String, String>  params = new HashMap<>();
                params.put("plant_id", String.valueOf(plant.getId()));
                return params;
            }
        };
        queue.add(postRequest);
    }
}
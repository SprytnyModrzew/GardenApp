package com.example.gardenwarden;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gardenwarden.db.device.Device;
import com.example.gardenwarden.db.plant.Plant;
import com.example.gardenwarden.db.plant.PlantRepository;
import com.example.gardenwarden.db.plantdefault.PlantDefault;
import com.example.gardenwarden.db.plantdefault.PlantDefaultCategory;
import com.example.gardenwarden.db.plantdefault.PlantDefaultRepository;
import com.example.gardenwarden.details.PlantDetailActivity;
import com.example.gardenwarden.recyclerViews.device.DeviceFragment;
import com.example.gardenwarden.forms.deviceAdd.DeviceAddActivity;
import com.example.gardenwarden.details.DeviceDetailActivity;
import com.example.gardenwarden.forms.userAdd.LoginActivity;
import com.example.gardenwarden.forms.plantAdd.PlantAddActivity;
import com.example.gardenwarden.recyclerViews.plant.PlantFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.gardenwarden.ui.main.SectionsPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements DeviceFragment.OnListFragmentInteractionListener, PlantFragment.OnListFragmentInteractionListener {
    private final int requestDeviceDetail = 69;
    private final int requestPlantDetail = 73;
    private final int requestDeviceAdd = 70;
    private final int requestLogin = 71;
    private final int requestPlantAdd = 72;


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
                Log.e("edo","do");
            }
            get_devices();
            get_plants();
        }
        if(requestCode == requestPlantDetail && resultCode == RESULT_OK){
            get_plants();
        }
        if(requestCode==requestDeviceAdd && resultCode == RESULT_OK){
            assert data != null;
            int id = data.getIntExtra("id",0);
            add_watch_event(id);
        }
        if(requestCode==requestLogin && resultCode == RESULT_OK){
            assert data != null;
            String command = data.getStringExtra("command");
            assert command != null;
            if(command.equals("login")){
                final String login = data.getStringExtra("login");
                final String password = data.getStringExtra("password");
                login(login, password);
            }
            if(command.equals("register")){
                final String login = data.getStringExtra("login");
                final String password = data.getStringExtra("password");
                final String email = data.getStringExtra("email");
                register(login, password, email);
            }
            Log.d("woo","doo");

        }
        if(requestCode==requestPlantAdd && resultCode == RESULT_OK){
            get_plants();
            get_devices();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = this.getSharedPreferences("network content",Context.MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("url",url_main);
        editor.apply();

        String token = sharedPref.getString("token","0");
        assert token != null;
        if(token.equals("0")){

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivityForResult(intent,requestLogin);
        }

        FloatingActionButton logoutButton = findViewById(R.id.fab_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        FloatingActionButton refreshButton = findViewById(R.id.fab_refresh);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_devices();
                get_plants();
            }
        });

        FloatingActionButton addButton = findViewById(R.id.fab_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem() == 1){
                    Intent intent = new Intent(getApplicationContext(), DeviceAddActivity.class);
                    startActivityForResult(intent,requestDeviceAdd);
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), PlantAddActivity.class);
                    startActivityForResult(intent,requestPlantAdd);
                }
            }
        });

        check_version();
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

    public void login(final String login, final String password){
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
                            get_devices();
                            check_version();
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
                        String token = sharedPref.getString("token","0");
                        Log.e("coooo","dooo");
                        assert token != null;
                        if(token.equals("0")){
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.putExtra("error",true);
                            startActivityForResult(intent,requestLogin);
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("login", login);
                params.put("password", password);

                return params;
            }
        };
        queue.add(postRequest);
    }
    public void register(final String login, final String password, final String email){
        String url =url_main+"/add/user";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Response", response);
                        if(response.equals("ok"))
                            login(login, password);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        String token = sharedPref.getString("token","0");
                        Log.e("coooo","dooo");
                        assert token != null;
                        if(token.equals("0")){
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.putExtra("error",true);
                            startActivityForResult(intent,requestLogin);
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("login", login);
                params.put("password", password);
                params.put("email", email);

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
                                Device device = new Device(id,temp.get(1).toString(),(int)temp.get(2), (int)temp.get(3));
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
                        get_plants();
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
                        get_plants();
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

    public void check_version(){
        String url =url_main+"/send/version";
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
                            Log.e("ob", object.get("version").toString());
                            Log.e("ob",object.get("count").toString());
                            get_images((Integer) object.get("count"));

                            double d = object.getDouble("version");
                            float f = (float)d;

                            if(f>sharedPref.getFloat("version", 0.0f)){
                                get_definitions();
                                get_plants();
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putFloat("version", f);
                                editor.apply();
                            }
                            else{
                                Log.e("nope","nope");
                            }
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

    public void get_images(int count){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        Log.e("count", String.valueOf(count));
        for(int i = 0; i<count; i++) {
            String url =url_main+"/send/picture/"+i;
            final int finalI = i+1;
            ImageRequest postRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    String filename = "default"+finalI+".png";
                    File sd = getApplicationContext().getFilesDir();
                    File dest = new File(sd, filename);

                    try {
                        bitmap = cropCircle(bitmap);
                        FileOutputStream out = new FileOutputStream(dest);
                        Log.e("dest",String.valueOf(dest));
                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        Log.e("notdone","error");
                        e.printStackTrace();
                    }
                    Log.e("eo", "done");
                }
            }, 600, 600, ImageView.ScaleType.FIT_XY, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("woops","woops");
                }
            }){
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String>  params = new HashMap<>();
                    String token = sharedPref.getString("token","0");

                    params.put("Authorization", "Token "+token);
                    return params;
                }
            };;
            queue.add(postRequest);
        }
    }
    public void get_definitions(){
        String url =url_main+"/send/default_definitions";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        final String response1 = response;
                        Thread thread = new Thread(new Runnable(){

                            @Override
                            public void run() {
                                final PlantDefaultRepository plantDefaultRepository = new PlantDefaultRepository(getApplication());
                                //plantDefaultRepository.updateDevices();
                                Log.d("Response", response1);
                                Thread thread1 = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        plantDefaultRepository.deletePlantDefaultCategories();
                                        plantDefaultRepository.deletePlantDefaults();
                                    }
                                });
                                thread1.start();
                                try {
                                    thread1.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    JSONArray defaults_array = new JSONArray(response1);
                                    int counter = 1;
                                    for(int i = 0; i<defaults_array.length(); i++){
                                        JSONObject object = defaults_array.getJSONObject(i);
                                        JSONArray array = object.getJSONArray("species");
                                        PlantDefault plantDefault = new PlantDefault(i,object.get("name").toString(),object.getInt("default_image"));
                                        Log.e("def image", String.valueOf(object.getInt("default_image")));
                                        for(int j = 0; j<array.length(); j++){
                                            String name = array.getString(j);
                                            Log.e(object.get("name").toString(),name + j+i);
                                            PlantDefaultCategory plantDefaultCategory = new PlantDefaultCategory(
                                                    name,
                                                    i,
                                                    counter++
                                            );
                                            plantDefaultRepository.insertPlantDefaultCategory(plantDefaultCategory);
                                        }
                                        plantDefaultRepository.insertPlantDefault(plantDefault);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        thread.start();
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

    public void get_plants(){
        String url =url_main+"/get/plants";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                PlantRepository plantRepository = new PlantRepository(getApplication());
                                plantRepository.deletePlants();
                            }
                        });
                        thread.start();
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.e("response",response);
                        PlantRepository plantRepository = new PlantRepository(getApplication());
                        List<Plant> plants = new ArrayList<>();
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("data");
                            Log.e("ro","do");
                            for(int i = 0; i<jsonArray.length(); i++){
                                Log.e("ii jest równe",String.valueOf(i));
                                JSONObject object1 = jsonArray.getJSONObject(i);
                                Log.e("ii jest równe",String.valueOf(object1.getInt("plant_category")));
                                Plant plant = new Plant(object1.getInt("id"),object1.getString("name"),object1.getInt("device"),object1.getInt("water_level"),object1.getInt("plant_category"),object1.getString("water_time"));
                                Log.e("objectDeviceId", String.valueOf(object1.getInt("device")));
                                plants.add(plant);
                            }
                        } catch (JSONException e) {
                            Log.e("toto","źle jest");
                            e.printStackTrace();
                        }
                        try {
                            plantRepository.updatePlants(plants);
                        } catch (InterruptedException e) {
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

    //https://stackoverflow.com/questions/24295015/cropping-image-into-circle
    private Bitmap cropCircle(Bitmap bmp) {
        int widthLight = bmp.getWidth();
        int heightLight = bmp.getHeight();

        Bitmap output = Bitmap.createBitmap(widthLight, heightLight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);

        RectF rectF = new RectF(new Rect(0, 0, widthLight, heightLight));

        canvas.drawRoundRect(rectF, widthLight / 2 ,heightLight / 2,paint);

        Paint paintImage = new Paint();
        paintImage.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(bmp, 0, 0, paintImage);

        return output;
    }

    private void errorMessage(int errorId){
        Snackbar snackbar = Snackbar.make(findViewById(R.id.main_layout), getResources().getText(errorId).toString(), Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void logout(){
        changeToken("0");
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(intent,requestLogin);
    }

    @Override
    public void onListFragmentInteraction(Plant item) {
        Intent intent = new Intent(this, PlantDetailActivity.class);
        intent.putExtra("object",item);
        startActivityForResult(intent,requestPlantDetail);
    }

    @Override
    public void onListFragmentLongClick(Plant item) {

    }

    @Override
    public void onButtonClick(Plant mItem) {

    }
}


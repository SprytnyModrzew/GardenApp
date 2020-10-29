package com.example.gardenwarden.form;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.gardenwarden.BlankFragment;
import com.example.gardenwarden.PlantFragment2;
import com.example.gardenwarden.PlantSubDefaultFragment;
import com.example.gardenwarden.R;
import com.example.gardenwarden.db.device.Device;
import com.example.gardenwarden.db.plantdefault.PlantDefault;
import com.example.gardenwarden.DefaultPlantFragment;
import com.example.gardenwarden.db.plantdefault.PlantDefaultCategory;
import com.example.gardenwarden.device.DeviceFragment;

public class PlantAddActivity extends AppCompatActivity implements DeviceFragment.OnListFragmentInteractionListener,DefaultPlantFragment.OnListFragmentInteractionListener, PlantSubDefaultFragment.OnListFragmentInteractionListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
    }

    @Override
    public void onListFragmentInteraction(PlantDefault item) {
        Log.e("clicked","eo");
        //todo zamiast device'ów, jakieś nowe deviceAvailable zrobić
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

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
        Log.e("click","click");
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

        DefaultPlantFragment blankFragment = new DefaultPlantFragment();

        fragmentTransaction.replace(R.id.plant_add_fragment,blankFragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentLongClick(Device item) {

    }

    @Override
    public void onButtonClick(Device mItem) {

    }
}

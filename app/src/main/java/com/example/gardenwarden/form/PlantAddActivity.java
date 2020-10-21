package com.example.gardenwarden.form;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gardenwarden.DefaultPlantFragment;
import com.example.gardenwarden.R;
import com.example.gardenwarden.db.Device;

public class PlantAddActivity extends AppCompatActivity implements DefaultPlantFragment.OnListFragmentInteractionListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
    }

    @Override
    public void onListFragmentInteraction(DefaultPlantFragment.PlantDefault item) {

    }

    @Override
    public void onListFragmentLongClick(DefaultPlantFragment.PlantDefault item) {

    }

    @Override
    public void onButtonClick(DefaultPlantFragment.PlantDefault mItem) {

    }
}

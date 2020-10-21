package com.example.gardenwarden.form;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gardenwarden.R;
import com.example.gardenwarden.db.plantdefault.PlantDefault;
import com.example.gardenwarden.DefaultPlantFragment;

public class PlantAddActivity extends AppCompatActivity implements DefaultPlantFragment.OnListFragmentInteractionListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
    }

    @Override
    public void onListFragmentInteraction(PlantDefault item) {

    }

    @Override
    public void onListFragmentLongClick(PlantDefault item) {

    }

    @Override
    public void onButtonClick(PlantDefault mItem) {

    }
}

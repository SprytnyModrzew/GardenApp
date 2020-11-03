package com.example.gardenwarden.form;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
import com.example.gardenwarden.device.DeviceFormFragment;
import com.example.gardenwarden.device.DeviceFragment;
import com.example.gardenwarden.ui.main.FormFragment;

public class PlantAddActivity extends AppCompatActivity implements
        DeviceFormFragment.OnListFragmentInteractionListener,
        DefaultPlantFragment.OnListFragmentInteractionListener,
        PlantSubDefaultFragment.OnListFragmentInteractionListener,
        FormFragment.OnButtonClicked {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
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
        //todo zamiast device'ów, jakieś nowe deviceAvailable zrobić
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FormFragment blankFragment = new FormFragment();
        fragmentTransaction.setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        );

        fragmentTransaction.replace(R.id.plant_add_fragment,blankFragment);
        fragmentTransaction.addToBackStack(null);

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

        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentLongClick(Device item) {

    }

    @Override
    public void onButtonClick(Device mItem) {

    }

    @Override
    public void onAddButtonClick() {
        Log.e("działa","auu");
    }
}

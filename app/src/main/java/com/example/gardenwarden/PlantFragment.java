package com.example.gardenwarden;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gardenwarden.form.DeviceDetailActivity;
import com.example.gardenwarden.form.PlantAddActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

public class PlantFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("ewo","ewo");
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ImageView imageView = view.findViewById(R.id.imageView2);
        String filename = "pippo.png";
        assert container != null;
        File sd = container.getContext().getApplicationContext().getFilesDir();
        File dest = new File(sd, filename);
        Log.e("eo",String.valueOf(dest));
        imageView.setImageBitmap(BitmapFactory.decodeFile(String.valueOf(dest)));

        return view;
    }
}

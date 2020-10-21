package com.example.gardenwarden;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gardenwarden.db.Device;
import com.example.gardenwarden.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A fragment representing a list of Items.
 */
public class DefaultPlantFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DefaultPlantFragment() {
    }

    public class PlantDefault{
        String name;
        String specimen;
        int defaultImage;

        public PlantDefault(String name, String specimen, int defaultImage) {
            this.name = name;
            this.specimen = specimen;
            this.defaultImage = defaultImage;
        }
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DefaultPlantFragment newInstance(int columnCount) {
        DefaultPlantFragment fragment = new DefaultPlantFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant_default_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            String filename = "data.json";
            File sd = context.getFilesDir();
            File dest = new File(sd, filename);
            List<PlantDefault> list = new ArrayList<>();
            try {
                String buffer = new Scanner(dest).useDelimiter("\\Z").next();
                Log.e("buf",buffer);
                JSONArray defaults_array = new JSONArray(buffer);
                for(int i = 0; i<defaults_array.length(); i++){
                    JSONObject object = defaults_array.getJSONObject(i);
                    list.add(new PlantDefault(object.get("name").toString(),object.get("species").toString(),object.getInt("default_image")));

                }
            } catch (FileNotFoundException | JSONException e) {
                e.printStackTrace();
            }
            recyclerView.setAdapter(new DefaultPlantRecyclerViewAdapter(list));
        }
        return view;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(PlantDefault item);

        void onListFragmentLongClick(PlantDefault item);

        void onButtonClick(PlantDefault mItem);
    }
}
package com.example.gardenwarden.recyclerViews.deviceForm;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gardenwarden.R;
import com.example.gardenwarden.db.device.Device;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class DeviceFormFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 2;

    public DeviceFormViewModel contactViewModel;
    public List<Device>contacts;

    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DeviceFormFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_list, container, false);
        recyclerView = (RecyclerView) view;
        // Set the adapter
        if (view != null) {
            contactViewModel = new ViewModelProvider(this).get(DeviceFormViewModel.class);
            contacts = contactViewModel.getDevices().getValue();
            Context context = view.getContext();
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new DeviceFormRecyclerViewAdapter(contacts, mListener));
            contactViewModel.getDevices().observe(getViewLifecycleOwner(), new Observer<List<Device>>() {
                @Override
                public void onChanged(List<Device> contacts) {
                    Log.i("userrr", "wooop");
                    recyclerView.setAdapter(new DeviceFormRecyclerViewAdapter(contacts, mListener));
                }
            });

        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Device item);

        void onListFragmentLongClick(Device item);

        void onButtonClick(Device mItem);
    }

}

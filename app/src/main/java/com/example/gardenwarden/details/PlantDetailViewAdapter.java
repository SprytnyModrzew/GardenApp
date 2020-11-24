package com.example.gardenwarden.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gardenwarden.R;

import java.util.List;

public class PlantDetailViewAdapter extends RecyclerView.Adapter<PlantDetailViewAdapter.ViewHolder> {

    private final List<Measurement> mValues;

    PlantDetailViewAdapter(List<Measurement> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public PlantDetailViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_device, parent, false);
        return new PlantDetailViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PlantDetailViewAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        String text = mValues.get(position).getType();
        holder.itemType.setText(text);
        String text2 = mValues.get(position).getValue();
        holder.itemValue.setText(text2);
        //Log.i("userrr", String.valueOf(getItemCount()));
        /*
        holder.mItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClick(holder.mItem);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        try{
            return mValues.size();
        }
        catch (NullPointerException e){
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView itemType;
        final TextView itemValue;
        Measurement mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            itemType = view.findViewById(R.id.measurement_type);
            itemValue = view.findViewById(R.id.measurement_value);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}


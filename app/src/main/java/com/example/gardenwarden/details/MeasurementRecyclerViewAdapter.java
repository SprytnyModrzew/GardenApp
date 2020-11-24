package com.example.gardenwarden.details;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gardenwarden.R;
import com.example.gardenwarden.details.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MeasurementRecyclerViewAdapter extends RecyclerView.Adapter<MeasurementRecyclerViewAdapter.ViewHolder> {

    private final List<Measurement> mValues;

    public MeasurementRecyclerViewAdapter(List<Measurement> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_plant_measurement_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getType());
        holder.mContentView.setText(mValues.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        try{
        return mValues.size();}
        catch(NullPointerException e){
            Log.e("woops","d");
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Measurement mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.measurement_type);
            mContentView = (TextView) view.findViewById(R.id.measurement_value);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
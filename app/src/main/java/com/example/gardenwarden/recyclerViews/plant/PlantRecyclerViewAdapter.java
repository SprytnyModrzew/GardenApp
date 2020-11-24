package com.example.gardenwarden.recyclerViews.plant;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gardenwarden.R;
import com.example.gardenwarden.db.plant.Plant;
import com.example.gardenwarden.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PlantRecyclerViewAdapter extends RecyclerView.Adapter<PlantRecyclerViewAdapter.ViewHolder> {

    private final List<Plant> mValues;
    private final PlantFragment.OnListFragmentInteractionListener mListener;

    PlantRecyclerViewAdapter(List<Plant> items, PlantFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public PlantRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_plant, parent, false);
        return new PlantRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PlantRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        String text = mValues.get(position).getName();
        holder.mItemText.setText(text);
        //Log.i("userrr", String.valueOf(getItemCount()));
        /*
        holder.mItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClick(holder.mItem);
            }
        });*/

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mListener.onListFragmentLongClick(holder.mItem);
                return false;
            }
        });
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
        final TextView mItemText;
        Plant mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mItemText = view.findViewById(R.id.plant_name);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}
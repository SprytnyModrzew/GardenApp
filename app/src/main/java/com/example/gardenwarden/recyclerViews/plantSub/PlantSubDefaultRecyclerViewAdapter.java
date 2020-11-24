package com.example.gardenwarden.recyclerViews.plantSub;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gardenwarden.R;
import com.example.gardenwarden.db.plantdefault.PlantDefaultCategory;
import com.example.gardenwarden.dummy.DummyContent.DummyItem;

import java.io.File;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PlantSubDefaultRecyclerViewAdapter extends RecyclerView.Adapter<PlantSubDefaultRecyclerViewAdapter.ViewHolder> {

    private final List<PlantDefaultCategory> mValues;
    private final PlantSubDefaultFragment.OnListFragmentInteractionListener mListener;
    private final File destination;

    public PlantSubDefaultRecyclerViewAdapter(List<PlantDefaultCategory> items, PlantSubDefaultFragment.OnListFragmentInteractionListener listener, File sd) {
        mValues = items;
        mListener = listener;
        destination = sd;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_sub_default, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        String text = mValues.get(position).getName();
        holder.mItemText.setText(text);
        //String dest = "default"+ holder.mItem.getDefaultImage() +".png";
       // File des = new File(destination,dest);
        //holder.mItemPic.setImageBitmap(BitmapFactory.decodeFile(String.valueOf(des)));

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
        //final ImageView mItemPic;
        PlantDefaultCategory mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mItemText = view.findViewById(R.id.sub_default_name);
            //mItemPic = view.findViewById(R.id.default_image);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}
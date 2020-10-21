package com.example.gardenwarden;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gardenwarden.dummy.DummyContent.DummyItem;

import java.io.File;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class DefaultPlantRecyclerViewAdapter extends RecyclerView.Adapter<DefaultPlantRecyclerViewAdapter.ViewHolder> {
    File filePath;

    private List<DefaultPlantFragment.PlantDefault> mValues;

    public DefaultPlantRecyclerViewAdapter(List<DefaultPlantFragment.PlantDefault> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_plant_default, parent, false);
        filePath = parent.getContext().getApplicationContext().getFilesDir();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).name);
        String filename = "default"+mValues.get(position).defaultImage+".png";

        File dest = new File(filePath, filename);
        Log.e("eo",String.valueOf(dest));
        holder.mImageView.setImageBitmap(BitmapFactory.decodeFile(String.valueOf(dest)));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final ImageView mImageView;
        public DefaultPlantFragment.PlantDefault mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.default_name);
            mImageView = (ImageView) view.findViewById(R.id.default_image);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
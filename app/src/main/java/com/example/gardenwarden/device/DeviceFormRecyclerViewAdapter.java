package com.example.gardenwarden.device;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gardenwarden.R;
import com.example.gardenwarden.db.DummyContent.DummyItem;
import com.example.gardenwarden.db.device.Device;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class DeviceFormRecyclerViewAdapter extends RecyclerView.Adapter<DeviceFormRecyclerViewAdapter.ViewHolder> {

    private final List<Device> mValues;
    private final DeviceFormFragment.OnListFragmentInteractionListener mListener;

    DeviceFormRecyclerViewAdapter(List<Device> items, DeviceFormFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_device, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        String text = mValues.get(position).getDeviceName();
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
        final ImageView mItemPic;
        Device mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mItemText = view.findViewById(R.id.device_name);
            mItemPic = view.findViewById(R.id.device_img);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}

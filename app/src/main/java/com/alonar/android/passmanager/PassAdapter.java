package com.alonar.android.passmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PassAdapter extends RecyclerView.Adapter<PassAdapter.PassAdapterViewHolder> {
    private ArrayList<String> mDataset;

    @NonNull
    @Override
    public PassAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.pass_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new PassAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PassAdapterViewHolder holder, int position) {
        holder.mPassNameTextView.setText(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class PassAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView mPassNameTextView;

        public PassAdapterViewHolder(View v) {
            super(v);
            mPassNameTextView = (TextView) v.findViewById(R.id.tv_pass_data);
        }


    }

    public PassAdapter(ArrayList<String> myDataset) {
        mDataset = myDataset;
    }



}

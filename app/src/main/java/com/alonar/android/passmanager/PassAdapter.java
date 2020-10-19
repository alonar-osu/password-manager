package com.alonar.android.passmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alonar.android.passmanager.utilities.DateConverter;

import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PassAdapter extends RecyclerView.Adapter<PassAdapter.PassAdapterViewHolder> {
    private ArrayList<PassEntry> mDataset;

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
        holder.nameTextView.setText(mDataset.get(position).getName());

        Date date = mDataset.get(position).getDate();
        String dateString = DateConverter.dateToString(date);
        holder.dateTextView.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class PassAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView nameTextView;
        public final TextView dateTextView;

        public PassAdapterViewHolder(View v) {
            super(v);
            nameTextView = (TextView) v.findViewById(R.id.tv_pass_name);
            dateTextView = (TextView) v.findViewById(R.id.tv_pass_date);
        }


    }

    public PassAdapter(ArrayList<PassEntry> myDataset) {
        mDataset = myDataset;
    }



}

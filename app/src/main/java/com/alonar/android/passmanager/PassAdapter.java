package com.alonar.android.passmanager;


import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.alonar.android.passmanager.data.PassEntry;
import com.alonar.android.passmanager.databinding.PassListItemBinding;
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

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PassListItemBinding itemBinding = PassListItemBinding.inflate(inflater, parent, false);

        return new PassAdapterViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PassAdapterViewHolder holder, int position) {

        PassEntry passEntry = mDataset.get(position);
        holder.bind(passEntry);

        holder.binding.tvPassName.setText(mDataset.get(position).getName());

        Date date = mDataset.get(position).getDate();
        String dateString = DateConverter.dateToString(date);
        holder.binding.tvPassDate.setText(dateString);
    }

    public static class PassAdapterViewHolder extends RecyclerView.ViewHolder {
        private PassListItemBinding binding;

        public PassAdapterViewHolder(PassListItemBinding binding) {
           super(binding.getRoot());
           this.binding = binding;
        }

        public void bind(PassEntry passEntry) {
            binding.setPassentry(passEntry);
            binding.executePendingBindings();
        }
    }

    public PassAdapter(ArrayList<PassEntry> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public int getItemCount() {
        return mDataset!= null ? mDataset.size() : 0;
    }

}

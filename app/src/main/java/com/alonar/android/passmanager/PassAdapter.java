package com.alonar.android.passmanager;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alonar.android.passmanager.data.PassEntry;
import com.alonar.android.passmanager.databinding.PassListItemBinding;
import com.alonar.android.passmanager.utilities.DateConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PassAdapter extends RecyclerView.Adapter<PassAdapter.PassAdapterViewHolder> {

    private static final String TAG = PassAdapter.class.getSimpleName();

    private ArrayList<PassEntry> mDataset;
    final private ItemClickListener mItemClickListener;

    public PassAdapter(ArrayList<PassEntry> myDataset, ItemClickListener listener) {
        mItemClickListener = listener;
        mDataset = myDataset;
    }

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

     class PassAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private PassListItemBinding binding;

        public PassAdapterViewHolder(PassListItemBinding binding) {
           super(binding.getRoot());
           this.binding = binding;
           binding.getRoot().setOnClickListener(this);
        }

        public void bind(PassEntry passEntry) {
            binding.setPassentry(passEntry);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            int elementId = mDataset.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    @Override
    public int getItemCount() {
        return mDataset!= null ? mDataset.size() : 0;
    }

    public List<PassEntry> getEntries() { return mDataset; }

}

package com.hpshekh.sqlitedatabase_sample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hpshekh.sqlitedatabase_sample.R;
import com.hpshekh.sqlitedatabase_sample.clickListener.ContactItemListener;
import com.hpshekh.sqlitedatabase_sample.databinding.ContactItemBinding;
import com.hpshekh.sqlitedatabase_sample.model.Contact;

import java.util.ArrayList;

public class ContactRVAdapter extends RecyclerView.Adapter<ContactRVAdapter.ViewHolder> {

    private final ArrayList<Contact> contactsList;
    private final ContactItemListener listener;

    public ContactRVAdapter(ArrayList<Contact> contactsList, ContactItemListener listener) {
        this.contactsList = contactsList;
        this.listener = listener;
    }

    public void UpdateList(ArrayList<Contact> contactsList) {
        this.contactsList.clear();
        this.contactsList.addAll(contactsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContactItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.contact_item,
                parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contactsList.get(position);
        holder.binding.nameText.setText(contact.getName());
        holder.binding.mobileText.setText(contact.getMobile());
        holder.binding.emailText.setText(contact.getEmail());

        holder.binding.container.setOnClickListener(view -> {
            listener.onItemClick(contactsList.get(position));
        });

        holder.binding.container.setOnLongClickListener(view -> {
            listener.onItemLongClick(contactsList.get(position));
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ContactItemBinding binding;

        public ViewHolder(@NonNull ContactItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}



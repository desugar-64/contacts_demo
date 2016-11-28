package com.sergeyfitis.contactsdemo;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sergeyfitis.contactsdemo.data.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergeyfitis on 28.11.16.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsViewHolder> {

    private final List<User> users = new ArrayList<>();
    private LayoutInflater inflater;

    public ContactsAdapter(@Nullable List<User> usersList) {
        updateAdapter(usersList);
    }

    public void updateAdapter(@Nullable List<User> usersList) {
        users.clear();
        if (usersList != null) {
            users.addAll(usersList);
        }
        notifyDataSetChanged();
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        return ContactsViewHolder.create(inflater, parent);
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}

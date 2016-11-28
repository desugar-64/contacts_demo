package com.sergeyfitis.contactsdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sergeyfitis.contactsdemo.data.User;
import com.squareup.picasso.Picasso;

/**
 * Created by sergeyfitis on 28.11.16.
 */

class ContactsViewHolder extends RecyclerView.ViewHolder {

    private TextView tvName;
    private TextView tvPhone;
    private ImageView ivAvatar;

    private final CircleTransform circleTransform = new CircleTransform();

    static ContactsViewHolder create(LayoutInflater inflater, ViewGroup parent) {
        return new ContactsViewHolder(inflater.inflate(R.layout.contact_item_layout, parent, false));
    }

    private ContactsViewHolder(View itemView) {
        super(itemView);
        tvName = (TextView) itemView.findViewById(R.id.tvContactName);
        tvPhone = (TextView) itemView.findViewById(R.id.tvContactPhone);
        ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
    }


    void bind(User user) {
        tvName.setText(user.getName());
        tvPhone.setText(user.getPhone());

        Picasso.with(ivAvatar.getContext())
                .load(user.getPhoto())
                .placeholder(R.drawable.oval)
                .error(R.drawable.oval)
                .transform(circleTransform)
                .into(ivAvatar);
    }
}

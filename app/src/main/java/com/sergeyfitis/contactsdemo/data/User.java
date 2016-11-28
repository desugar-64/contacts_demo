package com.sergeyfitis.contactsdemo.data;

import android.net.Uri;

/**
 * Created by sergeyfitis on 28.11.16.
 */

public class User {
    private String name;
    private String phone;
    private Uri photo;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", photo=" + photo +
                '}';
    }
}

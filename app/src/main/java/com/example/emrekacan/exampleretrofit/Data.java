package com.example.emrekacan.exampleretrofit;

import com.google.gson.annotations.SerializedName;

public class Data {


    @SerializedName("id")
    public Integer id;

    @SerializedName("first_name")
    public String firstName;

    @SerializedName("last_name")
    public String lastName;

    @SerializedName("avatar")
    public String avatar;

    @SerializedName("image_name")
    public String imageName;


    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAvatar() {
        return avatar;
    }
}

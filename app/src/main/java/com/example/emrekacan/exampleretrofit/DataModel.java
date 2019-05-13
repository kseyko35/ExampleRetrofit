package com.example.emrekacan.exampleretrofit;

public class DataModel {
    String first_name;
    String last_name;
    String avatar;
    String image_name;
    String id;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public DataModel(String name, String surname, String photo,String image_name) {
        this.first_name = name;
        this.last_name = surname;
        this.avatar = photo;
        this.image_name=image_name;
    }

    public String getName() {
        return first_name;
    }

    public void setName(String name) {

        this.first_name = name;
    }

    public String getSurname() {
        return last_name;
    }

    public void setSurname(String surname) {
        this.last_name = surname;
    }

    public String getPhoto() {
        return avatar;
    }

    public void setPhoto(String photo) {
        this.avatar = photo;
    }
}

package com.example.emrekacan.exampleretrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class VeriListem {

    @SerializedName("data")
    private List<Data> data=null;

    public List<Data> getData() {
        return data;
    }
}

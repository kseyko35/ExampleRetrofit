package com.example.emrekacan.exampleretrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @GET("data")
    Call<List<Data>> verilerimilistele();

    @GET("data")
    Call<VeriListem2> verilerimilistele2();

    @POST("data")
    Call<DataModel> veriEkle(@Body DataModel data);

    @DELETE("data/{id}")
    Call<DataModel> veriSil(@Path("id") int id);

}

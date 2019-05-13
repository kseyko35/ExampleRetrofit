package com.example.emrekacan.exampleretrofit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainList extends AppCompatActivity {

    private List<Data> dat;
    Data dat2;
    ListView listView;
    List<DataModel> users = new ArrayList<DataModel>();
    private StorageReference mStorageRef;
    String image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        listView=findViewById(R.id.listView);
        execute();
//        execute2();
    }

//    private void execute2() {
//        Gson gson=new GsonBuilder().setLenient().create();
//        Retrofit retrofit=new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
//        APIService apis=retrofit.create(APIService.class);
//        Call<VeriListem2> call=apis.verilerimilistele2();
//
//        call.enqueue(new Callback<VeriListem2>() {
//            @Override
//            public void onResponse(Call<VeriListem2> call, Response<VeriListem2> response) {
//                dat2=response.body().getData();
//                textView2.setText(dat2.avatar+dat2.firstName+dat2.lastName);
//            }
//
//            @Override
//            public void onFailure(Call<VeriListem2> call, Throwable t) {
//
//            }
//        });
//
//
//
//
//    }

    private void execute() {
        final Gson gson=new GsonBuilder().setLenient().create();
        final Retrofit retrofit=new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        final APIService apis=retrofit.create(APIService.class);
        Call<List<Data>> call=apis.verilerimilistele();

        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                dat=response.body();
                Log.d("seyfi", dat.toString());

                for (Data post:dat){

                    users.add(new DataModel(post.firstName,post.lastName,post.avatar,post.imageName));

                }
                final AdapterListView adapter=new AdapterListView(MainList.this,users);

                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        image=dat.get(position).avatar;
                        Intent intent=new Intent(getApplicationContext(),DetailsActivity.class);
                        intent.putExtra("image",image);
                        startActivity(intent);
                    }
                });

                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                        image=dat.get(position).avatar;
                        AlertDialog.Builder alert=new AlertDialog.Builder(MainList.this);
                        alert.setTitle("Attention!");
                        alert.setMessage("Are you sure to delete record ?");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deletePhoto(position);
                                users.remove(position);

                                adapter.notifyDataSetChanged();
                                Call<DataModel> call=apis.veriSil(dat.get(position).getId());
                                call.enqueue(new Callback<DataModel>() {
                                    @Override
                                    public void onResponse(Call<DataModel> call, Response<DataModel> response) {


                                        Log.d("seyfi" ," data silindi");
                                        adapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onFailure(Call<DataModel> call, Throwable t) {
                                        Log.d("seyfi" ," data silinmedi");
                                    }
                                });
                            }
                        });
                        alert.setNegativeButton(" No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alert.show();

                        return true;
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                Log.d("snow", t.getMessage().toString());
            }
        });

    }

    private void deletePhoto(final int position) {
        String image=users.get(position).getImage_name();
        Log.d("seyfi",image);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference desertRef = storageRef.child("upload/"+image);
        Log.d("seyfi",desertRef.toString());



        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("seyfi" ,"silindi");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("seyfi" ,"resim silinmedi");
            }
        });


       }
}

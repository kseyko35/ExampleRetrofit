package com.example.emrekacan.exampleretrofit;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button anotherButton, gsonActivityButton;
    ImageButton userPhoto;
    Context context;
    List<DataModel> users = new ArrayList<DataModel>();
    DataModel dat;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private static APIService service;
    private int PICK_IMAGE_REQUEST = 1;
    private int PICK_IMAGE = 1;
    Uri selectedImageUri;
    private StorageTask mUploadTask;
    @BindView(R.id.listButton) Button listButton;
    @BindView(R.id.createButton) Button createButton;
    @BindView(R.id.nameEdit)EditText nameEdit;
    @BindView(R.id.surnameEdit)EditText surnameEdit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);




 

        userPhoto = findViewById(R.id.userPhoto);
        anotherButton=findViewById(R.id.anotherButton);
        gsonActivityButton=findViewById(R.id.gsonActivity);
        mStorageRef = FirebaseStorage.getInstance().getReference("upload");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("upload");



        anotherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SharedRefExample.class);
                startActivity(intent);
            }
        });
        gsonActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GsonActivity.class);
                startActivity(intent);
            }
        });





        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);

            }
        });

    }
    @OnClick(R.id.listButton) void submit(){
        Intent intent = new Intent(this, MainList.class);
        startActivity(intent);
    }
    @OnClick(R.id.createButton) void create(){
        if (mUploadTask != null && mUploadTask.isInProgress()) {
            Toast.makeText(MainActivity.this, " Uploading...", Toast.LENGTH_SHORT).show();

        } else {
            uploadFile();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (selectedImageUri != null) {
            final String imageName=System.currentTimeMillis() + "." + getFileExtension(selectedImageUri);
            final StorageReference fileReference = mStorageRef.child(imageName);
            mUploadTask = fileReference.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 1);
                    uploadImage upload = new uploadImage(selectedImageUri.toString());
                    String uploadId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).setValue(upload);

                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUrl = uri;
                            if(nameEdit.getText().toString()!="" && surnameEdit.getText().toString()!=""&& downloadUrl.toString()!="") {
                                dat = new DataModel(nameEdit.getText().toString(), surnameEdit.getText().toString(), downloadUrl.toString(), imageName);
                            }
                            else Toast.makeText(context," fill all text", Toast.LENGTH_SHORT).show();
                        }
                    });

                    Gson gson = new GsonBuilder().setLenient().create();
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
                    APIService apis = retrofit.create(APIService.class);
                    Call<DataModel> call = apis.veriEkle(dat);

                    call.enqueue(new Callback<DataModel>() {
                        @Override
                        public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                            Toast.makeText(MainActivity.this, dat.getName() + dat.getSurname() + dat.getPhoto(), Toast.LENGTH_LONG)
                                    .show();

                        }

                        @Override
                        public void onFailure(Call<DataModel> call, Throwable t) {

                        }
                    });
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == PICK_IMAGE_REQUEST) {
                    selectedImageUri = data.getData();

                    Picasso.get().load(selectedImageUri).into(userPhoto);
                }
            }
        } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
        }
    }


}

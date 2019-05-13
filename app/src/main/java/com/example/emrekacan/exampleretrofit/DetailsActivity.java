package com.example.emrekacan.exampleretrofit;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ImageView imageView=findViewById(R.id.imageView2);
        Intent intent = getIntent();
        String name=intent.getStringExtra("image");
        Picasso.get().load(name).into(imageView);

    }
}

package com.example.emrekacan.exampleretrofit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SharedRefExample extends AppCompatActivity {
    Button getButton,setButton,anotherActivity;
    EditText nameEdit, surnameEdit;
    TextView nameText,surnameText;
    Switch backgroundColorChange;
    boolean value;
    RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_ref_example);
        unit();
        SharedPreferences preferences = getSharedPreferences(getPackageName()+".myFile",MODE_PRIVATE);
        value=preferences.getBoolean(Constant.KEY_COLOR,false);
        layout.setBackgroundColor(value ? Color.GREEN : Color.WHITE);
        backgroundColorChange.setChecked(value);

        backgroundColorChange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences preferences = getSharedPreferences(getPackageName()+".myFile",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putBoolean(Constant.KEY_COLOR,isChecked);
                layout.setBackgroundColor(isChecked ? Color.GREEN : Color.WHITE);
                value=isChecked;
                editor.apply();
            }
        });


        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences(getPackageName()+".myFile",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString(Constant.KEY_NAME,nameEdit.getText().toString());
                editor.putString(Constant.KEY_SURNAME,surnameEdit.getText().toString());
                editor.apply();
                Toast.makeText(v.getContext(),"Data is saved", Toast.LENGTH_SHORT).show();

            }
        });
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences=getSharedPreferences(getPackageName()+".myFile",MODE_PRIVATE);
                String name=sharedPreferences.getString(Constant.KEY_NAME,"test");
                String surname=sharedPreferences.getString(Constant.KEY_SURNAME,"test1");
                Boolean switchIs=sharedPreferences.getBoolean(Constant.KEY_COLOR,true);
                nameText.setText("Last name : "+ name);
                surnameText.setText("Last surname : "+surname + switchIs );


            }
        });
        anotherActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SeconSharedExample.class);
                startActivity(intent);
            }
        });
    }

    private void unit() {
        getButton=findViewById(R.id.button2);
        setButton=findViewById(R.id.button);
        anotherActivity=findViewById(R.id.newActivity);
        nameEdit=findViewById(R.id.nameEditBox);
        surnameEdit=findViewById(R.id.surnameEditBox);
        nameText=findViewById(R.id.nameTextView);
        surnameText=findViewById(R.id.surnameTextView);
        backgroundColorChange=findViewById(R.id.backgroundColorSwitch);
        layout=findViewById(R.id.mainLayout);
    }
}

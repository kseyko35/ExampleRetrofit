package com.example.emrekacan.exampleretrofit;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SeconSharedExample extends AppCompatActivity {
    Button getData2,deleteData,deleteWholeData,jobButton,getJobButton;
    TextView name,surname;
    RelativeLayout mainLayout;
    EditText jobEdit;
    boolean value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secon_shared_example);
        unit();
        final SharedPreferences sharedPreferences=getSharedPreferences(getPackageName()+".myFile",MODE_PRIVATE);
        value=sharedPreferences.getBoolean(Constant.KEY_COLOR,false);
        mainLayout.setBackgroundColor(value ? Color.GREEN : Color.WHITE);

        getData2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1=sharedPreferences.getString(Constant.KEY_NAME,"test3");
                String surname1=sharedPreferences.getString(Constant.KEY_SURNAME,"test13");
                Boolean switchIs=sharedPreferences.getBoolean(Constant.KEY_COLOR,true);
                name.setText("Last name: "+ name1);
                surname.setText("Last surname : "+ surname1 + "  " + switchIs);
            }
        });
        deleteWholeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().getSharedPreferences(getPackageName()+".myFile",0).edit().clear().commit();
                Toast.makeText(v.getContext(),"Whole shared references deleted ! " , Toast.LENGTH_SHORT).show();

            }
        });
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().getSharedPreferences(getPackageName()+".myFile",0).edit().remove(Constant.KEY_NAME).commit();
                Toast.makeText(v.getContext(),"Name deleted in shared references  ! " , Toast.LENGTH_SHORT).show();
            }
        });
        jobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences=getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString(Constant.KEY_JOB,jobEdit.getText().toString());


                editor.apply();
            }
        });
        getJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void unit() {
        getData2=findViewById(R.id.getData);
        deleteData=findViewById(R.id.deleteData);
        deleteWholeData=findViewById(R.id.deleteWholeData);
        name=findViewById(R.id.nameTextView2);
        surname=findViewById(R.id.surnameTextView2);
        mainLayout=findViewById(R.id.MainLayout);
        jobButton=findViewById(R.id.jobButton);
        jobEdit=findViewById(R.id.jobEdit);
        getJobButton=findViewById(R.id.jobGet);

    }

}

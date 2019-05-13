package com.example.emrekacan.exampleretrofit;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;

public class GsonActivity extends AppCompatActivity {
    Button gsonSetButton,gsonGetButton,genericGetButton,genericSetButton;
    TextView datatextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);
        unit();

        gsonSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmployeeConstant employe=employee();
                Gson gson=new Gson();
                SharedPreferences preferences=getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();

                editor.putString("EMPLOYEE_KEY",gson.toJson(employe));
                Log.d("seyfi",gson.toJson(employe));
                editor.apply();

            }
        });


        gsonGetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences=getPreferences(MODE_PRIVATE);
                Gson gson=new Gson();
                String json=preferences.getString("EMPLOYEE_KEY","");
                EmployeeConstant employe= gson.fromJson(json,EmployeeConstant.class);


                datatextView.setText("Name = " + employe.getName() + "Job = " + employe.getJob().get(0)) ;

            }
        });
        genericSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EmployeeConstant employe=employee();
                GenericEmploye<EmployeeConstant> wholeEmploye=new GenericEmploye<>();
                wholeEmploye.setObject(employe);

                SharedPreferences preferences=getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                Gson gson=new Gson();


                String gsonStr=gson.toJson(wholeEmploye,GenericEmploye.class);
                editor.putString("generic_type",gsonStr);
                Log.d("seyfi",gsonStr);
                editor.apply();
            }
        });
        genericGetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences=getPreferences(MODE_PRIVATE);
                String jsonStr=preferences.getString("generic_type","");
                Gson gson=new Gson();
                Log.d("seyfi",jsonStr);
                Type type=new TypeToken<GenericEmploye<EmployeeConstant>>(){}.getType();
                GenericEmploye<EmployeeConstant> calisan=gson.fromJson(jsonStr,type);
                String works="";
                for(int i=0;i<calisan.getObject().getJob().size();i++) {
                    works=works+calisan.getObject().getJob().get(i);
                    datatextView.setText(works);
                }
                }
        });

    }

    private void unit() {
        gsonGetButton=findViewById(R.id.gsonGetButton);
        gsonSetButton=findViewById(R.id.gsonSetButton);
        datatextView=findViewById(R.id.gsonTextView);
        genericGetButton=findViewById(R.id.genericGetButton);
        genericSetButton=findViewById(R.id.genericSetButton);
    }
    private EmployeeConstant employee(){
        EmployeeConstant employe=new EmployeeConstant();

        employe.setName("seyfi");
        employe.setAge(30);
        employe.setIswork(true);
        employe.setJob(Arrays.asList("Test Engineer" , "Android developer"));


        return employe;
    }
}

package com.example.emrekacan.exampleretrofit;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterListView extends BaseAdapter {

    private LayoutInflater inflater;
    private List<DataModel> dataModelList = null;
    Activity activity;
    Context context;
    public AdapterListView (Activity activity,List<DataModel> dataModelList){
        this.activity=activity;
        this.dataModelList=dataModelList;
        inflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;
        satirView=inflater.inflate(R.layout.list_item,null);
        TextView nameTextView=satirView.findViewById(R.id.nameTextView);
        TextView surnameTextView=satirView.findViewById(R.id.surnameTextView);
        ImageView imageView=satirView.findViewById(R.id.imageView);
        DataModel data =dataModelList.get(position);
        nameTextView.setText(data.first_name);
        surnameTextView.setText(data.last_name);
        Picasso.get().load(data.avatar).into(imageView);
//        Glide.with(context).load(data.photo).into(imageView);

        return satirView;
    }
}

package com.example.asus.loginapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Asus on 12/7/2017.
 */

public class ProductAdapter extends ArrayAdapter<ProductInfo> {
    public ProductAdapter(Activity context, ArrayList<ProductInfo> productInfos){
        super(context,0,productInfos);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        // Check for system has recyle view or not, if not, inflate the view
        if(listItem == null){
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_info,parent,false);
        }
        ProductInfo productInfo = getItem(position);
        TextView product_name = (TextView)listItem.findViewById(R.id.product_name);
        product_name.setText(productInfo.getProduct_name());
        TextView product_type = (TextView)listItem.findViewById(R.id.product_type);
        product_type.setText(productInfo.getProduct_type());
        TextView price = (TextView)listItem.findViewById(R.id.price);
        price.setText(productInfo.getPrice());

        return listItem;
    }
}

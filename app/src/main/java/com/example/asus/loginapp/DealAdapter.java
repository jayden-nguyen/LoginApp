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
 * Created by Asus on 12/14/2017.
 */

public class DealAdapter extends ArrayAdapter<DealInfo>{
    public DealAdapter(Activity context, ArrayList<DealInfo> dealInfos){
        super(context,0,dealInfos);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_history,parent,false);
        }
        DealInfo dealInfo = getItem(position);
        TextView product_name_history = (TextView)listItem.findViewById(R.id.product_name_history);
        product_name_history.setText(dealInfo.getProduct_name());
        TextView product_partner_history = (TextView)listItem.findViewById(R.id.product_partner_history);
        product_partner_history.setText(dealInfo.getPartner_name());
        TextView price_history = (TextView)listItem.findViewById(R.id.price_history);
        price_history.setText(dealInfo.getPrice());
        return listItem;
    }
}

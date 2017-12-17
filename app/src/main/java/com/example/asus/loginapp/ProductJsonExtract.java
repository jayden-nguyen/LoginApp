package com.example.asus.loginapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Asus on 12/7/2017.
 */

public class ProductJsonExtract {
    Context ctx;
    Activity activity;
   String json;

    public ProductJsonExtract(Context ctx){
        this.ctx = ctx;
        activity = (Activity)ctx;
        final SharedPrefManager sharedPrefManager = new SharedPrefManager(ctx);
        json = sharedPrefManager.getJsonProduct();

    }
    public void test() throws JSONException {
        final SharedPrefManager sharedPrefManager = new SharedPrefManager(ctx);
        json = sharedPrefManager.getJsonProduct();

    }
public ArrayList<ProductInfo> ProductExtract() throws JSONException {
    Log.d("this is json we have",json);
    Log.d("hello guy","begin extract");
    Log.d("this is json we have",json);
    ArrayList<ProductInfo> productInfos = new ArrayList<>();
    Log.d("this is json we have",json);
    // Extract Json
    JSONObject jsonObject = new JSONObject(json);

    JSONArray jsonArray = jsonObject.getJSONArray("server_response");
    for(int i = 0; i<jsonArray.length();i++){
        JSONObject product = jsonArray.getJSONObject(i);
        String product_name = product.getString("product_name");
        String product_type = product.getString("product_type");
        String price = product.getString("price");
        String partner_name = product.getString("partner_name");
        String partner_id = product.getString("partner_id");
        Log.d("partner_name extract",partner_name);
        productInfos.add(new ProductInfo(product_name,product_type,price,partner_name,partner_id));
    }
    return productInfos;
}

}

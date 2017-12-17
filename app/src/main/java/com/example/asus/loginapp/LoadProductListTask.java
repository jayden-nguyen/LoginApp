package com.example.asus.loginapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.asus.loginapp.Activities.ChatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Asus on 12/7/2017.
 */

public class LoadProductListTask extends AsyncTask<String,Void,String>{
    final String LoadUrl = "http://bugstore.000webhostapp.com/Product_list_return.php";
    Context ctx;
    Activity activity;
    ProgressDialog progressDialog;
    public LoadProductListTask(Context ctx){
        this.ctx = ctx;
        activity = (Activity)ctx;
    };
    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle("please wait");
        progressDialog.setMessage("Updating");
        progressDialog.show();
    }
    @Override
    protected String doInBackground(String... params) {
        try {
            Log.d("start load again","product list");
            URL url = new URL(LoadUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            //Read data
            //Get the inputStream to read from server
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            //Read data from server
            while((line=bufferedReader.readLine())!= null ){
                stringBuilder.append(line + "\n");
            }
            //disconnect the connection and return the json values
            httpURLConnection.disconnect();
            Thread.sleep(5000); Log.d("get product","list");

            return stringBuilder.toString().trim();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    protected void onPostExecute(String json) {
        try {
            progressDialog.dismiss();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
            ArrayList<ProductInfo> productInfos = new ArrayList<>();
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
            ListView productListView = (ListView) activity.findViewById(R.id.list_product_info);

            ProductAdapter productAdapter = new ProductAdapter(activity,productInfos);

            productListView.setAdapter(productAdapter);
            final ProductAdapter adapter = new ProductAdapter(activity,productInfos);

            productListView.setAdapter(adapter);

            productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ProductInfo productInfo = adapter.getItem(position);
                    Intent product_infomation = new Intent(ctx,ChatActivity.class);
                    product_infomation.putExtra("PRICE",productInfo.getPrice());
                    product_infomation.putExtra("PARTNER_NAME",productInfo.getPartner_name());
                    product_infomation.putExtra("PARTNER_ID",productInfo.getPartner_id());
                    product_infomation.putExtra("PRODUCT_NAME",productInfo.getProduct_name());
                    activity.startActivity(product_infomation);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}

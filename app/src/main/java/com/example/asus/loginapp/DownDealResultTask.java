package com.example.asus.loginapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Asus on 12/13/2017.
 */

public class DownDealResultTask extends AsyncTask<String,Void,String> {
    String downDealResult_url = "http://bugstore.000webhostapp.com/deal_list_return.php";
    Context ctx;
    Activity activity;
    ProgressDialog progressDialog;
    public DownDealResultTask(Context ctx){
        this.ctx = ctx;
        activity = (Activity) ctx;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle("please wait");
        progressDialog.setMessage(" Updating");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(downDealResult_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            //Set the outputStream to write to server
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            //
            final SharedPrefManager sharedPrefManager = new SharedPrefManager(ctx);
            String user_id =  sharedPrefManager.getUserId();
            String data = URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8");
            //Write and close the connection
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
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
            //  Thread.sleep(5000);
            return stringBuilder.toString().trim();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
            ArrayList<DealInfo> dealInfos = new ArrayList<>();
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject deal = jsonArray.getJSONObject(i);
                String deal_id = deal.getString("deal_id");
                String partner_name = deal.getString("partner_name");
                String price = deal.getString("price");
                String product_name = deal.getString("product_name");

                dealInfos.add(new DealInfo(deal_id,partner_name,price,product_name));
            }
            ListView history_list = (ListView)activity.findViewById(R.id.history_list);
            DealAdapter dealAdapter = new DealAdapter(activity,dealInfos);
            history_list.setAdapter(dealAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

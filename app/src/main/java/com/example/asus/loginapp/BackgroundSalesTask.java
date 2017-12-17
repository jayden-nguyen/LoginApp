package com.example.asus.loginapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.asus.loginapp.Activities.CreateContentActivity;
import com.example.asus.loginapp.Activities.HomeActivity;

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
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import static android.content.ContentValues.TAG;

/**
 * Created by Asus on 11/8/2017.
 */

public class BackgroundSalesTask extends AsyncTask<String,Void,String> {
    //Declare the URL
    String create_content_url = "http://bugstore.000webhostapp.com/Create_product.php";
    Context ctx;
    Activity activity;
    String getUserName;
    String getUserContent;
    String getMessage;
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;

    /**
     * Constructor
     * @param ctx
     */
    public BackgroundSalesTask(Context ctx){
        this.ctx = ctx;
        activity = (Activity)ctx;
    }
    @Override
    protected void onPreExecute() {
        builder = new AlertDialog.Builder(activity);
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle("please wait");
        progressDialog.setMessage("Connecting to server");
        /*progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);*/
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String method = params[0];
        if(method.equals("create")){
            try {
                // established the connection via HTTP
                URL url = new URL(create_content_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                //Set the outputStream to write to server
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String product_name = params[1];
                String product_type = params[2];
                String price = params[3];
                final SharedPrefManager sharedPrefManager = new SharedPrefManager(activity);
                String partner_name = sharedPrefManager.getUserName();
                String partner_id = sharedPrefManager.getUserId();
                Log.d(partner_id,partner_name);
                //construct data to write
                String data = URLEncoder.encode("product_name","UTF-8")+"="+URLEncoder.encode(product_name,"UTF-8")+"&"+
                        URLEncoder.encode("product_type","UTF-8")+"="+URLEncoder.encode(product_type,"UTF-8")+"&"+
                        URLEncoder.encode("partner_name","UTF-8")+"="+URLEncoder.encode(partner_name,"UTF-8")+"&"+
                        URLEncoder.encode("partner_id","UTF-8")+"="+URLEncoder.encode(partner_id,"UTF-8")+"&"+
                        URLEncoder.encode("price","UTF-8")+"="+URLEncoder.encode(price,"UTF-8");
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
             //   Thread.sleep(5000);
                Log.d("Did we done background","here");

                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String json) {
        // Process json
        try {
            Log.d("We gone to onpost",json);

            hideProgressDialog();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
            JSONObject JO = jsonArray.getJSONObject(0);
            getMessage = JO.getString("message");
            Log.d("message is ",getMessage);
            if(getMessage.equals("create success")){
                Log.d("Did we get there","right");
                builder.setTitle("Create success");
                builder.setMessage("Now you can watch your result");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(activity,HomeActivity.class);
                        activity.startActivity(intent);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }else{
                builder.setTitle("Create failed");
                builder.setMessage("do it again");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(activity,CreateContentActivity.class);
                        activity.startActivity(intent);
                    }
                });
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    /**
     * this method hide the Progress Dialog when the connection to server is established
     */
    private void hideProgressDialog() {
        try {

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                Log.d("Did it work","???");
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
        }
    }
}

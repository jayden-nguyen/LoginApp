package com.example.asus.loginapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import com.example.asus.loginapp.SharedPrefManager;
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

/**
 * Created by Asus on 12/13/2017.
 */

public class UploadDealResultTask extends AsyncTask<String,Void,String> {
    String updealresult_url = "http://bugstore.000webhostapp.com/UpDealResult.php";
    Context ctx;
    Activity activity;
    AlertDialog.Builder builder;


    public UploadDealResultTask(Context ctx){
        this.ctx = ctx;
        activity = (Activity)ctx;
    }
    @Override
    protected void onPreExecute() {
        builder = new AlertDialog.Builder(activity);
    }
    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(updealresult_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            //Set the outputStream to write to server
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            //
            String price = params[0];
            String partner_id = params[1];
            String partner_name = params[2];
            final SharedPrefManager sharedPrefManager = new SharedPrefManager(ctx);
            String user_id = sharedPrefManager.getUserId();
            String user_name = sharedPrefManager.getUserName();
            String product_name = params[3];
            String data = URLEncoder.encode("price","UTF-8")+"="+URLEncoder.encode(price,"UTF-8")+"&"+
                    URLEncoder.encode("partner_id","UTF-8")+"="+URLEncoder.encode(partner_id,"UTF-8")+"&"+
                    URLEncoder.encode("partner_name","UTF-8")+"="+URLEncoder.encode(partner_name,"UTF-8")+"&"+
                    URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8")+"&"+
                    URLEncoder.encode("product_name","UTF-8")+"="+URLEncoder.encode(product_name,"UTF-8")+"&"+
                    URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8");
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
            Log.d("this is updeal json",json);
            JSONObject jsonObject = new JSONObject(json);
            String message = jsonObject.getString("server_response");

                builder.setMessage(message);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

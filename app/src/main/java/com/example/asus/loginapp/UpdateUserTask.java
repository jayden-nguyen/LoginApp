package com.example.asus.loginapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.example.asus.loginapp.Activities.ProfileUserActivity;

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
 * Created by Asus on 12/12/2017.
 */

public class UpdateUserTask extends AsyncTask<String,Void,String> {
    String update_user_url = "http://bugstore.000webhostapp.com/Update_user_profile.php";
    Context ctx;
    Activity activity;
    AlertDialog.Builder builder;
    ProgressDialog progressDialog;
    public UpdateUserTask(Context ctx){
        this.ctx = ctx;
        activity = (Activity)ctx;
    }

    @Override
    protected void onPreExecute() {
        builder = new AlertDialog.Builder(activity);
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("please wait");
        progressDialog.setMessage("Updating");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(update_user_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            //Set the outputStream to write to server
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            final SharedPrefManager sharedPrefManager = new SharedPrefManager(ctx);
            String id = sharedPrefManager.getUserId();
            String user_name = params[0];
            String email = params[1];
            String password = params[2];
            String full_name = params[3];
            String age = params[4];
            String DOB = params[5];
            String gender = params[6];
            String rating = params[7];

            String data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&"+
                    URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"+
                    URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                    URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+
                    URLEncoder.encode("full_name","UTF-8")+"="+URLEncoder.encode(full_name,"UTF-8")+"&"+
                    URLEncoder.encode("age","UTF-8")+"="+URLEncoder.encode(age,"UTF-8")+"&"+
                    URLEncoder.encode("gender","UTF-8")+"="+URLEncoder.encode(gender,"UTF-8")+"&"+
                    URLEncoder.encode("DOB","UTF-8")+"="+URLEncoder.encode(DOB,"UTF-8")+"&"+
                    URLEncoder.encode("rating","UTF-8")+"="+URLEncoder.encode(rating,"UTF-8");
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
        return  null;
    }

    @Override
    protected void onPostExecute(String json) {
        try {
            progressDialog.dismiss();
            JSONObject jsonObject = new JSONObject(json);
            String message = jsonObject.getString("message");
            if(message.equals("update success")){
                builder.setMessage(message);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(activity, ProfileUserActivity.class);
                        activity.startActivity(intent);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }else{
                builder.setMessage(message);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        EditText update_user_name,update_email,update_password,update_DOB,update_rating,update_age,update_gender,update_full_name;
                        //Match
                        update_user_name = (EditText)activity.findViewById(R.id.update_user_name);
                        update_email = (EditText)activity.findViewById(R.id.update_email);
                        update_password = (EditText)activity.findViewById(R.id.update_password);
                        update_DOB = (EditText)activity.findViewById(R.id.update_DOB);
                        update_full_name = (EditText)activity.findViewById(R.id.update_fullname);
                        update_gender = (EditText)activity.findViewById(R.id.update_gender);
                        update_rating = (EditText)activity.findViewById(R.id.update_user_rating);
                        update_age = (EditText)activity.findViewById(R.id.update_user_age);
                        //
                        update_user_name.setText("");
                        update_age.setText("");
                        update_DOB.setText("");
                        update_gender.setText("");
                        update_email.setText("");
                        update_full_name.setText("");
                        update_rating.setText("");
                        update_password.setText("");
                    }
                });
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

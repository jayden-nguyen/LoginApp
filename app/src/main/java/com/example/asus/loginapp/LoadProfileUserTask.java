package com.example.asus.loginapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

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

/**
 * Created by Asus on 12/7/2017.
 */

public class LoadProfileUserTask extends AsyncTask<String,Void,String> {
String Load_profile_user_url = "http://bugstore.000webhostapp.com/User_profile_return.php";
    Context ctx;
    Activity activity;
    ProgressDialog progressDialog;
    public LoadProfileUserTask(Context ctx){
        this.ctx = ctx;
        activity = (Activity)ctx;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle("please wait");
        progressDialog.setMessage("UPDATING");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(Load_profile_user_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            //Open output Stream
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            //
            final SharedPrefManager sharedPrefManager = new SharedPrefManager(ctx);
            String id = sharedPrefManager.getUserId();
            String data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");

            //
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            //Open input Stream
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
            Thread.sleep(5000);
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
        Log.d("profile user json ",json);
        try {
            progressDialog.dismiss();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
            JSONObject user_info = jsonArray.getJSONObject(0);
            String user_name = user_info.getString("user_name");
            String age = user_info.getString("age");
            String email = user_info.getString("email");
            String fullname = user_info.getString("fullname");
            String gender = user_info.getString("gender");
            String DOB = user_info.getString("DOB");
            String password = user_info.getString("password");
            String rating = user_info.getString("rating");

            TextView profile_user_name = (TextView)(activity).findViewById(R.id.profile_user_name);
            profile_user_name.setText(user_name);
            TextView profile_password = (TextView)(activity).findViewById(R.id.profile_password);
            profile_password.setText(password);
            TextView profile_email = (TextView)(activity).findViewById(R.id.profile_email);
            profile_email.setText(email);
            TextView profile_fullname = (TextView)(activity).findViewById(R.id.profile_full_name);
            profile_fullname.setText(fullname);
            TextView profile_gender = (TextView)(activity).findViewById(R.id.profile_gender);
            profile_gender.setText(gender);
            TextView profile_rating = (TextView)(activity).findViewById(R.id.profile_rating);
            profile_rating.setText(rating);
            TextView profile_DOB = (TextView)(activity).findViewById(R.id.profile_birth);
            profile_DOB.setText(DOB);
            TextView profile_age = (TextView)(activity).findViewById(R.id.profile_age);
            profile_age.setText(age);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

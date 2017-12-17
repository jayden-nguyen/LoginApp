package com.example.asus.loginapp.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.asus.loginapp.BackgroundSalesTask;
import com.example.asus.loginapp.R;

public class CreateContentActivity extends AppCompatActivity {
    //Declare variables
    EditText product_name,product_type,price;
    Button create_content_btn;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_content);

        // Match the id with the variables
        product_name = (EditText)findViewById(R.id.product_name_detail);
        product_type = (EditText)findViewById(R.id.product_type_detail);
        price = (EditText) findViewById(R.id.price_detail);
        create_content_btn = (Button)findViewById(R.id.create_content_button);
        //Set on click button
        create_content_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(product_name.getText().toString().equals("")||product_type.getText().toString().equals("")||price.getText().toString().equals("")){
                    //Display error dialog
                    builder = new AlertDialog.Builder(CreateContentActivity.this);
                    builder.setTitle("Something went wrong");
                    builder.setMessage("Please fill all the fields");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else{
                    BackgroundSalesTask backgroundSalesTask = new BackgroundSalesTask(CreateContentActivity.this);
                    backgroundSalesTask.execute("create",product_name.getText().toString(),product_type.getText().toString(),price.getText().toString());

                }
            }
        });

    }
}

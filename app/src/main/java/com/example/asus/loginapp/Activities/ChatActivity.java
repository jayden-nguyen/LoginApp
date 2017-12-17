package com.example.asus.loginapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.loginapp.R;

public class ChatActivity extends AppCompatActivity {
    TextView price_current;
    Button confirm_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final String price = getIntent().getStringExtra("PRICE");
        final String partner_name = getIntent().getStringExtra("PARTNER_NAME");
        final String partner_id = getIntent().getStringExtra("PARTNER_ID");
        final String product_name = getIntent().getStringExtra("PRODUCT_NAME");


        price_current = (TextView) findViewById(R.id.price_current_product);
        price_current.setText("Price: "+price);
        confirm_btn = (Button)findViewById(R.id.confirm_button);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadDealResultTask uploadDealResultTask = new UploadDealResultTask(ChatActivity.this);
                uploadDealResultTask.execute(price,partner_id,partner_name,product_name);
            }
        });



    }
}

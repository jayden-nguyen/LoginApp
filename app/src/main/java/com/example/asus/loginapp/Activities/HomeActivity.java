package com.example.asus.loginapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.asus.loginapp.LoadProductListTask;
import com.example.asus.loginapp.R;
import com.example.asus.loginapp.SharedPrefManager;

public class HomeActivity extends AppCompatActivity {
    TextView home,profile,community;
    String user_name,user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final SharedPrefManager sharedPrefManager = new SharedPrefManager(HomeActivity.this);
        user_name = sharedPrefManager.getUserName();
        user_id = sharedPrefManager.getUserId();

        LoadProductListTask loadProductListTask = new LoadProductListTask(HomeActivity.this);
        loadProductListTask.execute();
       /* ArrayList<ProductInfo> productInfos = new ArrayList<>();
        Log.d("here are Home","After create arraylist");
        ProductJsonExtract productJsonExtract = new ProductJsonExtract(HomeActivity.this);
        try {
            productInfos = productJsonExtract.ProductExtract();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListView productListView = (ListView) findViewById(R.id.list_product_info);

        ProductAdapter productAdapter = new ProductAdapter(this,productInfos);

        productListView.setAdapter(productAdapter);

        final ProductAdapter adapter = new ProductAdapter(this,productInfos);

        productListView.setAdapter(adapter);

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductInfo productInfo = adapter.getItem(position);
                Intent product_infomation = new Intent(HomeActivity.this,ChatActivity.class);
                product_infomation.putExtra("PRICE",productInfo.getPrice());
                product_infomation.putExtra("PARTNER_NAME",productInfo.getPartner_name());
                product_infomation.putExtra("PARTNER_ID",productInfo.getPartner_id());
                product_infomation.putExtra("PRODUCT_NAME",productInfo.getProduct_name());
                startActivity(product_infomation);
            }
        });*/

        home = (TextView)findViewById(R.id.home);
        profile = (TextView)findViewById(R.id.profile);
        community = (TextView)findViewById(R.id.community);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ProfileUserActivity.class);
                startActivity(intent);
            }
        });
        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,MainCommunity.class);
                startActivity(intent);
            }
        });

    }
    public void updateInfo(View v){
        Intent intent = new Intent(HomeActivity.this, CreateContentActivity.class);
        startActivity(intent);
    }

}

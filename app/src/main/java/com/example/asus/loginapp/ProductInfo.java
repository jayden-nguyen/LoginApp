package com.example.asus.loginapp;

/**
 * Created by Asus on 12/7/2017.
 */

public class ProductInfo {
    String product_name,product_id,product_type,price,partner_name,partner_id;
    public ProductInfo(String product_name,String product_type,String price,String partner_name,String partner_id){
        this.product_name = product_name;
        this.product_type = product_type;
        this.price = price;
        this.partner_name = partner_name;
        this.partner_id = partner_id;
    }
    //setter method
    public void setProduct_name(String product_name){
        this.product_name = product_name;
    }
    public void setProduct_type(String product_type){
        this.product_type = product_type;
    }
    public void setPrice(String price){
        this.price = price;
    }
    public void setPartner_name(String partner_name){this.partner_name = partner_name;}
    public void setPartner_id(String partner_id){
        this.partner_id = partner_id;
    }
    //getter methods
    public String getProduct_name(){
        return  product_name;
    }
    public String getProduct_type(){
        return product_type;
    }
    public String getPrice(){
        return price;
    }
    public String getPartner_name(){
        return  partner_name;
    }
    public String getPartner_id(){
        return partner_id;
    }

}

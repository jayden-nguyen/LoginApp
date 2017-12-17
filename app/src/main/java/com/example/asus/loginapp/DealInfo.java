package com.example.asus.loginapp;

/**
 * Created by Asus on 12/13/2017.
 */

public class DealInfo {
    String deal_id,partner_name,price,product_name;

    public DealInfo(String deal_id,String partner_name,String price,String product_name){
        this.deal_id = deal_id;
        this.partner_name = partner_name;
        this.price = price;
        this.product_name = product_name;
    }
    //setter methods
    public void setDeal_id(String deal_id){
        this.deal_id = deal_id;
    }
    public void setPartner_name(String partner_name){
        this.partner_name = partner_name;
    }
    public void setPrice(String price){
        this.price = price;
    }
    public void setProduct_name(String product_name){
        this.product_name = product_name;
    }
    //getter methods
    public String getDeal_id(){
        return deal_id;
    }
    public String getPartner_name(){
        return partner_name;
    }
    public String getPrice(){
        return price;
    }
    public String getProduct_name(){
        return product_name;
    }
}

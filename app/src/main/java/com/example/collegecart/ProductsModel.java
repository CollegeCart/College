package com.example.collegecart;

import androidx.annotation.Keep;

public class ProductsModel {

    String Branch;
    @Keep
    String Subject;
    @Keep
    String Category;
    @Keep
    String Year;
    @Keep

    String Price;
    @Keep
    String userID;

    @Keep
    String imgUrl;
    @Keep
    String productname;
    @Keep
    String timestamp;
    @Keep
    String username;





    @Keep
    public ProductsModel() {



    }

    @Keep
    public ProductsModel(String nameproduct ,String branch, String subject, String year, String category , String url , String price, String userid , String Username , String time) {
        Branch = branch;
        Subject = subject;
        Year = year;
        imgUrl = url;
        Category = category;
        Price =  price;
        productname = nameproduct;

        userID = userid;
        timestamp = time;
        username = Username;
    }


    @Keep
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Keep
    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    @Keep
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Keep
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Keep
    public String getPrice() {
        return Price;
    }

    @Keep
    public void setPrice(String price) {
        Price = price;
    }






    @Keep
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }



    @Keep
    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    @Keep
    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    @Keep
    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }



    @Keep
    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }
}

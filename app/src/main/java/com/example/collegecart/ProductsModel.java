package com.example.collegecart;

public class ProductsModel {

    String Branch;
    String Subject;
    String Category;
    String Year;

    String Price;

    String imgUrl;





    private ProductsModel() {



    }

    public ProductsModel(String branch, String subject, String year, String category , String url , String price) {
        Branch = branch;
        Subject = subject;
        Year = year;
        imgUrl = url;
        Category = category;
        Price =  price;
    }


    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }






    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }



    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }



    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }
}

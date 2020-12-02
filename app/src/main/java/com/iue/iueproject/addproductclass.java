package com.iue.iueproject;

public class addproductclass {
    public   String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageproduct() {
        return imageproduct;
    }

    public void setImageproduct(String imageproduct) {
        this.imageproduct = imageproduct;
    }

    public String getNameproduct() {
        return nameproduct;
    }

    public void setNameproduct(String nameproduct) {
        this.nameproduct = nameproduct;
    }

    public String getPriceproduct() {
        return priceproduct;
    }

    public void setPriceproduct(String priceproduct) {
        this.priceproduct = priceproduct;
    }

    public String getSizeproduct() {
        return sizeproduct;
    }

    public void setSizeproduct(String sizeproduct) {
        this.sizeproduct = sizeproduct;
    }

    public String getProductdescription() {
        return productdescription;
    }

    public void setProductdescription(String productdescription) {
        this.productdescription = productdescription;
    }

    public   String imageproduct;
    public   String nameproduct;
    public   String priceproduct;
    public   String sizeproduct;
    public   String productdescription;

    public  addproductclass() {

    }
    addproductclass (
            String email,
            String imageproduct,
            String nameproduct,
            String priceproduct,
            String sizeproduct,
            String productdescription

    ){
        this.email=email;
        this.imageproduct=imageproduct;
        this.nameproduct=nameproduct;
        this.priceproduct=priceproduct;
        this.sizeproduct=sizeproduct;
        this.productdescription=productdescription;

    }



}

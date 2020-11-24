package com.iue.iueproject;

public class AdminRegclass  {
    public String uSerimageURL;
    public   String businessName;
    public   String userName;
    public   String email;
    public   String password;
    public   String telephonelumicash;
    public   String telephoneecocash;
    public   String nefID;
    public   String idpurmission;

    public  AdminRegclass() {

    }


    public String getuSerimageURL() {
        return uSerimageURL;
    }

    public void setuSerimageURL(String uSerimageURL) {
        this.uSerimageURL = uSerimageURL;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephonelumicash() {
        return telephonelumicash;
    }

    public void setTelephonelumicash(String telephonelumicash) {
        this.telephonelumicash = telephonelumicash;
    }

    public String getTelephoneecocash() {
        return telephoneecocash;
    }

    public void setTelephoneecocash(String telephoneecocash) {
        this.telephoneecocash = telephoneecocash;
    }

    public String getNefID() {
        return nefID;
    }

    public void setNefID(String nefID) {
        this.nefID = nefID;
    }

    public String getIdpurmission() {
        return idpurmission;
    }

    public void setIdpurmission(String idpurmission) {
        this.idpurmission = idpurmission;
    }

    public AdminRegclass(

            String USerimageURL,
            String BusinessName,
            String UserName,
            String email,
            String password,
            String telephonelumicash,
            String Telephoneecocash,
            String nefID,
            String IDpurmission
    ) {


        this.uSerimageURL=USerimageURL;
        this.businessName=BusinessName;
        this.userName=UserName;
        this.email=email;
        this.password=password;
        this.telephonelumicash=telephonelumicash;
        this.telephoneecocash=Telephoneecocash;
        this.nefID=nefID;
        this.idpurmission=IDpurmission;

    }

}

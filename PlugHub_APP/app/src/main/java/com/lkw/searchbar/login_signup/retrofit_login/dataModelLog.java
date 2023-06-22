package com.lkw.searchbar.login_signup.retrofit_login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class dataModelLog {

    @SerializedName("ID")
    @Expose
    private String username;

    @SerializedName("PW")
    @Expose
    private String password;

    public String getID() {
        return "ID";
    }

    public String getPW() {
        return "PW";
    }
}


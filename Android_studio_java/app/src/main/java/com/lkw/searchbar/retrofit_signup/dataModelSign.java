package com.lkw.searchbar.retrofit_signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class dataModelSign {

    @SerializedName("ID")
    @Expose
    private String username;

    @SerializedName("PW")
    @Expose
    private String password;

    @SerializedName("PW2")
    @Expose
    private String password2;

    public String getID() {
        return "ID";
    }

    public String getPW() {
        return "PW";
    }

    public String getPW2() {
        return "PW2";
    }


}


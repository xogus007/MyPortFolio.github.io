package com.lkw.searchbar.retrofit_login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("result")
    @Expose(serialize = true)
    private boolean result;

//    public boolean getResult() {
//        return result;
//    }
//
//    public void setResult(boolean result) {
//        this.result = result;
//    }
}

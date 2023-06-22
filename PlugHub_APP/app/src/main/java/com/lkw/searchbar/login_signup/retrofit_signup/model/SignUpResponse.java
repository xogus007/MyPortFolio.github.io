package com.lkw.searchbar.login_signup.retrofit_signup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpResponse {
    @SerializedName("result")
    @Expose(serialize = true)
    private boolean result;


}

package com.lkw.searchbar.login_signup.retrofit_login;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofitClientLogin {
    private static final String BASE_URL = "http://192.168.5.3/login_db";

    public static RetrofitInterfaceLog getApiService(){return getInstance().create(RetrofitInterfaceLog.class);}

    private static Retrofit getInstance(){
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }


}

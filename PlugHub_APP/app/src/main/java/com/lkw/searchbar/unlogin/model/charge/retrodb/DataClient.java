package com.lkw.searchbar.unlogin.model.charge.retrodb;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataClient {
    private static final String BASE_URL = "http://192.168.5.6:7852";
    private static Retrofit retrofit2;

    public static Retrofit getInstance(){
        if(retrofit2 == null){
            retrofit2 = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit2;
    }
}

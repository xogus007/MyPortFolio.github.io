package com.lkw.searchbar.retrofit_login;


import com.lkw.searchbar.retrofit_login.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitInterfaceLog {
    @POST("/auth_mobile/login_process_mobile")
    @FormUrlEncoded
    Call<LoginResponse> login(
        @Field(value = "id") String id,
        @Field(value = "pw") String pw);

}
package com.lkw.searchbar.retrofit_signup;


import com.lkw.searchbar.retrofit_signup.model.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitInterfaceSign {
    @POST("/auth_mobile/register_process_mobile")
    @FormUrlEncoded

    Call<SignUpResponse> signup(
        @Field(value = "id") String id,
        @Field(value = "pw") String pw,
        @Field(value = "pw2") String pw2);


}
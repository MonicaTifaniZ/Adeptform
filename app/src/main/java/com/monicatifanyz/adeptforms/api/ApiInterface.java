package com.monicatifanyz.adeptforms.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("user")
    @FormUrlEncoded
    Call<LoginResponse>loginUser(
            @Field("email") String email,
            @Field("password") String password
    );
}

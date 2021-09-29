package com.monicatifanyz.adeptforms.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("user/login")
        //login request ke model
    Call<ResponseBody> signIn(@Field("username") String username,
                              @Field("password") String password);
}

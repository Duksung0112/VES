package com.example.ves;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @GET("userinfo")
    Call<List<Post>> getUserinfo();

    /*@POST("/userinfo")
    Call<Post> postData(@FieldMap HashMap<String, Object> param);*/
}

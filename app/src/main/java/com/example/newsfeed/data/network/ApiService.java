package com.example.newsfeed.data.network;

import com.example.newsfeed.data.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("top-headlines")
    Call<ApiResponse> getResponse(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

}

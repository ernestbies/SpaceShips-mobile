package com.space.ships;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceholderAPI {

    @GET("api/newgame")
    Call<ServerResponse> newgame(@Query("user") String user);

    @GET("api/getgame")
    Call<ServerResponse> getgame(@Query("user") String user);

    @GET("api/shotgame")
    Call<ServerResponse> shotgame(@Query("user") String user, @Query("shot") String shot);

}

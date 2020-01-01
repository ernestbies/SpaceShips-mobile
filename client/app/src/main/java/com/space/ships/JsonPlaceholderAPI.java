package com.space.ships;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceholderAPI {

    @GET("api/newgame/{user}")
    Call<ServerResponse> newgame(@Path("user") String user);

    @GET("api/getgame/{user}")
    Call<ServerResponse> getgame(@Path("user") String user);

    @GET("api/shotgame")
    Call<ServerResponse> shotgame(@Query("user") String user, @Query("shot") String shot);

    @GET("api/getrank")
    Call<ServerResponse> getrank();
}

package com.space.ships;

import android.os.StrictMode;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerConnection {

    private JsonPlaceholderAPI jsonPlaceholderAPI;
//    private ServerResponse serverResponse;
//    String user = "example";
//    String serverUrl = "https://techify.tk/spaceships/";
    String user;
    String serverUrl;


    public ServerConnection(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    void createConnection(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(serverUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);
    }

    public ServerResponse getGame(){

        Call<ServerResponse> call = jsonPlaceholderAPI.getgame(user);

        try {
            MainActivity.serverResponse = call.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return MainActivity.serverResponse;
    }

    public ServerResponse shotGame(String shot){

        Call<ServerResponse> call = jsonPlaceholderAPI.shotgame(user,shot);

        try {
            MainActivity.serverResponse = call.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return MainActivity.serverResponse;
    }

    public ServerResponse newGame(){

        Call<ServerResponse> call = jsonPlaceholderAPI.newgame(user);

        try {
            MainActivity.serverResponse = call.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return MainActivity.serverResponse;
    }
}

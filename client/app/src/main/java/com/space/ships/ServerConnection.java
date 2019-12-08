package com.space.ships;

import android.os.StrictMode;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerConnection {

    private static JsonPlaceholderAPI jsonPlaceholderAPI;
    static ServerResponse serverResponse;
    static String user;
    private static String password;
    static String serverUrl;


    public ServerConnection(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    static void createConnection(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(serverUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);
    }

    public static ServerResponse getGame(){

        Call<ServerResponse> call = jsonPlaceholderAPI.getgame(user+password);

        try {
            serverResponse = call.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverResponse;
    }

    public static ServerResponse shotGame(String shot){

        Call<ServerResponse> call = jsonPlaceholderAPI.shotgame(user+password,shot);

        try {
            serverResponse = call.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverResponse;
    }

    public static ServerResponse newGame(){

        Call<ServerResponse> call = jsonPlaceholderAPI.newgame(user+password);

        try {
            serverResponse = call.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverResponse;
    }

    public static void setPassword(String pass){
        ServerConnection.password=pass;
    }
}

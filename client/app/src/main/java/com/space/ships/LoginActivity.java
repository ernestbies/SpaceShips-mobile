package com.space.ships;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    private EditText loginField, passwordField, serverField;
    private Switch rememberSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginField = findViewById(R.id.loginField);
        passwordField = findViewById(R.id.passwordField);
        serverField = findViewById(R.id.serverField);
        rememberSwitch = findViewById(R.id.rememberSwitch);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        loadData();
    }

    private void saveData(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USERNAME",String.valueOf(loginField.getText()));
        editor.putString("PASSWORD",String.valueOf(loginField.getText()));
        editor.putString("SERVER",String.valueOf(serverField.getText()));
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        loginField.setText(sharedPreferences.getString("USERNAME",""));
        passwordField.setText(sharedPreferences.getString("PASSWORD",""));
        serverField.setText(sharedPreferences.getString("SERVER",""));
    }

    public void loginToGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("USERNAME", String.valueOf(loginField.getText()));
        intent.putExtra("PASSWORD", md5(String.valueOf(passwordField.getText())));
        intent.putExtra("SERVER", String.valueOf(serverField.getText()));

        if(rememberSwitch.isChecked()){
            saveData();
        }
        if(loginField.getText().length() != 0 && passwordField.getText().length() != 0 && serverField.getText().length() != 0){
            ServerConnection.user = intent.getStringExtra("USERNAME");
            ServerConnection.setPassword(intent.getStringExtra("PASSWORD"));
            ServerConnection.serverUrl = intent.getStringExtra("SERVER");
            ServerConnection.createConnection();

            if(ServerConnection.getGame() == null) {
                Toast.makeText(this, "Błędny adres serwera", Toast.LENGTH_LONG).show();
            }else {
                startActivity(intent);
            }
        }else{
            Toast.makeText(this, "Błędne dane logowania", Toast.LENGTH_LONG).show();
        }
    }

    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            BigInteger md5Data = new BigInteger(1, md.digest(input.getBytes()));
            return String.format("%032X", md5Data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void openSettings(View v) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}

package com.space.ships;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.ScrollView;
import android.widget.TextView;

public class LogActivity extends AppCompatActivity {

    private TextView logText;
    private ScrollView scrollView;
    static String textContent="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);


        logText = findViewById(R.id.logText);
        scrollView = findViewById(R.id.scrollView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            logText.setText(Html.fromHtml(textContent,Html.FROM_HTML_MODE_LEGACY));
        }

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}

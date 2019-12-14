package com.space.ships;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class LogActivity extends AppCompatActivity {

    private ScrollView scrollView;
    static String textContent="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        TextView logText = findViewById(R.id.logText);
        scrollView = findViewById(R.id.scrollView);

        logText.setText(Html.fromHtml(textContent,Html.FROM_HTML_MODE_LEGACY));
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

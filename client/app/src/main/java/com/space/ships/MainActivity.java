package com.space.ships;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;


public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;
    private int panelSize;
    private int size;
    private String board;
    private Canvas canvas;
    private Paint paint;
    private Drawable mCustomImageA, mCustomImageB,mCustomImageC,mCustomImageD;
    private ServerConnection serverConnection = new ServerConnection();
    private ServerResponse serverResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION   |
                SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.imageView);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        init();
        addTouchListener();
//        draw();
    }

    private void addTouchListener(){
        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x=(int)Math.floor(event.getX()/size);
                int y=(int)Math.floor(event.getY()/size);
                shotGame(x,y);
                return false;
            }
        });
    }

    private void shotGame(int x, int y){
        serverResponse = serverConnection.shotGame(""+x+y);
        switch(serverResponse.getCode()){

            case "MISS":
                System.out.println("MISS");
                break;
            case "HIT":
                System.out.println("HIT");
                break;
            case "SHOTDOWN":
                System.out.println("SHOTDOWN");
                break;
            case "CHECKED":
                System.out.println("CHECKED");
                break;
            case "ENDGAME":
                System.out.println("ENDGAME");
                new AlertDialog.Builder(this)
                        .setTitle("ENDGAME")
                        .setMessage("Liczba kroków: "+serverResponse.getSteps())
                        .setPositiveButton("NEWGAME", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                newGame(null);
                            }
                        })
                        .setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
                                System.exit(1);
                            }
                        })
                        .show();
                break;
        }
        draw();
    }

    public void newGame(View v){
        serverResponse = serverConnection.newGame();
        draw();
    }


    private void init(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        board = "";
        for (int i =0;i<81;i++) {
            board+=" ";
        }
//	board="0 CCC2    45 3 C4CDDDD  C6C 3  1 C4C1 111 34 B32A11BB B3A 44  124 DDDD 01BB    10";

        panelSize = height-10;
        size = (panelSize/9);
        panelSize = 9 * size;

        mCustomImageA = mImageView.getResources().getDrawable(R.drawable.a);
        mCustomImageB = mImageView.getResources().getDrawable(R.drawable.b);
        mCustomImageC = mImageView.getResources().getDrawable(R.drawable.c);
        mCustomImageD = mImageView.getResources().getDrawable(R.drawable.d);
        serverResponse=serverConnection.getGame();
    }

    private void draw(){
        mImageView.requestLayout();
        mImageView.getLayoutParams().height = panelSize;
        mImageView.getLayoutParams().width = panelSize;
        int p;

        Bitmap bitmap = Bitmap.createBitmap(panelSize, panelSize, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        canvas.drawColor(Color.argb(150,68, 74, 88));
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1);
        paint.setTextSize(50);

        board=serverResponse.getBoard();
        android.widget.TextView tv = findViewById(R.id.shotsNumber);
        tv.setText(String.valueOf(serverResponse.getSteps()));

        for (int i=0;i<9;i++){
            canvas.drawLine(i*size+size, 0, i*size+size, panelSize, paint);
            canvas.drawLine(0, i*size+size, panelSize, i*size+size, paint);
        }


        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                p = y * 9 + x;
                if (board.charAt(p) >= '0' && board.charAt(p) <= '9') {
//                    canvas.drawLine(x*size, y*size, x*size + size, y*size + size,paint);
//                    canvas.drawLine(x*size, y*size + size, x*size + size, y*size,paint);
//                    canvas.drawText(Character.toString(board.charAt(p)),x*size + (int) (size/2), y*size + (int) (size/4),paint);
                    canvas.drawText(Character.toString(board.charAt(p)),x*size + (int) (size/2)-(paint.getTextSize()/4), y*size + (int) (size/2) + (paint.getTextSize()/4),paint);
                } else if (board.charAt(p) >= 'A' && board.charAt(p) <= 'Z'){
                    drawShip(board.charAt(p),x*size + 1, y*size + 1, (x+1)*size - 1, (y+1)*size - 1);
                }

            }

        mImageView.setImageBitmap(bitmap);


        }
    }

    private void drawShip(char c,int l, int t, int r, int b){

        switch(c){
            case 'A':
                mCustomImageA.setBounds(l,t,r,b);
                mCustomImageA.draw(canvas);
                break;
            case 'B':
                mCustomImageB.setBounds(l,t,r,b);
                mCustomImageB.draw(canvas);
                break;
            case 'C':
                mCustomImageC.setBounds(l,t,r,b);
                mCustomImageC.draw(canvas);
                break;
            case 'D':
                mCustomImageD.setBounds(l,t,r,b);
                mCustomImageD.draw(canvas);
                break;
        }


    }
}


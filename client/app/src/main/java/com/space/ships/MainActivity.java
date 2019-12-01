package com.space.ships;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION   |
                SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.imageView);

        init();
        addTouchListener();
        draw();
    }

    private void addTouchListener(){
        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x=(float)Math.floor(event.getX()/size);
                float y=(float)Math.floor(event.getY()/size);
                shotGame(x,y);
                return false;
            }
        });
    }

    private void shotGame(float x, float y){
        char[] bo = board.toCharArray();
        int pos=(int)(9*y+x);
        bo[pos] = 'B';
        board = String.valueOf(bo);
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
        board="0 CCC2    45 3 C4CDDDD  C6C 3  1 C4C1 111 34 B32A11BB B3A 44  124 DDDD 01BB    10";
        panelSize = height-10;
        size = (panelSize/9);
        panelSize = 9 * size;

        mCustomImageA = mImageView.getResources().getDrawable(R.drawable.a);
        mCustomImageB = mImageView.getResources().getDrawable(R.drawable.b);
        mCustomImageC = mImageView.getResources().getDrawable(R.drawable.c);
        mCustomImageD = mImageView.getResources().getDrawable(R.drawable.d);
    }

    private void draw(){
        mImageView.requestLayout();
        mImageView.getLayoutParams().height = panelSize;
        mImageView.getLayoutParams().width = panelSize;
        int p;

        Bitmap bitmap = Bitmap.createBitmap(panelSize, panelSize, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
//        canvas.drawColor(Color.DKGRAY);
        canvas.drawColor(Color.argb(55,68, 74, 88));
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1);

        for (int i=0;i<9;i++){
            canvas.drawLine(i*size+size, 0, i*size+size, panelSize, paint);
            canvas.drawLine(0, i*size+size, panelSize, i*size+size, paint);
        }


        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                p = y * 9 + x;
                if (board.charAt(p) >= '0' && board.charAt(p) <= '9') {
                    canvas.drawLine(x*size, y*size, x*size + size, y*size + size,paint);
                    canvas.drawLine(x*size, y*size + size, x*size + size, y*size,paint);
                    canvas.drawText(Character.toString(board.charAt(p)),x*size + (int) (size/2), y*size + (int) (size/4),paint);
                } else if (board.charAt(p) >= 'A' && board.charAt(p) <= 'Z'){
//                    image = ImageIO.read(new File("./images/"+Character.toString(board.charAt(p))+".jpg"));
//                    g.drawImage(image, x*size + 1, y*size + 1, size - 1, size - 1, null);
                    testDraw(board.charAt(p),x*size + 1, y*size + 1, (x+1)*size - 1, (y+1)*size - 1);
//                    testDraw2(x*size+1, y*size+1, size-1, size-1);
                }

            }

        mImageView.setImageBitmap(bitmap);


        }
    }

    private void testDraw(char c,int l, int t, int r, int b){

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


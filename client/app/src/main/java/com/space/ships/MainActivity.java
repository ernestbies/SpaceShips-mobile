package com.space.ships;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
        addTouchListner();
        draw();

    }

    private void addTouchListner(){
        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();
                String text = "You click at x = " + x + " and y = " + y;
                Log.d("Ships ",text);
                return false;
            }
        });
    }


    private void init(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        board = "";
        for (int i =0;i<81;i++) {
            board+=" ";
        }

        panelSize = height-10;
        size = (panelSize/9);
        panelSize = 9 * size;
    }

    private void draw(){



        mImageView.requestLayout();
        mImageView.getLayoutParams().height = panelSize;
        mImageView.getLayoutParams().width = panelSize;
        int p;

        Bitmap bitmap = Bitmap.createBitmap(panelSize, panelSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.DKGRAY);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1);

        for (int i=0;i<9;i++){
            canvas.drawLine(i*size+size, 0, i*size+size, panelSize, paint);
            canvas.drawLine(0, i*size+size, panelSize, i*size+size, paint);
        }

        /*
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                p = y * 9 + x;
                //canvas.drawLine(offset, canvas.getHeight() / 2, canvas.getWidth() - offset, canvas.getHeight() / 2, paint);

                if (board.charAt(p) >= '0' && board.charAt(p) <= '9') {
                    g.drawLine(x*size, y*size, x*size + size, y*size + size);
                    g.drawLine(x*size, y*size + size, x*size + size, y*size);
                    g.setColor(Color.WHITE);
                    g.drawString(Character.toString(board.charAt(p)), x*size + (int) (size/2), y*size + (int) (size/4));
                } else if (board.charAt(p) >= 'A' && board.charAt(p) <= 'Z'){
                    image = ImageIO.read(new File("./images/"+Character.toString(board.charAt(p))+".jpg"));
                    g.drawImage(image, x*size + 1, y*size + 1, size - 1, size - 1, null);

                }

            }
        */

        mImageView.setImageBitmap(bitmap);

    }

    /*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            String text = "You click at x = " + event.getX() + " and y = " + event.getY();
            Log.d("Ships ",text);
        }
        return super.onTouchEvent(event);
    }
    */

}


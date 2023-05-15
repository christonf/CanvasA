package com.example.canvasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Canvas mCanvas;
    private Paint mPaint = new Paint();
    private Paint mPaint2 = new Paint();
    private Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);
    private Bitmap mBitmap;
    private ImageView mImageView;
    private Rect mRect = new Rect();
    private Rect mBounds = new Rect();

    private static final int OFFSET = 120;
    //mengatur posisi objek
    private int mOffset = OFFSET;
    //mengatur warna agar berubah otomatis
    private static final int MULTIPLIER = 100;

    private int mColorBackground;
    private int mColorRectangle;
    private int mColorAccent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mColorBackground = ResourcesCompat.getColor(getResources(),
                R.color.colorBackground, null);
        mColorAccent = ResourcesCompat.getColor(getResources(),
                R.color.colorAccent, null);
        mColorRectangle = ResourcesCompat.getColor(getResources(),
                R.color.colorRectangle, null);

        mPaint.setColor(mColorBackground);
        mPaintText.setColor(ResourcesCompat.getColor(getResources(),
                R.color.black, null));
        mPaintText.setTextSize(70);

        mImageView = findViewById(R.id.myimageview);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawSomething(view);
            }
        });
    }

    public void drawSomething(View view) {
        int vWidth = view.getWidth();
        int vHeight = view.getHeight();
        int halfWidth = vWidth/2;
        int halfHeight = vHeight/2;

        if(mOffset == OFFSET) {
            mBitmap = Bitmap.createBitmap(vWidth, vHeight,
                    Bitmap.Config.ARGB_8888);
            mImageView.setImageBitmap(mBitmap);
            mCanvas = new Canvas(mBitmap);
            mCanvas.drawColor(mColorBackground);

            mCanvas.drawText(getString(R.string.keep_tapping), 100,
                    100, mPaintText);
            mOffset += OFFSET;
        }
        else{
            if(mOffset < halfWidth && mOffset < halfHeight) {
                mPaint.setColor(mColorRectangle - MULTIPLIER * mOffset);
                mRect.set(mOffset, mOffset, vWidth - mOffset,
                        vHeight - mOffset);
                mCanvas.drawRect(mRect, mPaint);
                mOffset += OFFSET;
            }
            else{
                mPaint.setColor(mColorAccent);
                mPaint2.setColor(mColorBackground);
                mCanvas.drawCircle(halfWidth, halfHeight, halfWidth/5, mPaint);

                Point a = new Point(halfWidth - 50, halfHeight + 30);
                Point b = new Point(halfWidth + 50, halfHeight + 30);
                Point c = new Point(halfWidth, halfHeight - 50);

                Path path = new Path();
                path.setFillType(Path.FillType.EVEN_ODD);
                path.lineTo(a.x, a.y);
                path.lineTo(b.x, b.y);
                path.lineTo(c.x, c.y);
                path.lineTo(a.x, a.y);


                mCanvas.drawPath(path, mPaint2);
//                String text = getString(R.string.done);
//                mPaintText.getTextBounds(text, 0, text.length(), mBounds);
//                int x = halfWidth - mBounds.centerX();
//                int y = halfHeight - mBounds.centerY();
//                mCanvas.drawText(text, x, y, mPaintText);


            }
        }
        view.invalidate();
    }
}
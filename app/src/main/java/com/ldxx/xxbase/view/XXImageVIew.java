package com.ldxx.xxbase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ldxx.xxbase.R;
import com.ldxx.xxbase.utils.XXLog;

/**
 * Created by WANGZHUO on 2015/4/16.
 */
public class XXImageVIew extends View {
    private Bitmap bitmap;
    private Paint drawablePaint;
    private float left;
    private float top;
    private float scale=1f;

    public XXImageVIew(Context context) {
        super(context);
        init(context, null, 0);
    }

    public XXImageVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public XXImageVIew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        drawablePaint = new Paint();
        TypedArray ta = null;
        try {
            ta = context.obtainStyledAttributes(attrs, R.styleable.XXImageView);
            int drawable = ta.getResourceId(R.styleable.XXImageView_src, 0);
            if (drawable != 0) {
                bitmap = BitmapFactory.decodeResource(context.getResources(), drawable);
            }
        } catch (Exception e) {
            XXLog.e(XXImageVIew.this.getClass().getSimpleName(), e.getMessage());
        } finally {
            if (ta != null) {
                ta.recycle();
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (bitmap == null)
            return;
        int w = getWidth();
        int h = getHeight();
        left = getPaddingLeft();
        top = getPaddingTop();
        int bottom = getPaddingBottom();
        int right = getPaddingRight();
        int dW = w - getPaddingLeft() - getPaddingRight();
        int dH = h - getPaddingBottom() - getPaddingTop();

        //1.先判断 图片是正方形
        if (bitmap.getWidth() == bitmap.getHeight()) {
            //取最小值
            int value = Math.min(dW, dH);
            scale = value * 1f / bitmap.getWidth();
        } else {
            //长方形
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.YELLOW);
        Matrix matrix = new Matrix();
        matrix.setScale(scale,scale);
        matrix.setTranslate(bitmap.getHeight()/2+getPaddingTop(),bitmap.getWidth()/2+getPaddingLeft());
        canvas.drawBitmap(bitmap, matrix, drawablePaint);
    }
}

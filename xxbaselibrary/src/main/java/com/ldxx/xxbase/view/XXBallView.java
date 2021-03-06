package com.ldxx.xxbase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.ldxx.xxbase.R;
import com.ldxx.xxbase.utils.XXDensityUtils;

/**
 * Balls
 */
public class XXBallView extends View {
    /**
     * ball type : red ball
     */
    public static final int BALL_TYPE_RED = 1;

    /**
     * ball type : blue ball
     */
    public static final int BALL_TYPE_BLUE = 2;

    /**
     * context
     */
    private Context context;

    /**
     * ball num
     */
    private String num;

    /**
     * ball is selectable
     */
    private boolean isSelectable = true;

    /**
     * ball type
     */
    private int ballType = BALL_TYPE_RED;

    /**
     * ball select event listener
     */
    private BallViewSelectedListener ballViewSelectedListener;


    /**
     *
     */
    private boolean isSelected = false;

    /**
     *
     */
    private int ballColor = Color.RED;

    private int textSize;


    private TextPaint mTextPaint;
    private Paint mBallPaint;

    private Rect mTextBounds;

    private float radius;
    private float x;
    private float y;

    public XXBallView(Context context) {
        super(context);
        this.context = context;
        init(null, 0);
    }

    public XXBallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs, 0);
    }

    public XXBallView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.XXBallView, defStyle, 0);

        num = a.getString(
                R.styleable.XXBallView_num);
        ballColor = a.getColor(
                R.styleable.XXBallView_ball_color,
                ballColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        textSize = (int) a.getDimension(
                R.styleable.XXBallView_text_size,
                XXDensityUtils.sp2px(context, 14));

        ballType = a.getInt(R.styleable.XXBallView_ball_type, BALL_TYPE_RED);
        isSelectable = a.getBoolean(R.styleable.XXBallView_selectable, false);
        isSelected = a.getBoolean(R.styleable.XXBallView_checked, false);


        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mTextBounds = new Rect();
        mTextPaint.getTextBounds(num, 0, num.length(), mTextBounds);

        if(ballType==BALL_TYPE_RED){
            ballColor =Color.RED;
        }else {
            ballColor = Color.BLUE;
        }

        mBallPaint = new Paint();
        mBallPaint.setColor(ballColor);
        mBallPaint.setAntiAlias(true);



        if (isSelected) {
            mTextPaint.setColor(Color.WHITE);
            mBallPaint.setStyle(Paint.Style.FILL);
        } else {
            mBallPaint.setStyle(Paint.Style.STROKE);
            mBallPaint.setStrokeWidth(4f);
            mTextPaint.setColor(ballColor);
        }

        // Update TextPaint and text measurements from attributes
        //invalidateTextPaintAndMeasurements();
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        radius = Math.min(getMeasuredHeight() - getPaddingBottom() - getPaddingTop(), getMeasuredWidth() - getPaddingLeft() - getPaddingRight()) / 2;
        x = getMeasuredWidth() / 2;
        y = getMeasuredHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(x, y, radius, mBallPaint);
        //canvas.drawText(num,x-mTextBounds.width()/2,y-mTextBounds.height()+getPaddingTop(),mTextPaint);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        // 计算文字高度
        float fontHeight = fontMetrics.bottom - fontMetrics.top;
        // 计算文字baseline
        float textBaseY = getMeasuredHeight() - (getMeasuredHeight() - fontHeight) / 2 - fontMetrics.bottom;
        canvas.drawText(num,x-mTextBounds.width()/2, textBaseY, mTextPaint);

    }


    public void setBallNum(String num) {
        num = num;
        //invalidateTextPaintAndMeasurements();
    }


    public interface BallViewSelectedListener {
        void OnBallViewSelected(XXBallView ballView, boolean isSelected);
    }
}

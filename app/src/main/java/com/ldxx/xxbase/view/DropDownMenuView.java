package com.ldxx.xxbase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListPopupWindow;

import com.ldxx.xxbase.R;
import com.ldxx.xxbase.utils.XXDensityUtils;
import com.ldxx.xxbase.utils.XXLog;
import com.ldxx.xxbase.utils.XXScreenUtils;
import com.ldxx.xxbase.view.adapter.DropDownMenuAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhuo on 2015/6/9.
 */
public class DropDownMenuView extends View {
    private String TAG = this.getClass().getSimpleName();

    //menu
    private String title;
    //
    private int titleTextSize;
    //
    private int titleTextColor;
    //
    private BitmapDrawable defualtArrowIcon;
    //
    private BitmapDrawable seletedArrowIcon;

    private Paint textPaint;

    private Rect iconRect;
    private Rect textBound;

    //menu
    private DropDownMenuAdapter adapter;

    private ListPopupWindow popupWindow;

    private List<DropDownMenuData> data = new ArrayList<>();

    private Context context;

    private boolean selected = false;

    public DropDownMenuView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public DropDownMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public DropDownMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.context = context;
        loadAttributes(attrs, defStyleAttr);
        initPaint();

        popupWindow = new ListPopupWindow(this.context);
        adapter = new DropDownMenuAdapter(this.context, data, R.layout.dropdownmenu_select_item);
        popupWindow.setAdapter(adapter);
        popupWindow.setWidth(XXScreenUtils.getScreenWidth(context));
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setModal(true);
        //set listener
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = !selected;
                invalidate();
                if (selected && adapter.getCount() > 0) {
                    popupWindow.setAnchorView(v);
                    popupWindow.show();
                } else {
                    popupWindow.dismiss();
                }
            }
        });
    }

    public void setData(List<DropDownMenuData> data) {
        this.data.clear();
        this.data.addAll(data);
        adapter.notifyDataSetChanged();
    }

    /**
     * ��ʼ������
     */
    private void initPaint() {
        textPaint = new Paint();
        textPaint.setColor(titleTextColor);
        textPaint.setTextSize(titleTextSize);
        textBound = new Rect();
        textPaint.getTextBounds(title, 0, title.length(), textBound);

        iconRect = new Rect();

    }

    private int drawableHeight;
    private int drawableWidth;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //��ȡ�������ֵ���ʼλ��
        drawableHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        drawableWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();

        XXLog.e(TAG, "drawableWidth:" + drawableWidth + " drawableHeight:" + drawableHeight + " textWith:" + textBound.width() + " textHeight:" + textBound.height());
    }

    @Override
    protected void onDraw(Canvas canvas) {

        float x = (drawableWidth - textBound.width()) * 1f / 2;
        float y = (drawableHeight - textBound.height()) * 1f / 2;
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;

        //canvas.drawCircle(x,y,3f,textPaint);
        canvas.drawColor(Color.YELLOW);
        canvas.drawText(title, getMeasuredWidth() / 2 - textBound.width() / 2, baseline, textPaint);
        // canvas.drawText(title, x, y, textPaint);
        if (selected) {
            if (seletedArrowIcon != null) {
                canvas.drawBitmap(seletedArrowIcon.getBitmap(), x + textBound.width() + 3, y, null);
            }
        } else {
            if (defualtArrowIcon != null) {
                canvas.drawBitmap(defualtArrowIcon.getBitmap(), x + textBound.width(), y, null);
            }
        }
    }

    /**
     * ��ȡxml�����õ�����ֵ
     *
     * @param attrs
     * @param defStyleAttr
     */
    private void loadAttributes(AttributeSet attrs, int defStyleAttr) {
        final TypedArray ta = this.context.obtainStyledAttributes(attrs, R.styleable.DropDownMenuView, defStyleAttr, 0);
        title = ta.getString(R.styleable.DropDownMenuView_title);
        titleTextSize = ta.getDimensionPixelSize(R.styleable.DropDownMenuView_title_text_size, XXDensityUtils.sp2px(this.context, 16f));
        titleTextColor = ta.getColor(R.styleable.DropDownMenuView_title_text_color, Color.BLACK);
        defualtArrowIcon = (BitmapDrawable) ta.getDrawable(R.styleable.DropDownMenuView_default_arrow_icon);
        seletedArrowIcon = (BitmapDrawable) ta.getDrawable(R.styleable.DropDownMenuView_selected_arrow_icon);

        ta.recycle();
    }


    public interface MenuSelectedListener {
        void onMenuSelecte(DropDownMenuData menuData);
    }


}

package com.ldxx.xxbase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;

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
    private DropDownMenuData checkted;

    private int selectedIndex = 0;
    //
    private int titleTextSize;
    //
    private int titleTextColor;
    //
    private BitmapDrawable defaultArrowIcon;
    //
    private BitmapDrawable selectedArrowIcon;

    private Paint textPaint;

    private Rect iconRect;
    private Rect textBound;
    private int menuColor;
    private int menuSelectedColor;

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
        initPoPWindow();
        //set listener
        addEventListener();

    }

    private void addEventListener() {
        //this menu onClick
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

        //pop windows dismiss
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                selected = false;
                invalidate();
            }
        });
        //pop item onClick
        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                checkted = data.get(position);
                selected = false;
                popupWindow.dismiss();
                invalidate();
            }
        });
    }

    private void initPoPWindow() {
        popupWindow = new ListPopupWindow(this.context);
        adapter = new DropDownMenuAdapter(this.context, data, R.layout.dropdownmenu_select_item);
        popupWindow.setAdapter(adapter);
        popupWindow.setWidth(XXScreenUtils.getScreenWidth(context));
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        Drawable drawable = context.getResources().getDrawable(R.drawable.dropdown_window_bg);
        popupWindow.setBackgroundDrawable(drawable);

        popupWindow.setModal(true);

    }

    public void setData(List<DropDownMenuData> data) {
        this.data.clear();
        this.data.addAll(data);
        adapter.notifyDataSetChanged();
        checkted = data.get(selectedIndex);
        invalidate();
    }

    /**
     *
     */
    private void initPaint() {
        textPaint = new Paint();
        textPaint.setColor(titleTextColor);
        textPaint.setTextSize(titleTextSize);
        //消除锯齿
        textPaint.setAntiAlias(true);

        textBound = new Rect();

        iconRect = new Rect();

    }

    private int drawableHeight;
    private int drawableWidth;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        drawableHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        drawableWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();

        XXLog.e(TAG, "drawableWidth:" + drawableWidth + " drawableHeight:" + drawableHeight + " textWith:" + textBound.width() + " textHeight:" + textBound.height());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (checkted != null) {
            textPaint.getTextBounds(checkted.getKey(), 0, checkted.getKey().length(), textBound);
        }
        float x = (drawableWidth - textBound.width()) * 1f / 2;
        float y = (drawableHeight - textBound.height()) * 1f / 2;
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;

        //canvas.drawCircle(x,y,3f,textPaint);
        //canvas.drawColor(Color.YELLOW);
        if(selected){
            canvas.drawColor(menuSelectedColor);
        }else {
            canvas.drawColor(menuColor);
        }
        if(checkted!=null){
            canvas.drawText(checkted.getKey(), getMeasuredWidth() / 2 - textBound.width() / 2, baseline, textPaint);
        }

        // canvas.drawText(title, x, y, textPaint);
        if (selected) {
            if (selectedArrowIcon != null) {
                canvas.drawBitmap(selectedArrowIcon.getBitmap(), x + textBound.width() + 3, y, null);
            }
        } else {
            if (defaultArrowIcon != null) {
                canvas.drawBitmap(defaultArrowIcon.getBitmap(), x + textBound.width(), y, null);
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
        selectedIndex = ta.getInt(R.styleable.DropDownMenuView_default_checked_index, 0);
        titleTextSize = ta.getDimensionPixelSize(R.styleable.DropDownMenuView_title_text_size, XXDensityUtils.sp2px(this.context, 16f));
        titleTextColor = ta.getColor(R.styleable.DropDownMenuView_title_text_color, Color.BLACK);
        defaultArrowIcon = (BitmapDrawable) ta.getDrawable(R.styleable.DropDownMenuView_default_arrow_icon);
        selectedArrowIcon = (BitmapDrawable) ta.getDrawable(R.styleable.DropDownMenuView_selected_arrow_icon);
        menuColor = ta.getColor(R.styleable.DropDownMenuView_menu_color, Color.WHITE);
        menuSelectedColor = ta.getColor(R.styleable.DropDownMenuView_menu_selected_color, Color.WHITE);
        ta.recycle();
    }


    public interface MenuSelectedListener {
        void onMenuSelecte(DropDownMenuData menuData);
    }

}

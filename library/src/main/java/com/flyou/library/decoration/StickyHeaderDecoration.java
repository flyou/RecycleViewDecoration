package com.flyou.library.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.flyou.library.bean.StickyHeadeBean;
import com.flyou.library.utils.DensityUtil;

import java.util.List;

/**
 * Created by fzl on 2017/08/22.
 * VersionCode: 1
 * Desc: 带步骤装饰的ItemDecoration
 */

public class StickyHeaderDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "HeaderDecoration";
    private List<StickyHeadeBean> dataList;
    private float driverHeight = 2f;
    private float offsetsLeft = 150f;
    private Paint paint;
    private Paint mTextPaint;
    private String headerText = "Header";
    private float textHeight = 0;
    private float textWidth = 0;


    public StickyHeaderDecoration(Context context, List<StickyHeadeBean> dataList) {
        this.dataList = dataList;
        this.paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        this.mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(DensityUtil.dip2px(context, 22));
        mTextPaint.setColor(Color.RED);

        textWidth = mTextPaint.measureText(headerText, 0, headerText.length());
        Rect rect = new Rect();
        mTextPaint.getTextBounds(headerText, 0, headerText.length(), rect);
//        textHeight= mTextPaint.descent()-mTextPaint.ascent();
        textHeight = rect.height();
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        super.getItemOffsets(outRect, view, parent, state);
        if (dataList.get(parent.getChildAdapterPosition(view)).isTeamFirst()) {
            outRect.set(0, (int) offsetsLeft, 0, 0);
        } else {
            outRect.set(0, (int) driverHeight, 0, 0);
        }
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);

        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);

            if (dataList.get(parent.getChildAdapterPosition(view)).isTeamFirst()) {

            } else {

                int bottom = view.getTop();
                float top = bottom - driverHeight;
                canvas.drawRect(left, top, right, bottom, paint);
            }


        }
    }

    private void drawHeader(Canvas canvas, int left, int right, int top, int bottom, View view) {

        canvas.drawRect(left, top, right, bottom, paint);
        int textX = (int) ((right - left) / 2 - textWidth / 2);
        int textY = (int) (bottom - offsetsLeft / 2 + textHeight / 2);
        Log.d(TAG, "onDraw: " + textY);
        canvas.drawText(headerText, textX, textY, mTextPaint);
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);


        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int childAdapterPosition = parent.getChildAdapterPosition(view);
            StickyHeadeBean stickyHeadeBean = dataList.get(childAdapterPosition);
            if (i != 0) {
                if (stickyHeadeBean.isTeamFirst()) {
                    int bottom = view.getTop();
                    float top = bottom - offsetsLeft;
                    drawHeader(canvas, left, right, (int) top, bottom, view);
                }
            } else {

                int top = view.getPaddingTop();

                if (stickyHeadeBean.isTeamLast()){
                    int suggestTop = (int) (view.getBottom() - offsetsLeft);
                    if (suggestTop<top){
                        top=suggestTop;
                    }

                }
                int bottom = (int) (top + offsetsLeft);
                drawHeader(canvas, left, right, top, bottom, view);

            }
        }
    }
}

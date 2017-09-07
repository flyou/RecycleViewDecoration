package com.flyou.library.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.flyou.library.utils.DensityUtil;


/**
 * Created by fzl on 2017/08/22.
 * VersionCode: 1
 * Desc: 带步骤装饰的ItemDecoration
 */

public class HeaderDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "HeaderDecoration";
    private float driverHeight = 2f;
    private float offsetsLeft = 150f;
    private Paint paint;
    private Paint mTextPaint;
    private String headerText = "Header";
    private float textHeight=0;
    private float textWidth=0;


    public HeaderDecoration(Context context) {

        this.paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        this.mTextPaint=new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(DensityUtil.dip2px(context,22));
        mTextPaint.setColor(Color.RED);

        textWidth=mTextPaint.measureText(headerText,0,headerText.length());
        Rect rect=new Rect();
        mTextPaint.getTextBounds(headerText,0,headerText.length(),rect);
//        textHeight= mTextPaint.descent()-mTextPaint.ascent();
        textHeight=rect.height();
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) % 4 == 0) {
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

            if (parent.getChildAdapterPosition(view) % 4 == 0) {
                int bottom = view.getTop();
                float top = bottom -offsetsLeft;
                canvas.drawRect(left,top,right,bottom,paint);
                int  textX= (int) ((right - left)/2 - textWidth/ 2);
                int  textY= (int) (bottom-offsetsLeft/2+textHeight/2);
                Log.d(TAG, "onDraw: "+textY);
                canvas.drawText(headerText,textX,textY,mTextPaint);
            } else {

                int bottom = view.getTop();
                float top = bottom -driverHeight;
                canvas.drawRect(left, top, right, bottom, paint);
            }


        }
    }
}

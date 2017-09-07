package com.flyou.library.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.flyou.library.utils.DensityUtil;


/**
 * Created by fzl on 2017/08/22.
 * VersionCode: 1
 * Desc: 带步骤装饰的ItemDecoration
 */

public class LableDecoration extends RecyclerView.ItemDecoration {
    private int maxLength = 0;
    private float driverHeight = 2f;
    private float offsetsLeft = 150f;
    private float circleRadius = 40f;
    private Paint paint;
    private Paint rankPaint;
    private Paint textPaint;

    public LableDecoration(Context context, int maxLength) {
        this.maxLength = maxLength;
        this.paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        rankPaint = new Paint();
        rankPaint.setColor(Color.RED);
        rankPaint.setAntiAlias(true);
        rankPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(DensityUtil.dip2px(context, 22));
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        super.getItemOffsets(outRect, view, parent, state);


        outRect.set((int) offsetsLeft, 0, 0, (int) driverHeight);

    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);

        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);

            int top = view.getBottom();
            float bottom = top + driverHeight;
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(left, top, right, bottom, paint);
            if (position < 3) {
                rankPaint.setColor(Color.RED);
            } else {
                rankPaint.setColor(Color.GRAY);
            }
            paint.setStrokeWidth(5);
            float circleX = left + offsetsLeft / 2;
            float circleY = view.getTop() + (view.getBottom() - view.getTop()) / 2;
            paint.setStrokeCap(Paint.Cap.ROUND);
            Rect bounds = new Rect();
            String rank = String.valueOf(position+1);
            textPaint.getTextBounds(rank, 0, rank.length(), bounds);
            int height = bounds.height();
            int width = (int) textPaint.measureText(rank, 0, rank.length());
            canvas.drawCircle(circleX, circleY, circleRadius, rankPaint);
            canvas.drawText(rank, circleX - width / 2, circleY + height / 2, textPaint);


        }


    }

}

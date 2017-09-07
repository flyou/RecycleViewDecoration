package com.flyou.library.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by fzl on 2017/08/22.
 * VersionCode: 1
 * Desc: 带步骤装饰的ItemDecoration
 */

public class StepLineDecoration extends RecyclerView.ItemDecoration {
    private float driverHeight=2f;
    private float offsetsLeft=200f;
    private float circleRadius=30f;
    private Paint paint;

    public StepLineDecoration() {
        this.paint=new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }



    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        super.getItemOffsets(outRect, view, parent, state);
            outRect.set((int) offsetsLeft,0,0, (int) driverHeight);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);

        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int top = view.getBottom();
            float bottom = top + driverHeight;
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(left,top,right,bottom,paint);

            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
            float circleX=left+offsetsLeft/2;
            float circleY=view.getTop()+(view.getBottom()-view.getTop())/2;

            float topLineStartX=circleX;
            float topLineStartY=view.getTop();

            float topLineEndX=circleX;
            float topLineEndY=circleY-circleRadius;

            float bottomLineStartX=circleX;
            float bottomLineStartY=circleY+circleRadius;

            float bottomLineEndX=circleX;
            float bottomLineEndY=view.getBottom();

            canvas.drawLine(topLineStartX,topLineStartY,topLineEndX,topLineEndY,paint);
            canvas.drawLine(bottomLineStartX,bottomLineStartY,bottomLineEndX,bottomLineEndY,paint);
            paint.setStrokeCap(Paint.Cap.ROUND);


            canvas.drawCircle(circleX,circleY,circleRadius,paint);

        }
    }
}

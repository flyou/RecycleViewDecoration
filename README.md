## 1 前言

其实本不打算写RecycleView相关的东西，因为周围一直围绕着RecycleView好用吗？RecycleView简单吗？RecycleView能实现这个功能？的疑问，以及在疑问中坚守ListView的守门员。所以，这个关（Kan）我（bu）什(xia)么(qu)事(le)。

> 阅读本文大概需要10分钟，当然也可能是10秒。

## 2 基本用法

以下就是RecycleView的基本用法

1. 在xml中引入RecycleView
2. 初始化控件
3. 设置LayoutManager
4. 设置‘分割线’（可选）
5. 设置Item动画（可选）
6. 设置Adapter
<center>
![](http://ww1.sinaimg.cn/large/0060lm7Tly1fj8os1jjejj308c08ct8r.jpg)
</center>

是的就是这个样子，不然你还想怎么样？我让我把别人都写过的东西再写一遍？别闹了，乖，我反手扔给你一个链接怎么样？

[Android RecyclerView 使用完全解析 体验艺术般的控件](http://blog.csdn.net/lmj623565791/article/details/45059587)

鸿洋大婶将的很好，我再写也不是没有必要，主要是我懒啊。。。


> 阅读本文之前，裂墙建议先阅读上述文章

## 3 ItemDecoration ≠ Split Line

是谁告诉你们Decoration是分割线的意思了？为什么一提到addItemDecoration（）你们就觉得是添加分割线呢？再看看网上的文章也都是分割线，为了科普下Decoration的真是意思我特地的Google了下
<center>
![](http://ww2.sinaimg.cn/large/0060lm7Tly1fj9ladixjfj30hq06dwen.jpg)
</center>
人家是装饰的意思有没有，并不是单单的分割线那么简单，造吗？这是知识点，知识点！！
<br/>
好吧，咱们还是去看看官网上的解释吧


> An ItemDecoration allows the application to add a special drawing and layout offset to specific item views from the adapter's data set. This can be useful for drawing dividers between items, highlights, visual grouping boundaries and more.
>
ItemDecoration允许应用程序从适配器的数据集中向特定项目视图添加特殊图形和布局偏移量。 这可以用于在项目，高亮度，视觉分组边界等之间绘制分隔符等等。

### 3.1 ItemDecoration基本流程

要先绘制自己的装饰器，我们需要了解下ItemDecoration的方法。
ItemDecoration包括三个方法。

> getItemOffsets(Rect outRect, View view, RecyclerView parent, State state)
<br/>
> onDraw(Canvas c, RecyclerView parent, State state)
<br/>
> onDrawOver(Canvas c, RecyclerView parent, State state)

**getItemOffsets()**<br/>
负责对RecycleView每一个Item上下左右偏移量的测量，什么意思也就是你的每个子View距离上下左右有多少间隔。


**onDraw()**<br/>
见名知意，就是对你想要装饰的绘制，canvas对象都给你了，你想画什么还不都是你的事？

**onDrawOver()**<br/>
与onDraw()类似都是负责装饰的绘制，但是这个方法可以绘制在你ItemView上面，具体什么意思，下面会具体介绍。

**注意**

decoration 的 onDraw，child view 的 onDraw，decoration 的 onDrawOver，这三者是依次发生的<br/>
在 onDraw绘制到 canvas 上，而这个绘制范围可以超出在 getItemOffsets 中设置的范围，但由于 decoration 是绘制在 child view 的底下，所以并不可见，但是会存在 overdraw<br/>
onDrawOver 是绘制在最上层的，所以它的绘制位置并不受限制
<center>

![](http://ww2.sinaimg.cn/large/0060lm7Tly1fj9p0l0f5fj30p00gojrq.jpg)
</center>
正如上文和上图所示，onDraw()的绘制区域是由getItemOffsets测量的结果决定，由于绘制的先后性，RecycleView ItemView 的onDraw方法会覆盖掉getItemOffsets测量以外的区域。由于onDrawOver在最后进行绘制，所以onDrawOver的绘制返回不受限制。

### 3.2 getItemOffsets()

首先我们先来具体看下getItemOffsets()，getItemOffsets会让你去决定上下左右的偏移尺寸，如下图所示。
<center>
![](http://ww4.sinaimg.cn/large/0060lm7Tly1fj9o0kragzj30p00gowen.jpg)
</center>

上下左右这个偏移的尺寸，而这个尺寸，被计入了 RecyclerView 每个 item view 的 padding 中。

为了验证上面的结论咱们写个例子死一死啊
<br/>
首先我们创建我们自己的Decoration继承ItemDecoration，先只覆写其中的getItemOffsets方法，然后使得ItemView的上下左右都有10像素的偏移。
    
    public class SimpleDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(10,10,10,10);
        }
    }


把我们自定义的ItemDecoration设置给RecycleView

    public class SimpleDecorationActivity extends AppCompatActivity {
    private RecyclerView recycleView;
    private List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_demo);
        for (int i = 1; i < 80; i++) {

            dataList.add("第" + i + "个");
        }
        this.recycleView = (RecyclerView) findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        SimpleDecoration headerDecoration = new SimpleDecoration();
        recycleView.addItemDecoration(headerDecoration);
        recycleView.setAdapter(new RecyclerAdapter(this, dataList));
    }
    }

来嘛，这就是见证奇迹的时刻，啊嘿
<center>

![](http://ww3.sinaimg.cn/large/0060lm7Tly1fj9pksf46oj30dc0npmyo.jpg)
</center>

纳尼？说好的效果呢？为啥我看不到？

那是因为ItemView的背景和activity的背景都是白色，来我们给activity布局加个背景颜色再试试
<center>

![](http://ww3.sinaimg.cn/large/0060lm7Tly1fjazxoa15oj30dc0np75e.jpg)
</center>

可以看到RecycleView的ItemView的上下左右都加上了间隔（同理，如果只设置下面的Offset，只有下面才会有背景颜色），这个颜色其实是背景的颜色，并不是我们绘制的装饰，因为我们还没调用任何的绘制函数。

**可以看到，getItemOffsets()其实就是对ItemView设置Padding，为OnDraw提供绘制控件**


### 3.3 onDraw()

熟悉了getItemOffsets()我们接下来来看看装饰的绘制

       @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {}

可以看到onDraw()中有三个参数，一个Canvas绘制的画布，一个RecyclerView和一个存储RecyclerView状态的State（recycle的滑动状态，位置等，暂时用不到）对象。


由于我们获得了Canvas对象，所以我们可以画任意我们想要的，那么就简单画一个分隔线吧

首先获得绘制范围：
     private float driverHeight=10f;
     private Paint paint;
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0,0,0,driverHeight);//只设置底部offset
        }


在构造方法中声明Paint对象

          public SimpleDecoration() {
        this.paint=new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }

在onDraw方法中为每个ItemView绘制分割线

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
            canvas.drawRect(left,top,right,bottom,paint);
        }
    }


**备注：**这里的onDraw绘制是针对于每个ItemView的绘制，需要你自己根据自己的需求完成绘制，而getItemOffsets里面设置的间隔是针对于每个Item，所以这里需要对每一个item View进行绘制。

其实，上面的代码很简单，就是在每一个Item的底部画了一个长方形而已。
<center>

![](http://ww3.sinaimg.cn/large/0060lm7Tly1fjb1l21r0ej30dc0np75g.jpg)
</center>

但是这个仅仅是简单的演示，因为这仅仅是针对竖向的LinearLayoutManager而言的，针对于横向的不行了，中间分割线的样式也不能随意定制，下面就贴出一个比较完善的分割线Decoration


    Public class DividerListItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;

    private int mOrientation;

    public DividerListItemDecoration(Context context, int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    public DividerListItemDecoration(Context context, int orientation, int drawableId) {
        mDivider = ContextCompat.getDrawable(context, drawableId);
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    //画线 > 就是画出你想要的分割线样式
    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }

    }


    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    //设置条目周边的偏移量
    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
    }

其实，代码依旧很简单，只不过让用户传入了布局的方向和要绘制的图案，当然可以实现和上面一样的效果，这里就不再贴图了。

[ListItemDecoration on Github](https://github.com/flyou/RecycleViewDecoration/blob/master/library/src/main/java/com/flyou/library/decoration/DividerListItemDecoration.java)

关于GridItemDecoration的写法和ListItemDecoration类似不再贴代码了，直接反手又给你了一个地址。

[GridItemDecoration on Github](https://github.com/flyou/RecycleViewDecoration/blob/master/library/src/main/java/com/flyou/library/decoration/DividerGridItemDecoration.java)


那么有人肯定要问了，逼逼了这么多，这不还是分割线吗？别急啊，下面就开始装逼，不对，不对，是说说其他的。

<center>
![](http://ww3.sinaimg.cn/large/0060lm7Tly1fj8qo1om43j306y06ywef.jpg)
</center>

上面我们已经提到了，onDraw方法给我们了Canvas对象，那么我们配合getItemOffsets真的是想画什么就画什么啊

比如，时间轴效果，物流流转效果。
<center>
![](http://ww1.sinaimg.cn/large/0060lm7Tly1fjb38utq79j30dc0npq41.jpg)
</center>

原谅我比较懒，原谅我放荡不羁爱自由，界面比较丑，大家可以充分发挥下想象力

怎么样？是是觉得惊艳了很多？
<center>

![](http://ww2.sinaimg.cn/large/0060lm7Tly1fj8qo1p2tyj30du0af74i.jpg)
</center>

好啦，好啦不闹了……说说怎么实现吧，那么你觉得应该怎么实现，先不说今天怎么做，就说常规的肯定是在ItemView的布局文件里实现，其实我想说布局复杂点的话布局文件真的不好写……
<br/>
但是，如果使用ItemDecoration就不一样了，完全不用考虑ItemView里面有什么，布局怎么样。
具体怎么做呢？咱们先看看下面的图：
<center>
![](http://ww3.sinaimg.cn/large/0060lm7Tly1fjb45irvgjj30p00gojrq.jpg)
</center>

可以看到左边是内容我们需要绘制的区域，右边是内容区域，我们主要是在左侧进行view的绘制就ok了，分隔线怎么实现上面已经讲过了就不在具体赘述了。

首先我们需要定义分割线的高度（offsetBottom），和左侧宽度（offsetsLeft）圆的半径以及画笔对象如下：


    private float driverHeight=2f;
    private float offsetsLeft=200f;
    private float circleRadius=30f;
    private Paint paint;



当然这些参数可以从构造函数或者其他方法传入，这里仅仅为了演示效果不再写了（对，还是懒……）。

构造方法初始化Paint对象

    public StepLineDecoration() {
        this.paint=new Paint();
        paint.setColor(Color.BLUE);//设置绘制颜色
        paint.setAntiAlias(true);//抗锯齿
        paint.setStyle(Paint.Style.FILL);//填充样式
    }


设置onDraw的绘制区域，设置itemView的左侧偏移和底部分割线的偏移

     @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        super.getItemOffsets(outRect, view, parent, state);
            outRect.set((int) offsetsLeft,0,0, (int) driverHeight);
    }

好的吧，接下来就是左侧和下部分割线的绘制

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
            canvas.drawRect(left,top,right,bottom,paint);//画分割线

            paint.setStrokeWidth(5);//设置竖线的宽度
            paint.setStyle(Paint.Style.STROKE);//填充类型空心
            float circleX=left+offsetsLeft/2;//圆心的x坐标
            float circleY=view.getTop()+(view.getBottom()-view.getTop())/2;//圆心的x坐标

            float topLineStartX=circleX;//上部线条起始X坐标
            float topLineStartY=view.getTop();//上部线条起始Y坐标

            float topLineEndX=circleX;//上部线条终止X坐标
            float topLineEndY=circleY-circleRadius;//上部线条终止Y坐标

            float bottomLineStartX=circleX;
            float bottomLineStartY=circleY+circleRadius;

            float bottomLineEndX=circleX;
            float bottomLineEndY=view.getBottom();

            paint.setStrokeCap(Paint.Cap.ROUND);//线条头类型
            canvas.drawLine(topLineStartX,topLineStartY,topLineEndX,topLineEndY,paint);//画上部线条
            canvas.drawLine(bottomLineStartX,bottomLineStartY,bottomLineEndX,bottomLineEndY,paint);//画底部线条
            canvas.drawCircle(circleX,circleY,circleRadius,paint);//画圆圈

        }
    }

绘制的流程也很简单，我们首先绘制底部的分割线，然后计算圆心位置线段的起始位置并绘制线段和圆。

同理，我们可以绘制出许多我们想要的效果，如排名效果：
<center>

![](http://ww4.sinaimg.cn/large/0060lm7Tly1fjb4vnj7ysj30dc0npgnf.jpg)
</center>

原理和上面类似，简单的额绘制而已，获取圆、测量文字、绘制文字。

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


### 3.4 onDrawOver()
上面已经介绍过了，onDrawOver可以超过getItemOffsets测量的绘制区域，也就是你说他可以覆盖在ItemView上。那么它有什么用？

> 其实用途也是很多的举个栗子吧还是，比如说ListView中常见的悬浮头效果，在ListView中常见的实现方式都是复写ondraw方法实现的在super.ondraw()方法下绘制上自己想要的头，实现起来可以说是非常的麻烦，但是如果在RecycleView中使用ItemDecoration可以很轻松的实现。
<center>
![](http://ww1.sinaimg.cn/large/0060lm7Tly1fjb887e4ipg30dc0npx6q.gif)
</center>

由于要实现浮选效果，onDraw方法自然就没办法满足了，使用onDrawOver方法可以很轻松实现这个效果。

定义针对于头的Bean
    public class StickyHeadeBean {
    private String title;
    private boolean isFirst;//列表第一个
    private boolean isTeamFirst;//是否是组内第一个
    private boolean isTeamLast;//是否是组内最后一个
    }
首先我们为绘制获得Offset

如果是组内第一个元素则设置topOffset为header的高度，否则则为分割线的高度

        @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        super.getItemOffsets(outRect, view, parent, state);
        if (dataList.get(parent.getChildAdapterPosition(view)).isTeamFirst()) {
            outRect.set(0, (int) offsetsLeft, 0, 0);
        } else {
            outRect.set(0, (int) driverHeight, 0, 0);
        }
    }


**onDraw**


在这里仅仅绘制分割线，头部布局在onDrawOver中统一绘制

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);

        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);

            if (dataList.get(parent.getChildAdapterPosition(view)).isTeamFirst()) {//头部在OndrawOver中绘制

            } else {
                int bottom = view.getTop();
                float top = bottom - driverHeight;
                canvas.drawRect(left, top, right, bottom, paint);
            }


        }
    }


**onDrawOver**

首先我们需要知道绘制头的条件：
1.屏幕内第一个可见的元素
2.如果它不是屏幕内第一个可见的元素，但是它是组内第一个可见的元素

代码如下：

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
            if (i != 0) {//不是第一个元素时，只有组内第一个才绘制
                if (stickyHeadeBean.isTeamFirst()) {
                    int bottom = view.getTop();
                    float top = bottom - offsetsLeft;
                    drawHeader(canvas, left, right, (int) top, bottom, view);
                }
            } else {
                    //第一个元素直接绘制
                int top = view.getPaddingTop();
                int bottom = (int) (top + offsetsLeft);
                drawHeader(canvas, left, right, top, bottom, view);

            }
        }
    }

绘制头部的代码：

      private void drawHeader(Canvas canvas, int left, int right, int top, int bottom, View view) {

        canvas.drawRect(left, top, right, bottom, paint);
        int textX = (int) ((right - left) / 2 - textWidth / 2);
        int textY = (int) (bottom - offsetsLeft / 2 + textHeight / 2);
        Log.d(TAG, "onDraw: " + textY);
        canvas.drawText(headerText, textX, textY, mTextPaint);
    }
 
写完代码，满心欢喜地运行下

嗯嗯，效果还不错但是，下一个头是直接盖在了上一个上面，效果并不是很优雅，所以我们需要判断元素是否是组内最后一个来把自己给推上去

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
                //判断是否为组内最后一个，如果是，则头的位置需要随View改变
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

加上上面的判断，再次运行就是我们想要的效果了


<center>
![](http://ww3.sinaimg.cn/large/0060lm7Tly1fjb9e651dcg30dc0np1ky.gif)
</center>


ItemDecoration其实很简单，但是确实可以完成很多炫酷的效果，我这么帅怎么肯能骗你？

照例贴上代码地址 [点我查看](https://github.com/flyou/RecycleViewDecoration "点位查看")

## 后记

写着，写着就写了好长，最后还是觉得分开几篇讲比较好。下篇我们看下LayoutManager

<center>
![](http://ww1.sinaimg.cn/large/0060lm7Tly1fj8qo1phnvj308c08ct8q.jpg)
</center>

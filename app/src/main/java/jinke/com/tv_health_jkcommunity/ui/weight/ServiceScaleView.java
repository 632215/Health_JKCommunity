package jinke.com.tv_health_jkcommunity.ui.weight;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

import jinke.com.tv_health_jkcommunity.R;

public class ServiceScaleView extends View {
    private Paint mPaint;
    private static int arrow;
    private static List<Integer> indexList = new ArrayList<>();
    private static int index;

    public ServiceScaleView(Context context) {
        super(context);
        init();
    }

    public ServiceScaleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ServiceScaleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(R.color.white));
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }

    static int width, height;
    static float xWidth;
    static float tempWidth;
    static float totalIndexImageWidth;
    static float totalArrowImageWidth;

    static float totalImageHeight;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        xWidth = 0;
        tempWidth = 0;
        for (int x = 0; x < index; x++) {
            if (x == index - 1) {
                tempWidth += BitmapFactory.decodeResource(getResources(), indexList.get(x)).getWidth() / 2;
                break;
            } else {
                tempWidth += BitmapFactory.decodeResource(getResources(), indexList.get(x)).getWidth();
            }
        }
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), arrow), (tempWidth-2*BitmapFactory.decodeResource(getResources(), arrow).getWidth())*width/totalIndexImageWidth, 0, mPaint);

        for (int x = 0; x < this.indexList.size(); x++) {
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), indexList.get(x)), x + xWidth * width / totalIndexImageWidth, (10 + BitmapFactory.decodeResource(getResources(), arrow).getHeight()), mPaint);
            xWidth = xWidth + BitmapFactory.decodeResource(getResources(), indexList.get(x)).getWidth();
        }

    }


    private float arcAngle;

    public void initAnimator(float width) {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, width);
        animator.setDuration(3000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                arcAngle = Math.round((float) animation.getAnimatedValue());
                invalidate();
            }
        });
    }

    public void setList(int arrow, List<Integer> indexList, int index) {
        this.indexList.clear();
        this.arrow = arrow;
        this.indexList = indexList;
        this.index = index;
        totalIndexImageWidth = 0;
        totalArrowImageWidth = 0;
        for (int bitmapId : indexList) {
            totalIndexImageWidth += BitmapFactory.decodeResource(getResources(), bitmapId).getWidth();
        }
        totalArrowImageWidth = BitmapFactory.decodeResource(getResources(), arrow).getWidth();
        invalidate();
    }

}

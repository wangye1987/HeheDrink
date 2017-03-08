package com.heheys.ec.animationManage;


import com.heheys.ec.utils.LogUtil;

import android.graphics.Matrix;
import android.os.Build.VERSION;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ParabolaAnimation extends Animation {

    private int mFromXType = ABSOLUTE;
    private int mToXType = ABSOLUTE;
    private int mFromYType = ABSOLUTE;
    private int mToYType = ABSOLUTE;
    private float mFromXValue = 0.0f;
    private float mToXValue = 0.0f;

    private float mFromYValue = 0.0f;
    private float mToYValue = 0.0f;
    private float mFromXDelta;
    private float mToXDelta;
    private float mFromYDelta;
    private float mToYDelta;
    /**
     * 二次坐标顶点Y比起始点高的像素
     */
    private float topX = 150;
    private double a, b, c;
    private float endX;
    private float endY;
    /**
     * 缩放轴 x y 表示中心位置
     */
    private float pivotValue = 0.5f;
    private float fromXY = 1.0f;
    private float toXY = 0.2f;
    private float mPivotX;
    private float mPivotY;
    private float mFromX;
    private float mToX;
    private float mFromY;
    private float mToY;

    public ParabolaAnimation(float fromXDelta, float toXDelta,
            float fromYDelta, float toYDelta, float topX) {
        mFromXValue = fromXDelta;
        mToXValue = toXDelta;
        mFromYValue = fromYDelta;
        mToYValue = toYDelta;
        mFromXType = ABSOLUTE;
        mToXType = ABSOLUTE;
        mFromYType = ABSOLUTE;
        mToYType = ABSOLUTE;
        this.topX = topX;
    }

    @Override
    public void initialize(int width, int height, int parentWidth,
            int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mFromXDelta = resolveSize(mFromXType, mFromXValue, width, parentWidth);
        mToXDelta = resolveSize(mToXType, mToXValue, width, parentWidth);
        mFromYDelta = resolveSize(mFromYType, mFromYValue, height, parentHeight);
        mToYDelta = resolveSize(mToYType, mToYValue, height, parentHeight);
        endX = mToXDelta - mFromXDelta;
        endY = mToYDelta - mFromYDelta;

        /**
         * 以下处理缩放值
         */
        mPivotX = resolveSize(Animation.RELATIVE_TO_SELF, pivotValue, width,
                parentWidth);
        mPivotY = resolveSize(Animation.RELATIVE_TO_SELF, pivotValue, height,
                parentHeight);
        mFromX = resolveScale(fromXY, mFromXType, 0, width, parentWidth);
        mToX = resolveScale(toXY, mToXType, 0, width, parentWidth);
        mFromY = resolveScale(fromXY, mFromYType, 0, height, parentHeight);
        mToY = resolveScale(toXY, mToYType, 0, height, parentHeight);
        initData();
    }

    private void initData() {
        if (endY == 0) {
            this.a = Math.abs((mFromYDelta - 200)
                    / ((endX / 2 - 0) * (endX / 2 - endX)));
        } else {
            this.b = topX;
            this.a = Math.abs(endY / (endX * endX - 2 * this.b * endX));
            this.c = 0 - a * this.b * this.b;
        }

    }

    private float resolveScale(float scale, int type, int data, int size,
            int psize) {
        float targetSize;
        if (type == TypedValue.TYPE_FRACTION) {
            targetSize = TypedValue.complexToFraction(data, size, psize);
        } else if (type == TypedValue.TYPE_DIMENSION) {
            targetSize = scale;
        } else {
            return scale;
        }
        if (size == 0) {
            return 1;
        }
        return targetSize / (float) size;

    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        Matrix m = t.getMatrix();
        // 以下部分是处理缩放
        float sx = 1.0f;
        float sy = 1.0f;
        if (VERSION.SDK_INT < 11) {
            if (mFromX != 1.0f || mToX != 1.0f) {
                sx = mFromX + ((mToX - mFromX) * interpolatedTime);
            }
            if (mFromY != 1.0f || mToY != 1.0f) {
                sy = mFromY + ((mToY - mFromY) * interpolatedTime);
            }
            if (mPivotX == 0 && mPivotY == 0) {
                m.setScale(sx, sy);
            } else {
                m.setScale(sx, sy, mPivotX, mPivotY);
            }
        } else {
            float scale = getScaleFactor();
            if (mFromX != 1.0f || mToX != 1.0f) {
                sx = mFromX + ((mToX - mFromX) * interpolatedTime);
            }
            if (mFromY != 1.0f || mToY != 1.0f) {
                sy = mFromY + ((mToY - mFromY) * interpolatedTime);
            }
            if (mPivotX == 0 && mPivotY == 0) {
                m.setScale(sx, sy);
            } else {
                m.setScale(sx, sy, scale * mPivotX, scale * mPivotY);
            }
        }
        float dx = mFromXDelta;
        float dy = mFromYDelta;
        if (mFromXDelta != mToXDelta) {
            dx = mFromXDelta + (endX * interpolatedTime);
        }
        if (mFromYDelta != mToYDelta) {
            dy = mFromYDelta + getDY(dx);
        } else {
            dy = (float) (mFromYDelta + (this.a * dx * (dx - endX)));
        }
        m.postTranslate(dx, dy);
        LogUtil.d("Translate", "x==" + dx + "==y==" + dy);
    }

    private float getDY(float dx) {

        float dy = 0;
        if (dx == 0) {
            dy = 0;
        } else {
            dy = (float) (this.a * (dx - this.b) * (dx - this.b) + this.c);
        }
        return dy;
    }
}

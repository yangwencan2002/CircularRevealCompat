package com.vincan.circularrevealcompat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Root layout for ViewAnimationCompatUtils.
 *
 * @author vincanyang
 */
public class CircularRevealCompatLayout extends FrameLayout {

    private float mCenterX;

    private float mCenterY;

    private float mRadius;

    private final Path mClipPath = new Path();

    private boolean mEnableClip;

    public CircularRevealCompatLayout(Context context) {
        this(context, null);
    }

    public CircularRevealCompatLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularRevealCompatLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (!mEnableClip || mClipPath.isEmpty()) {
            super.dispatchDraw(canvas);
            return;
        }
        canvas.save();
        canvas.clipPath(mClipPath);
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    public void setRevealClip(float centerX, float centerY, float radius) {
        mCenterX = centerX;
        mCenterY = centerY;
        mRadius = radius;
        float x1 = centerX;
        float x2 = getWidth() - centerX;
        float y1 = centerY;
        float y2 = getWidth() - centerY;
        // 根据勾股定理算出来的中心点到4个顶点的最大值, 如果比半径小, 就说明能够完整显示不需要clip
        mEnableClip = Math.max(x1 * x1, x2 * x2) + Math.max(y1 * y1, y2 * y2) > radius * radius;
        mClipPath.reset();
        mClipPath.addCircle(mCenterX, mCenterY, mRadius,
                Path.Direction.CW);
        mClipPath.close();
        invalidate();
    }

    /**
     * @return radius value if enabled, -1 otherwise
     */
    public float getRadius() {
        return mEnableClip ? mRadius : -1;
    }

    public void setEnableClip(boolean enableCircle) {
        mEnableClip = enableCircle;
    }
}

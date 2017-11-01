package com.vincan.circularrevealcompat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

/**
 * ValueAnimator for changing center point and radius of clipping circle.
 *
 * @author vincanyang
 */
class CircularRevealAnimator extends ValueAnimator {

    private AnimatorUpdateListener mUpdateListener;

    private CircularRevealAnimator(final View revealView, final float startCenterX, final float startCenterY, float startRadius,
                                   final float endCenterX, final float endCenterY, float endRadius) {
        setObjectValues(new RevealCircle(startCenterX, startCenterY, startRadius), new RevealCircle(endCenterX, endCenterY, endRadius));
        setEvaluator(new RevealCircleEvaluator());
        final CircularRevealCompatLayout circularRevealCompatLayout = getCircularRevealCompatLayout(revealView);
        mUpdateListener = new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                RevealCircle circle = (RevealCircle) animation.getAnimatedValue();
                circularRevealCompatLayout.setRevealClip(circle.centerX, circle.centerY, circle.radius);
            }
        };
        addUpdateListener(mUpdateListener);
    }

    private CircularRevealCompatLayout getCircularRevealCompatLayout(View clipView) {
        final CircularRevealCompatLayout circularRevealCompatLayout;
        if (clipView instanceof CircularRevealCompatLayout) {
            circularRevealCompatLayout = (CircularRevealCompatLayout) clipView;
        } else {
            ViewGroup parentView = (ViewGroup) clipView.getParent();
            if (parentView instanceof CircularRevealCompatLayout) {
                circularRevealCompatLayout = (CircularRevealCompatLayout) parentView;
            } else {//如果父容器不是CircularRevealCompatLayout，则动态添加
                circularRevealCompatLayout = new CircularRevealCompatLayout(clipView.getContext());
                ViewGroup.LayoutParams params = clipView.getLayoutParams();
                int index = parentView.indexOfChild(clipView);
                parentView.removeView(clipView);
                circularRevealCompatLayout.addView(clipView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                parentView.addView(circularRevealCompatLayout, index, params);
            }
        }
        return circularRevealCompatLayout;
    }

    public static CircularRevealAnimator ofRevealCircle(View revealView, int centerX, int centerY,
                                                        float startRadius, float endRadius) {
        return new CircularRevealAnimator(revealView, centerX, centerY, startRadius,
                centerX, centerY, endRadius);
    }

    public static CircularRevealAnimator ofRevealCircle(View clipView, float startCenterX, float startCenterY, float startRadius,
                                                        float endCenterX, float endCenterY, float endRadius) {
        return new CircularRevealAnimator(clipView, startCenterX, startCenterY, startRadius,
                endCenterX, endCenterY, endRadius);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAllUpdateListeners() {
        super.removeAllUpdateListeners();
        addUpdateListener(mUpdateListener);//防止被删
    }

    private final static class RevealCircle {
        public float centerX;
        public float centerY;
        public float radius;

        public RevealCircle(float centerX, float centerY, float radius) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.radius = radius;
        }
    }

    private final static class RevealCircleEvaluator implements TypeEvaluator<RevealCircle> {

        private RevealCircle mRevealCircle;

        @Override
        public RevealCircle evaluate(float fraction, RevealCircle startValue,
                                     RevealCircle endValue) {
            float currentX = startValue.centerX + ((endValue.centerX - startValue.centerX) * fraction);
            float currentY = startValue.centerY + ((endValue.centerY - startValue.centerY) * fraction);
            float currentRadius = startValue.radius + ((endValue.radius - startValue.radius) * fraction);
            if (mRevealCircle == null) {
                mRevealCircle = new RevealCircle(currentX, currentY, currentRadius);
            } else {
                mRevealCircle.centerX = currentX;
                mRevealCircle.centerY = currentY;
                mRevealCircle.radius = currentRadius;
            }
            return mRevealCircle;
        }
    }

    final static class ChangeViewLayerTypeListener extends AnimatorListenerAdapter {
        private View clipView;
        private int newLayerType;
        private int originalLayerType;

        ChangeViewLayerTypeListener(View clipView, int layerType) {
            this.clipView = clipView;
            this.newLayerType = layerType;
            this.originalLayerType = clipView.getLayerType();
        }

        @Override
        public void onAnimationStart(Animator animation) {
            clipView.setLayerType(newLayerType, null);
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            clipView.setLayerType(originalLayerType, null);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            clipView.setLayerType(originalLayerType, null);
        }
    }
}
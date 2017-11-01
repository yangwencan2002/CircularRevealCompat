package com.vincan.circularrevealcompat;

import android.animation.Animator;
import android.view.View;

/**
 * Android 5.0+ API ViewAnimationUtils.createCircularReveal compatible for 4.0+.
 *
 * @author vincanyang
 */
public final class ViewAnimationCompatUtils {

    public static Animator createCircularReveal(View revealView, int centerX, int centerY,
                                                float startRadius, float endRadius) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            return android.view.ViewAnimationUtils.createCircularReveal(revealView, centerX, centerY,
                    startRadius, endRadius);
        }
        return createCircularReveal(revealView, centerX, centerY,
                startRadius, endRadius, View.LAYER_TYPE_HARDWARE);
    }

    public static Animator createCircularReveal(View revealView, int centerX, int centerY,
                                                float startRadius, float endRadius, int layerType) {
        Animator animator = CircularRevealAnimator.ofRevealCircle(revealView, centerX, centerY,
                startRadius, endRadius);
        if (layerType != revealView.getLayerType()) {
            animator.addListener(new CircularRevealAnimator.ChangeViewLayerTypeListener(revealView, layerType));
        }
        return animator;
    }

    public static Animator createCircularReveal(View revealView, float startCenterX, float startCenterY, float startRadius,
                                                float endCenterX, float endCenterY, float endRadius) {
        return createCircularReveal(revealView, startCenterX, startCenterY, startRadius,
                endCenterX, endCenterY, endRadius, View.LAYER_TYPE_HARDWARE);
    }

    public static Animator createCircularReveal(View revealView, float startCenterX, float startCenterY, float startRadius,
                                                float endCenterX, float endCenterY, float endRadius, int layerType) {
        Animator animator = CircularRevealAnimator.ofRevealCircle(revealView, startCenterX, startCenterY, startRadius,
                endCenterX, endCenterY, endRadius);
        if (layerType != revealView.getLayerType()) {
            animator.addListener(new CircularRevealAnimator.ChangeViewLayerTypeListener(revealView, layerType));
        }
        return animator;
    }
}

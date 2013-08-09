package com.li.learn.path.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import com.li.learn.path.framework.BeanContext;
import com.li.learn.path.utils.DisplayUtils;

public class ZoomViewAnimation {
    private Animator animator;
    private View finalView;
    private Rect startBounds;

    private Rect finalBounds;
    private float startScale;
    private long duration;
    private float startScaleFinal;

    private boolean isCalculatedBound;

    public ZoomViewAnimation(View finalView, int duration) {
        this.finalView = finalView;
        this.duration = duration;
        calculateBounds();
        initListener();
    }

    private void initListener() {
        finalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomToOrigin();
            }
        });
    }

    private void zoomToOrigin() {
        if (animator != null) {
            animator.cancel();
        }

        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator
                .ofFloat(finalView, View.X, startBounds.left))
                .with(ObjectAnimator.ofFloat(finalView, View.Y, startBounds.top))
                .with(ObjectAnimator.ofFloat(finalView, View.SCALE_X, startScaleFinal))
                .with(ObjectAnimator.ofFloat(finalView, View.SCALE_Y, startScaleFinal));
        set.setDuration(duration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                finalView.setVisibility(View.GONE);
                animator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                finalView.setVisibility(View.GONE);
                animator = null;
            }
        });
        set.start();
        animator = set;
    }

    public void calculateBounds() {
        finalBounds = new Rect();
        BeanContext.getInstance().getBean(DisplayUtils.class).getRectSize(finalBounds);
        startBounds = new Rect(finalBounds.left, finalBounds.top + finalBounds.height() / 3, finalBounds.right,
                finalBounds.bottom - finalBounds.height() / 3);

        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }
        startScaleFinal = startScale;
    }


    public void toFullScreen() {
        if (animator != null) {
            animator.cancel();
        }
        finalView.setVisibility(View.VISIBLE);
        finalView.setPivotX(0f);
        finalView.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(finalView, View.X,
                startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(finalView, View.Y, startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(finalView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(finalView, View.SCALE_Y, startScale, 1f));
        set.setDuration(duration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                animator = null;
            }
        });
        set.start();
        animator = set;
    }

    public boolean isCalculatedBound() {
        return isCalculatedBound;
    }

    public void setCalculatedBound(boolean calculatedBound) {
        isCalculatedBound = calculatedBound;
    }
}

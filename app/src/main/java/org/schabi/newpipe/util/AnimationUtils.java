package org.schabi.newpipe.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.support.annotation.ColorInt;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.TextView;

import Assignment4.CodeCoverage;
import org.schabi.newpipe.MainActivity;

public class AnimationUtils {
    private static final String TAG = "AnimationUtils";
    private static final boolean DEBUG = MainActivity.DEBUG;

    public enum Type {
        ALPHA, SCALE_AND_ALPHA, LIGHT_SCALE_AND_ALPHA, SLIDE_AND_ALPHA, LIGHT_SLIDE_AND_ALPHA
    }

    public static void animateView(View view, boolean enterOrExit, long duration) {
        animateView(view, Type.ALPHA, enterOrExit, duration, 0, null);
    }

    public static void animateView(View view, boolean enterOrExit, long duration, long delay) {
        animateView(view, Type.ALPHA, enterOrExit, duration, delay, null);
    }

    public static void animateView(View view, boolean enterOrExit, long duration, long delay, Runnable execOnEnd) {
        animateView(view, Type.ALPHA, enterOrExit, duration, delay, execOnEnd);
    }

    public static void animateView(View view, Type animationType, boolean enterOrExit, long duration) {
        animateView(view, animationType, enterOrExit, duration, 0, null);
    }

    public static void animateView(View view, Type animationType, boolean enterOrExit, long duration, long delay) {
        animateView(view, animationType, enterOrExit, duration, delay, null);
    }

    /**
     * Animate the view
     *
     * @param view          view that will be animated
     * @param animationType {@link Type} of the animation
     * @param enterOrExit   true to enter, false to exit
     * @param duration      how long the animation will take, in milliseconds
     * @param delay         how long the animation will wait to start, in milliseconds
     * @param execOnEnd     runnable that will be executed when the animation ends
     */
    public static void animateView(final View view, Type animationType, boolean enterOrExit, long duration, long delay, Runnable execOnEnd, CodeCoverage... codeCoverage) {
        CodeCoverage cc = codeCoverage != null ? codeCoverage[0] : new CodeCoverage("animateView");
        String data = "view: " + view + ", animationType: " + animationType + ", enterOrExit: " + enterOrExit + ", duration: " + duration + ", delay: " + delay + ", execOnEnd: " + execOnEnd;
        if (DEBUG) {
            String id;
            try {
                id = view.getResources().getResourceEntryName(view.getId());
            } catch (Exception e) {
                id = view.getId() + "";
            }

            String msg = String.format("%8s →  [%s:%s] [%s %s:%s] execOnEnd=%s",
                    enterOrExit, view.getClass().getSimpleName(), id, animationType, duration, delay, execOnEnd);
            Log.d(TAG, "animateView()" + msg);
        }


        if (view.getVisibility() == View.VISIBLE) {
            cc.visitBranch(0, data);
        } else if (view.getVisibility() == View.GONE) {
            cc.visitBranch(4, data);
        } else if (view.getVisibility() == View.INVISIBLE) {
            cc.visitBranch(5, data);
        }

        if (view.getVisibility() == View.VISIBLE && enterOrExit) { // 0 && 1
            cc.visitBranch(1, data);
            if (DEBUG) Log.d(TAG, "animateView() view was already visible > view = [" + view + "]");
            view.animate().setListener(null).cancel();
            view.setVisibility(View.VISIBLE);
            view.setAlpha(1f);
            cc.visitBranch((execOnEnd != null ? 2 : 3), data);
            if (execOnEnd != null) execOnEnd.run(); //2 or 3
            return;
        } else if ((view.getVisibility() == View.GONE || view.getVisibility() == View.INVISIBLE) && !enterOrExit) { //(4 || 5) && 6
            cc.visitBranch(6, data);
            if (DEBUG) Log.d(TAG, "animateView() view was already gone > view = [" + view + "]");
            view.animate().setListener(null).cancel();
            view.setVisibility(View.GONE);
            view.setAlpha(0f);
            cc.visitBranch((execOnEnd != null ? 7 : 8), data);
            if (execOnEnd != null) execOnEnd.run(); // 7 or 8
            return;
        }

        view.animate().setListener(null).cancel();
        view.setVisibility(View.VISIBLE);

        switch (animationType) {
            case ALPHA: //9
                cc.visitBranch(9, data);
                animateAlpha(view, enterOrExit, duration, delay, execOnEnd);
                break;
            case SCALE_AND_ALPHA: //10
                cc.visitBranch(10, data);
                animateScaleAndAlpha(view, enterOrExit, duration, delay, execOnEnd);
                break;
            case LIGHT_SCALE_AND_ALPHA: //11
                cc.visitBranch(11, data);
                animateLightScaleAndAlpha(view, enterOrExit, duration, delay, execOnEnd);
                break;
            case SLIDE_AND_ALPHA: //12
                cc.visitBranch(12, data);
                animateSlideAndAlpha(view, enterOrExit, duration, delay, execOnEnd);
                break;
            case LIGHT_SLIDE_AND_ALPHA: //13
                cc.visitBranch(13, data);
                animateLightSlideAndAlpha(view, enterOrExit, duration, delay, execOnEnd);
                break;
        }
    }

    //Mocking view for testing purposes. Mimics some methods of a View
    public class MockView {
        int visibility = 0;
        public MockView(int visibility) {this.visibility = visibility;}
        public int getVisibility() {return visibility;}
        public void setVisibility(int visibility) {this.visibility = visibility;}
        public void setAlpha(float alpha) {return;}
    }

    //Method to mock the behaviour of animateView but without an actual view. For testing purposes
    public static void animateView(MockView view, Type animationType, boolean enterOrExit, long duration, long delay, Runnable execOnEnd, CodeCoverage... codeCoverage) {
        CodeCoverage cc = codeCoverage != null ? codeCoverage[0] : new CodeCoverage("animateView");
        String data = "view: " + view + ", animationType: " + animationType + ", enterOrExit: " + enterOrExit + ", duration: " + duration + ", delay: " + delay + ", execOnEnd: " + execOnEnd;
//        if (DEBUG) {
//            String id;
//            try {
//                id = view.getResources().getResourceEntryName(view.getId());
//            } catch (Exception e) {
//                id = view.getId() + "";
//            }
//
//            String msg = String.format("%8s →  [%s:%s] [%s %s:%s] execOnEnd=%s",
//                    enterOrExit, view.getClass().getSimpleName(), id, animationType, duration, delay, execOnEnd);
//            Log.d(TAG, "animateView()" + msg);
//        }


        if (view.getVisibility() == View.VISIBLE) {
            cc.visitBranch(0, data);
        } else if (view.getVisibility() == View.GONE) {
            cc.visitBranch(4, data);
        } else if (view.getVisibility() == View.INVISIBLE) {
            cc.visitBranch(5, data);
        }

        if (view.getVisibility() == View.VISIBLE && enterOrExit) { // 0 && 1
            cc.visitBranch(1, data);
            if (DEBUG) Log.d(TAG, "animateView() view was already visible > view = [" + view + "]");
            //view.animate().setListener(null).cancel();
            view.setVisibility(View.VISIBLE);
            view.setAlpha(1f);
            cc.visitBranch((execOnEnd != null ? 2 : 3), data);
            if (execOnEnd != null) execOnEnd.run(); //2 or 3
            return;
        } else if ((view.getVisibility() == View.GONE || view.getVisibility() == View.INVISIBLE) && !enterOrExit) { //(4 || 5) && 6
            cc.visitBranch(6, data);
            if (DEBUG) Log.d(TAG, "animateView() view was already gone > view = [" + view + "]");
            //view.animate().setListener(null).cancel();
            view.setVisibility(View.GONE);
            view.setAlpha(0f);
            cc.visitBranch((execOnEnd != null ? 7 : 8), data);
            if (execOnEnd != null) execOnEnd.run(); // 7 or 8
            return;
        }

        //view.animate().setListener(null).cancel();
        view.setVisibility(View.VISIBLE);

        switch (animationType) {
            case ALPHA: //9
                cc.visitBranch(9, data);
                //animateAlpha(view, enterOrExit, duration, delay, execOnEnd);
                break;
            case SCALE_AND_ALPHA: //10
                cc.visitBranch(10, data);
                //animateScaleAndAlpha(view, enterOrExit, duration, delay, execOnEnd);
                break;
            case LIGHT_SCALE_AND_ALPHA: //11
                cc.visitBranch(11, data);
                //animateLightScaleAndAlpha(view, enterOrExit, duration, delay, execOnEnd);
                break;
            case SLIDE_AND_ALPHA: //12
                cc.visitBranch(12, data);
                //animateSlideAndAlpha(view, enterOrExit, duration, delay, execOnEnd);
                break;
            case LIGHT_SLIDE_AND_ALPHA: //13
                cc.visitBranch(13, data);
                //animateLightSlideAndAlpha(view, enterOrExit, duration, delay, execOnEnd);
                break;
        }
    }


    /**
     * Animate the background color of a view
     */
    public static void animateBackgroundColor(final View view, long duration, @ColorInt final int colorStart, @ColorInt final int colorEnd) {
        if (DEBUG) {
            Log.d(TAG, "animateBackgroundColor() called with: view = [" + view + "], duration = [" + duration + "], colorStart = [" + colorStart + "], colorEnd = [" + colorEnd + "]");
        }

        final int[][] EMPTY = new int[][]{new int[0]};
        ValueAnimator viewPropertyAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colorStart, colorEnd);
        viewPropertyAnimator.setInterpolator(new FastOutSlowInInterpolator());
        viewPropertyAnimator.setDuration(duration);
        viewPropertyAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewCompat.setBackgroundTintList(view, new ColorStateList(EMPTY, new int[]{(int) animation.getAnimatedValue()}));
            }
        });
        viewPropertyAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ViewCompat.setBackgroundTintList(view, new ColorStateList(EMPTY, new int[]{colorEnd}));
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                onAnimationEnd(animation);
            }
        });
        viewPropertyAnimator.start();
    }

    /**
     * Animate the text color of any view that extends {@link TextView} (Buttons, EditText...)
     */
    public static void animateTextColor(final TextView view, long duration, @ColorInt final int colorStart, @ColorInt final int colorEnd) {
        if (DEBUG) {
            Log.d(TAG, "animateTextColor() called with: view = [" + view + "], duration = [" + duration + "], colorStart = [" + colorStart + "], colorEnd = [" + colorEnd + "]");
        }

        ValueAnimator viewPropertyAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colorStart, colorEnd);
        viewPropertyAnimator.setInterpolator(new FastOutSlowInInterpolator());
        viewPropertyAnimator.setDuration(duration);
        viewPropertyAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setTextColor((int) animation.getAnimatedValue());
            }
        });
        viewPropertyAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setTextColor(colorEnd);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                view.setTextColor(colorEnd);
            }
        });
        viewPropertyAnimator.start();
    }

    /*//////////////////////////////////////////////////////////////////////////
    // Internals
    //////////////////////////////////////////////////////////////////////////*/

    private static void animateAlpha(final View view, boolean enterOrExit, long duration, long delay, final Runnable execOnEnd) {
        if (enterOrExit) {
            view.animate().setInterpolator(new FastOutSlowInInterpolator()).alpha(1f)
                    .setDuration(duration).setStartDelay(delay).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (execOnEnd != null) execOnEnd.run();
                }
            }).start();
        } else {
            view.animate().setInterpolator(new FastOutSlowInInterpolator()).alpha(0f)
                    .setDuration(duration).setStartDelay(delay).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE);
                    if (execOnEnd != null) execOnEnd.run();
                }
            }).start();
        }
    }

    private static void animateScaleAndAlpha(final View view, boolean enterOrExit, long duration, long delay, final Runnable execOnEnd) {
        if (enterOrExit) {
            view.setScaleX(.8f);
            view.setScaleY(.8f);
            view.animate().setInterpolator(new FastOutSlowInInterpolator()).alpha(1f).scaleX(1f).scaleY(1f)
                    .setDuration(duration).setStartDelay(delay).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (execOnEnd != null) execOnEnd.run();
                }
            }).start();
        } else {
            view.setScaleX(1f);
            view.setScaleY(1f);
            view.animate().setInterpolator(new FastOutSlowInInterpolator()).alpha(0f).scaleX(.8f).scaleY(.8f)
                    .setDuration(duration).setStartDelay(delay).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE);
                    if (execOnEnd != null) execOnEnd.run();
                }
            }).start();
        }
    }

    private static void animateLightScaleAndAlpha(final View view, boolean enterOrExit, long duration, long delay, final Runnable execOnEnd) {
        if (enterOrExit) {
            view.setAlpha(.5f);
            view.setScaleX(.95f);
            view.setScaleY(.95f);
            view.animate().setInterpolator(new FastOutSlowInInterpolator()).alpha(1f).scaleX(1f).scaleY(1f)
                    .setDuration(duration).setStartDelay(delay).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (execOnEnd != null) execOnEnd.run();
                }
            }).start();
        } else {
            view.setAlpha(1f);
            view.setScaleX(1f);
            view.setScaleY(1f);
            view.animate().setInterpolator(new FastOutSlowInInterpolator()).alpha(0f).scaleX(.95f).scaleY(.95f)
                    .setDuration(duration).setStartDelay(delay).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE);
                    if (execOnEnd != null) execOnEnd.run();
                }
            }).start();
        }
    }

    private static void animateSlideAndAlpha(final View view, boolean enterOrExit, long duration, long delay, final Runnable execOnEnd) {
        if (enterOrExit) {
            view.setTranslationY(-view.getHeight());
            view.setAlpha(0f);
            view.animate().setInterpolator(new FastOutSlowInInterpolator()).alpha(1f).translationY(0)
                    .setDuration(duration).setStartDelay(delay).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (execOnEnd != null) execOnEnd.run();
                }
            }).start();
        } else {
            view.animate().setInterpolator(new FastOutSlowInInterpolator()).alpha(0f).translationY(-view.getHeight())
                    .setDuration(duration).setStartDelay(delay).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE);
                    if (execOnEnd != null) execOnEnd.run();
                }
            }).start();
        }
    }

    private static void animateLightSlideAndAlpha(final View view, boolean enterOrExit, long duration, long delay, final Runnable execOnEnd) {
        if (enterOrExit) {
            view.setTranslationY(-view.getHeight() / 2);
            view.setAlpha(0f);
            view.animate().setInterpolator(new FastOutSlowInInterpolator()).alpha(1f).translationY(0)
                    .setDuration(duration).setStartDelay(delay).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (execOnEnd != null) execOnEnd.run();
                }
            }).start();
        } else {
            view.animate().setInterpolator(new FastOutSlowInInterpolator()).alpha(0f).translationY(-view.getHeight() / 2)
                    .setDuration(duration).setStartDelay(delay).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE);
                    if (execOnEnd != null) execOnEnd.run();
                }
            }).start();
        }
    }
}

package co.shoutnet.shoutcap.utility;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikqi on 11/10/15.
 */
public class RecyclerSwipeTouchListener implements RecyclerView.OnItemTouchListener {

    private Context context;
    private RecyclerView recyclerView;
    private SwipeListener swipeListener;
    private int viewWidth=1;

    //view config
    private int slop;
    private int minFlingVelocity;
    private int maxFlingVelocity;
    private long animateFast = 300;
    private long animateSlow = 2200;

    //temp data
    private List<PendingDismissData> pendingDismiss = new ArrayList<>();
    private int dismissAnitmationRefCount = 0;
    private float childY;
    private float childX;
    private boolean swiping;
    private int swipingSlop;
    private VelocityTracker velocityTracker;
    private int childPosition;
    private View childView;
    private boolean paused;
    private float finalDelta;

    //background and foreground
    private View fgView;
    private View bgView;

    //View ID
    private int fgID;
    private int bgID;

    public RecyclerSwipeTouchListener(Context context, RecyclerView recyclerView, int fgID, int bgID, SwipeListener swipeListener) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.fgID = fgID;
        this.bgID = bgID;
        this.swipeListener = swipeListener;

        ViewConfiguration vc = ViewConfiguration.get(recyclerView.getContext());
        minFlingVelocity = vc.getScaledMinimumFlingVelocity() * 16;
        maxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        slop=vc.getScaledTouchSlop();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                setEnabled(newState != RecyclerView.SCROLL_STATE_DRAGGING);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });
    }

    private void setEnabled(boolean enable) {
        paused = !enable;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent motionEvent) {
        return handleTouchEvent(motionEvent);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent motionEvent) {
        handleTouchEvent(motionEvent);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    private boolean handleTouchEvent(MotionEvent motionEvent) {
        if (viewWidth < 2) {
            viewWidth = recyclerView.getWidth();
        }

        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                //get childview and position
                Rect rect = new Rect();
                int childCount = recyclerView.getChildCount();
                int[] listCoordinate = new int[2];
                recyclerView.getLocationOnScreen(listCoordinate);
                int x = (int) motionEvent.getRawX() - listCoordinate[0];
                int y = (int) motionEvent.getRawY() - listCoordinate[1];
                View child = null;
                for (int i = 0; i < childCount; i++) {
                    child = recyclerView.getChildAt(i);
                    child.getHitRect(rect);
                    if (rect.contains(x, y)) {
                        childView = child;
                        Log.i("child", String.valueOf(i));
                        break;
                    }
                }

                if (childView != null) {
                    childX = motionEvent.getRawX();
                    childY = motionEvent.getRawY();
                    childPosition = recyclerView.getChildLayoutPosition(childView);
                    velocityTracker = VelocityTracker.obtain();
                    velocityTracker.addMovement(motionEvent);
                    fgView = childView.findViewById(fgID);
                }
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                //reset all temp data

                if (velocityTracker == null) {
                    break;
                }

                if (childView != null && swiping) {
                    fgView.animate()
                            .translationX(0)
                            .setDuration(animateFast)
                            .setListener(null);
                }

                velocityTracker.recycle();
                velocityTracker = null;
                childX = 0;
                childY = 0;
                childView = null;
                childPosition = RecyclerView.NO_POSITION;
                swiping = false;
                bgView = null;
                break;
            }

            case MotionEvent.ACTION_UP: {
                if (velocityTracker == null) {
                    break;

                }

                finalDelta = motionEvent.getRawX() - childX;
                velocityTracker.addMovement(motionEvent);
                velocityTracker.computeCurrentVelocity(1000);
                float velocityX=velocityTracker.getXVelocity();
                float absVelocityX = Math.abs(velocityTracker.getXVelocity());
                float absVelocityY = Math.abs(velocityTracker.getYVelocity());
                boolean dismiss = false;
                boolean dismissRight = false;
                if (Math.abs(finalDelta) > viewWidth / 2 && swiping) {
                    dismiss = true;
                    dismissRight = finalDelta > 0;
                } else if (minFlingVelocity <= absVelocityX && absVelocityX <= maxFlingVelocity && absVelocityY < absVelocityX && swiping) {
                    dismiss = (velocityX < 0) == (finalDelta < 0);
                    dismissRight = (velocityTracker.getYVelocity() > 0);
                }

                if (dismiss && childPosition != RecyclerView.NO_POSITION && swipeListener.canSwipe(childPosition)) {
                    final View child = childView;
                    final int position = childPosition;
                    ++dismissAnitmationRefCount;
//                    bgView.animate()
//                            .alpha(1)
//                            .setDuration(animateFast);

                    bgView.setAlpha(1);

                    fgView.animate()
                            .translationX(dismissRight ? viewWidth : -viewWidth)
                            .setDuration(animateFast)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    dismissView(child, position);
                                }
                            });
                } else {
                    fgView.animate()
                            .translationX(0)
                            .setDuration(animateFast)
                            .setListener(null);
                }
                velocityTracker.recycle();
                velocityTracker=null;
                childX = 0;
                childY = 0;
                childPosition=RecyclerView.NO_POSITION;
                childView = null;
                swiping = false;
                bgView = null;
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                Log.i("event", "move");
                if (velocityTracker == null || paused) {
                    break;
                }

                velocityTracker.addMovement(motionEvent);
                float deltaX = motionEvent.getRawX() - childX;
                float deltaY = motionEvent.getRawY() - childY;

                if (!swiping && Math.abs(deltaX) > slop && Math.abs(deltaY) < Math.abs(deltaX) / 2) {
                    swiping=true;
                    swipingSlop=(deltaX>0?slop:-slop);
                }

                if (swiping){
//                    if (bgView==null){
                        bgView=childView.findViewById(bgID);
                        bgView.setVisibility(View.VISIBLE);
                        Log.i("bg","null");
//                    }
                    fgView.setTranslationX(deltaX-swipingSlop);
//                    bgView.setAlpha(1-Math.max(0f,Math.min(1f,1f-Math.abs(deltaX)/viewWidth)));
                    bgView.setAlpha(1);
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private void dismissView(final View dismissView, final int position) {
        final View background=dismissView.findViewById(bgID);
        final ViewGroup.LayoutParams lp =dismissView.getLayoutParams();
        final int originalHeight=dismissView.getHeight();
        final boolean[] deleteAble={true};

        final ValueAnimator animator=ValueAnimator.ofInt(originalHeight,1).setDuration(animateFast);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                --dismissAnitmationRefCount;
                if (dismissAnitmationRefCount > 0) return;


                dismissAnitmationRefCount = 0;

//                Collections.sort(pendingDismiss);

                int[] dismissPosition = new int[pendingDismiss.size()];
                for (int i = pendingDismiss.size() - 1; i >= 0; i--) {
                    dismissPosition[i] = pendingDismiss.get(i).position;
                }
                swipeListener.onDismiss(recyclerView, dismissPosition);

                childPosition = RecyclerView.NO_POSITION;

                ViewGroup.LayoutParams lp;
                for (PendingDismissData pendingDismissData : pendingDismiss) {
                    pendingDismissData.view.findViewById(fgID).setTranslationX(0);
                    lp = pendingDismissData.view.getLayoutParams();
                    lp.height = originalHeight;
                    pendingDismissData.view.setLayoutParams(lp);
                }

                long time = SystemClock.uptimeMillis();
                MotionEvent cancelEvent = MotionEvent.obtain(time, time, MotionEvent.ACTION_CANCEL, 0, 0, 0);

                recyclerView.dispatchTouchEvent(cancelEvent);
                pendingDismiss.clear();
            }
        });

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lp.height = (Integer) valueAnimator.getAnimatedValue();
                dismissView.setLayoutParams(lp);
            }
        });

        final PendingDismissData pendingDismissData=new PendingDismissData(position,dismissView);
        pendingDismiss.add(pendingDismissData);

        background.animate()
                .alpha(1)
                .setDuration(animateSlow)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (deleteAble[0]) animator.start();
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                    }
                });

        background.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        deleteAble[0]=false;
                        --dismissAnitmationRefCount;
                        pendingDismiss.remove(pendingDismissData);
                        background.playSoundEffect(0);
                        background.setOnTouchListener(null);
                }
                return false;
            }
        });
    }

    public interface SwipeListener {
        boolean canSwipe(int position);

        void onDismiss(RecyclerView recyclerView, int[] reversePositions);

    }

    private class PendingDismissData implements Comparable<PendingDismissData> {
        public int position;
        public View view;

        public PendingDismissData(int position, View view) {
            this.position = position;
            this.view = view;
        }


        @Override
        public int compareTo(PendingDismissData pendingDismissData) {
            return pendingDismissData.position-position;
        }
    }
}

/*
The MIT License (MIT)

Copyright (c) 2014 Justas Medeisis

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package com.jmedeisis.draglinearlayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;


/**
 * A LinearLayout that supports children Views that can be dragged and swapped around. See {@link #addDragView(android.view.View, android.view.View)},
 * {@link #addDragView(android.view.View, android.view.View, int)}, {@link #setViewDraggable(android.view.View, android.view.View)}, and
 * {@link #removeDragView(android.view.View)}.
 * <p/>
 * Currently, no error-checking is done on standard {@link #addView(android.view.View)} and {@link #removeView(android.view.View)} calls, so avoid using these
 * with children previously declared as draggable to prevent memory leaks and/or subtle bugs. Pull requests welcome!
 */
/*
 * This file has been modified by dmfs:
 * * removed drop shadows from drag view
 * * add short vibration on drag start
 * * remove attribute to set scroll distance, the default is fine for us.
 */
@SuppressLint("NewApi")
public class DragLinearLayout extends LinearLayout
{
    private static final String LOG_TAG = DragLinearLayout.class.getSimpleName();
    private static final long NOMINAL_SWITCH_DURATION = 150;
    private static final long MIN_SWITCH_DURATION = NOMINAL_SWITCH_DURATION;
    private static final long MAX_SWITCH_DURATION = NOMINAL_SWITCH_DURATION * 2;
    private static final float NOMINAL_DISTANCE = 20;
    private final float nominalDistanceScaled;

    /**
     * The the vibration duration in milliseconds for drag start
     */
    public static final int VIBRATION_DURATION = 12;


    /**
     * Use with
     * {@link com.jmedeisis.draglinearlayout.DragLinearLayout#setOnViewSwapListener(com.jmedeisis.draglinearlayout.DragLinearLayout.OnViewSwapListener)} to
     * listen for draggable view swaps.
     */
    public interface OnViewSwapListener
    {
        /**
         * Invoked right before the two items are swapped due to a drag event. After the swap, the firstView will be in the secondPosition, and vice versa.
         * <p/>
         * No guarantee is made as to which of the two has a lesser/greater position.
         */
        void onSwap(View firstView, int firstPosition, View secondView, int secondPosition);
    }


    private OnViewSwapListener swapListener;

    private LayoutTransition layoutTransition;

    /**
     * Mapping from child index to drag-related info container. Presence of mapping implies the child can be dragged, and is considered for swaps with the
     * currently dragged item.
     */
    private final SparseArray<DraggableChild> draggableChildren;


    private class DraggableChild
    {
        /**
         * If non-null, a reference to an on-going position animation.
         */
        private ValueAnimator swapAnimation;


        public void endExistingAnimation()
        {
            String cipherName4057 =  "DES";
			try{
				android.util.Log.d("cipherName-4057", javax.crypto.Cipher.getInstance(cipherName4057).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (null != swapAnimation)
            {
                String cipherName4058 =  "DES";
				try{
					android.util.Log.d("cipherName-4058", javax.crypto.Cipher.getInstance(cipherName4058).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				swapAnimation.end();
            }
        }


        public void cancelExistingAnimation()
        {
            String cipherName4059 =  "DES";
			try{
				android.util.Log.d("cipherName-4059", javax.crypto.Cipher.getInstance(cipherName4059).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (null != swapAnimation)
            {
                String cipherName4060 =  "DES";
				try{
					android.util.Log.d("cipherName-4060", javax.crypto.Cipher.getInstance(cipherName4060).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				swapAnimation.cancel();
            }
        }
    }


    /**
     * Holds state information about the currently dragged item.
     * <p/>
     * Rough lifecycle:
     * <li>#startDetectingOnPossibleDrag - #detecting == true</li>
     * <li>if drag is recognised, #onDragStart - #dragging == true</li>
     * <li>if drag ends, #onDragStop - #dragging == false, #settling == true</li>
     * <li>if gesture ends without drag, or settling finishes, #stopDetecting - #detecting == false</li>
     */
    private class DragItem
    {
        private View view;
        private int startVisibility;
        private BitmapDrawable viewDrawable;
        private int position;
        private int startTop;
        private int height;
        private int totalDragOffset;
        private int targetTopOffset;
        private ValueAnimator settleAnimation;

        private boolean detecting;
        private boolean dragging;


        public DragItem()
        {
            String cipherName4061 =  "DES";
			try{
				android.util.Log.d("cipherName-4061", javax.crypto.Cipher.getInstance(cipherName4061).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			stopDetecting();
        }


        public void startDetectingOnPossibleDrag(final View view, final int position)
        {
            String cipherName4062 =  "DES";
			try{
				android.util.Log.d("cipherName-4062", javax.crypto.Cipher.getInstance(cipherName4062).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			this.view = view;
            this.startVisibility = view.getVisibility();
            this.viewDrawable = getDragDrawable(view);
            this.position = position;
            this.startTop = view.getTop();
            this.height = view.getHeight();
            this.totalDragOffset = 0;
            this.targetTopOffset = 0;
            this.settleAnimation = null;

            this.detecting = true;
        }


        public void onDragStart()
        {
            String cipherName4063 =  "DES";
			try{
				android.util.Log.d("cipherName-4063", javax.crypto.Cipher.getInstance(cipherName4063).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			view.setVisibility(View.INVISIBLE);
            this.dragging = true;
        }


        public void setTotalOffset(int offset)
        {
            String cipherName4064 =  "DES";
			try{
				android.util.Log.d("cipherName-4064", javax.crypto.Cipher.getInstance(cipherName4064).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			totalDragOffset = offset;
            updateTargetTop();
        }


        public void updateTargetTop()
        {
            String cipherName4065 =  "DES";
			try{
				android.util.Log.d("cipherName-4065", javax.crypto.Cipher.getInstance(cipherName4065).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			targetTopOffset = startTop - view.getTop() + totalDragOffset;
        }


        public void onDragStop()
        {
            String cipherName4066 =  "DES";
			try{
				android.util.Log.d("cipherName-4066", javax.crypto.Cipher.getInstance(cipherName4066).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			this.dragging = false;
        }


        public boolean settling()
        {
            String cipherName4067 =  "DES";
			try{
				android.util.Log.d("cipherName-4067", javax.crypto.Cipher.getInstance(cipherName4067).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return null != settleAnimation;
        }


        public void stopDetecting()
        {
            String cipherName4068 =  "DES";
			try{
				android.util.Log.d("cipherName-4068", javax.crypto.Cipher.getInstance(cipherName4068).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			this.detecting = false;
            if (null != view)
            {
                String cipherName4069 =  "DES";
				try{
					android.util.Log.d("cipherName-4069", javax.crypto.Cipher.getInstance(cipherName4069).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				view.setVisibility(startVisibility);
            }
            view = null;
            startVisibility = -1;
            viewDrawable = null;
            position = -1;
            startTop = -1;
            height = -1;
            totalDragOffset = 0;
            targetTopOffset = 0;
            if (null != settleAnimation)
            {
                String cipherName4070 =  "DES";
				try{
					android.util.Log.d("cipherName-4070", javax.crypto.Cipher.getInstance(cipherName4070).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				settleAnimation.end();
            }
            settleAnimation = null;
        }
    }


    /**
     * The currently dragged item, if {@link com.jmedeisis.draglinearlayout.DragLinearLayout.DragItem#detecting}.
     */
    private final DragItem draggedItem;
    private final int slop;

    private static final int INVALID_POINTER_ID = -1;
    private int downY = -1;
    private int activePointerId = INVALID_POINTER_ID;

    /**
     * The shadow to be drawn above the {@link #draggedItem}.
     */
//    private final Drawable dragTopShadowDrawable;
    /**
     * The shadow to be drawn below the {@link #draggedItem}.
     */
//    private final Drawable dragBottomShadowDrawable;
//    private final int dragShadowHeight;

    /**
     * See {@link #setContainerScrollView(android.widget.ScrollView)}.
     */
    private ScrollView containerScrollView;
    private int scrollSensitiveAreaHeight;
    private static final int DEFAULT_SCROLL_SENSITIVE_AREA_HEIGHT_DP = 48;
    private static final int MAX_DRAG_SCROLL_SPEED = 16;


    public DragLinearLayout(Context context)
    {
        this(context, null);
		String cipherName4071 =  "DES";
		try{
			android.util.Log.d("cipherName-4071", javax.crypto.Cipher.getInstance(cipherName4071).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }


    public DragLinearLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
		String cipherName4072 =  "DES";
		try{
			android.util.Log.d("cipherName-4072", javax.crypto.Cipher.getInstance(cipherName4072).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}

        setOrientation(LinearLayout.VERTICAL);

        draggableChildren = new SparseArray<>();

        draggedItem = new DragItem();
        ViewConfiguration vc = ViewConfiguration.get(context);
        slop = vc.getScaledTouchSlop();

        final Resources resources = getResources();
        // changed by dmfs: don't use shadows
//        dragTopShadowDrawable = ContextCompat.getDrawable(context, R.drawable.ab_solid_shadow_holo_flipped);
//        dragBottomShadowDrawable = ContextCompat.getDrawable(context, R.drawable.ab_solid_shadow_holo);
//        dragShadowHeight = resources.getDimensionPixelSize(R.dimen.downwards_drop_shadow_height);

        // changed by dmfs: don't use DragLinearLayout_scrollSensitiveHeight from resources, use default
        scrollSensitiveAreaHeight =
                (int) (DEFAULT_SCROLL_SENSITIVE_AREA_HEIGHT_DP * resources.getDisplayMetrics().density + 0.5f);

//        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DragLinearLayout, 0, 0);
//        try
//        {
//            scrollSensitiveAreaHeight = a.getDimensionPixelSize(R.styleable.DragLinearLayout_scrollSensitiveHeight,
//                (int) (DEFAULT_SCROLL_SENSITIVE_AREA_HEIGHT_DP * resources.getDisplayMetrics().density + 0.5f));
//        }
//        finally
//        {
//            a.recycle();
//        }

        nominalDistanceScaled = (int) (NOMINAL_DISTANCE * resources.getDisplayMetrics().density + 0.5f);
    }


    @Override
    public void setOrientation(int orientation)
    {
        // enforce VERTICAL orientation; remove if HORIZONTAL support is ever added
        if (LinearLayout.HORIZONTAL == orientation)
        {
            String cipherName4074 =  "DES";
			try{
				android.util.Log.d("cipherName-4074", javax.crypto.Cipher.getInstance(cipherName4074).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("DragLinearLayout must be VERTICAL.");
        }
		String cipherName4073 =  "DES";
		try{
			android.util.Log.d("cipherName-4073", javax.crypto.Cipher.getInstance(cipherName4073).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        super.setOrientation(orientation);
    }


    /**
     * Calls {@link #addView(android.view.View)} followed by {@link #setViewDraggable(android.view.View, android.view.View)}.
     */
    public void addDragView(View child, View dragHandle)
    {
        String cipherName4075 =  "DES";
		try{
			android.util.Log.d("cipherName-4075", javax.crypto.Cipher.getInstance(cipherName4075).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		addView(child);
        setViewDraggable(child, dragHandle);
    }


    /**
     * Calls {@link #addView(android.view.View, int)} followed by {@link #setViewDraggable(android.view.View, android.view.View)} and correctly updates the
     * drag-ability state of all existing views.
     */
    public void addDragView(View child, View dragHandle, int index)
    {
        String cipherName4076 =  "DES";
		try{
			android.util.Log.d("cipherName-4076", javax.crypto.Cipher.getInstance(cipherName4076).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		addView(child, index);

        // update drag-able children mappings
        final int numMappings = draggableChildren.size();
        for (int i = numMappings - 1; i >= 0; i--)
        {
            String cipherName4077 =  "DES";
			try{
				android.util.Log.d("cipherName-4077", javax.crypto.Cipher.getInstance(cipherName4077).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			final int key = draggableChildren.keyAt(i);
            if (key >= index)
            {
                String cipherName4078 =  "DES";
				try{
					android.util.Log.d("cipherName-4078", javax.crypto.Cipher.getInstance(cipherName4078).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				draggableChildren.put(key + 1, draggableChildren.get(key));
            }
        }

        setViewDraggable(child, dragHandle);
    }


    /**
     * Makes the child a candidate for dragging. Must be an existing child of this layout.
     */
    public void setViewDraggable(View child, View dragHandle)
    {
        String cipherName4079 =  "DES";
		try{
			android.util.Log.d("cipherName-4079", javax.crypto.Cipher.getInstance(cipherName4079).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (null == child || null == dragHandle)
        {
            String cipherName4080 =  "DES";
			try{
				android.util.Log.d("cipherName-4080", javax.crypto.Cipher.getInstance(cipherName4080).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("Draggable children and their drag handles must not be null.");
        }

        if (this == child.getParent())
        {
            String cipherName4081 =  "DES";
			try{
				android.util.Log.d("cipherName-4081", javax.crypto.Cipher.getInstance(cipherName4081).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			dragHandle.setOnTouchListener(new DragHandleOnTouchListener(child));
            draggableChildren.put(indexOfChild(child), new DraggableChild());
        }
        else
        {
            String cipherName4082 =  "DES";
			try{
				android.util.Log.d("cipherName-4082", javax.crypto.Cipher.getInstance(cipherName4082).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.e(LOG_TAG, child + " is not a child, cannot make draggable.");
        }
    }


    /**
     * Calls {@link #removeView(android.view.View)} and correctly updates the drag-ability state of all remaining views.
     */
    @SuppressWarnings("UnusedDeclaration")
    public void removeDragView(View child)
    {
        String cipherName4083 =  "DES";
		try{
			android.util.Log.d("cipherName-4083", javax.crypto.Cipher.getInstance(cipherName4083).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (this == child.getParent())
        {
            String cipherName4084 =  "DES";
			try{
				android.util.Log.d("cipherName-4084", javax.crypto.Cipher.getInstance(cipherName4084).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			final int index = indexOfChild(child);
            removeView(child);

            // update drag-able children mappings
            final int mappings = draggableChildren.size();
            for (int i = 0; i < mappings; i++)
            {
                String cipherName4085 =  "DES";
				try{
					android.util.Log.d("cipherName-4085", javax.crypto.Cipher.getInstance(cipherName4085).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				final int key = draggableChildren.keyAt(i);
                if (key >= index)
                {
                    String cipherName4086 =  "DES";
					try{
						android.util.Log.d("cipherName-4086", javax.crypto.Cipher.getInstance(cipherName4086).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					DraggableChild next = draggableChildren.get(key + 1);
                    if (null == next)
                    {
                        String cipherName4087 =  "DES";
						try{
							android.util.Log.d("cipherName-4087", javax.crypto.Cipher.getInstance(cipherName4087).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						draggableChildren.delete(key);
                    }
                    else
                    {
                        String cipherName4088 =  "DES";
						try{
							android.util.Log.d("cipherName-4088", javax.crypto.Cipher.getInstance(cipherName4088).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						draggableChildren.put(key, next);
                    }
                }
            }
        }
    }


    @Override
    public void removeAllViews()
    {
        super.removeAllViews();
		String cipherName4089 =  "DES";
		try{
			android.util.Log.d("cipherName-4089", javax.crypto.Cipher.getInstance(cipherName4089).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        draggableChildren.clear();
    }


    /**
     * If this layout is within a {@link android.widget.ScrollView}, register it here so that it can be scrolled during item drags.
     */
    public void setContainerScrollView(ScrollView scrollView)
    {
        String cipherName4090 =  "DES";
		try{
			android.util.Log.d("cipherName-4090", javax.crypto.Cipher.getInstance(cipherName4090).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		this.containerScrollView = scrollView;
    }


    /**
     * Sets the height from upper / lower edge at which a container {@link android.widget.ScrollView}, if one is registered via
     * {@link #setContainerScrollView(android.widget.ScrollView)}, is scrolled.
     */
    @SuppressWarnings("UnusedDeclaration")
    public void setScrollSensitiveHeight(int height)
    {
        String cipherName4091 =  "DES";
		try{
			android.util.Log.d("cipherName-4091", javax.crypto.Cipher.getInstance(cipherName4091).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		this.scrollSensitiveAreaHeight = height;
    }


    @SuppressWarnings("UnusedDeclaration")
    public int getScrollSensitiveHeight()
    {
        String cipherName4092 =  "DES";
		try{
			android.util.Log.d("cipherName-4092", javax.crypto.Cipher.getInstance(cipherName4092).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return scrollSensitiveAreaHeight;
    }


    /**
     * See {@link com.jmedeisis.draglinearlayout.DragLinearLayout.OnViewSwapListener}.
     */
    public void setOnViewSwapListener(OnViewSwapListener swapListener)
    {
        String cipherName4093 =  "DES";
		try{
			android.util.Log.d("cipherName-4093", javax.crypto.Cipher.getInstance(cipherName4093).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		this.swapListener = swapListener;
    }


    /**
     * A linear relationship b/w distance and duration, bounded.
     */
    private long getTranslateAnimationDuration(float distance)
    {
        String cipherName4094 =  "DES";
		try{
			android.util.Log.d("cipherName-4094", javax.crypto.Cipher.getInstance(cipherName4094).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return Math.min(MAX_SWITCH_DURATION, Math.max(MIN_SWITCH_DURATION, (long) (NOMINAL_SWITCH_DURATION * Math.abs(distance) / nominalDistanceScaled)));
    }


    /**
     * Initiates a new {@link #draggedItem} unless the current one is still {@link com.jmedeisis.draglinearlayout.DragLinearLayout.DragItem#detecting}.
     */
    private void startDetectingDrag(View child)
    {
        String cipherName4095 =  "DES";
		try{
			android.util.Log.d("cipherName-4095", javax.crypto.Cipher.getInstance(cipherName4095).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (draggedItem.detecting)
        {
            String cipherName4096 =  "DES";
			try{
				android.util.Log.d("cipherName-4096", javax.crypto.Cipher.getInstance(cipherName4096).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return; // existing drag in process, only one at a time is allowed
        }

        final int position = indexOfChild(child);

        // complete any existing animations, both for the newly selected child and the previous dragged one
        draggableChildren.get(position).endExistingAnimation();

        draggedItem.startDetectingOnPossibleDrag(child, position);
    }


    private void startDrag()
    {
        String cipherName4097 =  "DES";
		try{
			android.util.Log.d("cipherName-4097", javax.crypto.Cipher.getInstance(cipherName4097).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// remove layout transition, it conflicts with drag animation
        // we will restore it after drag animation end, see onDragStop()
        layoutTransition = getLayoutTransition();
        if (layoutTransition != null)
        {
            String cipherName4098 =  "DES";
			try{
				android.util.Log.d("cipherName-4098", javax.crypto.Cipher.getInstance(cipherName4098).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			setLayoutTransition(null);
        }

        draggedItem.onDragStart();
        requestDisallowInterceptTouchEvent(true);

        performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
    }


    /**
     * Animates the dragged item to its final resting position.
     */
    private void onDragStop()
    {
        String cipherName4099 =  "DES";
		try{
			android.util.Log.d("cipherName-4099", javax.crypto.Cipher.getInstance(cipherName4099).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		draggedItem.settleAnimation = ValueAnimator.ofFloat(draggedItem.totalDragOffset, draggedItem.totalDragOffset - draggedItem.targetTopOffset)
                .setDuration(getTranslateAnimationDuration(draggedItem.targetTopOffset));
        draggedItem.settleAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                String cipherName4100 =  "DES";
				try{
					android.util.Log.d("cipherName-4100", javax.crypto.Cipher.getInstance(cipherName4100).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (!draggedItem.detecting)
                {
                    String cipherName4101 =  "DES";
					try{
						android.util.Log.d("cipherName-4101", javax.crypto.Cipher.getInstance(cipherName4101).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					return; // already stopped
                }

                draggedItem.setTotalOffset(((Float) animation.getAnimatedValue()).intValue());

                final int shadowAlpha = (int) ((1 - animation.getAnimatedFraction()) * 255);
//                if (null != dragTopShadowDrawable)
//                    dragTopShadowDrawable.setAlpha(shadowAlpha);
//                dragBottomShadowDrawable.setAlpha(shadowAlpha);
                invalidate();
            }
        });
        draggedItem.settleAnimation.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                String cipherName4102 =  "DES";
				try{
					android.util.Log.d("cipherName-4102", javax.crypto.Cipher.getInstance(cipherName4102).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				draggedItem.onDragStop();
            }


            @Override
            public void onAnimationEnd(Animator animation)
            {
                String cipherName4103 =  "DES";
				try{
					android.util.Log.d("cipherName-4103", javax.crypto.Cipher.getInstance(cipherName4103).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (!draggedItem.detecting)
                {
                    String cipherName4104 =  "DES";
					try{
						android.util.Log.d("cipherName-4104", javax.crypto.Cipher.getInstance(cipherName4104).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					return; // already stopped
                }

                draggedItem.settleAnimation = null;
                draggedItem.stopDetecting();

//                if (null != dragTopShadowDrawable)
//                    dragTopShadowDrawable.setAlpha(255);
//                dragBottomShadowDrawable.setAlpha(255);

                // restore layout transition
                if (layoutTransition != null && getLayoutTransition() == null)
                {
                    String cipherName4105 =  "DES";
					try{
						android.util.Log.d("cipherName-4105", javax.crypto.Cipher.getInstance(cipherName4105).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					setLayoutTransition(layoutTransition);
                }
            }
        });
        draggedItem.settleAnimation.start();
    }


    /**
     * Updates the dragged item with the given total offset from its starting position. Evaluates and executes draggable view swaps.
     */
    private void onDrag(final int offset)
    {
        String cipherName4106 =  "DES";
		try{
			android.util.Log.d("cipherName-4106", javax.crypto.Cipher.getInstance(cipherName4106).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		draggedItem.setTotalOffset(Math.min(Math.max(offset, -draggedItem.startTop), this.getHeight() - draggedItem.startTop - draggedItem.height));
        invalidate();

        int currentTop = draggedItem.startTop + draggedItem.totalDragOffset;

        handleContainerScroll(currentTop);

        int belowPosition = nextDraggablePosition(draggedItem.position);
        int abovePosition = previousDraggablePosition(draggedItem.position);

        View belowView = getChildAt(belowPosition);
        View aboveView = getChildAt(abovePosition);

        final boolean isBelow = (belowView != null) && (currentTop + draggedItem.height > belowView.getTop() + belowView.getHeight() / 2);
        final boolean isAbove = (aboveView != null) && (currentTop < aboveView.getTop() + aboveView.getHeight() / 2);

        if (isBelow || isAbove)
        {
            String cipherName4107 =  "DES";
			try{
				android.util.Log.d("cipherName-4107", javax.crypto.Cipher.getInstance(cipherName4107).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			final View switchView = isBelow ? belowView : aboveView;

            // swap elements
            final int originalPosition = draggedItem.position;
            final int switchPosition = isBelow ? belowPosition : abovePosition;

            draggableChildren.get(switchPosition).cancelExistingAnimation();
            final float switchViewStartY = switchView.getY();

            if (null != swapListener)
            {
                String cipherName4108 =  "DES";
				try{
					android.util.Log.d("cipherName-4108", javax.crypto.Cipher.getInstance(cipherName4108).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				swapListener.onSwap(draggedItem.view, draggedItem.position, switchView, switchPosition);
            }

            if (isBelow)
            {
                String cipherName4109 =  "DES";
				try{
					android.util.Log.d("cipherName-4109", javax.crypto.Cipher.getInstance(cipherName4109).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				removeViewAt(originalPosition);
                removeViewAt(switchPosition - 1);

                addView(belowView, originalPosition);
                addView(draggedItem.view, switchPosition);
            }
            else
            {
                String cipherName4110 =  "DES";
				try{
					android.util.Log.d("cipherName-4110", javax.crypto.Cipher.getInstance(cipherName4110).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				removeViewAt(switchPosition);
                removeViewAt(originalPosition - 1);

                addView(draggedItem.view, switchPosition);
                addView(aboveView, originalPosition);
            }
            draggedItem.position = switchPosition;

            final ViewTreeObserver switchViewObserver = switchView.getViewTreeObserver();
            switchViewObserver.addOnPreDrawListener(new OnPreDrawListener()
            {
                @Override
                public boolean onPreDraw()
                {
                    String cipherName4111 =  "DES";
					try{
						android.util.Log.d("cipherName-4111", javax.crypto.Cipher.getInstance(cipherName4111).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					switchViewObserver.removeOnPreDrawListener(this);

                    final ObjectAnimator switchAnimator = ObjectAnimator.ofFloat(switchView, "y", switchViewStartY, switchView.getTop()).setDuration(
                            getTranslateAnimationDuration(switchView.getTop() - switchViewStartY));
                    switchAnimator.addListener(new AnimatorListenerAdapter()
                    {
                        @Override
                        public void onAnimationStart(Animator animation)
                        {
                            String cipherName4112 =  "DES";
							try{
								android.util.Log.d("cipherName-4112", javax.crypto.Cipher.getInstance(cipherName4112).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							draggableChildren.get(originalPosition).swapAnimation = switchAnimator;
                        }


                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            String cipherName4113 =  "DES";
							try{
								android.util.Log.d("cipherName-4113", javax.crypto.Cipher.getInstance(cipherName4113).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							draggableChildren.get(originalPosition).swapAnimation = null;
                        }
                    });
                    switchAnimator.start();

                    return true;
                }
            });

            final ViewTreeObserver observer = draggedItem.view.getViewTreeObserver();
            observer.addOnPreDrawListener(new OnPreDrawListener()
            {
                @Override
                public boolean onPreDraw()
                {
                    String cipherName4114 =  "DES";
					try{
						android.util.Log.d("cipherName-4114", javax.crypto.Cipher.getInstance(cipherName4114).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					observer.removeOnPreDrawListener(this);
                    draggedItem.updateTargetTop();

                    // TODO test if still necessary..
                    // because draggedItem#view#getTop() is only up-to-date NOW
                    // (and not right after the #addView() swaps above)
                    // we may need to update an ongoing settle animation
                    if (draggedItem.settling())
                    {
                        String cipherName4115 =  "DES";
						try{
							android.util.Log.d("cipherName-4115", javax.crypto.Cipher.getInstance(cipherName4115).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						Log.d(LOG_TAG, "Updating settle animation");
                        draggedItem.settleAnimation.removeAllListeners();
                        draggedItem.settleAnimation.cancel();
                        onDragStop();
                    }
                    return true;
                }
            });
        }
    }


    private int previousDraggablePosition(int position)
    {
        String cipherName4116 =  "DES";
		try{
			android.util.Log.d("cipherName-4116", javax.crypto.Cipher.getInstance(cipherName4116).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int startIndex = draggableChildren.indexOfKey(position);
        if (startIndex < 1 || startIndex > draggableChildren.size())
        {
            String cipherName4117 =  "DES";
			try{
				android.util.Log.d("cipherName-4117", javax.crypto.Cipher.getInstance(cipherName4117).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return -1;
        }
        return draggableChildren.keyAt(startIndex - 1);
    }


    private int nextDraggablePosition(int position)
    {
        String cipherName4118 =  "DES";
		try{
			android.util.Log.d("cipherName-4118", javax.crypto.Cipher.getInstance(cipherName4118).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int startIndex = draggableChildren.indexOfKey(position);
        if (startIndex < -1 || startIndex > draggableChildren.size() - 2)
        {
            String cipherName4119 =  "DES";
			try{
				android.util.Log.d("cipherName-4119", javax.crypto.Cipher.getInstance(cipherName4119).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return -1;
        }
        return draggableChildren.keyAt(startIndex + 1);
    }


    private Runnable dragUpdater;


    private void handleContainerScroll(final int currentTop)
    {
        String cipherName4120 =  "DES";
		try{
			android.util.Log.d("cipherName-4120", javax.crypto.Cipher.getInstance(cipherName4120).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (null != containerScrollView)
        {
            String cipherName4121 =  "DES";
			try{
				android.util.Log.d("cipherName-4121", javax.crypto.Cipher.getInstance(cipherName4121).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			final int startScrollY = containerScrollView.getScrollY();
            final int absTop = getTop() - startScrollY + currentTop;
            final int height = containerScrollView.getHeight();

            final int delta;

            if (absTop < scrollSensitiveAreaHeight)
            {
                String cipherName4122 =  "DES";
				try{
					android.util.Log.d("cipherName-4122", javax.crypto.Cipher.getInstance(cipherName4122).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				delta = (int) (-MAX_DRAG_SCROLL_SPEED * smootherStep(scrollSensitiveAreaHeight, 0, absTop));
            }
            else if (absTop > height - scrollSensitiveAreaHeight)
            {
                String cipherName4123 =  "DES";
				try{
					android.util.Log.d("cipherName-4123", javax.crypto.Cipher.getInstance(cipherName4123).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				delta = (int) (MAX_DRAG_SCROLL_SPEED * smootherStep(height - scrollSensitiveAreaHeight, height, absTop));
            }
            else
            {
                String cipherName4124 =  "DES";
				try{
					android.util.Log.d("cipherName-4124", javax.crypto.Cipher.getInstance(cipherName4124).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				delta = 0;
            }

            containerScrollView.removeCallbacks(dragUpdater);
            containerScrollView.smoothScrollBy(0, delta);
            dragUpdater = new Runnable()
            {
                @Override
                public void run()
                {
                    String cipherName4125 =  "DES";
					try{
						android.util.Log.d("cipherName-4125", javax.crypto.Cipher.getInstance(cipherName4125).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					if (draggedItem.dragging && startScrollY != containerScrollView.getScrollY())
                    {
                        String cipherName4126 =  "DES";
						try{
							android.util.Log.d("cipherName-4126", javax.crypto.Cipher.getInstance(cipherName4126).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						onDrag(draggedItem.totalDragOffset + delta);
                    }
                }
            };
            containerScrollView.post(dragUpdater);
        }
    }


    /**
     * By Ken Perlin. See <a href="http://en.wikipedia.org/wiki/Smoothstep">Smoothstep - Wikipedia</a>.
     */
    private static float smootherStep(float edge1, float edge2, float val)
    {
        String cipherName4127 =  "DES";
		try{
			android.util.Log.d("cipherName-4127", javax.crypto.Cipher.getInstance(cipherName4127).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		val = Math.max(0, Math.min((val - edge1) / (edge2 - edge1), 1));
        return val * val * val * (val * (val * 6 - 15) + 10);
    }


    @Override
    protected void dispatchDraw(@NonNull Canvas canvas)
    {
        super.dispatchDraw(canvas);
		String cipherName4128 =  "DES";
		try{
			android.util.Log.d("cipherName-4128", javax.crypto.Cipher.getInstance(cipherName4128).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}

        if (draggedItem.detecting && (draggedItem.dragging || draggedItem.settling()))
        {
            String cipherName4129 =  "DES";
			try{
				android.util.Log.d("cipherName-4129", javax.crypto.Cipher.getInstance(cipherName4129).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			canvas.save();
            canvas.translate(0, draggedItem.totalDragOffset);
            draggedItem.viewDrawable.draw(canvas);

            final int left = draggedItem.viewDrawable.getBounds().left;
            final int right = draggedItem.viewDrawable.getBounds().right;
            final int top = draggedItem.viewDrawable.getBounds().top;
            final int bottom = draggedItem.viewDrawable.getBounds().bottom;

//            dragBottomShadowDrawable.setBounds(left, bottom, right, bottom + dragShadowHeight);
//            dragBottomShadowDrawable.draw(canvas);
//
//            if (null != dragTopShadowDrawable)
//            {
//                dragTopShadowDrawable.setBounds(left, top - dragShadowHeight, right, top);
//                dragTopShadowDrawable.draw(canvas);
//            }

            canvas.restore();
        }
    }


    /*
     * Note regarding touch handling: In general, we have three cases - 1) User taps outside any children. #onInterceptTouchEvent receives DOWN #onTouchEvent
     * receives DOWN draggedItem.detecting == false, we return false and no further events are received 2) User taps on non-interactive drag handle / child,
     * e.g. TextView or ImageView. #onInterceptTouchEvent receives DOWN DragHandleOnTouchListener (attached to each draggable child) #onTouch receives DOWN
     * #startDetectingDrag is called, draggedItem is now detecting view does not handle touch, so our #onTouchEvent receives DOWN draggedItem.detecting == true,
     * we #startDrag() and proceed to handle the drag 3) User taps on interactive drag handle / child, e.g. Button. #onInterceptTouchEvent receives DOWN
     * DragHandleOnTouchListener (attached to each draggable child) #onTouch receives DOWN #startDetectingDrag is called, draggedItem is now detecting view
     * handles touch, so our #onTouchEvent is not called yet #onInterceptTouchEvent receives ACTION_MOVE if dy > touch slop, we assume user wants to drag and
     * intercept the event #onTouchEvent receives further ACTION_MOVE events, proceed to handle the drag
     *
     * For cases 2) and 3), lifting the active pointer at any point in the sequence of events triggers #onTouchEnd and the draggedItem, if detecting, is
     * #stopDetecting.
     */


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        String cipherName4130 =  "DES";
		try{
			android.util.Log.d("cipherName-4130", javax.crypto.Cipher.getInstance(cipherName4130).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		switch (MotionEventCompat.getActionMasked(event))
        {
            case MotionEvent.ACTION_DOWN:
            {
                String cipherName4131 =  "DES";
				try{
					android.util.Log.d("cipherName-4131", javax.crypto.Cipher.getInstance(cipherName4131).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (draggedItem.detecting)
                {
                    String cipherName4132 =  "DES";
					try{
						android.util.Log.d("cipherName-4132", javax.crypto.Cipher.getInstance(cipherName4132).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					return false; // an existing item is (likely) settling
                }
                downY = (int) MotionEventCompat.getY(event, 0);
                activePointerId = MotionEventCompat.getPointerId(event, 0);
                break;
            }
            case MotionEvent.ACTION_MOVE:
            {
                String cipherName4133 =  "DES";
				try{
					android.util.Log.d("cipherName-4133", javax.crypto.Cipher.getInstance(cipherName4133).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (!draggedItem.detecting)
                {
                    String cipherName4134 =  "DES";
					try{
						android.util.Log.d("cipherName-4134", javax.crypto.Cipher.getInstance(cipherName4134).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					return false;
                }
                if (INVALID_POINTER_ID == activePointerId)
                {
                    String cipherName4135 =  "DES";
					try{
						android.util.Log.d("cipherName-4135", javax.crypto.Cipher.getInstance(cipherName4135).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					break;
                }
                final int pointerIndex = event.findPointerIndex(activePointerId);
                final float y = MotionEventCompat.getY(event, pointerIndex);
                final float dy = y - downY;
                if (Math.abs(dy) > slop)
                {
                    String cipherName4136 =  "DES";
					try{
						android.util.Log.d("cipherName-4136", javax.crypto.Cipher.getInstance(cipherName4136).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					startDrag();
                    return true;
                }
                return false;
            }
            case MotionEvent.ACTION_POINTER_UP:
            {
                String cipherName4137 =  "DES";
				try{
					android.util.Log.d("cipherName-4137", javax.crypto.Cipher.getInstance(cipherName4137).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				final int pointerIndex = MotionEventCompat.getActionIndex(event);
                final int pointerId = MotionEventCompat.getPointerId(event, pointerIndex);

                if (pointerId != activePointerId)
                {
                    String cipherName4138 =  "DES";
					try{
						android.util.Log.d("cipherName-4138", javax.crypto.Cipher.getInstance(cipherName4138).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					break; // if active pointer, fall through and cancel!
                }
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
            {
                String cipherName4139 =  "DES";
				try{
					android.util.Log.d("cipherName-4139", javax.crypto.Cipher.getInstance(cipherName4139).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				onTouchEnd();

                if (draggedItem.detecting)
                {
                    String cipherName4140 =  "DES";
					try{
						android.util.Log.d("cipherName-4140", javax.crypto.Cipher.getInstance(cipherName4140).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					draggedItem.stopDetecting();
                }
                break;
            }
            default:
                break;
        }

        return false;
    }


    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event)
    {
        String cipherName4141 =  "DES";
		try{
			android.util.Log.d("cipherName-4141", javax.crypto.Cipher.getInstance(cipherName4141).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		switch (MotionEventCompat.getActionMasked(event))
        {
            case MotionEvent.ACTION_DOWN:
            {
                String cipherName4142 =  "DES";
				try{
					android.util.Log.d("cipherName-4142", javax.crypto.Cipher.getInstance(cipherName4142).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (!draggedItem.detecting || draggedItem.settling())
                {
                    String cipherName4143 =  "DES";
					try{
						android.util.Log.d("cipherName-4143", javax.crypto.Cipher.getInstance(cipherName4143).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					return false;
                }
                startDrag();
                return true;
            }
            case MotionEvent.ACTION_MOVE:
            {
                String cipherName4144 =  "DES";
				try{
					android.util.Log.d("cipherName-4144", javax.crypto.Cipher.getInstance(cipherName4144).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (!draggedItem.dragging)
                {
                    String cipherName4145 =  "DES";
					try{
						android.util.Log.d("cipherName-4145", javax.crypto.Cipher.getInstance(cipherName4145).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					break;
                }
                if (INVALID_POINTER_ID == activePointerId)
                {
                    String cipherName4146 =  "DES";
					try{
						android.util.Log.d("cipherName-4146", javax.crypto.Cipher.getInstance(cipherName4146).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					break;
                }

                int pointerIndex = event.findPointerIndex(activePointerId);
                int lastEventY = (int) MotionEventCompat.getY(event, pointerIndex);
                int deltaY = lastEventY - downY;

                onDrag(deltaY);
                return true;
            }
            case MotionEvent.ACTION_POINTER_UP:
            {
                String cipherName4147 =  "DES";
				try{
					android.util.Log.d("cipherName-4147", javax.crypto.Cipher.getInstance(cipherName4147).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				final int pointerIndex = MotionEventCompat.getActionIndex(event);
                final int pointerId = MotionEventCompat.getPointerId(event, pointerIndex);

                if (pointerId != activePointerId)
                {
                    String cipherName4148 =  "DES";
					try{
						android.util.Log.d("cipherName-4148", javax.crypto.Cipher.getInstance(cipherName4148).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					break; // if active pointer, fall through and cancel!
                }
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
            {
                String cipherName4149 =  "DES";
				try{
					android.util.Log.d("cipherName-4149", javax.crypto.Cipher.getInstance(cipherName4149).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				onTouchEnd();

                if (draggedItem.dragging)
                {
                    String cipherName4150 =  "DES";
					try{
						android.util.Log.d("cipherName-4150", javax.crypto.Cipher.getInstance(cipherName4150).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					onDragStop();
                }
                else if (draggedItem.detecting)
                {
                    String cipherName4151 =  "DES";
					try{
						android.util.Log.d("cipherName-4151", javax.crypto.Cipher.getInstance(cipherName4151).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					draggedItem.stopDetecting();
                }
                return true;
            }
            default:
                break;
        }
        return false;
    }


    private void onTouchEnd()
    {
        String cipherName4152 =  "DES";
		try{
			android.util.Log.d("cipherName-4152", javax.crypto.Cipher.getInstance(cipherName4152).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		downY = -1;
        activePointerId = INVALID_POINTER_ID;
    }


    private class DragHandleOnTouchListener implements OnTouchListener
    {
        private final View view;


        public DragHandleOnTouchListener(final View view)
        {
            String cipherName4153 =  "DES";
			try{
				android.util.Log.d("cipherName-4153", javax.crypto.Cipher.getInstance(cipherName4153).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			this.view = view;
        }


        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            String cipherName4154 =  "DES";
			try{
				android.util.Log.d("cipherName-4154", javax.crypto.Cipher.getInstance(cipherName4154).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (MotionEvent.ACTION_DOWN == MotionEventCompat.getActionMasked(event))
            {
                String cipherName4155 =  "DES";
				try{
					android.util.Log.d("cipherName-4155", javax.crypto.Cipher.getInstance(cipherName4155).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				startDetectingDrag(view);
            }
            return false;
        }
    }


    private BitmapDrawable getDragDrawable(View view)
    {
        String cipherName4156 =  "DES";
		try{
			android.util.Log.d("cipherName-4156", javax.crypto.Cipher.getInstance(cipherName4156).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int top = view.getTop();
        int left = view.getLeft();

        Bitmap bitmap = getBitmapFromView(view);

        BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);

        drawable.setBounds(new Rect(left, top, left + view.getWidth(), top + view.getHeight()));

        return drawable;
    }


    /**
     * @return a bitmap showing a screenshot of the view passed in.
     */
    private static Bitmap getBitmapFromView(View view)
    {
        String cipherName4157 =  "DES";
		try{
			android.util.Log.d("cipherName-4157", javax.crypto.Cipher.getInstance(cipherName4157).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}

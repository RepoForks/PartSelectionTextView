package com.thepacific.partselectiontextview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * @author UsherBaby
 * @version 0.0.0
 * @ModifiedBy
 * @LastModifiedDate 2015年1月19日
 * @Copyright The Pacific organization
 */

public class UIScrollView extends ScrollView {

    public UIScrollView(Context context) {
        this(context, null);
    }

    public UIScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UIScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        /**
         * When in selection state , prevent ScrollView scrolling. The
         * ScrollView doesn't handle the touch event , but passing it to child
         * view.
         */
        if (((SelectionTextView) getChildAt(0)).isAllowSelectText() == true) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

}

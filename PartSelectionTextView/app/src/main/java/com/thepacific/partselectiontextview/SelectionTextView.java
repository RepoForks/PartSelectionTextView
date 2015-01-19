package com.thepacific.partselectiontextview;

import android.content.Context;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * @author UsherBaby
 * @version 0.0.0
 * @ModifiedBy
 * @LastModifiedDate 2015年1月19日
 * @Copyright The Pacific organization
 */

public class SelectionTextView extends TextView {

    private int startIndex = 0;
    private int line = 0;
    private int endIndex = 0;
    private int selectionColor = 0xFF33B5E5;
    private boolean isAllowSelectText = false;
    private boolean isOnScroll = false;
    private int scrollSpeed = 20;
    private SpannableString spannableString;
    private BackgroundColorSpan backgroundColorSpan;
    private Layout layout;
    private ScrollView scrollView;

    public SelectionTextView(Context context) {
        this(context, null);
    }

    public SelectionTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectionTextView(Context context, AttributeSet attrs,
                             int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        backgroundColorSpan = new BackgroundColorSpan(selectionColor);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start,
                                 int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        spannableString = new SpannableString(getText());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        scrollView = (ScrollView) getParent();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isAllowSelectText() == false) return super.onTouchEvent(event);
        layout = getLayout();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                removeSelection();
                try {
                    // 0 = getScrollY()
                    line = layout.getLineForVertical(0 + (int) event.getY());
                    startIndex = layout.getOffsetForHorizontal(line, (int) event.getX());
                } catch (Exception e) {

                }
                isOnScroll = true;
                break;
            case MotionEvent.ACTION_MOVE:
                spannableString.removeSpan(backgroundColorSpan);
                if (isOnScroll) {
                    if (event.getY() > scrollView.getScrollY()
                            + scrollView.getHeight()) {
                        scrollView.smoothScrollTo(0, scrollView.getScrollY()
                                + scrollSpeed); //  0 =scrollView.getScrollX()
                    } else if (event.getY() - scrollView.getScrollY() < 0) {
                        scrollView.smoothScrollTo(0, scrollView.getScrollY()
                                - scrollSpeed); //  0 =scrollView.getScrollX()
                    } else {
                    }
                }
                try {
                    // 0 = getScrollY()
                    line = layout.getLineForVertical(0 + (int) event.getY());
                    endIndex = layout.getOffsetForHorizontal(line, (int) event.getX());
                } catch (Exception e) {

                }
                if (startIndex < endIndex) {
                    spannableString.setSpan(backgroundColorSpan, startIndex,
                            endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    spannableString.setSpan(backgroundColorSpan, endIndex,
                            startIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                setText(spannableString);
                break;
            case MotionEvent.ACTION_UP:
                if (getSelectedText() != null) {
                    onActionUp();
                }
                isOnScroll = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                removeSelection();
                break;
            default:
                break;
        }
        return true;
    }

    public void removeSelection() {
        startIndex = 0;
        endIndex = 0;
        if (spannableString != null && backgroundColorSpan != null) {
            spannableString.removeSpan(backgroundColorSpan);
        }
    }

    public CharSequence getSelectedText() {
        if (startIndex == endIndex)
            return null;
        if (startIndex < endIndex)
            return getText().subSequence(startIndex, endIndex);
        return getText().subSequence(endIndex, startIndex);
    }

    public boolean isAllowSelectText() {
        return isAllowSelectText;
    }

    public void setAllowSelectText(boolean isSelectable) {
        this.isAllowSelectText = isSelectable;
    }

    public void setScrollSpeed(int scrollSpeed) {
        this.scrollSpeed = scrollSpeed;
    }

    public void setSelectionColor(int selectionColor) {
        this.selectionColor = selectionColor;
    }

    /**
     * Usually , you should override this method for what you want after touch
     * action up. Maybe like removing selection , showing pop menu to deal with
     * the selected text.
     */
    public void onActionUp() {
    }

}

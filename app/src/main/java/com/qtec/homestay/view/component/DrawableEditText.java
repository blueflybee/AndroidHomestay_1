package com.qtec.homestay.view.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * <pre>
 *     author : shaojun
 *     e-mail : wusj@qtec.cn
 *     time   : 2018/08/01
 *     desc   : 加强版的EditText,可以响应DrawableLeft 和 DrawableRight的点击事件
 *     version: 1.0
 * </pre>
 */
@SuppressLint("AppCompatCustomView")
public class DrawableEditText extends EditText {
  private DrawableLeftListener mLeftListener ;
  private DrawableRightListener mRightListener ;

  final int DRAWABLE_LEFT = 0;
  final int DRAWABLE_TOP = 1;
  final int DRAWABLE_RIGHT = 2;
  final int DRAWABLE_BOTTOM = 3;

  @SuppressLint("NewApi")
  public DrawableEditText(Context context, AttributeSet attrs, int defStyleAttr,
                   int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  public DrawableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public DrawableEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public DrawableEditText(Context context) {
    super(context);
  }

  public void setDrawableLeftListener(DrawableLeftListener listener) {
    this.mLeftListener = listener;
  }

  public void setDrawableRightListener(DrawableRightListener listener) {
    this.mRightListener = listener;
  }

  public interface DrawableLeftListener {
    void onDrawableLeftClick(View view) ;
  }

  public interface DrawableRightListener {
    void onDrawableRightClick(View view) ;
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_UP:
        if (mRightListener != null) {
          Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT] ;
          if (drawableRight != null && event.getRawX() >= (getRight() - drawableRight.getBounds().width())) {
            mRightListener.onDrawableRightClick(this) ;
            return true ;
          }
        }

        if (mLeftListener != null) {
          Drawable drawableLeft = getCompoundDrawables()[DRAWABLE_LEFT] ;
          if (drawableLeft != null && event.getRawX() <= (getLeft() + drawableLeft.getBounds().width())) {
            mLeftListener.onDrawableLeftClick(this) ;
            return true ;
          }
        }
        break;
    }

    return super.onTouchEvent(event);
  }
}

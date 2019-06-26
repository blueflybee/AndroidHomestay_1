package com.qtec.homestay.utils;

import android.view.View;

/**
 * <pre>
 *     author : shaojun
 *     e-mail : wusj@qtec.cn
 *     time   : 2018/07/31
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class MeasureUtil {
  /**
   * 测量View的宽高
   *
   * @param view View
   */
  public static void measureWidthAndHeight(View view) {
    int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    view.measure(widthMeasureSpec, heightMeasureSpec);
  }

}
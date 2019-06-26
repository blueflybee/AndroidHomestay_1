package com.qtec.homestay.utils;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

import static com.blueflybee.titlebarlib.utils.TitleBarUtils.getDisplayMetrics;

/**
 * @author shaojun
 * @name WidgetUtil
 * @package com.fernandocejas.android10.sample.presentation.view.utils
 * 界面的ui控件辅助工具类
 * @date
 */
public class WidgetUtil {

  /**
   * 获取TextView文本内容
   *
   * @param tv
   * @return
   */
  public static String getText(TextView tv) {
    return tv.getText().toString().trim();
  }

  public static void setText(TextView tv, String txt) {
    tv.setText(txt);
  }

  public static void clearText(TextView tv) {
    setText(tv, "");
  }

  /**
   * 限制 EditText 输入表情,无clear按钮
   *
   * @param editView
   */
  public static void watchEditTextNoClear(EditText editView) {
    editView.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override
      public void afterTextChanged(Editable editable) {
        int index = editView.getSelectionStart() - 1;
        if (index > 0) {
          if (isEmojiCharacter(editable.charAt(index))) {
            Editable edit = editView.getText();
            if (index == 1) {
              edit.clear();
            } else {
              edit.delete(index, index + 1);
            }
          }
        }
      }
    });
  }

  /**
   * 限制 EditText 输入表情,同时显示clear按钮
   *
   * @param editView
   * @param clearView
   */
  public static void watchEditText(EditText editView, ImageView clearView) {
    editView.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s)) {
          clearView.setVisibility(View.GONE);
        } else {
          clearView.setVisibility(View.VISIBLE);
          clearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              editView.getText().clear();
            }
          });
        }
      }

      @Override
      public void afterTextChanged(Editable editable) {
        int index = editView.getSelectionStart() - 1;
        if (index > 0) {
          if (isEmojiCharacter(editable.charAt(index))) {
            Editable edit = editView.getText();
            if (index == 1) {
              edit.clear();
            } else {
              edit.delete(index, index + 1);
            }
          }
        }
      }
    });
  }

  /**
   * 判断是否是表情
   */
  public static boolean isEmojiCharacter(char codePoint) {
    return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
  }

  /**
   * EditText光标移动到最后
   *
   * @param et
   */
  public static void moveEditCursorToEnd(EditText et) {
    Selection.setSelection(et.getText(), et.getText().length());
  }


  /**
   * 修改TabLayout底部导航条Indicator的长短
   * @param context
   * @param tabs
   * @param leftDip
   * @param rightDip
   */
  public static void setIndicator(Context context, TabLayout tabs, int leftDip, int rightDip) {
    Class<?> tabLayout = tabs.getClass();
    Field tabStrip = null;
    try {
      tabStrip = tabLayout.getDeclaredField("mTabStrip");
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }

    tabStrip.setAccessible(true);
    LinearLayout ll_tab = null;
    try {
      ll_tab = (LinearLayout) tabStrip.get(tabs);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    int left = (int) (getDisplayMetrics(context).density * leftDip);
    int right = (int) (getDisplayMetrics(context).density * rightDip);

    for (int i = 0; i < ll_tab.getChildCount(); i++) {
      View child = ll_tab.getChildAt(i);
      child.setPadding(0, 0, 0, 0);
      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
      params.leftMargin = left;
      params.rightMargin = right;
      child.setLayoutParams(params);
      child.invalidate();
    }
  }
}

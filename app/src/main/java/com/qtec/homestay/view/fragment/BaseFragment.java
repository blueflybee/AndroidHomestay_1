/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.qtec.homestay.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.blueflybee.titlebarlib.widget.TitleBar;
import com.qtec.homestay.R;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.internal.di.HasComponent;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.utils.DialogUtil;
import com.qtec.homestay.view.LoadDataView;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.user.login.LoginActivity;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

/**
 * Base {@link Fragment} class for every fragment in this application.
 */
public abstract class BaseFragment extends Fragment implements LoadDataView {

  @Inject
  protected Navigator mNavigator;

  protected TitleBar mTitleBar;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  /**
   * Gets a component for dependency injection by its type.
   */
  @SuppressWarnings("unchecked")
  protected <C> C getComponent(Class<C> componentType) {
    return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
  }

  @Override
  public void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd("MainScreen");//统计时长
  }

  @Override
  public void onResume() {
    super.onResume();
    MobclickAgent.onPageStart("MainScreen");
  }

  protected void initTitleBar() {
    if (mTitleBar == null) {
      mTitleBar = (TitleBar) getActivity().findViewById(R.id.title_bar);
    }
    mTitleBar.setListener((view, action, extra) -> {
      if (action == TitleBar.ACTION_LEFT_TEXT) {
        getActivity().finish();
      }
    });
  }

  protected void initTitleBar(String title) {
    if (mTitleBar == null) {
      mTitleBar = (TitleBar) getActivity().findViewById(R.id.title_bar);
    }
    mTitleBar.getCenterTextView().setText(title);
    mTitleBar.setListener((view, action, extra) -> {
      if (action == TitleBar.ACTION_LEFT_TEXT) {
        getActivity().finish();
      }
    });
  }

  @Override
  public void showLoading() {
    DialogUtil.showProgress((AppCompatActivity) getActivity());
  }

  @Override
  public void hideLoading() {
    DialogUtil.hideProgress();
  }

  @Override
  public Context getContext() {
    return super.getContext();
  }

  @Override
  public void onError(ErrorBundle bundle) {
    String message = bundle.getErrorMessage();
    if (!TextUtils.isEmpty(message)) {
      ToastUtils.showShort(message);
    }
  }

  @Override
  public void showLoginInvalid() {
    DialogUtil.showOkDialog(
        getContext(),
        "登录失效", "您的登录已失效，请重新登录",
        v -> mNavigator.navigateNewAndClearTask(getContext(), LoginActivity.class));
  }

  /**
   * 隐藏或显示密码
   *
   * @param et
   */
  protected void showHidePwd(EditText et) {
    int selectionStart = et.getSelectionStart();
    int hide = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
    if (et.getInputType() == hide) {
      et.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    } else {
      et.setInputType(hide);
    }
    et.setSelection(selectionStart);
  }

  @NonNull
  protected String getText(TextView tv) {
    return tv.getText().toString().trim();
  }

  protected BaseActivity getBaseActivity() {
    return (BaseActivity) getActivity();
  }

}

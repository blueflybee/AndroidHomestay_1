package com.qtec.homestay.view.user.forgetpwd;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityResetPwdBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.internal.di.components.DaggerUserComponent;
import com.qtec.homestay.internal.di.modules.UserModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.utils.InputUtil;
import com.qtec.homestay.utils.WidgetUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.component.InputWatcher;
import com.qtec.homestay.view.user.login.LoginActivity;

import javax.inject.Inject;

/**
 * <pre>
 *      author: xiehao
 *      e-mail: xieh@qtec.cn
 *      time: 2017/06/09
 *      desc: 重置密码页(忘记密码)
 *      version: 1.0
 * </pre>
 */

public class ResetPwdActivity extends BaseActivity implements ResetPwdView {

  private ActivityResetPwdBinding mBinding;

  @Inject
  ResetPwdPresenter mResetPwdPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_reset_pwd);
    initializeInjector();
    initPresenter();
    initView();
  }

  public void sendVerificationCode(View view) {
    mResetPwdPresenter.getIdCode(userPhone());
  }

  private void initView() {
    initTitleBar();

    InputWatcher watcher = new InputWatcher();
    InputWatcher.WatchCondition condition2 = new InputWatcher.WatchCondition();
    condition2.setRange(new InputWatcher.InputRange(6, 20));
    watcher.addEt(mBinding.etPwd, condition2);
    watcher.setInputListener(isEmpty -> {
      mBinding.btnResetPwd.setClickable(!isEmpty);
      if (isEmpty) {
        mBinding.btnResetPwd.setBackgroundResource(R.drawable.shape_rec_btn_disable);
      } else {
        mBinding.btnResetPwd.setBackgroundResource(R.drawable.btn_login_selector);
      }
    });

    InputUtil.inhibit(mBinding.etPwd, InputUtil.REGULAR_CHINESE_AND_SPACE, 20);
    WidgetUtil.watchEditTextNoClear(mBinding.etPwd);

  }

  private String pwd() {
    return mBinding.etPwd.getText().toString();
  }

  private String userPhone() {
    return getIntent().getStringExtra(Navigator.EXTR_RESET_PWD_PHONE);
  }

  private void initPresenter() {
    mResetPwdPresenter.setView(this);
  }

  private void initializeInjector() {
    DaggerUserComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .userModule(new UserModule())
        .build()
        .inject(this);
  }

  public void resetPwd(View view) {
    mResetPwdPresenter.resetPwd(userPhone(), smsCode(), pwd());
  }

  private String smsCode() {
    return getIntent().getStringExtra(Navigator.EXTRA_SMS_CODE);
  }

  public void changeInputType(View view) {
    Object tag = mBinding.ivShowPwd.getTag();
    if ("hide".equals(tag)) {
      mBinding.ivShowPwd.setImageResource(R.mipmap.otheropen);
      mBinding.ivShowPwd.setTag("show");
    } else {
      mBinding.ivShowPwd.setImageResource(R.mipmap.otherbihe);
      mBinding.ivShowPwd.setTag("hide");
    }
    InputUtil.showHidePwd(mBinding.etPwd);
  }

  @Override
  public void onGetIdCodeSuccess() {
  }

  @Override
  public void showResetPwdSuccess() {
    Toast.makeText(this, "重置密码成功", Toast.LENGTH_SHORT).show();
    mNavigator.navigateExistAndClearTop(getContext(), LoginActivity.class);
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
//    mBinding.tvTips.setText(bundle.getErrorMessage());
  }

}

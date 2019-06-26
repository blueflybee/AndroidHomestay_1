package com.qtec.homestay.view.user.forgetpwd;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityResetPwdGetIdCodeBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.mapp.rsp.ResetPwdGetIdCodeResponse;
import com.qtec.homestay.internal.di.components.DaggerUserComponent;
import com.qtec.homestay.internal.di.modules.UserModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.component.InputWatcher;

import javax.inject.Inject;

/**
 * <pre>
 *      author: shaojun
 *      e-mail: wusj@qtec.cn
 *      time: 2017/06/14
 *      desc:
 *      version: 1.1
 * </pre>
 */
public class ResetPwdGetIdCodeActivity extends BaseActivity implements ResetPwdGetIdCodeView {
  private ActivityResetPwdGetIdCodeBinding mBinding;

  @Inject
  ResetPwdGetIdCodePresenter mResetPwdGetIdCodePresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_reset_pwd_get_id_code);

    initializeInjector();

    initPresenter();

    initView();
  }

  private void initView() {
    initTitleBar();

    InputWatcher watcher = new InputWatcher();
    InputWatcher.WatchCondition condition = new InputWatcher.WatchCondition();
    condition.setLength(11);
    watcher.addEt(mBinding.etEnterNum, condition);
    watcher.setInputListener(isEmpty -> {
      mBinding.btnNext.setClickable(!isEmpty);
      if (isEmpty) {
        mBinding.btnNext.setBackgroundResource(R.drawable.shape_rec_btn_disable);
      } else {
        mBinding.btnNext.setBackgroundResource(R.drawable.btn_login_selector);
      }
    });
  }

  private String userPhone() {
    return mBinding.etEnterNum.getText().toString();
  }

  private void initPresenter() {
    mResetPwdGetIdCodePresenter.setView(this);
  }

  private void initializeInjector() {
    DaggerUserComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .userModule(new UserModule())
        .build()
        .inject(this);
  }

  public void clearUsername(View view) {
    mBinding.etEnterNum.setText("");
  }

  public void nextStep(View view) {
    mResetPwdGetIdCodePresenter.getIdCode(userPhone());
  }

  @Override
  public void showGetIdCodeSuccess() {
    Toast.makeText(getContext(), "验证码已经发送到您的手机", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void openResetPwd(ResetPwdGetIdCodeResponse response) {
    Intent intent = new Intent();
    intent.putExtra(Navigator.EXTR_RESET_PWD_PHONE, userPhone());
    mNavigator.navigateTo(getContext(), ResetPwdSmsCodeActivity.class, intent);
  }

  @Override
  public void onError(ErrorBundle bundle) {
    mBinding.tvTips.setText(bundle.getErrorMessage());
  }
}

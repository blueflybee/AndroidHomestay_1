package com.qtec.homestay.view.user.register;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityRegisterBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.mapp.rsp.RegisterResponse;
import com.qtec.homestay.internal.di.components.DaggerUserComponent;
import com.qtec.homestay.internal.di.modules.UserModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.utils.InputUtil;
import com.qtec.homestay.utils.WidgetUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.component.InputWatcher;
import com.qtec.homestay.view.main.MainActivity;

import javax.inject.Inject;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2017/06/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class RegisterActivity extends BaseActivity implements RegisterView {
  private ActivityRegisterBinding mBinding;

  @Inject
  RegisterPresenter mRegisterPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
    initializeInjector();
    initPresenter();
    initView();
  }

  private void initView() {
    initTitleBar();

    InputWatcher watcher = new InputWatcher();
    InputWatcher.WatchCondition condition2 = new InputWatcher.WatchCondition();
    condition2.setRange(new InputWatcher.InputRange(6, 20));
    watcher.addEt(mBinding.etPwd, condition2);
    watcher.setInputListener(isEmpty -> {
      mBinding.btnRegister.setClickable(!isEmpty);
      if (isEmpty) {
        mBinding.btnRegister.setBackgroundResource(R.drawable.shape_rec_btn_disable);
      } else {
        mBinding.btnRegister.setBackgroundResource(R.drawable.btn_login_selector);
      }
    });

    InputUtil.inhibit(mBinding.etPwd, InputUtil.REGULAR_CHINESE_AND_SPACE, 20);

    WidgetUtil.watchEditTextNoClear(mBinding.etPwd);
  }

  public void changeInputType(View view) {
    InputUtil.showHidePwd(mBinding.etPwd);
  }

  public void register(View view) {
    mRegisterPresenter.register(userPhone(), smsCode(), pwd());
  }

  public void sendVerificationCode(View view) {
    mRegisterPresenter.getIdCode(userPhone());
  }

  private String smsCode() {
    return getIntent().getStringExtra(Navigator.EXTRA_SMS_CODE);
  }

  private String pwd() {
    return mBinding.etPwd.getText().toString();
  }

  private String userPhone() {
    return getIntent().getStringExtra(Navigator.EXTRA_REGISTER_PHONE);
  }

  private void initPresenter() {
    mRegisterPresenter.setView(this);
  }

  private void initializeInjector() {
    DaggerUserComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .userModule(new UserModule())
        .build()
        .inject(this);

  }

  @Override
  public void onGetIdCodeSuccess() {
//    ToastUtils.showShort("验证码发送成功");
  }

  @Override
  public void showRegisterSuccess(RegisterResponse response) {
//    DialogUtil.showSuccessDialog(getContext(), "注册成功", () ->
//        mNavigator.navigateTo(getContext(), PersonalProfileActivity.class));
    ToastUtils.showShort("注册成功");
    mNavigator.navigateNewAndClearTask(getContext(), MainActivity.class);
    finish();
  }

//  @Override
//  public void onError(ErrorBundle bundle) {
//    mBinding.tvTips.setText(bundle.getErrorMessage());
//  }

}

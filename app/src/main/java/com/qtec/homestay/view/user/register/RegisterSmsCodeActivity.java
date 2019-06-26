package com.qtec.homestay.view.user.register;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityRegisterBinding;
import com.qtec.homestay.databinding.ActivityRegisterSmsCodeBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.mapp.rsp.CheckCodeResponse;
import com.qtec.homestay.domain.model.mapp.rsp.RegisterResponse;
import com.qtec.homestay.internal.di.components.DaggerUserComponent;
import com.qtec.homestay.internal.di.modules.UserModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.utils.InputUtil;
import com.qtec.homestay.utils.StringUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.component.InputWatcher;
import com.qtec.homestay.view.main.MainActivity;
import com.qtec.homestay.view.user.forgetpwd.ResetPwdActivity;

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
public class RegisterSmsCodeActivity extends BaseActivity implements RegisterView, CheckCodeView {
  private ActivityRegisterSmsCodeBinding mBinding;

  private TimeCounter mTimer;

  @Inject
  RegisterPresenter mRegisterPresenter;

  @Inject
  CheckCodePresenter mCheckCodePresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_sms_code);

    initializeInjector();

    initPresenter();

    initData();

    initView();

  }

  private void initData() {
    mTimer = new TimeCounter(60000, 1000);
    mTimer.start();
  }

  private void initView() {
    initTitleBar();

    mBinding.tvTipTitle.setText("已发送验证码到" + StringUtil.addStar(userPhone()));

    InputWatcher watcher = new InputWatcher();
    InputWatcher.WatchCondition condition1 = new InputWatcher.WatchCondition();
    condition1.setLength(6);
    watcher.addEt(mBinding.etEnterVertifiCode, condition1);
    watcher.setInputListener(isEmpty -> {
      mBinding.btnRegister.setClickable(!isEmpty);
      if (isEmpty) {
        mBinding.btnRegister.setBackgroundResource(R.drawable.shape_rec_btn_disable);
      } else {
        mBinding.btnRegister.setBackgroundResource(R.drawable.btn_login_selector);
      }
    });
  }

  public void register(View view) {
    mCheckCodePresenter.checkCode(userPhone(), smsCode());
  }

  public void sendVerificationCode(View view) {
    mRegisterPresenter.getIdCode(userPhone());
  }

  private String smsCode() {
    return mBinding.etEnterVertifiCode.getText().toString();
  }

  private String userPhone() {
    return getIntent().getStringExtra(Navigator.EXTRA_REGISTER_PHONE);
  }

  private void initPresenter() {
    mRegisterPresenter.setView(this);
    mCheckCodePresenter.setView(this);
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
    ToastUtils.showShort("验证码发送成功");
    mTimer.start();
  }

  @Override
  public void showRegisterSuccess(RegisterResponse response) {
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
//    mBinding.tvTips.setText(bundle.getErrorMessage());
  }

  @Override
  public void onCheckCodeSuccess(CheckCodeResponse response) {
    Intent intent = new Intent();
    intent.putExtra(Navigator.EXTRA_SMS_CODE, smsCode());
    intent.putExtra(Navigator.EXTRA_REGISTER_PHONE, userPhone());
    mNavigator.navigateTo(getContext(), RegisterActivity.class, intent);
  }

  private class TimeCounter extends CountDownTimer {

    TimeCounter(long millisInFuture, long countDownInterval) {
      super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
      mBinding.tvReSendVertifiCode.setTextColor(getResources().getColor(R.color.green_00beaf));
      mBinding.tvReSendVertifiCode.setClickable(false);
      mBinding.tvReSendVertifiCode.setText(millisUntilFinished / 1000 + "s后重新发送");
    }

    @Override
    public void onFinish() {
      mBinding.tvReSendVertifiCode.setText(new SpanUtils()
          .append("收不到验证码？").setForegroundColor(getResources().getColor(R.color.gray_666666))
          .append("重新发送").setForegroundColor(getResources().getColor(R.color.green_00beaf))
          .create());
      mBinding.tvReSendVertifiCode.setClickable(true);
    }
  }

}

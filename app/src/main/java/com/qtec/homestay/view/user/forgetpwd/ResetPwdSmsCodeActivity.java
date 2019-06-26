package com.qtec.homestay.view.user.forgetpwd;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.SpanUtils;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityResetPwdSmsCodeBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.mapp.rsp.CheckCodeResponse;
import com.qtec.homestay.internal.di.components.DaggerUserComponent;
import com.qtec.homestay.internal.di.modules.UserModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.utils.StringUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.component.InputWatcher;
import com.qtec.homestay.view.user.login.LoginActivity;
import com.qtec.homestay.view.user.register.CheckCodePresenter;
import com.qtec.homestay.view.user.register.CheckCodeView;

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

public class ResetPwdSmsCodeActivity extends BaseActivity implements ResetPwdView, CheckCodeView{

  private ActivityResetPwdSmsCodeBinding mBinding;

  @Inject
  ResetPwdPresenter mResetPwdPresenter;
  @Inject
  CheckCodePresenter mCheckCodePresenter;

  private TimeCounter mTimer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_reset_pwd_sms_code);

    initializeInjector();

    initPresenter();

    initData();

    initView();

  }

  private void initData() {
    mTimer = new TimeCounter(60000, 1000);
    mTimer.start();
  }

  public void sendVerificationCode(View view) {
    mResetPwdPresenter.getIdCode(userPhone());
  }

  private void initView() {
    initTitleBar();

    mBinding.tvTipTitle.setText("已发送验证码到" + StringUtil.addStar(userPhone()));

    InputWatcher watcher = new InputWatcher();
    InputWatcher.WatchCondition condition1 = new InputWatcher.WatchCondition();
    condition1.setLength(6);
    watcher.addEt(mBinding.etEnterVertifiCode, condition1);
    watcher.setInputListener(isEmpty -> {
      mBinding.btnResetPwd.setClickable(!isEmpty);
      if (isEmpty) {
        mBinding.btnResetPwd.setBackgroundResource(R.drawable.shape_rec_btn_disable);
      } else {
        mBinding.btnResetPwd.setBackgroundResource(R.drawable.btn_login_selector);
      }
    });
  }

  private String smsCode() {
    return mBinding.etEnterVertifiCode.getText().toString();
  }

  private String userPhone() {
    return getIntent().getStringExtra(Navigator.EXTR_RESET_PWD_PHONE);
  }

  private void initPresenter() {
    mResetPwdPresenter.setView(this);
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

  public void resetPwd(View view) {
    mCheckCodePresenter.checkCode(userPhone(), smsCode());
  }

  @Override
  public void onGetIdCodeSuccess() {
    Toast.makeText(this, "验证码发送成功", Toast.LENGTH_SHORT).show();
    mTimer.start();
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

  @Override
  public void onCheckCodeSuccess(CheckCodeResponse response) {
    Intent intent = new Intent();
    intent.putExtra(Navigator.EXTRA_SMS_CODE, smsCode());
    intent.putExtra(Navigator.EXTR_RESET_PWD_PHONE, userPhone());
    mNavigator.navigateTo(getContext(), ResetPwdActivity.class, intent);
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

package com.qtec.homestay.view.user.register;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityRegisterGetIdCodeBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.mapp.rsp.GetIdCodeResponse;
import com.qtec.homestay.internal.di.components.DaggerUserComponent;
import com.qtec.homestay.internal.di.modules.UserModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.component.InputWatcher;

import javax.inject.Inject;

public class RegisterGetIdCodeActivity extends BaseActivity implements GetIdCodeView {

  @Inject
  GetIdCodePresenter mGetIdCodePresenter;

  private ActivityRegisterGetIdCodeBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_get_id_code);

    initializeInjector();

    initPresenter();

    initView();
  }

  private void initView() {
    initTitleBar();
    mBinding.tvAgree.setText(new SpanUtils()
        .append("点击：“下一步”即代表同意").setForegroundColor(getResources().getColor(R.color.gray_666666))
        .append("《三点智能门锁用户协议》").setForegroundColor(getResources().getColor(R.color.black_333333))
        .create());
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

  private void initPresenter() {
    mGetIdCodePresenter.setView(this);
  }

  private void initializeInjector() {
    DaggerUserComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .userModule(new UserModule())
        .build()
        .inject(this);
  }

  public void nextStep(View view) {
    mGetIdCodePresenter.getIdCode(userPhone());
  }


  @NonNull
  private String userPhone() {
    return mBinding.etEnterNum.getText().toString();
  }

  @Override
  public void showUserPhoneEmp() {
    Toast.makeText(getContext(), "请输入注册帐号", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void openRegister(GetIdCodeResponse response) {
    Intent intent = new Intent();
    intent.putExtra(Navigator.EXTRA_REGISTER_PHONE, userPhone());
    mNavigator.navigateTo(getContext(), RegisterSmsCodeActivity.class, intent);
  }

  @Override
  public void showGetIdCodeSuccess() {
    ToastUtils.showShort("验证码已经发送到您的手机");
  }

  @Override
  public void onError(ErrorBundle bundle) {
    mBinding.tvTips.setText(bundle.getErrorMessage());
  }
}

package com.qtec.homestay.view.user.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.support.annotation.NonNull;

import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityPerfectInfoBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.mapp.rsp.LoginResponse;
import com.qtec.homestay.domain.model.mapp.rsp.WxLoginResponse;
import com.qtec.homestay.internal.di.components.DaggerUserComponent;
import com.qtec.homestay.internal.di.modules.UserModule;
import com.qtec.homestay.utils.InputUtil;
import com.qtec.homestay.utils.WidgetUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.component.InputWatcher;
import com.qtec.homestay.view.main.MainActivity;


import javax.inject.Inject;
/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/24
 *     desc   : PerfectInfoActivity
 * </pre>
 */
public class PerfectInfoActivity extends BaseActivity implements LoginView {

  private static final String TAG = PerfectInfoActivity.class.getName();

  @Inject
  LoginPresenter mLoginPresenter;

  private ActivityPerfectInfoBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_perfect_info);
    initializeInjector();
    initPresenter();
    initData();
    initView();
  }

  private void initializeInjector() {
    DaggerUserComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .userModule(new UserModule())
        .build()
        .inject(this);
  }

  private void initPresenter() {
    mLoginPresenter.setView(this);
  }

  private void initData() {

  }

  private void initView() {
    initTitleBar();
    InputWatcher watcher = new InputWatcher();
    InputWatcher.WatchCondition nameCondition = new InputWatcher.WatchCondition();
    nameCondition.setLength(11);
    InputWatcher.WatchCondition pwdCondition = new InputWatcher.WatchCondition();
    pwdCondition.setRange(new InputWatcher.InputRange(6, 20));
    watcher.addEt(mBinding.etPhone, nameCondition);
    watcher.addEt(mBinding.etPwd, pwdCondition);
    watcher.setInputListener(isEmpty -> {
      mBinding.btnSave.setClickable(!isEmpty);
      if (isEmpty) {
        mBinding.btnSave.setBackgroundResource(R.drawable.shape_rec_btn_disable);
      } else {
        mBinding.btnSave.setBackgroundResource(R.drawable.btn_login_selector);
      }
    });

    InputUtil.inhibit(mBinding.etPwd, InputUtil.REGULAR_CHINESE_AND_SPACE, 20);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mLoginPresenter != null) {
      mLoginPresenter.destroy();
    }
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
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

	public void save(View view) {
    mLoginPresenter.wxLogin("", phone(), pwd());
	}

	@NonNull
	private String phone() {
		return mBinding.etPhone.getText().toString().trim();
	}

	@NonNull
	private String pwd() {
		return mBinding.etPwd.getText().toString().trim();
	}

  @Override
  public void openMain(LoginResponse response) {}

  @Override
  public void showPasswordErrorThreeTimes(Throwable e) {}

  @Override
  public void showPasswordErrorMoreTimes(Throwable e) {}

  @Override
  public void onFirstWxLogin(WxLoginResponse response) {}

  @Override
  public void onWxLoginSuccess(WxLoginResponse response) {
    mNavigator.navigateTo(getContext(), MainActivity.class);
    finish();
  }
}
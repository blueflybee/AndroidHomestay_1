

package com.qtec.homestay.view.user.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.qtec.homestay.AndroidApplication;
import com.qtec.homestay.R;
import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.data.net.CloudUrlPath;
import com.qtec.homestay.databinding.ActivityLoginBinding;
import com.qtec.homestay.domain.model.mapp.rsp.LoginResponse;
import com.qtec.homestay.domain.model.mapp.rsp.WxLoginResponse;
import com.qtec.homestay.internal.di.components.DaggerUserComponent;
import com.qtec.homestay.internal.di.modules.UserModule;
import com.qtec.homestay.utils.ClickUtil;
import com.qtec.homestay.utils.InputUtil;
import com.qtec.homestay.utils.WidgetUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.component.InputWatcher;
import com.qtec.homestay.view.main.MainActivity;
import com.qtec.homestay.view.user.forgetpwd.ResetPwdGetIdCodeActivity;
import com.qtec.homestay.view.user.register.RegisterGetIdCodeActivity;
import com.qtec.homestay.view.user.selectconfig.ServerActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity implements LoginView {

  private static final String TAG = LoginActivity.class.getName();

  @Inject
  LoginPresenter mLoginPresenter;

  private ActivityLoginBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (!isTaskRoot()) {
      finish();
      return;
    }
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

    initializeInjector();

    initPresenter();

    initView();

    mBinding.etName.setText(PrefConstant.getUserPhone());

    mBinding.etPwd.setText("123456");

    checkAutoLogin();

  }

  private void checkAutoLogin() {
    String token = PrefConstant.getAppToken();
    if (!TextUtils.isEmpty(token)) {
      CloudUrlPath.setToken(token);
      mNavigator.navigateTo(getContext(), MainActivity.class);
      finish();
    }
  }

  private void initView() {
    InputWatcher watcher = new InputWatcher();
    InputWatcher.WatchCondition nameCondition = new InputWatcher.WatchCondition();
    nameCondition.setLength(11);
    InputWatcher.WatchCondition pwdCondition = new InputWatcher.WatchCondition();
    pwdCondition.setRange(new InputWatcher.InputRange(6, 20));
    watcher.addEt(mBinding.etName, nameCondition);
    watcher.addEt(mBinding.etPwd, pwdCondition);
    watcher.setInputListener(isEmpty -> {
      mBinding.btnLogin.setClickable(!isEmpty);
      if (isEmpty) {
        mBinding.btnLogin.setBackgroundResource(R.drawable.shape_rec_btn_disable);
      } else {
        mBinding.btnLogin.setBackgroundResource(R.drawable.btn_login_selector);
      }
    });

    InputUtil.inhibit(mBinding.etPwd, InputUtil.REGULAR_CHINESE_AND_SPACE, 20);
    WidgetUtil.watchEditTextNoClear(mBinding.etPwd);
  }


  private void initPresenter() {
    mLoginPresenter.setView(this);
  }

  private void initializeInjector() {
    DaggerUserComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .userModule(new UserModule())
        .build()
        .inject(this);
  }

  public void login(View view) {
    if (ClickUtil.isFastClick()) return;
    mLoginPresenter.login(username(), password());
//    openMain(null);
//    throw new NullPointerException("crash log test!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
  }

  public void forgetPwd(View view) {
    mNavigator.navigateTo(this, ResetPwdGetIdCodeActivity.class);
  }

  public void register(View view) {
    mNavigator.navigateTo(this, RegisterGetIdCodeActivity.class);
  }

  public void changeInputType(View view) {
    Object tag = mBinding.ivShowPwd.getTag();
    if ("hide".equals(tag)) {
      mBinding.ivShowPwd.setImageResource(R.mipmap.login_open);
      mBinding.ivShowPwd.setTag("show");
    } else {
      mBinding.ivShowPwd.setImageResource(R.mipmap.login_bihe);
      mBinding.ivShowPwd.setTag("hide");
    }
    InputUtil.showHidePwd(mBinding.etPwd);
  }

  @NonNull
  private String password() {
    return getText(mBinding.etPwd);
  }

  @NonNull
  private String username() {
    return getText(mBinding.etName);
  }

  @Override
  public void openMain(LoginResponse response) {
    mNavigator.navigateTo(getContext(), MainActivity.class);
    finish();
  }

  @Override
  public void showPasswordErrorThreeTimes(Throwable e) {
//    DialogUtil.showPwdErrThreeTimesDialog(getContext(), new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
////        mNavigator.navigateTo(getContext(), ResetPwdGetIdCodeActivity.class);
//      }
//    });
  }

  @Override
  public void showPasswordErrorMoreTimes(Throwable e) {
//    DialogUtil.showCancelConfirmWithTipsDialog(getContext(),
//        "提示", "短时间内登录次数过多，请半小时后重试",
//        "您可以重置密码立即登录", "知道了", "重置密码", new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
////            mNavigator.navigateTo(getContext(), ResetPwdGetIdCodeActivity.class);
//          }
//        });
  }

  @Override
  public void onFirstWxLogin(WxLoginResponse response) {
    mNavigator.navigateTo(getContext(), PerfectInfoActivity.class);
  }

  @Override
  public void onWxLoginSuccess(WxLoginResponse response) {
    mNavigator.navigateTo(getContext(), MainActivity.class);
    finish();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mLoginPresenter != null) {
      mLoginPresenter.destroy();
    }
  }

  public void selectServer(View view) {
    mNavigator.navigateTo(getContext(), ServerActivity.class);
  }

  public void selectEncryption(View view) {
//    mNavigator.navigateTo(getContext(), SelectLockEncryptionActivity.class);
  }

  public void weChat(View view) {
    wxLogin();
  }

  private void wxLogin() {
    IWXAPI wxApi = ((AndroidApplication) getApplication()).getWxApi();
    if (!wxApi.isWXAppInstalled()) {
      ToastUtils.showShort("您还未安装微信客户端");
      return;
    }
    final SendAuth.Req req = new SendAuth.Req();
    req.scope = "snsapi_userinfo";
    req.state = "wechat_sdk_demo_test";
    wxApi.sendReq(req);
    mLoginPresenter.setWxLogin(true);
  }

  @Override
  protected void onResume() {
    super.onResume();
    System.out.println("wxLogin = " + mLoginPresenter.isWxLogin());
    if (mLoginPresenter.isWxLogin()) {
      mLoginPresenter.setWxLogin(false);
      String wxCode = PrefConstant.getWXCode();
      System.out.println("wxCode = " + wxCode);
      if (TextUtils.isEmpty(wxCode)) return;
      mLoginPresenter.wxLogin(wxCode, "", "");
    }

  }
}

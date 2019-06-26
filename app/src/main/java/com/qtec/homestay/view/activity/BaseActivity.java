package com.qtec.homestay.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blueflybee.blelibrary.BleRequest;
import com.blueflybee.blelibrary.BleService;
import com.blueflybee.blelibrary.IBle;
import com.blueflybee.titlebarlib.widget.TitleBar;
import com.qtec.homestay.AndroidApplication;
import com.qtec.homestay.R;
import com.qtec.homestay.data.data.BleLock;
import com.qtec.homestay.data.mapper.BleMapper;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.lock.model.core.BleBody;
import com.qtec.homestay.internal.di.components.ApplicationComponent;
import com.qtec.homestay.internal.di.modules.ActivityModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.utils.DialogUtil;
import com.qtec.homestay.view.LoadDataView;
import com.qtec.homestay.view.ViewServer;
import com.qtec.homestay.view.user.login.LoginActivity;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

//import com.umeng.analytics.MobclickAgent;

/**
 * Base {@link Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity implements LoadDataView {

  @Inject
  protected Navigator mNavigator;

  protected TitleBar mTitleBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getApplicationComponent().inject(this);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
    getWindow().setBackgroundDrawable(null);
    ViewServer.get(this).addWindow(this);
  }

  /**
   * Adds a {@link Fragment} to this activity's file_paths.
   *
   * @param containerViewId The container view to where add the fragment.
   * @param fragment        The fragment to be added.
   */
  protected void addFragment(int containerViewId, Fragment fragment) {
    FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
    fragmentTransaction.add(containerViewId, fragment);
    fragmentTransaction.commit();
  }

  /**
   * Get the Main Application component for dependency injection.
   *
   * @return {@link ApplicationComponent}
   */
  protected ApplicationComponent getApplicationComponent() {
    return ((AndroidApplication) getApplication()).getApplicationComponent();
  }

  /**
   * Get an Activity module for dependency injection.
   *
   * @return {@link ActivityModule}
   */
  protected ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }

  public Navigator getNavigator() {
    return mNavigator;
  }


  @Override
  protected void onResume() {
    super.onResume();
    MobclickAgent.onPageStart("SplashScreen");//统计页面
    MobclickAgent.onResume(this); //统计时长
    ViewServer.get(this).setFocusedWindow(this);
  }


  @Override
  protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd("SplashScreen");
    MobclickAgent.onPause(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    ViewServer.get(this).removeWindow(this);
  }

  protected void initTitleBar() {
    if (mTitleBar == null) {
      mTitleBar = (TitleBar) findViewById(R.id.title_bar);
    }
    mTitleBar.setListener((view, action, extra) -> {
      if (action == TitleBar.ACTION_LEFT_TEXT) {
        finish();
      }
    });
  }

  protected void initTitleBar(String title) {
    if (mTitleBar == null) {
      mTitleBar = (TitleBar) findViewById(R.id.title_bar);
    }
    mTitleBar.getCenterTextView().setText(title);
    mTitleBar.setListener((view, action, extra) -> {
      if (action == TitleBar.ACTION_LEFT_TEXT) {
        finish();
      }
    });
  }

  @Override
  public void showLoading() {
    DialogUtil.showProgress(this);
  }

  @Override
  public void hideLoading() {
    DialogUtil.hideProgress();
  }

  protected void hideSoftKeyBoard() {
    new Handler().postDelayed(
        () -> KeyboardUtils.hideSoftInput(this), 200);
  }

  @Override
  public Context getContext() {
    return this;
  }

  /*
   * 为子类提供权限检查方法
   * */
  protected boolean hasPermission(String... permissions) {
    for (String permission : permissions) {
      if (ContextCompat.checkSelfPermission(this, permission)
          != PackageManager.PERMISSION_GRANTED) {
        return false;
      }
    }
    return true;
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
        v -> mNavigator.navigateNewAndClearTask(BaseActivity.this.getContext(), LoginActivity.class));
  }

  @NonNull
  protected String getText(TextView tv) {
    return tv.getText().toString().trim();
  }

  protected BleRequest.RequestType getBleRequestType(Intent intent) {
    return (BleRequest.RequestType) intent.getSerializableExtra(BleService.EXTRA_REQUEST);
  }

  protected BleRequest.FailReason getBleFailReason(Intent intent) {
    return (BleRequest.FailReason) intent.getSerializableExtra(BleService.EXTRA_REASON);
  }

  public BleRequest.CmdType getBleCmdType(Intent intent) {
    return (BleRequest.CmdType) intent.getSerializableExtra(BleService.EXTRA_CMD_TYPE);
  }

  protected int getBleStatus(Intent intent) {
    return intent.getIntExtra(BleService.EXTRA_STATUS, -100);
  }

  public IBle getBle() {
    return ((AndroidApplication) getApplication()).getIBle();
  }

  protected void restartBle() {
    IBle ble = getBle();
    if (ble == null) return;
    ble.disable();
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        ble.enable();
      }
    }, 2000);
  }

  protected void openBle() {
    IBle ble = getBle();
    if (ble == null) return;
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        ble.enable();
      }
    }, 2000);
  }

  protected void close(String address) {
    IBle ble = getBle();
    if (ble != null && !TextUtils.isEmpty(address)) {
      ble.close(address);
    }
  }

  protected void disconnect(String address) {
    IBle ble = getBle();
    if (ble != null && !TextUtils.isEmpty(address)) {
      ble.disconnect(address);
    }
  }

  protected void closeAll() {
    IBle ble = getBle();
    if (ble != null) {
      ble.closeAll();
    }
  }

  public boolean bleRspFail(String code, boolean show, String lockMacAddress) {
    if (!BleBody.RSP_OK.equals(code)) {
      String msg;
      switch (code) {
        case BleBody.RSP_INVALID_USER:
          msg = "无效的用户id";
//          LockManager.deleteKeyRepo(getContext(), lockMacAddress);
//          mNavigator.navigateExistAndClearTop(getContext(), MainActivity.class);
          break;

        case BleBody.RSP_PWD_DUPLICATE:
          msg = "密码已存在";
          break;

        case BleBody.RSP_PWD_IS_FULL:
          msg = "密码达到上限";
          break;

        case BleBody.RSP_FP_IS_FULL:
          msg = "指纹达到上限";
          break;

        case BleBody.RSP_CARD_IS_FULL:
          msg = "卡片达到上限";
          break;

        case BleBody.RSP_LOCK_KEY_INVALID:
        case BleBody.RSP_ILLEGAL_CMD:
          msg = "门锁密钥失效";
//          Intent intent = new Intent();
//          intent.putExtra(Navigator.EXTRA_BLE_DEVICE_ADDRESS, lockMacAddress);
//          intent.setAction(BleMapper.ACTION_BLE_LOCK_KEY_INVALID);
//          sendBroadcast(intent);
          break;

        case BleBody.RSP_NO_ZIGBEE:
        case BleBody.RSP_NO_MAC_ADDRESS:
          msg = "门锁硬件错误，请联系售后支持";
          break;

        case BleBody.RSP_SYS_LOCKED:
          msg = "系统锁定，请稍候再试";
          break;

        case BleBody.RSP_UPDATING:
          msg = "门锁升级中";
          break;

        case BleBody.RSP_NO_SUCH_CMD:
          msg = "无效的门锁操作，请升级门锁固件";
          break;

        case BleBody.RSP_LOCK_FACTORY_RESET:
//          BleLock lock = LockManager.getLock(getContext(), lockMacAddress);
//          msg = "";
//          if (lock != null) {
            msg = "门锁已经恢复出厂设置";
//            Intent lockIntent = new Intent();
//            lockIntent.putExtra(Navigator.EXTRA_BLE_DEVICE_ADDRESS, lockMacAddress);
//            mNavigator.navigateTo(getContext(), LockFactoryResetActivity.class, lockIntent);
//          }
          break;

        default:
          msg = "";
          break;
      }
      if (!show) return true;
      if (!TextUtils.isEmpty(msg)) {
        ToastUtils.showShort(msg);
      }
      return true;
    }
    return false;
  }
}

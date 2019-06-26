package com.qtec.homestay.view.user.mine;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.AppUtils;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityVersionInfoBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.mapp.rsp.CheckAppVersionResponse;
import com.qtec.homestay.internal.di.components.DaggerUserComponent;
import com.qtec.homestay.internal.di.modules.UserModule;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.lodge.AppUtil;


import javax.inject.Inject;

/**
 * <pre>
 *     author : author
 *     e-mail :
 *     time   : 2018/07/30
 *     desc   : VersionInfoActivity
 * </pre>
 */
public class VersionInfoActivity extends BaseActivity implements VersionInfoView {

  private static final String TAG = VersionInfoActivity.class.getName();

  @Inject
  VersionInfoPresenter mVersionInfoPresenter;

  private ActivityVersionInfoBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_version_info);
    initializeInjector();
    initPresenter();
    initData();
    initView();

    mVersionInfoPresenter.checkVersion();
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
    mVersionInfoPresenter.setView(this);
  }

  private void initData() {

  }

  private void initView() {
    initTitleBar("检测更新");
    mBinding.tvVersionName.setText("版本号：v" + AppUtils.getAppVersionName() + " [" + AppUtils.getAppVersionCode() + "]");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mVersionInfoPresenter != null) {
      mVersionInfoPresenter.destroy();
    }
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
  }

  public void update(View view) {
    mVersionInfoPresenter.update();
  }

  @Override
  public void showVersionInfo(CheckAppVersionResponse response) {
    int newVersion = response.getVersionNum();
    int currentVersion = AppUtils.getAppVersionCode();
    System.out.println("版本 newVersion = " + newVersion);
    System.out.println("版本 currentVersion = " + currentVersion);
    boolean canUpdate = newVersion > currentVersion;
    System.out.println("版本 canUpdate = " + canUpdate);
    mBinding.tvVersionInfo.setText(canUpdate ? "发现新版本 "
        + response.getVersionNo()
        + "[code = " + response.getVersionNum()
        + ",mini = " + response.getMinVersion() + "]" : "当前已是最新版本！");
    mBinding.btnUpdate.setVisibility(canUpdate ? View.VISIBLE : View.GONE);

  }
}
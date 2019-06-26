package com.qtec.homestay.view.device.router;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityRouterSetBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.mapp.rsp.AddDevResponse;
import com.qtec.homestay.domain.model.mapp.rsp.UnbindDevResponse;
import com.qtec.homestay.internal.di.components.DaggerRouterComponent;
import com.qtec.homestay.internal.di.modules.RouterModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.utils.DialogUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.device.activity.UpdateDevicePresenter;
import com.qtec.homestay.view.device.activity.UpdateDeviceView;
import com.qtec.homestay.view.device.data.Device;
import com.qtec.homestay.view.main.MainActivity;


import javax.inject.Inject;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/07
 *     desc   : RouterSetActivity
 * </pre>
 */
public class RouterSetActivity extends BaseActivity implements UpdateDeviceView {

  private static final String TAG = RouterSetActivity.class.getName();

  @Inject
  UpdateDevicePresenter mUpdateDevicePresenter;

  private ActivityRouterSetBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_router_set);
    initializeInjector();
    initPresenter();
    initData();
    initView();
  }

  private void initializeInjector() {
    DaggerRouterComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .routerModule(new RouterModule())
        .build()
        .inject(this);
  }

  private void initPresenter() {
    mUpdateDevicePresenter.setView(this);
  }

  private void initData() {

  }

  private void initView() {
    initTitleBar("网关设置");
    mBinding.tvName.setText(device().getName());
    mBinding.tvId.setText(device().getId());
    mBinding.tvModel.setText(device().getModel());
    mBinding.tvType.setText(device().getTypeName());
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mUpdateDevicePresenter != null) {
      mUpdateDevicePresenter.destroy();
    }
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
  }

  public void unbind(View view) {
    DialogUtil.showOkCancelDialog(getContext(), "删除网关", "确定删除此网关吗？", v -> {
      mUpdateDevicePresenter.unbind(device().getId());
    });
  }

  @Override
  public void onAddDevice(AddDevResponse response) {
  }

  @Override
  public void onUnbindDevice(UnbindDevResponse response) {
    ToastUtils.showShort("解绑网关成功");
    Intent intent = new Intent();
    intent.putExtra(Navigator.EXTRA_MAIN_PAGE_INDEX, 1);
    mNavigator.navigateExistAndClearTop(getContext(), MainActivity.class, intent);
    finish();
  }

  private Device device() {
    Device data = (Device) getIntent().getSerializableExtra(Navigator.EXTRA_DEVICE);
    return data == null ? new Device() : data;
  }

  public void configWifi(View view) {
    device().setBind(true);
    mNavigator.navigateTo(getContext(), ConfigRouterActivity.class, getIntent());
  }
}
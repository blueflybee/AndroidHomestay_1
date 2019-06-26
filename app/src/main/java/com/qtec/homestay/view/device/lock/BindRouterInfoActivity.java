package com.qtec.homestay.view.device.lock;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityBindRouterInfoBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.mapp.rsp.GetDeviceDetailResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevsResponse;
import com.qtec.homestay.internal.di.components.DaggerRouterComponent;
import com.qtec.homestay.internal.di.modules.RouterModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.utils.DialogUtil;
import com.qtec.homestay.utils.WidgetUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.device.activity.DeviceDetailPresenter;
import com.qtec.homestay.view.device.activity.DeviceDetailView;
import com.qtec.homestay.view.device.activity.ZigbeePresenter;
import com.qtec.homestay.view.device.activity.ZigbeeView;
import com.qtec.homestay.view.device.data.Device;
import com.qtec.homestay.view.device.data.Lock;


import javax.inject.Inject;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/09
 *     desc   : BindRouterInfoActivity
 * </pre>
 */
public class BindRouterInfoActivity extends BaseActivity implements DeviceDetailView, ZigbeeView {

  private static final String TAG = BindRouterInfoActivity.class.getName();

  @Inject
  DeviceDetailPresenter mGetDeviceDetailPresenter;

  @Inject
  ZigbeePresenter mZigbeePresenter;

  private ActivityBindRouterInfoBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_bind_router_info);
    initializeInjector();
    initPresenter();
    initData();
    initView();

    mGetDeviceDetailPresenter.getDeviceDetail(lock().getMasterSerialNo());
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
    mGetDeviceDetailPresenter.setView(this);
    mZigbeePresenter.setView(this);
  }

  private void initData() {
    System.out.println("lock() = " + lock());
  }

  private void initView() {
    initTitleBar("组网网关");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mGetDeviceDetailPresenter.destroy();
    mZigbeePresenter.destroy();
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
  }

  public void unbind(View view) {
    DialogUtil.showOkCancelDialog(getContext(), "门锁解绑网关", "确定解绑该网关吗？", v -> {
      String routerId = WidgetUtil.getText(mBinding.tvId);
      if (TextUtils.isEmpty(routerId)) {
        ToastUtils.showShort("未知网关");
        return;
      }
      mZigbeePresenter.unbind(lock().getId(), routerId);
    });
  }

  private Lock lock() {
    Lock data = (Lock) getIntent().getSerializableExtra(Navigator.EXTRA_DEVICE);
    return data == null ? new Lock() : data;
  }

  @Override
  public void showBindFail(String msg) {
  }

  @Override
  public void showBindSuccess() {
  }

  @Override
  public void showUnbindFail(String msg) {
    ToastUtils.showShort(msg);
  }

  @Override
  public void showUnbindSuccess() {
    DialogUtil.showZigbeeNetDialog(getContext(), "解除绑定", "门锁与网关正在解除绑定，请耐心等待", 10000, () -> {
      mZigbeePresenter.query(lock().getId(), WidgetUtil.getText(mBinding.tvId));
    });
  }

  @Override
  public void showQuerySuccess() {
    ToastUtils.showShort("门锁与网关解除绑定成功");
    finish();
  }

  @Override
  public void showDeviceDetail(GetDeviceDetailResponse response) {
    mBinding.tvName.setText(response.getDeviceName());
    mBinding.tvId.setText(response.getDeviceSerialNo());
    mBinding.tvModel.setText(response.getDeviceModel());
  }
}
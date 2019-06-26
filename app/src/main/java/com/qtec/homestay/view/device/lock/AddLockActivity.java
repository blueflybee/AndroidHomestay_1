package com.qtec.homestay.view.device.lock;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.qtec.homestay.R;
import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.databinding.ActivityAddLockBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.mapp.req.AddDevRequest;
import com.qtec.homestay.domain.model.mapp.rsp.AddDevResponse;
import com.qtec.homestay.domain.model.mapp.rsp.UnbindDevResponse;
import com.qtec.homestay.internal.di.components.DaggerRouterComponent;
import com.qtec.homestay.internal.di.modules.RouterModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.utils.DialogUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.component.InputWatcher;
import com.qtec.homestay.view.device.activity.UpdateDevicePresenter;
import com.qtec.homestay.view.device.activity.UpdateDeviceView;
import com.qtec.homestay.view.device.data.Device;


import javax.inject.Inject;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/07
 *     desc   : AddLockActivity
 * </pre>
 */
public class AddLockActivity extends BaseActivity implements UpdateDeviceView {

  private static final String TAG = AddLockActivity.class.getName();

  @Inject
  UpdateDevicePresenter mUpdateDevicePresenter;

  private ActivityAddLockBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_lock);
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
    initTitleBar("添加门锁");
    mBinding.tvName.setText(device().getName());
    mBinding.tvId.setText(device().getId());
    mBinding.tvModel.setText(device().getModel());
    mBinding.tvType.setText(device().getTypeName());

    InputWatcher watcher = new InputWatcher();
    InputWatcher.WatchCondition condition = new InputWatcher.WatchCondition();
    condition.setMinLength(1);
    watcher.addEt(mBinding.etRoom, condition);
    watcher.setInputListener(isEmpty -> {
      mBinding.btnAddLock.setClickable(!isEmpty);
      if (isEmpty) {
        mBinding.btnAddLock.setBackgroundResource(R.drawable.shape_rec_btn_disable);
      } else {
        mBinding.btnAddLock.setBackgroundResource(R.drawable.btn_login_selector);
      }
    });
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
    showRetry();
  }

  private void showRetry() {
    DialogUtil.showBindLockDialog(getContext(), view -> {
      addLock(null);
    });
  }

  public void addLock(View view) {
    AddDevRequest request = new AddDevRequest();
    request.setDeviceSerialNo(device().getId());
    request.setDeviceName(device().getName());
    request.setDeviceType(device().getType());
    request.setDeviceModel(device().getModel());
    request.setDeviceVersion(device().getVersion());
    request.setMac(device().getMac());
    request.setBluetoothName(device().getBleName());
    request.setRoomNo(room());
    request.setUserUniqueKey(PrefConstant.getUserUniqueKey());
    mUpdateDevicePresenter.add(request);
  }

  @NonNull
  private String room() {
    return mBinding.etRoom.getText().toString().trim();
  }

  @Override
  public void onAddDevice(AddDevResponse response) {
    ToastUtils.showShort("门锁绑定成功");
    Intent intent = new Intent();
    intent.putExtra(Navigator.EXTRA_BACK_TO_DEVICE_FRAGMENT, true);
    mNavigator.navigateTo(getContext(), LockListActivity.class, intent);
  }

  @Override
  public void onUnbindDevice(UnbindDevResponse response) {}

  private Device device() {
    Device data = (Device) getIntent().getSerializableExtra(Navigator.EXTRA_DEVICE);
    return data == null ? new Device() : data;
  }
}
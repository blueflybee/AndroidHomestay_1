package com.qtec.homestay.view.device.lock;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;

import com.blueflybee.titlebarlib.widget.TitleBar;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityLockBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.mapp.rsp.GetDeviceDetailResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetHoldDetailResponse;
import com.qtec.homestay.internal.di.components.DaggerLodgeComponent;
import com.qtec.homestay.internal.di.modules.LodgeModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.device.activity.DeviceDetailPresenter;
import com.qtec.homestay.view.device.activity.DeviceDetailView;
import com.qtec.homestay.view.device.data.Lock;
import com.qtec.homestay.view.lodge.GetHolderDetailPresenter;
import com.qtec.homestay.view.lodge.HouseholdDetailView;

import javax.inject.Inject;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/03
 *     desc   : LockActivity
 * </pre>
 */
public class LockActivity extends BaseActivity implements HouseholdDetailView, DeviceDetailView {

  private static final String TAG = LockActivity.class.getName();

  private ActivityLockBinding mBinding;

  @Inject
  GetHolderDetailPresenter mGetHolderDetailPresenter;
  @Inject
  DeviceDetailPresenter mGetDeviceDetailPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_lock);
    initializeInjector();
    initPresenter();
    initData();
    initView();
    mGetHolderDetailPresenter.getHolderDetail(lock().getId());
  }

  private void initPresenter() {
    mGetHolderDetailPresenter.setView(this);
    mGetDeviceDetailPresenter.setView(this);
  }

  private void initializeInjector() {
    DaggerLodgeComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .lodgeModule(new LodgeModule())
        .build()
        .inject(this);
  }

  private void initData() {

  }

  private void initView() {
    initTitleBar(lock().getName());
    boolean onLine = "1".equals(lock().getNetworkStatus());
    initBatteryView(onLine);
    mBinding.tvOnLine.setVisibility(onLine ? View.VISIBLE : View.GONE);
    mBinding.ivStatus.setBackgroundResource(onLine ? R.mipmap.lock_off : R.mipmap.lock_lx);
    mBinding.tvRoomNo.setText(lock().getRoomNo());

    mBinding.tvGuest.setText(lock().getResidentName());
    mBinding.tvDate.setText(lock().getStartTime());
    mBinding.tvPwd.setText(lock().getLockPass());
  }

  private void initBatteryView(boolean onLine) {
    if (!onLine) return;
    String batteryPower = lock().getBatteryPower();
    float batteryPix = -1;
    try {
      int batteryInt = Integer.parseInt(batteryPower);
      if (batteryInt < 0 || batteryInt > 100) return;
      batteryPix = (1 - ((float) batteryInt / 100)) * 35 + 6;
    } catch (NumberFormatException e) {
      e.printStackTrace();
      return;
    }
    if (batteryPix == -1) return;

    mBinding.tvBattery.setVisibility(View.VISIBLE);
    mBinding.tvBattery.setText(batteryPower + "%");
    //35
    LayerDrawable drawable = (LayerDrawable) getResources().getDrawable(R.drawable.shape_lock_battery);
    drawable.setLayerInset(1, 6, (int) batteryPix, 6, 6);
    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
    mBinding.tvBattery.setCompoundDrawables(drawable, null, null, null);
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
  }

  public void showAdminPwd(View view) {
    mNavigator.navigateTo(getContext(), AdminPwdActivity.class, getIntent());
  }

  public void showRecord(View view) {
    mNavigator.navigateTo(getContext(), UnlockRecordsActivity.class, getIntent());
  }

  @Override
  protected void initTitleBar(String title) {
    super.initTitleBar(title);
    mTitleBar.setListener((view, action, extra) -> {
      if (action == TitleBar.ACTION_LEFT_TEXT) {
        finish();
      } else if (action == TitleBar.ACTION_RIGHT_BUTTON) {
        mNavigator.navigateTo(getContext(), LockSetActivity.class, getIntent());
      }
    });
  }

  private Lock lock() {
    Lock data = (Lock) getIntent().getSerializableExtra(Navigator.EXTRA_DEVICE);
    return data == null ? new Lock() : data;
  }

  @Override
  public void holdDetail(GetHoldDetailResponse response) {
    mBinding.tvGuest.setText(response.getResidentName());
    mBinding.tvDate.setText(response.getStartTime());
    mBinding.tvPwd.setText(response.getLockPass());
  }

  @Override
  protected void onResume() {
    super.onResume();
    mGetDeviceDetailPresenter.getDeviceDetail(lock().getId());
  }

  @Override
  public void showDeviceDetail(GetDeviceDetailResponse response) {
    boolean onLine = "1".equals(response.getNetworkStatus());
    mBinding.tvBattery.setVisibility(onLine ? View.VISIBLE : View.GONE);
    mBinding.tvOnLine.setVisibility(onLine ? View.VISIBLE : View.GONE);
    mBinding.ivStatus.setBackgroundResource(onLine ? R.mipmap.lock_off : R.mipmap.lock_lx);

    initTitleBar(response.getDeviceName());
  }
}
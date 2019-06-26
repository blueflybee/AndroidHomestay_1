package com.qtec.homestay.view.lodge;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.blueflybee.titlebarlib.widget.TitleBar;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityResidentDetailBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.interactor.cloud.ModifyResident;
import com.qtec.homestay.domain.model.mapp.rsp.GetHoldDetailResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetRoomManageResponse;
import com.qtec.homestay.domain.model.mapp.rsp.LeaveHotelResponse;
import com.qtec.homestay.internal.di.components.DaggerLodgeComponent;
import com.qtec.homestay.internal.di.modules.LodgeModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.utils.BeanConvertUtil;
import com.qtec.homestay.utils.DialogUtil;
import com.qtec.homestay.utils.StringUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.device.data.Lock;
import com.qtec.homestay.view.device.lock.LockActivity;


import javax.inject.Inject;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/09/05
 *     desc   : ResidentDetailActivity
 * </pre>
 */
public class ResidentDetailActivity extends BaseActivity implements LeaveHotelView, HouseholdDetailView {

  private static final String TAG = ResidentDetailActivity.class.getName();

  @Inject
  LeaveHotelPresenter mLeaveHotelPresenter;

  @Inject
  GetHolderDetailPresenter mGetHolderDetailPresenter;

  private ActivityResidentDetailBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_resident_detail);
    initializeInjector();
    initPresenter();
    initData();
    initView();
  }

  private void initializeInjector() {
    DaggerLodgeComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .lodgeModule(new LodgeModule())
        .build()
        .inject(this);
  }

  private void initPresenter() {
    mLeaveHotelPresenter.setView(this);
    mGetHolderDetailPresenter.setView(this);
  }

  private void initData() {

  }

  private void initView() {
    initTitleBar("客户详情");
    mBinding.tvName.setText(resident().getDeviceName());
    boolean onLine = "1".equals(resident().getNetworkStatus());
    if (onLine) {
      Drawable drawable = getResources().getDrawable(R.mipmap.home_online);
      drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
      mBinding.tvName.setCompoundDrawablePadding(20);
      mBinding.tvName.setCompoundDrawables(null, null, drawable, null);
    }
    mBinding.tvDetail.setText(StringUtil.getResidentInfo(resident()));

  }

  @Override
  protected void onRestart() {
    super.onRestart();
    mGetHolderDetailPresenter.getHolderDetail(resident().getDeviceSerialNo());
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mLeaveHotelPresenter != null) {
      mLeaveHotelPresenter.destroy();
    }
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
  }

  public void checkOut(View view) {
    DialogUtil.showOkCancelDialog(getContext(), "离店", StringUtil.getCheckOutInfo(resident()), v -> {
      mLeaveHotelPresenter.checkOut(resident().getDeviceSerialNo());
    });
  }

  public void showLock(View view) {
    Intent intent = new Intent();
    intent.putExtra(Navigator.EXTRA_DEVICE, BeanConvertUtil.getLock(resident()));
    mNavigator.navigateTo(getContext(), LockActivity.class, intent);
  }

  @Override
  public void onCheckOut(LeaveHotelResponse response) {
    Intent intent = new Intent();
    intent.putExtra(Navigator.EXTRA_ROOM_NO, resident().getRoomNo());
    mNavigator.navigateTo(getContext(), LeaveHotelSuccessActivity.class, intent);
  }

  private GetRoomManageResponse resident() {
    GetRoomManageResponse data = (GetRoomManageResponse) getIntent().getSerializableExtra(Navigator.EXTRA_RESIDENT);
    return data == null ? new GetRoomManageResponse() : data;
  }

  @Override
  protected void initTitleBar(String title) {
    super.initTitleBar(title);
    mTitleBar.getRightTextView().setText("编辑");
    mTitleBar.setListener((view, action, extra) -> {
      if (action == TitleBar.ACTION_LEFT_TEXT) {
        finish();
      } else if (action == TitleBar.ACTION_RIGHT_TEXT) {
        mNavigator.navigateTo(getContext(), ModifyResidentActivity.class, getIntent());
      }
    });
  }

  @Override
  public void holdDetail(GetHoldDetailResponse response) {
    resident().setResidentName(response.getResidentName());
    resident().setResidentIdentifier(response.getResidentIdentifier());
    resident().setResidentPhone(response.getResidentPhone());
    mBinding.tvDetail.setText(StringUtil.getResidentInfo(resident()));
  }
}
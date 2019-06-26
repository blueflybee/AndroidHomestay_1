package com.qtec.homestay.view.lodge;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.qtec.homestay.R;
import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.databinding.ActivityCheckInRoomBinding;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.mapp.req.GetHoldDetailRequest;
import com.qtec.homestay.domain.model.mapp.rsp.GetHoldDetailResponse;
import com.qtec.homestay.internal.di.components.DaggerLodgeComponent;
import com.qtec.homestay.internal.di.modules.LodgeModule;
import com.qtec.homestay.utils.CalendarUtil;
import com.qtec.homestay.view.activity.BaseActivity;

import java.util.List;

import javax.inject.Inject;

public class HouseholdDetailsActivity extends BaseActivity implements HouseholdDetailView {
  private static final String TAG = HouseholdDetailsActivity.class.getName();

  private ActivityCheckInRoomBinding mBinding;

  @Inject
  GetHolderDetailPresenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_check_in_room);

    initializeInjector();

    initPresenter();

    initView();

  }

  private void initView() {
    initTitleBar("客户详情");

  }

  private void initPresenter() {
     mPresenter.setView(this);
  }

  private void initializeInjector() {
    DaggerLodgeComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .lodgeModule(new LodgeModule())
        .build()
        .inject(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mPresenter != null) {
      mPresenter.destroy();
    }
  }

  public void checkInRoom(View view) {
    mPresenter.getHolderDetail("123456");
  }

  @Override
  public void holdDetail(GetHoldDetailResponse response) {
    Toast.makeText(this, "获取用户详情", Toast.LENGTH_SHORT).show();
  }

}

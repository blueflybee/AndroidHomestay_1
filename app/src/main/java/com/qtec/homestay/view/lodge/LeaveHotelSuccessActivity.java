package com.qtec.homestay.view.lodge;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.blueflybee.titlebarlib.widget.TitleBar;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityLeavlHotelSuccessBinding;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.main.MainActivity;

public class LeaveHotelSuccessActivity extends BaseActivity {
  private static final String TAG = LeaveHotelSuccessActivity.class.getName();
  private ActivityLeavlHotelSuccessBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_leavl_hotel_success);
    initView();
  }

  private String getRoomNo() {
    return getIntent().getStringExtra(Navigator.EXTRA_ROOM_NO);
  }

  private void initView() {
    initTitleBar("离店成功");
    mBinding.tvInvalid.setText(getRoomNo() + "房间门锁密码失效");
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    mNavigator.navigateTo(getContext(), MainActivity.class);
  }

  @Override
  protected void initTitleBar(String title) {
    super.initTitleBar(title);
    mTitleBar.setListener((view, action, extra) -> {
      if (action == TitleBar.ACTION_LEFT_TEXT) {
        mNavigator.navigateTo(getContext(), MainActivity.class);
      }
    });
  }
}
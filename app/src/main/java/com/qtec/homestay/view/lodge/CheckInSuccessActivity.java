package com.qtec.homestay.view.lodge;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.blueflybee.titlebarlib.widget.TitleBar;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityCheckInSuccessBinding;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.main.MainActivity;

public class CheckInSuccessActivity extends BaseActivity {
  private static final String TAG = CheckInSuccessActivity.class.getName();
  private ActivityCheckInSuccessBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_check_in_success);
    initView();
  }

  private void initView() {
    initTitleBar("入住成功");
    mBinding.tvPwd.setText(getPwd());
  }

  private String getPwd() {
    return getIntent().getStringExtra("PWD");
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

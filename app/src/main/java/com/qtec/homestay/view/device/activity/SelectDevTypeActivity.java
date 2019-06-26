package com.qtec.homestay.view.device.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivitySelectDevTypeBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.utils.DialogUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.device.lock.SearchLockActivity;
import com.qtec.homestay.view.device.scan.activity.ScanActivity;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/01
 *     desc   : SelectDevTypeActivity
 * </pre>
 */
public class SelectDevTypeActivity extends BaseActivity {

  private static final String TAG = SelectDevTypeActivity.class.getName();

  private ActivitySelectDevTypeBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_dev_type);
    initData();
    initView();
  }

  private void initData() {

  }

  private void initView() {
    initTitleBar("添加设备");
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
  }

  public void addRouter(View view) {
    mNavigator.navigateTo(getContext(), ScanActivity.class);
  }

  public void addLock(View view) {
    mNavigator.navigateTo(getContext(), SearchLockActivity.class);
  }

}
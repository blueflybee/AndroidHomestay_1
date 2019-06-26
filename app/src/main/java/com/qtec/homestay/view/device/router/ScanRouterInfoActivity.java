package com.qtec.homestay.view.device.router;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.support.annotation.NonNull;

import com.blueflybee.titlebarlib.widget.TitleBar;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityScanRouterInfoBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.device.data.Device;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/06
 *     desc   : ScanRouterInfoActivity
 * </pre>
 */
public class ScanRouterInfoActivity extends BaseActivity {

  private static final String TAG = ScanRouterInfoActivity.class.getName();

  private ActivityScanRouterInfoBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_scan_router_info);
    initData();
    initView();
  }

  private void initData() {

  }

  private void initView() {
    initTitleBar("添加网关");
    mBinding.tvName.setText(device().getName());
    mBinding.tvId.setText(device().getId());
    mBinding.tvModel.setText(device().getModel());
    mBinding.tvType.setText(device().getTypeName());
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
  }

	public void addRouter(View view) {
    mNavigator.navigateTo(getContext(), ConfigRouterActivity.class, getIntent());
	}

	private Device device() {
	  Device data = (Device) getIntent().getSerializableExtra(Navigator.EXTRA_DEVICE);
	  return data == null ? new Device() : data;
	}

  @Override
  protected void initTitleBar(String title) {
    super.initTitleBar(title);
    mTitleBar.getRightTextView().setText("网关列表");
    mTitleBar.setListener((view, action, extra) -> {
      if (action == TitleBar.ACTION_LEFT_TEXT) {
        finish();
      } else if (action == TitleBar.ACTION_RIGHT_TEXT) {
        mNavigator.navigateTo(getContext(), RouterListActivity.class);
      }
    });
  }
}
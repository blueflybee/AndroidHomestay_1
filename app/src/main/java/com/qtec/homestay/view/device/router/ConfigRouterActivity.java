package com.qtec.homestay.view.device.router;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.support.annotation.NonNull;

import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityConfigRouterBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.view.activity.BaseActivity;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/06
 *     desc   : ConfigRouterActivity
 * </pre>
 */
public class ConfigRouterActivity extends BaseActivity {

  private static final String TAG = ConfigRouterActivity.class.getName();

  private ActivityConfigRouterBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_config_router);
    initData();
    initView();
  }

  private void initData() {

  }

  private void initView() {
    initTitleBar("配置网关");
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
  }

	public void next(View view) {
    mNavigator.navigateTo(getContext(), ConfigRouterWifiActivity.class, getIntent());
	}


}
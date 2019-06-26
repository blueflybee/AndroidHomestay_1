package com.qtec.homestay.view.user.mine;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.AppUtils;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityAboutUsBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.view.activity.BaseActivity;

/**
 * <pre>
 *     author : author
 *     e-mail :
 *     time   : 2018/07/30
 *     desc   : AboutUsActivity
 * </pre>
 */
public class AboutUsActivity extends BaseActivity {

  private static final String TAG = AboutUsActivity.class.getName();

  private ActivityAboutUsBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_about_us);
    initData();
    initView();
  }

  private void initData() {

  }

  private void initView() {
    initTitleBar("关于我们");
    mBinding.tvVersion.setText("版本号：v" + AppUtils.getAppVersionName());
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
  }

	public void checkUpdate(View view) {
		mNavigator.navigateTo(getContext(), VersionInfoActivity.class);
	}

	public void agree(View view) {
		
	}


}
package com.qtec.homestay.view.device.lock;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.support.annotation.NonNull;

import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityAdminPwdBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.mapp.rsp.GetDeviceDetailResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevsResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevsResponse.DeviceDetailBean;
import com.qtec.homestay.internal.di.components.DaggerRouterComponent;
import com.qtec.homestay.internal.di.modules.RouterModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.device.activity.DeviceDetailPresenter;
import com.qtec.homestay.view.device.activity.DeviceDetailView;
import com.qtec.homestay.view.device.data.Lock;

import javax.inject.Inject;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/13
 *     desc   : AdminPwdActivity
 * </pre>
 */
public class AdminPwdActivity extends BaseActivity implements DeviceDetailView{

  private static final String TAG = AdminPwdActivity.class.getName();

  private ActivityAdminPwdBinding mBinding;
  private DeviceDetailBean mDetailBean;

  @Inject
  DeviceDetailPresenter mGetDeviceDetailPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_admin_pwd);
    initializeInjector();
    initPresenter();
    initData();
    initView();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mGetDeviceDetailPresenter.getDeviceDetail(lock().getId());
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
    mGetDeviceDetailPresenter.setView(this);
  }

  private void initData() {
  }

  private void initView() {
    initTitleBar("管理员密码");
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
  }

	public void modifyPwd(View view) {
    mNavigator.navigateTo(getContext(), ModifyAdminPwdActivity.class, getIntent());
	}

  private Lock lock() {
    Lock data = (Lock) getIntent().getSerializableExtra(Navigator.EXTRA_DEVICE);
    return data == null ? new Lock() : data;
  }

  @Override
  public void showDeviceDetail(GetDeviceDetailResponse response) {
    mDetailBean = DeviceDetailBean.fromJson(response.getDeviceDetail());
    mBinding.tvPwd.setText(mDetailBean.getLockPass());
  }
}
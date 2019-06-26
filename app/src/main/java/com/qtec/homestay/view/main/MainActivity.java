package com.qtec.homestay.view.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.blankj.utilcode.util.AppUtils;
import com.qtec.homestay.AndroidApplication;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityMainBinding;
import com.qtec.homestay.domain.model.mapp.rsp.CheckAppVersionResponse;
import com.qtec.homestay.internal.di.HasComponent;
import com.qtec.homestay.internal.di.components.DaggerMainComponent;
import com.qtec.homestay.internal.di.components.MainComponent;
import com.qtec.homestay.internal.di.modules.UserModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.utils.DialogUtil;
import com.qtec.homestay.utils.WidgetUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.device.fragment.DeviceFragment;
import com.qtec.homestay.view.user.mine.MineFragment;
import com.qtec.homestay.view.user.mine.VersionInfoPresenter;
import com.qtec.homestay.view.user.mine.VersionInfoView;

import javax.inject.Inject;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 18-7-16
 *     desc   : presenter
 *     version: 1.0
 * </pre>
 */
public class MainActivity extends BaseActivity implements HasComponent<MainComponent>, VersionInfoView {

  private static final String TAG = MainActivity.class.getName();

  private static int TAB_MARGIN_DIP = 30;

  @Inject
  VersionInfoPresenter mVersionInfoPresenter;

  private ActivityMainBinding mBinding;

  private MainComponent mMainComponent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    initializeInjector();
    initPresenter();
    initData();
    initView();
    checkAppVersion();
  }

  private void initializeInjector() {
    this.mMainComponent = DaggerMainComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .userModule(new UserModule())
        .build();
    this.mMainComponent.inject(this);
  }

  private void initPresenter() {
    mVersionInfoPresenter.setView(this);
  }

  private void initData() {

  }

  private void initView() {
    mBinding.viewpager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
    mBinding.viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mBinding.tablayout));
    mBinding.tablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mBinding.viewpager));
    mBinding.tablayout.setSmoothScrollingEnabled(true);
    mBinding.viewpager.setCurrentItem(0);
    WidgetUtil.setIndicator(getContext(), mBinding.tablayout, TAB_MARGIN_DIP, TAB_MARGIN_DIP);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mVersionInfoPresenter.destroy();
  }

  private void checkAppVersion() {
    if (!VersionInfoPresenter.isChecked()) {
      mVersionInfoPresenter.checkVersion();
      VersionInfoPresenter.setIsChecked(true);
    }
  }

  @Override
  public void showVersionInfo(CheckAppVersionResponse response) {
    int newVersion = response.getVersionNum();
    int currentVersion = AppUtils.getAppVersionCode();
    if (newVersion <= currentVersion) return;
    int minVersion = response.getMinVersion();
//    ToastUtils.showShort("发现新版本");
    DialogUtil.showUpdateDialog(
        getContext(),
        currentVersion < minVersion,
//        response.getVersionNo(),
        response.getVersionStatement(),
        v -> mVersionInfoPresenter.update());
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    System.out.println("MainActivity.onNewIntent");
    int page = intent.getIntExtra(Navigator.EXTRA_MAIN_PAGE_INDEX, 0);
    mBinding.viewpager.setCurrentItem(page);
  }

  @Override
  public MainComponent getComponent() {
    return mMainComponent;
  }

  @Override
  public void onBackPressed() {
    DialogUtil.showOkCancelDialog(getContext(), "退出应用", "您确定要退出应用吗？", v -> {
      ((AndroidApplication) getApplication()).unbindBleService();
      super.onBackPressed();
      AppUtils.exitApp();
    });
  }

  private class MainPagerAdapter extends FragmentPagerAdapter {

    MainPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      switch (position) {
        case 0:
          return HomeFragment.newInstance();
        case 1:
          return DeviceFragment.newInstance();
        case 2:
          return MineFragment.newInstance();
        default:
          return null;
      }
    }

    @Override
    public int getCount() {
      return mBinding.tablayout.getTabCount();
    }
  }
}

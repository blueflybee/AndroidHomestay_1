package com.qtec.homestay.view.device.lock;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityUnlockRecordsBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevsResponse;
import com.qtec.homestay.internal.di.HasComponent;
import com.qtec.homestay.internal.di.components.DaggerLockComponent;
import com.qtec.homestay.internal.di.components.LockComponent;
import com.qtec.homestay.internal.di.modules.LockModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.utils.WidgetUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.device.activity.DeviceRecordPresenter;
import com.qtec.homestay.view.device.data.Lock;
import com.qtec.homestay.view.device.lock.fragment.AbnormalUnlockRecordsFragment;
import com.qtec.homestay.view.device.lock.fragment.AllUnlockRecordsFragment;

import javax.inject.Inject;
/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/13
 *     desc   : UnlockRecordsActivity
 * </pre>
 */
public class UnlockRecordsActivity extends BaseActivity implements HasComponent<LockComponent> {

  private static final String TAG = UnlockRecordsActivity.class.getName();

  private LockComponent mLockComponent;

  private ActivityUnlockRecordsBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_unlock_records);
    initializeInjector();
    initData();
    initView();
  }

  private void initializeInjector() {
    this.mLockComponent = DaggerLockComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .lockModule(new LockModule())
        .build();
    this.mLockComponent.inject(this);
  }

  private void initData() {

  }

  private void initView() {
    initTitleBar("开锁记录");
    mBinding.viewpager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    mBinding.viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mBinding.tablayout));
    mBinding.tablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mBinding.viewpager));
    mBinding.tablayout.setSmoothScrollingEnabled(true);
    mBinding.viewpager.setCurrentItem(0);
    WidgetUtil.setIndicator(getContext(),mBinding.tablayout,50, 50);
  }

  public Lock lock() {
    Lock data = (Lock) getIntent().getSerializableExtra(Navigator.EXTRA_DEVICE);
    return data == null ? new Lock() : data;
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
  }

  @Override
  public LockComponent getComponent() {
    return mLockComponent;
  }

  private class MyPagerAdapter extends FragmentPagerAdapter {

    MyPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      switch (position) {
        case 0:
          return AllUnlockRecordsFragment.newInstance();
        case 1:
          return AbnormalUnlockRecordsFragment.newInstance();
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
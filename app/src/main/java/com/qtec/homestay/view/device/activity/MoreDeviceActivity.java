package com.qtec.homestay.view.device.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.blueflybee.uilibrary2.data.TopologyNode;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityMoreDeviceBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevsResponse;
import com.qtec.homestay.internal.di.components.DaggerRouterComponent;
import com.qtec.homestay.internal.di.modules.RouterModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.utils.BeanConvertUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.device.adapter.DeviceListAdapter;
import com.qtec.homestay.view.device.data.Device;
import com.qtec.homestay.view.device.data.Lock;
import com.qtec.homestay.view.device.lock.LockActivity;
import com.qtec.homestay.view.device.router.RouterSetActivity;


import java.util.List;

import javax.inject.Inject;

import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/16
 *     desc   : MoreDeviceActivity
 * </pre>
 */
public class MoreDeviceActivity extends BaseActivity implements GetDeviceView {

  private static final String TAG = MoreDeviceActivity.class.getName();

  @Inject
  GetDevicePresenter mGetDevicePresenter;

  private ActivityMoreDeviceBinding mBinding;
  private String mLimit = "-1";
  private DeviceListAdapter mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_more_device);
    initializeInjector();
    initPresenter();
    initData();
    initView();

    getDevices();

  }

  private void getDevices() {
    mGetDevicePresenter.getDevs(deviceType(), 10, mLimit);
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
    mGetDevicePresenter.setView(this);
  }

  private void initData() {

  }

  private void initView() {
    initTitleBar(Device.TYPE_ROUTER.equals(deviceType()) ? "网关" : "门锁");

    mBinding.ptrLayout.setLastUpdateTimeRelateObject(this);
    mBinding.ptrLayout.setDurationToClose(200);
    mBinding.ptrLayout.disableWhenHorizontalMove(true);//解决横向滑动冲突
    mBinding.ptrLayout.setPtrHandler(new PtrDefaultHandler2() {
      @Override
      public void onRefreshBegin(PtrFrameLayout frame) {
        frame.postDelayed(() -> {
          mBinding.ptrLayout.refreshComplete();
          mLimit = "-1";
          getDevices();
        }, 1000);
      }

      @Override
      public void onLoadMoreBegin(PtrFrameLayout frame) {
        frame.postDelayed(() -> {
          mBinding.ptrLayout.refreshComplete();
          getDevices();
        }, 1000);
      }

      @Override
      public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
        return super.checkCanDoLoadMore(frame, mBinding.list, footer);
      }

      @Override
      public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return super.checkCanDoRefresh(frame, mBinding.list, header);
      }
    });
    mAdapter = new DeviceListAdapter(getContext());
    mBinding.list.setAdapter(mAdapter);
    mBinding.list.setOnItemClickListener((parent, view, position, id) -> {
      GetDevsResponse item = mAdapter.getItem(position);
      Intent intent = new Intent();
      if (Device.TYPE_ROUTER.equals(deviceType())) {
        Device device = new Device();
        device.setId(item.getDeviceSerialNo());
        device.setName(item.getDeviceName());
        device.setModel("SD001");
        device.setType(Device.TYPE_ROUTER);
        device.setTypeName("智能锁网关");
        device.setBind(true);
        intent.putExtra(Navigator.EXTRA_DEVICE, device);
        mNavigator.navigateTo(getContext(), RouterSetActivity.class, intent);
      } else {
        Lock lock = BeanConvertUtil.getLock(item);
        intent.putExtra(Navigator.EXTRA_DEVICE, lock);
        mNavigator.navigateTo(getContext(), LockActivity.class, intent);
      }

    });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mGetDevicePresenter != null) {
      mGetDevicePresenter.destroy();
    }
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
    mBinding.ptrLayout.refreshComplete();
  }

  @Override
  public void showDevices(List<GetDevsResponse> responses, String devType) {
    mBinding.ptrLayout.refreshComplete();
    if ("-1".equals(mLimit)) {
      mAdapter.update(responses);
    } else {
      mAdapter.add(responses);
    }
    if (responses == null || responses.isEmpty()) return;
    GetDevsResponse lastRsp = responses.get(responses.size() - 1);
    mLimit = lastRsp.getId();
  }

  @Override
  public void showTopology(TopologyNode nodes) {

  }

  private String deviceType() {
    return getIntent().getStringExtra(Navigator.EXTRA_DEV_TYPE);
  }

}
package com.qtec.homestay.view.device.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.blueflybee.uilibrary2.data.TopologyNode;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityTopologyBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevsResponse;
import com.qtec.homestay.internal.di.components.DaggerRouterComponent;
import com.qtec.homestay.internal.di.modules.RouterModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.device.data.Device;
import com.qtec.homestay.view.device.data.Lock;
import com.qtec.homestay.view.device.lock.LockActivity;
import com.qtec.homestay.view.device.router.RouterSetActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/09/18
 *     desc   : TopologyActivity
 * </pre>
 */
public class TopologyActivity extends BaseActivity  implements GetDeviceView{

  private static final String TAG = TopologyActivity.class.getName();

  private ActivityTopologyBinding mBinding;
  @Inject
  GetDevicePresenter mGetDevicePresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_topology);
    initializeInjector();
    initPresenter();
    initData();
    initView();
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
    initTitleBar("拓扑图");
    mBinding.viewTopology.setOnItemClickListener(node -> {
      if (Device.TYPE_ROUTER.equals(node.getType())) {
        Device device = new Device();
        device.setId(node.getId());
        device.setName("三点安全网关");
        device.setModel("SD001");
        device.setType(Device.TYPE_ROUTER);
        device.setTypeName("智能锁网关");
        device.setBind(true);
        Intent intent = new Intent();
        intent.putExtra(Navigator.EXTRA_DEVICE, device);
        mNavigator.navigateTo(getContext(), RouterSetActivity.class, intent);
      } else if (Device.TYPE_LOCK.equals(node.getType())) {
        Lock lock = new Lock();
        lock.setId(node.getId());
        lock.setRoomNo(node.getNodeNo());
        Intent intent = new Intent();
        intent.putExtra(Navigator.EXTRA_DEVICE, lock);
        mNavigator.navigateTo(getContext(), LockActivity.class, intent);
      }
    });
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mGetDevicePresenter.getDevTree();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mGetDevicePresenter != null) {
      mGetDevicePresenter.destroy();
    }
  }

  @Override
  public void showDevices(List<GetDevsResponse> responses, String devType) {}

  @Override
  public void showTopology(TopologyNode node) {
    mBinding.viewTopology.setNodes(node);
    mBinding.viewTopology.invalidate();
    mBinding.viewTopology.requestLayout();
  }
}
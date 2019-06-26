package com.qtec.homestay.view.device.router;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.blueflybee.uilibrary2.data.TopologyNode;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityRouterListBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevsResponse;
import com.qtec.homestay.internal.di.components.DaggerRouterComponent;
import com.qtec.homestay.internal.di.modules.RouterModule;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.device.activity.GetDevicePresenter;
import com.qtec.homestay.view.device.activity.GetDeviceView;
import com.qtec.homestay.view.device.adapter.RouterListAdapter;


import java.util.List;

import javax.inject.Inject;
/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/02
 *     desc   : RouterListActivity
 * </pre>
 */
public class RouterListActivity extends BaseActivity implements GetDeviceView {

  private static final String TAG = RouterListActivity.class.getName();

  @Inject
  GetDevicePresenter mGetDevicePresenter;

  private ActivityRouterListBinding mBinding;
  private RouterListAdapter mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_router_list);
    initializeInjector();
    initPresenter();
    initData();
    initView();

    mGetDevicePresenter.getDevs("0", 1000, "-1");
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
    initTitleBar("网关列表");
    mAdapter = new RouterListAdapter(getContext());
    mBinding.listRouter.setAdapter(mAdapter);
  }
//
//  private List<GetDevsResponse> getRouters() {
//    ArrayList<GetDevsResponse> devsResponses = new ArrayList<>();
//    for (int i = 0; i < 20; i++) {
//      GetDevsResponse dev = new GetDevsResponse();
//      dev.setDeviceNickName("安全网关00" + i);
//      devsResponses.add(dev);
//    }
//    return devsResponses;
//  }

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
  }

  @Override
  public void showDevices(List<GetDevsResponse> responses, String devType) {
    mAdapter.update(responses);
  }

  @Override
  public void showTopology(TopologyNode nodes) {

  }
}
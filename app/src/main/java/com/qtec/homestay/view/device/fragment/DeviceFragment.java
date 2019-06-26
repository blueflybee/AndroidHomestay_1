package com.qtec.homestay.view.device.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.blueflybee.titlebarlib.widget.TitleBar;
import com.blueflybee.uilibrary2.data.TopologyNode;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.FragmentDeviceBinding;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevsResponse;
import com.qtec.homestay.internal.di.components.MainComponent;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.utils.BeanConvertUtil;
import com.qtec.homestay.view.device.activity.GetDevicePresenter;
import com.qtec.homestay.view.device.activity.GetDeviceView;
import com.qtec.homestay.view.device.activity.MoreDeviceActivity;
import com.qtec.homestay.view.device.activity.SelectDevTypeActivity;
import com.qtec.homestay.view.device.activity.TopologyActivity;
import com.qtec.homestay.view.device.adapter.DeviceListAdapter;
import com.qtec.homestay.view.device.data.Device;
import com.qtec.homestay.view.device.data.Lock;
import com.qtec.homestay.view.device.lock.LockActivity;
import com.qtec.homestay.view.device.router.RouterSetActivity;
import com.qtec.homestay.view.fragment.BaseFragment;

import java.util.List;

import javax.inject.Inject;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/02
 *     desc   : DeviceFragment
 * </pre>
 */
public class DeviceFragment extends BaseFragment implements GetDeviceView {

  private static final String TAG = DeviceFragment.class.getName();

  @Inject
  GetDevicePresenter mGetDevicePresenter;

  private FragmentDeviceBinding mBinding;
  private DeviceListAdapter mRouterAdapter;
  private DeviceListAdapter mLockAdapter;

  public static DeviceFragment newInstance() {
    Bundle args = new Bundle();
    DeviceFragment fragment = new DeviceFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getComponent(MainComponent.class).inject(this);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_device, container, false);
    return mBinding.getRoot();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initPresenter();
    initView();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initTitleBar();
  }

  private void initPresenter() {
    mGetDevicePresenter.setView(this);
  }

  private void initView() {
    mBinding.tvRouterMore.setOnClickListener(v -> {
      Intent intent = new Intent();
      intent.putExtra(Navigator.EXTRA_DEV_TYPE, Device.TYPE_ROUTER);
      mNavigator.navigateTo(getContext(), MoreDeviceActivity.class, intent);
    });

    mBinding.tvLockMore.setOnClickListener(v -> {
      Intent intent = new Intent();
      intent.putExtra(Navigator.EXTRA_DEV_TYPE, Device.TYPE_LOCK);
      mNavigator.navigateTo(getContext(), MoreDeviceActivity.class, intent);
    });

    mRouterAdapter = new DeviceListAdapter(getContext());
    mBinding.listRouter.setAdapter(mRouterAdapter);
    mBinding.listRouter.setOnItemClickListener((parent, view, position, id) -> {
      GetDevsResponse item = mRouterAdapter.getItem(position);
      Device device = new Device();
      device.setId(item.getDeviceSerialNo());
      device.setName(item.getDeviceName());
      device.setModel("SD001");
      device.setType(Device.TYPE_ROUTER);
      device.setTypeName("智能锁网关");
      device.setBind(true);
      Intent intent = new Intent();
      intent.putExtra(Navigator.EXTRA_DEVICE, device);
      mNavigator.navigateTo(getContext(), RouterSetActivity.class, intent);
    });


    mLockAdapter = new DeviceListAdapter(getContext());
    mBinding.listLock.setAdapter(mLockAdapter);
    mBinding.listLock.setOnItemClickListener((parent, view, position, id) -> {
      Lock lock = BeanConvertUtil.getLock(mLockAdapter.getItem(position));
      Intent intent = new Intent();
      intent.putExtra(Navigator.EXTRA_DEVICE, lock);
      mNavigator.navigateTo(getContext(), LockActivity.class, intent);
    });
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
  }

  @Override
  protected void initTitleBar() {
    mBinding.titleBar.setListener((view, action, extra) -> {
      if (action == TitleBar.ACTION_LEFT_TEXT) {
        mNavigator.navigateTo(getContext(), TopologyActivity.class);
      } else if (action == TitleBar.ACTION_RIGHT_TEXT) {
        mNavigator.navigateTo(getContext(), SelectDevTypeActivity.class);
      }
    });
  }

  @Override
  public void showDevices(List<GetDevsResponse> responses, String devType) {
    if (Device.TYPE_ROUTER.equals(devType)) {
      mRouterAdapter.update(responses);
    } else if (Device.TYPE_LOCK.equals(devType)) {
      mLockAdapter.update(responses);
    }
  }

  @Override
  public void showTopology(TopologyNode node) {}

  @Override
  public void onResume() {
    super.onResume();
    mGetDevicePresenter.getDevs(Device.TYPE_ROUTER, 3, "-1");
    mGetDevicePresenter.getDevs(Device.TYPE_LOCK, 3, "-1");
  }
}
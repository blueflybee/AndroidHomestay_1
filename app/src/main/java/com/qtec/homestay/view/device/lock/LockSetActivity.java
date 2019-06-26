package com.qtec.homestay.view.device.lock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.blueflybee.blelibrary.BleGattCharacteristic;
import com.blueflybee.blelibrary.BleGattService;
import com.blueflybee.blelibrary.BleRequest;
import com.blueflybee.blelibrary.BleService;
import com.blueflybee.blelibrary.IBle;
import com.blueflybee.uilibrary2.data.TopologyNode;
import com.qtec.homestay.R;
import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.data.mapper.BleMapper;
import com.qtec.homestay.databinding.ActivityLockSetBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.lock.model.core.BleBody;
import com.qtec.homestay.domain.model.lock.model.core.BlePkg;
import com.qtec.homestay.domain.model.mapp.rsp.AddDevResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetDeviceDetailResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevsResponse;
import com.qtec.homestay.domain.model.mapp.rsp.UnbindDevResponse;
import com.qtec.homestay.internal.di.components.DaggerRouterComponent;
import com.qtec.homestay.internal.di.modules.RouterModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.utils.DialogUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.device.activity.DeviceDetailPresenter;
import com.qtec.homestay.view.device.activity.DeviceDetailView;
import com.qtec.homestay.view.device.activity.GetDevicePresenter;
import com.qtec.homestay.view.device.activity.GetDeviceView;
import com.qtec.homestay.view.device.activity.UpdateDevicePresenter;
import com.qtec.homestay.view.device.activity.UpdateDeviceView;
import com.qtec.homestay.view.device.activity.ZigbeePresenter;
import com.qtec.homestay.view.device.activity.ZigbeeView;
import com.qtec.homestay.view.device.data.Device;
import com.qtec.homestay.view.device.data.Lock;
import com.qtec.homestay.view.device.lock.utils.BleLockUtils;
import com.qtec.homestay.view.main.MainActivity;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import static com.blueflybee.blelibrary.BleRequest.CmdType.BIND_ROUTER_ZIGBEE;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/08
 *     desc   : LockSetActivity
 * </pre>
 */
public class LockSetActivity extends BaseActivity implements UpdateDeviceView, DeviceDetailView, GetDeviceView, ZigbeeView {

  private static final String TAG = LockSetActivity.class.getName();

  @Inject
  UpdateDevicePresenter mUpdateDevicePresenter;
  @Inject
  DeviceDetailPresenter mGetDeviceDetailPresenter;
  @Inject
  GetDevicePresenter mGetDevicePresenter;
  @Inject
  ZigbeePresenter mZigbeePresenter;

  private ActivityLockSetBinding mBinding;

  private GetDevsResponse mRouter;
  protected String mDeviceAddress;
  private BlePkg mPkg;
  private BleRequest.CmdType mCmdType;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_lock_set);
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
    mUpdateDevicePresenter.setView(this);
    mGetDeviceDetailPresenter.setView(this);
    mGetDevicePresenter.setView(this);
    mZigbeePresenter.setView(this);
  }

  private void initData() {

  }

  private void initView() {
    initTitleBar("门锁设置");

    mBinding.tvName.setText(lock().getName());
    mBinding.tvId.setText(lock().getId());

    mBinding.tvType.setText("智能门锁");
    mBinding.tvRoom.setText(lock().getRoomNo());

  }

  @Override
  protected void onResume() {
    super.onResume();
    mGetDeviceDetailPresenter.getDeviceDetail(lock().getId());
    registerReceiver(mBleReceiver, BleService.getIntentFilter());
  }

  @Override
  protected void onPause() {
    super.onPause();
    unregisterReceiver(mBleReceiver);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mUpdateDevicePresenter.destroy();
    mGetDeviceDetailPresenter.destroy();
    mGetDevicePresenter.destroy();
    mZigbeePresenter.destroy();
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
  }

  public void unbind(View view) {
    DialogUtil.showOkCancelDialog(getContext(), "解绑门锁", "确定解绑该门锁吗？", v -> {
      mUpdateDevicePresenter.unbind(lock().getId());
    });
  }

  public void showRouter(View view) {
    String masterSerialNo = lock().getMasterSerialNo();
    if (TextUtils.isEmpty(masterSerialNo)) {
      mGetDevicePresenter.getDevs(Device.TYPE_ROUTER, 1000, "-1");

    } else {
      mNavigator.navigateTo(getContext(), BindRouterInfoActivity.class, getIntent());
    }
  }

  @Override
  public void onAddDevice(AddDevResponse response) {
  }

  @Override
  public void onUnbindDevice(UnbindDevResponse response) {
    ToastUtils.showShort("解绑门锁成功");
    Intent intent = new Intent();
    intent.putExtra(Navigator.EXTRA_MAIN_PAGE_INDEX, 1);
    mNavigator.navigateExistAndClearTop(getContext(), MainActivity.class, intent);
  }

  @Override
  public void showDeviceDetail(GetDeviceDetailResponse response) {
    mBinding.tvName.setText(response.getDeviceName());
    mBinding.tvRouter.setText(TextUtils.isEmpty(response.getMasterSerialNo()) ? "" : "三点安全网关");
    mBinding.tvModel.setText(response.getDeviceModel());
    lock().setMasterSerialNo(response.getMasterSerialNo());
    lock().setNetworkStatus(response.getNetworkStatus());
    lock().setMac(response.getMac());
  }

  private Lock lock() {
    Lock data = (Lock) getIntent().getSerializableExtra(Navigator.EXTRA_DEVICE);
    return data == null ? new Lock() : data;
  }

  @Override
  public void showDevices(List<GetDevsResponse> responses, String devType) {
    if (Device.TYPE_ROUTER.equals(devType)) {
      showRouters(responses);
    }
  }

  @Override
  public void showTopology(TopologyNode nodes) {

  }

  private void showRouters(List<GetDevsResponse> responses) {
    if (responses == null || responses.isEmpty()) {
      ToastUtils.showShort("请先添加网关");
      return;
    }
    DialogUtil.showChooseRouterDialog(getContext(), responses, router -> {
      mRouter = router;
      System.out.println("router = " + router);
      bindRouter();
    });
  }


  @Override
  public void showBindFail(String msg) {
    ToastUtils.showShort(msg);
  }

  @Override
  public void showBindSuccess() {
    DialogUtil.showZigbeeNetDialog(getContext(), "门锁正在组网", "门锁正在组网，请耐心等待", 30000, () -> {
      mGetDeviceDetailPresenter.getDeviceDetail(lock().getId());
    });
  }

  @Override
  public void showUnbindFail(String msg) {}

  @Override
  public void showUnbindSuccess() {}

  @Override
  public void showQuerySuccess() {}

  private void bindRouter() {
    if (!BleLockUtils.isBleEnable()) {
      DialogUtil.showOpenBleDialog(getContext(), view -> openBle());
      return;
    }
    showLoading();
    BlePkg pkg = new BlePkg();
    pkg.setHead("aa");
    pkg.setLength("28");
    pkg.setKeyId(BleMapper.DEFAULT_ENCRYPTION);
    pkg.setUserId(PrefConstant.getUserIdInHexString());
    pkg.setSeq("00");
    BleBody body = new BleBody();
    body.setCmd(BleMapper.BLE_CMD_BIND_ROUTER_ZIGBEE);
    body.setPayload("");
    pkg.setBody(body);
    System.out.println("pkg = " + pkg);
    sendBlePkg(pkg, BIND_ROUTER_ZIGBEE);
  }

  protected void sendBlePkg(BlePkg pkg, BleRequest.CmdType cmdType) {
    mPkg = pkg;
    mCmdType = cmdType;
    mDeviceAddress = lock().getMac();
    IBle ble = getBle();
    if (ble == null || TextUtils.isEmpty(mDeviceAddress)) return;
    BleGattService service = ble.getService(mDeviceAddress, UUID.fromString(BleMapper.SERVICE_UUID));
    if (service == null) {
      ble.requestConnect(mDeviceAddress, mCmdType, 30);
      return;
    }
    BleGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString(BleMapper.CHARACTERISTIC_UUID));

    ble.requestCharacteristicNotification(mDeviceAddress, characteristic);
    String data = new BleMapper().pkgToReqString(mPkg, "");
    ble.requestWriteCharacteristicToLock(mCmdType, mDeviceAddress, characteristic, data, true);

  }

  private final BroadcastReceiver mBleReceiver = new BroadcastReceiver() {
    private BleMapper bleMapper = new BleMapper();

    @Override
    public void onReceive(Context context, Intent intent) {
      Bundle extras = intent.getExtras();
      if (!mDeviceAddress.equals(extras.getString(BleService.EXTRA_ADDR))) {
        return;
      }
      String action = intent.getAction();
      if (BleService.BLE_GATT_CONNECTED.equals(action)) {
        System.out.println(TAG + " connected +++++++++++++++++++++++++++++++");
      } else if (BleService.BLE_GATT_DISCONNECTED.equals(action)) {
        System.out.println(TAG + " disconnected +++++++++++++++++++++++++++++++");
        ToastUtils.showShort("蓝牙连接失败，请重试");
        hideLoading();
      } else if (BleService.BLE_SERVICE_DISCOVERED.equals(action)) {
        System.out.println(TAG + " service discovered +++++++++++++++++++++++++++++++");
        sendBlePkg(mPkg, mCmdType);
      } else if (BleService.BLE_REQUEST_FAILED.equals(action)) {
        ToastUtils.showShort("蓝牙通信失败，请重试");
        hideLoading();
      }

      //////////////////////////////////////////////////////////////////////////////////////////////
      else if (BleService.BLE_BIND_ROUTER_ZIGBEE.equals(action)) {
        hideLoading();
        ArrayList<String> values = extras.getStringArrayList(BleService.EXTRA_VALUE);
        BlePkg blePkg = bleMapper.stringValuesToPkg(values, "");
        String payload = blePkg.getBody().getPayload();
        System.out.println("blePkg = " + blePkg);
        System.out.println("payload = " + payload);
        if (!bleRspFail(payload, true, mDeviceAddress)) {
          mZigbeePresenter.bind(lock().getId(), mRouter.getDeviceSerialNo());
        }
//        else if (BleBody.RSP_CONFIG_MODE_NOT_OPEN.equals(payload)) {
//          DialogUtil.showConfirmDialog(getContext(), "门锁未在配置状态", "请触碰门锁背后的按钮开启配置状态。", null);
//        }
        else {
          ToastUtils.showShort("门锁绑定网关失败");
        }
      }

    }
  };

}
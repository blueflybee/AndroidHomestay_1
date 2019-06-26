package com.qtec.homestay.view.device.lock;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.blueflybee.blelibrary.BleGattCharacteristic;
import com.blueflybee.blelibrary.BleGattService;
import com.blueflybee.blelibrary.BleRequest;
import com.blueflybee.blelibrary.BleService;
import com.blueflybee.blelibrary.IBle;
import com.blueflybee.blelibrary.utils.BleBase;
import com.blueflybee.blelibrary.utils.ParsedAd;
import com.blueflybee.titlebarlib.widget.TitleBar;
import com.qtec.homestay.AndroidApplication;
import com.qtec.homestay.R;
import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.data.mapper.BleMapper;
import com.qtec.homestay.databinding.ActivitySearchLockBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.lock.model.core.BleBody;
import com.qtec.homestay.domain.model.lock.model.core.BlePkg;
import com.qtec.homestay.domain.model.lock.model.rsp.BleGetLockInfoResponse;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.utils.DialogUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.device.adapter.SearchLockAdapter;
import com.qtec.homestay.view.device.data.Device;
import com.qtec.homestay.view.device.lock.utils.BleLockUtils;
import com.blueflybee.blelibrary.BleRequest.CmdType;
import com.qtec.homestay.view.device.router.ScanRouterInfoActivity;

import java.util.ArrayList;
import java.util.UUID;

import static com.blueflybee.blelibrary.BleRequest.CmdType.*;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/02
 *     desc   : SearchLockActivity
 * </pre>
 */
public class SearchLockActivity extends BaseActivity {

  private static final String TAG = SearchLockActivity.class.getName();
  public static final int SCAN_PERIOD = 5000;

  private static final int REQUEST_ENABLE_BT = 1;
  private ActivitySearchLockBinding mBinding;
  private SearchLockAdapter mSearchLockAdapter;

  private IBle mBle;
  private Handler mHandler = new Handler();
  private boolean mScanning;

  // Stops scanning after 5 seconds.
  private BluetoothDevice mDevice;
  private UUID[] mServiceUUids = new UUID[]{UUID.fromString(BleMapper.SERVICE_UUID)};
  private ObjectAnimator mRotation;
  private String mDeviceAddress;
  private AlertDialog mAlertDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_lock);

    initData();
    initView();
  }

  private void initData() {
  }

  private void initView() {
    initTitleBar("搜索门锁");

    mSearchLockAdapter = new SearchLockAdapter(getContext());
    mBinding.listLock.setAdapter(mSearchLockAdapter);
    mBinding.listLock.setOnItemClickListener((parent, view, position, id) -> {
      if (mBle == null) return;
      mBle.stopScan();

      mDevice = mSearchLockAdapter.getItem(position);
      if (mDevice == null) return;
      mDeviceAddress = mDevice.getAddress();
      showLoading();
      mBle.requestConnect(mDevice.getAddress(), GET_LOCK_INFO, 30);
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    registerReceiver(mBleReceiver, BleService.getIntentFilter());
    startScan();
  }

  @Override
  protected void onPause() {
    super.onPause();
    unregisterReceiver(mBleReceiver);
    stopScan();
  }

  private void scanLeDevice(final boolean enable) {
    AndroidApplication app = (AndroidApplication) getApplication();
    mBle = app.getIBle();
    if (mBle == null) {
      return;
    }
    if (enable) {
//      showLoading();
      // Stops scanning after a pre-defined scan period.
      mHandler.postDelayed(() -> {
//        hideLoading();
        mScanning = false;
        if (mBle != null) {
          mBle.stopScan();
        }
      }, SCAN_PERIOD);

      mScanning = true;
      if (mBle != null) {
        mBle.startScan();
      }
    } else {
//      hideLoading();
      mScanning = false;
      if (mBle != null) {
        mBle.stopScan();
      }
    }
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
  }

  public void scan(View view) {
    startScan();
  }

  private void startScan() {
    if (!BleLockUtils.isBleEnable()) {
      DialogUtil.showOpenBleDialog(getContext(), view -> openBle());
      return;
    }
    mSearchLockAdapter.clear();
    //    closeAll();
    if (mDevice != null) {
      close(mDevice.getAddress());
    }
    scanLeDevice(true);
    mBinding.ibScan.setVisibility(View.GONE);
    mBinding.llScanning.setVisibility(View.VISIBLE);
    mRotation = ObjectAnimator.ofFloat(mBinding.ivScanning, "rotation", 0, 1080);
    mRotation.setDuration(SCAN_PERIOD).start();
    mRotation.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        mBinding.ibScan.setVisibility(View.VISIBLE);
        mBinding.llScanning.setVisibility(View.GONE);
      }
    });
  }

  private void stopScan() {
    scanLeDevice(false);
    mSearchLockAdapter.clear();
    if (mRotation != null) {
      mRotation.end();
    }
    close(mDeviceAddress);
  }

  @Override
  protected void initTitleBar(String title) {
    super.initTitleBar(title);
    mTitleBar.getRightTextView().setText("门锁列表");
    mTitleBar.setListener((view, action, extra) -> {
      if (action == TitleBar.ACTION_LEFT_TEXT) {
        finish();
      } else if (action == TitleBar.ACTION_RIGHT_TEXT) {
        mNavigator.navigateTo(getContext(), LockListActivity.class);
      }
    });
  }

  private void getLockInfo() {
    BlePkg pkg = new BlePkg();
    pkg.setHead("aa");
    pkg.setLength("0c");
    pkg.setKeyId(BleMapper.NO_ENCRYPTION);
    pkg.setUserId(PrefConstant.getUserIdInHexString());
    pkg.setSeq("00");
    BleBody body = new BleBody();
    body.setCmd(BleMapper.BLE_CMD_GET_LOCK_INFO);
    body.setPayload("");
    pkg.setBody(body);
    sendBlePkg(pkg, GET_LOCK_INFO);
  }

  private void sendBlePkg(BlePkg pkg, CmdType cmdType) {
    mBle = ((AndroidApplication) getApplication()).getIBle();
    if (mBle == null || TextUtils.isEmpty(mDeviceAddress)) return;

    BleGattService service = mBle.getService(mDeviceAddress, UUID.fromString(BleMapper.SERVICE_UUID));
    if (service == null) {
      mBle.requestConnect(mDeviceAddress, cmdType, 30);
      return;
    }

    BleGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString(BleMapper.CHARACTERISTIC_UUID));

    mBle.requestCharacteristicNotification(mDeviceAddress, characteristic);
    String data = new BleMapper().pkgToReqString(pkg, "");
    mBle.requestWriteCharacteristicToLock(cmdType, mDeviceAddress, characteristic, data, true);
  }

  private void retry() {
    hideLoading();
    if (mAlertDialog == null) {
      mAlertDialog = DialogUtil.showGetLockInfoDialog(getContext(), view -> {
        showLoading();
        getLockInfo();
      });
      return;
    }
    if (mAlertDialog.isShowing()) return;
    mAlertDialog.show();
  }

  private final BroadcastReceiver mBleReceiver = new BroadcastReceiver() {

    @Override
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      if (BleService.BLE_NOT_SUPPORTED.equals(action)) {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            ToastUtils.showShort("您的设备不支持蓝牙4.0");
            finish();
          }
        });
      } else if (BleService.BLE_NO_BT_ADAPTER.equals(action)) {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            ToastUtils.showShort("您的设备不支持蓝牙4.0");
            finish();
          }
        });
      } else if (BleService.BLE_DEVICE_FOUND.equals(action)) {

        BleRequest.CmdType cmdType = getBleCmdType(intent);
        if (cmdType != null) return;
        // device found
        byte[] scanRecord = intent.getByteArrayExtra(BleService.EXTRA_SCAN_RECORD);
        ParsedAd parsedAd = BleBase.parseScanRecord(scanRecord);
        if (!parsedAd.uuids.contains(mServiceUUids[0])) return;
        Bundle extras = intent.getExtras();
        final BluetoothDevice device = extras.getParcelable(BleService.EXTRA_DEVICE);
        final int rssi = intent.getIntExtra(BleService.EXTRA_RSSI, 0);

        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            mSearchLockAdapter.update(device, rssi);
          }
        });
      } else if (BleService.BLE_REQUEST_FAILED.equals(action)) {
        System.out.println(TAG + " BLE_REQUEST_FAILED +++++++++++++++++++++++++++++++");
        retry();
      } else if (BleService.BLE_GATT_DISCONNECTED.equals(action)) {
        System.out.println(TAG + " disconnected +++++++++++++++++++++++++++++++");
        retry();
      } else if (BleService.BLE_SERVICE_DISCOVERED.equals(action)) {
        System.out.println(TAG + " service discovered +++++++++++++++++++++++++++++++");
        getLockInfo();
      } else if (BleService.BLE_GET_LOCK_INFO.equals(action)) {
        hideLoading();
        Bundle extras = intent.getExtras();
        ArrayList<String> values = extras.getStringArrayList(BleService.EXTRA_VALUE);
        BleMapper bleMapper = new BleMapper();
        BleGetLockInfoResponse lockInfo = bleMapper.getLockInfo(bleMapper.stringValuesToPkg(values, ""));
        System.out.println("lockInfo = " + lockInfo);
        if (!bleRspFail(lockInfo.getRspCode(), true, mDeviceAddress)) {
          Device device = new Device();
          device.setId(lockInfo.getDeviceId());
          device.setName("三点智能门锁");
          device.setModel(lockInfo.getModel());
          device.setType(Device.TYPE_LOCK);
          device.setTypeName("智能门锁");
          device.setVersion(lockInfo.getVersion());
          device.setMac(mDeviceAddress);
          device.setBleName(mDevice.getName());
          device.setBind(false);
          Intent addLockIntent = new Intent();
          addLockIntent.putExtra(Navigator.EXTRA_DEVICE, device);
          mNavigator.navigateTo(getContext(), AddLockActivity.class, addLockIntent);
        } else {
          retry();
        }

      }
    }
  };

}
package com.qtec.homestay.view.device.router;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.espressif.iot.esptouch.EsptouchTask;
import com.espressif.iot.esptouch.IEsptouchListener;
import com.espressif.iot.esptouch.IEsptouchResult;
import com.espressif.iot.esptouch.IEsptouchTask;
import com.espressif.iot.esptouch.task.__IEsptouchTask;
import com.espressif.iot.esptouch.util.ByteUtil;
import com.espressif.iot.esptouch.util.EspAES;
import com.espressif.iot.esptouch.util.EspNetUtil;
import com.qtec.homestay.R;
import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.databinding.ActivityConfigRouterWifiBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.mapp.req.AddDevRequest;
import com.qtec.homestay.domain.model.mapp.rsp.AddDevResponse;
import com.qtec.homestay.domain.model.mapp.rsp.UnbindDevResponse;
import com.qtec.homestay.internal.di.components.DaggerRouterComponent;
import com.qtec.homestay.internal.di.modules.RouterModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.utils.DialogUtil;
import com.qtec.homestay.utils.InputUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.component.InputWatcher;
import com.qtec.homestay.view.device.activity.UpdateDevicePresenter;
import com.qtec.homestay.view.device.activity.UpdateDeviceView;
import com.qtec.homestay.view.device.data.Device;
import com.qtec.homestay.view.device.router.utils.EspUtils;
import com.qtec.homestay.view.main.MainActivity;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;
/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/06
 *     desc   : ConfigRouterWifiActivity
 * </pre>
 */
public class ConfigRouterWifiActivity extends BaseActivity implements UpdateDeviceView {

  private static final String TAG = ConfigRouterWifiActivity.class.getName();

  private static final boolean AES_ENABLE = false;
  private static final String AES_SECRET_KEY = "1234567890123456"; // TODO modify your own key
  private static final String DEVICE_COUNT = "1";
  public static final String PAIR_WIFI_FAILD = "组网失败，请重试";
  public static final String PAIR_WIFI_SUCCESS = "组网成功";

  @Inject
  UpdateDevicePresenter mUpdateDevicePresenter;

  private ActivityConfigRouterWifiBinding mBinding;

//  private TextToSpeech mTextToSpeech; // TTS对象
  private EsptouchAsyncTask4 mTask;
  private String mBSsid = "";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_config_router_wifi);
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
  }

  private void initData() {
//    mTextToSpeech = new TextToSpeech(getContext(), status -> {
//      if (status == TextToSpeech.SUCCESS) {
//        int result = mTextToSpeech.setLanguage(Locale.CHINESE);
//        if (result == TextToSpeech.LANG_MISSING_DATA
//            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//          System.out.println("语音播报 onInit() 数据丢失或不支持");
//        }
//      }
//    });//语音播报
//    new Handler().postDelayed(new Runnable() {
//      @Override
//      public void run() {
//        if (mTextToSpeech != null && !mTextToSpeech.isSpeaking()) {
//          mTextToSpeech.setPitch(0.7f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
//          mTextToSpeech.speak("请输入无线密码然后点击配置按钮",
//              TextToSpeech.QUEUE_FLUSH, null);
//        }
//      }
//    }, 1000);

    IntentFilter filter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
    registerReceiver(mReceiver, filter);
  }

  private void initView() {
    initTitleBar("连接WiFi");

    String wifiName = getWifiSsid().replace("\"", "");
    mBinding.etWifiName.setText(wifiName);
//    moveEditCursorToEnd(mBinding.etWifiName);

    InputWatcher watcher = new InputWatcher();
    InputWatcher.WatchCondition condition = new InputWatcher.WatchCondition();
    condition.setMinLength(1);
    watcher.addEt(mBinding.etWifiName, condition);
    watcher.addEt(mBinding.etWifiPwd, condition);
    watcher.setInputListener(isEmpty -> {
      mBinding.btnNext.setClickable(!isEmpty);
      if (isEmpty) {
        mBinding.btnNext.setBackgroundResource(R.drawable.shape_rec_btn_disable);
      } else {
        mBinding.btnNext.setBackgroundResource(R.drawable.btn_login_selector);
      }
    });
  }

  private String getWifiSsid() {
    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
    return wifiInfo.getSSID();
  }

  @Override
  public void onStop() {
    super.onStop();
//    mTextToSpeech.stop(); // 不管是否正在朗读TTS都被打断
//    mTextToSpeech.shutdown(); // 关闭，释放资源
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mUpdateDevicePresenter != null) {
      mUpdateDevicePresenter.destroy();
    }
    unregisterReceiver(mReceiver);
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
  }

	public void showWifi(View view) {
    DialogUtil.showChooseWifiDialog(getContext(), wifi -> {
      mBinding.etWifiName.setText(wifi.SSID);
    });
	}

	public void changeInputType(View view) {
    Object tag = mBinding.ivShowPwd.getTag();
    if ("hide".equals(tag)) {
      mBinding.ivShowPwd.setImageResource(R.mipmap.otheropen);
      mBinding.ivShowPwd.setTag("show");
    } else {
      mBinding.ivShowPwd.setImageResource(R.mipmap.otherbihe);
      mBinding.ivShowPwd.setTag("hide");
    }
    InputUtil.showHidePwd(mBinding.etWifiPwd);
	}

	public void config(View view) {
    Device router = device();
    if (router == null) return;


    if ((Boolean) mBinding.btnNext.getTag()) {
      ToastUtils.showShort(R.string.wifi_5g_message);
      return;
    }

    byte[] ssid = mBinding.etWifiName.getTag() == null ? ByteUtil.getBytesByString(wifiName())
        : (byte[]) mBinding.etWifiName.getTag();
    byte[] password = ByteUtil.getBytesByString(wifiPwd());
    byte[] bssid = EspNetUtil.parseBssid2bytes(mBSsid);
    byte[] deviceCount = DEVICE_COUNT.getBytes();
    System.out.println("router = " + router);
    System.out.println("ssid = " + wifiName());
    System.out.println("password = " + wifiPwd());
    System.out.println("mBSsid = " + mBSsid);
    System.out.println("DEVICE_COUNT = " + DEVICE_COUNT);


    if (mTask != null) {
      mTask.cancelEsptouch();
    }
    mTask = new EsptouchAsyncTask4(this);
    mTask.execute(ssid, bssid, password, deviceCount);
	}

	@NonNull
	private String wifiName() {
		return mBinding.etWifiName.getText().toString().trim();
	}

	@NonNull
	private String wifiPwd() {
		return mBinding.etWifiPwd.getText().toString().trim();
	}

  private void onWifiChanged(WifiInfo info) {
    if (info == null) {
      mBinding.etWifiName.setText("");
      mBinding.etWifiName.setTag(null);
      mBSsid = "";
      mBinding.btnNext.setTag(null);

      if (mTask != null) {
        mTask.cancelEsptouch();
        mTask = null;
      }
    } else {
      String ssid = info.getSSID();
      System.out.println("ssid = " + ssid);
      if (ssid.startsWith("\"") && ssid.endsWith("\"")) {
        ssid = ssid.substring(1, ssid.length() - 1);
      }
      mBinding.etWifiName.setText(ssid);
      mBinding.etWifiName.setTag(ByteUtil.getBytesByString(ssid));
      byte[] ssidOriginalData = EspUtils.getOriginalSsidBytes(info);
      mBinding.etWifiName.setTag(ssidOriginalData);

      mBSsid = info.getBSSID();

      mBinding.btnNext.setTag(Boolean.FALSE);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        int frequence = info.getFrequency();
        if (frequence > 4900 && frequence < 5900) {
          // Connected 5G wifi. Device does not support 5G
          mBinding.btnNext.setTag(Boolean.TRUE);
        }
      }
    }
  }

  @Override
  public void onAddDevice(AddDevResponse response) {
    ToastUtils.showShort("绑定成功");
    finish();
    Intent intent = new Intent();
    intent.putExtra(Navigator.EXTRA_MAIN_PAGE_INDEX, 1);
    mNavigator.navigateExistAndClearTop(getContext(), MainActivity.class, intent);
  }

  @Override
  public void onUnbindDevice(UnbindDevResponse response) {}

  private class EsptouchAsyncTask4 extends AsyncTask<byte[], Void, List<IEsptouchResult>> {
    private WeakReference<ConfigRouterWifiActivity> mActivity;

    // without the lock, if the user tap confirm and cancel quickly enough,
    // the bug will arise. the reason is follows:
    // 0. task is starting created, but not finished
    // 1. the task is cancel for the task hasn't been created, it do nothing
    // 2. task is created
    // 3. Oops, the task should be cancelled, but it is running
    private final Object mLock = new Object();
    private Dialog mProgressDialog;
    private IEsptouchTask mEsptouchTask;

    EsptouchAsyncTask4(ConfigRouterWifiActivity activity) {
      mActivity = new WeakReference<>(activity);
    }

    void cancelEsptouch() {
      cancel(true);
      if (mProgressDialog != null) {
        mProgressDialog.dismiss();
      }
      synchronized (mLock) {
        if (__IEsptouchTask.DEBUG) {
          Log.i(TAG, "task canceled");
        }
        if (mEsptouchTask != null) {
          mEsptouchTask.interrupt();
        }
      }
    }

    @Override
    protected void onPreExecute() {
      Activity activity = mActivity.get();
      mProgressDialog = new Dialog(activity, R.style.CustomProgressDialog);
      mProgressDialog.setContentView(R.layout.app_progress);
      mProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
//      mProgressDialog.setMessage("组网中，请稍候...");
      mProgressDialog.setCanceledOnTouchOutside(false);
      mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
          synchronized (mLock) {
            if (__IEsptouchTask.DEBUG) {
              Log.i(TAG, "progress dialog back pressed canceled");
            }
            if (mEsptouchTask != null) {
              mEsptouchTask.interrupt();
            }
          }
        }
      });
      mProgressDialog.show();
    }

    @Override
    protected List<IEsptouchResult> doInBackground(byte[]... params) {
      ConfigRouterWifiActivity activity = mActivity.get();
      int taskResultCount;
      synchronized (mLock) {
        // !!!NOTICE
        byte[] apSsid = params[0];
        byte[] apBssid = params[1];
        byte[] apPassword = params[2];
        byte[] deviceCountData = params[3];
        taskResultCount = deviceCountData.length == 0 ? -1 : Integer.parseInt(new String(deviceCountData));
        Context context = activity.getApplicationContext();
        if (AES_ENABLE) {
          byte[] secretKey = AES_SECRET_KEY.getBytes();
          EspAES aes = new EspAES(secretKey);
          mEsptouchTask = new EsptouchTask(apSsid, apBssid, apPassword, aes, context);
        } else {
          mEsptouchTask = new EsptouchTask(apSsid, apBssid, apPassword, null, context);
        }
        mEsptouchTask.setEsptouchListener(activity.myListener);
      }
      return mEsptouchTask.executeForResults(taskResultCount);
    }

    @Override
    protected void onPostExecute(List<IEsptouchResult> results) {
      ConfigRouterWifiActivity activity = mActivity.get();
      mProgressDialog.dismiss();
      if (results == null) {
        ToastUtils.showShort(PAIR_WIFI_FAILD);
        return;
      }

      IEsptouchResult firstResult = results.get(0);
      // check whether the task is cancelled and no results received
      if (!firstResult.isCancelled()) {
//        int count = 0;
        // max results to be displayed, if it is more than maxDisplayCount,
        // just show the count of redundant ones
//        final int maxDisplayCount = 5;
        // the task received some results including cancelled while
        // executing before receiving enough results
        if (firstResult.isSuc()) {
//          2c3ae818d65e
          Device device = device();
          System.out.println("device suc = " + device);
          String bssid = null;

          for (IEsptouchResult result : results) {
            bssid = result.getBssid();
            String id = device.getId();
            if (bssid.equals(id)) break;
          }
          if (bssid.equals(device.getId())) {
            if (device.isBind()) {
              activity.mTask = null;
              ToastUtils.showShort(PAIR_WIFI_SUCCESS);
              finish();
              mNavigator.navigateExistAndClearTop(getContext(), RouterSetActivity.class, getIntent());
            } else {
              AddDevRequest request = new AddDevRequest();
              request.setDeviceSerialNo(device.getId());
              request.setDeviceName(device.getName());
              request.setDeviceType(device.getType());
              request.setUserUniqueKey(PrefConstant.getUserUniqueKey());
              mUpdateDevicePresenter.add(request);
            }
          } else {
            ToastUtils.showShort("设备序列号输入错误");
          }

        } else {
          ToastUtils.showShort(PAIR_WIFI_FAILD);
        }
      }

      activity.mTask = null;
    }
  }

  private IEsptouchListener myListener = new IEsptouchListener() {

    @Override
    public void onEsptouchResultAdded(final IEsptouchResult result) {
      onEsptoucResultAddedPerform(result);
    }
  };

  private void onEsptoucResultAddedPerform(final IEsptouchResult result) {
    runOnUiThread(new Runnable() {

      @Override
      public void run() {
      }
    });
  }


  private BroadcastReceiver mReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      if (action == null) return;
      switch (action) {
        case WifiManager.NETWORK_STATE_CHANGED_ACTION:
          onWifiChanged(intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO));
          break;
      }
    }
  };

  private Device device() {
    Device data = (Device) getIntent().getSerializableExtra(Navigator.EXTRA_DEVICE);
    return data == null ? new Device() : data;
  }

}
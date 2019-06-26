/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qtec.homestay;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.multidex.MultiDex;
import android.view.Gravity;

import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.blueflybee.blelibrary.BleService;
import com.blueflybee.blelibrary.IBle;
import com.hss01248.glidepicker.GlideIniter;
import com.hss01248.photoouter.PhotoUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.qtec.homestay.data.net.CloudRestApi;
import com.qtec.homestay.data.net.CloudRestApiImpl;
import com.qtec.homestay.data.net.ConnectionCreator;
import com.qtec.homestay.data.net.IPostConnection;
import com.qtec.homestay.internal.di.components.ApplicationComponent;
import com.qtec.homestay.internal.di.components.DaggerApplicationComponent;
import com.qtec.homestay.internal.di.modules.ApplicationModule;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

  private static final String APP_NAME = "HomeStay";
  private static final String TAG = AndroidApplication.class.getSimpleName();

  private ApplicationComponent mApplicationComponent;
  private IBle mBle;
  private IWXAPI mWxApi;
  private ServiceConnection mBleServiceConnection;

  @Override
  public void onCreate() {
    super.onCreate();
    initializeInjector();
    Utils.init(this);
    initLogger();
    initConnection();
    initToast();
    initBle();
    initRxJava();
//    initKeystoreRepertory();
    initImagePhotoUtil();
    initUmengAnalytics();
    requestPermissions();

    registToWX();
  }

  private void initRxJava() {
    /**
     * RxJava2 当取消订阅后(dispose())，RxJava抛出的异常后续无法接收(此时后台线程仍在跑，可能会抛出IO等异常),全部由RxJavaPlugin接收，需要提前设置ErrorHandler
     * 详情：http://engineering.rallyhealth.com/mobile/rxjava/reactive/2017/03/15/migrating-to-rxjava-2.html#Error Handling
     */
    RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
      @Override
      public void accept(Throwable throwable) throws Exception {
        System.out.println("setErrorHandler.accept");
      }
    });
  }

  private void initToast() {
    ToastUtils.setGravity(Gravity.CENTER, 0, 0);
    ToastUtils.setMsgTextSize(16);
    ToastUtils.setMsgColor(getResources().getColor(R.color.white));
    ToastUtils.setBgResource(R.drawable.shape_rec_toast);
  }

  private void initializeInjector() {
    this.mApplicationComponent = DaggerApplicationComponent
        .builder()
        .applicationModule(new ApplicationModule(this))
        .build();
  }

  private void initLogger() {
    Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy.newBuilder().tag(APP_NAME).build()));
  }

  private void initImagePhotoUtil() {
    PhotoUtil.init(getApplicationContext(), new GlideIniter());
  }

  private void initBle() {
    BleService.setBleSdkType(BleService.BLE_SDK_TYPE_ANDROID_LOCK);
    Intent bindIntent = new Intent(this, BleService.class);
    // TODO: enalbe adapter
    mBleServiceConnection = new ServiceConnection() {
      @Override
      public void onServiceConnected(ComponentName className,
                                     IBinder binder) {
        BleService service = ((BleService.LocalBinder) binder).getService();
        mBle = service.getBle();

        if (mBle != null && !mBle.adapterEnabled()) {
          // TODO: enalbe adapter
        }
      }

      @Override
      public void onServiceDisconnected(ComponentName classname) {
      }
    };
    bindService(bindIntent, mBleServiceConnection, Context.BIND_AUTO_CREATE);
  }

  public IBle getIBle() {
    return mBle;
  }

  public void unbindBleService() {
    try {
      if (mBleServiceConnection != null) {
        unbindService(mBleServiceConnection);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void initConnection() {
    //  PrefConstant.getCloudUrl()
    IPostConnection cloudConnection = ConnectionCreator.create(ConnectionCreator.CLOUD_CONNECTION, CloudRestApi.URL_DEVELOP);
    CloudRestApiImpl.setApiPostConnection(cloudConnection);
  }

  private void initUmengAnalytics() {
    //MobclickAgent.openActivityDurationTrack(false); // 禁止默认的页面统计方式，这样将不会再自动统计Activity。
    MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
    MobclickAgent.setDebugMode(true);//调试模式
    MobclickAgent.setSessionContinueMillis(40000);
  }

//  private void initKeystoreRepertory() {
//    IKeystoreRepertory repertory = KeystoreRepertory.getInstance();
//    repertory.init(getApplicationContext());
//  }

  private void requestPermissions() {
    String[] perms = {
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO
    };
    PermissionUtils
        .permission(perms)
        .callback(new PermissionUtils.FullCallback() {
          @Override
          public void onGranted(List<String> permissionsGranted) {
            if (permissionsGranted.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
              handleCrashLog();
            }
          }

          @Override
          public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
          }
        }).request();
  }

  @SuppressLint("MissingPermission")
  private void handleCrashLog() {
    File filesDir = new File(getFilesDir().getAbsolutePath() + System.getProperty("file.separator") + "crash");
    CrashUtils.init(filesDir, (crashInfo, e) -> MobclickAgent.reportError(getApplicationContext(), e));
  }

  private void registToWX() {
    //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
    String appId = "wx5739409145adb7c3";
    mWxApi = WXAPIFactory.createWXAPI(this, appId, false);
    // 将该app注册到微信
    mWxApi.registerApp(appId);
  }

  public IWXAPI getWxApi() {
    return mWxApi;
  }

  public ApplicationComponent getApplicationComponent() {
    return this.mApplicationComponent;
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }

}

package com.qtec.homestay.view.user.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;
import com.fruit.updatelib.DownloadService;
import com.fruit.updatelib.UpdateChecker;
import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.domain.mapper.JsonMapper;
import com.qtec.homestay.domain.model.mapp.req.CheckAppVersionRequest;
import com.qtec.homestay.domain.model.mapp.rsp.CheckAppVersionResponse;
import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.presenter.Presenter;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.interactor.AppSubscriber;

import javax.inject.Inject;
import javax.inject.Named;

import com.qtec.homestay.domain.interactor.cloud.CheckAppVersion;
import com.qtec.homestay.view.user.mine.data.UpdateData;

import java.io.File;
import java.io.IOException;

import dagger.internal.Preconditions;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 18-7-16
 *     desc   : presenter
 *     version: 1.0
 * </pre>
 */
@PerActivity
public class VersionInfoPresenter implements Presenter {

  public static final String DATA_TYPE_INSTALL = "application/vnd.android.package-archive";
  private static boolean isChecked = false;
  private final CheckAppVersion mCheckAppVersion;
  private VersionInfoView mView;

  private CheckAppVersionResponse mVersionResponse;

  @Inject
  public VersionInfoPresenter(@Named(CloudUseCaseComm.CHECK_APP_VERSION) CheckAppVersion pCheckAppVersion) {
    this.mCheckAppVersion = Preconditions.checkNotNull(pCheckAppVersion, "pCheckAppVersion is null");
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {

  }

  @Override
  public void destroy() {
    mCheckAppVersion.dispose();
  }

  public void setView(VersionInfoView view) {
    this.mView = view;
  }

  public void checkVersion() {
    CheckAppVersionRequest request = new CheckAppVersionRequest();
    request.setAppType("2");
    QtecEncryptInfo<CheckAppVersionRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);
    mView.showLoading();
    mCheckAppVersion.execute(encryptInfo, new AppSubscriber<CheckAppVersionResponse>(mView) {
      @Override
      protected void doNext(CheckAppVersionResponse response) {
        mVersionResponse = response;
        mView.showVersionInfo(response);
      }
    });
  }


  public void update() {
    if (mVersionResponse == null) {
      mVersionResponse = new CheckAppVersionResponse();
    }
    UpdateData updateData = new UpdateData();
    updateData.setUrl(mVersionResponse.getDownloadUrl());
    updateData.setVersionCode(mVersionResponse.getVersionNum());
    updateData.setUpdateMessage(mVersionResponse.getVersionStatement());
    UpdateChecker.checkForDownloadImmediate(mView.getContext(), new JsonMapper().toJson(updateData), mHandler);
    ToastUtils.showShort("请在通知栏查看下载进度");
  }

  public static boolean isChecked() {
    return isChecked;
  }

  public static void setIsChecked(boolean isChecked) {
    VersionInfoPresenter.isChecked = isChecked;
  }


  @SuppressLint("HandlerLeak")
  private Handler mHandler = new Handler() {
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case DownloadService.MSG_DOWNLOAD_SUCCESS:
          onDownloadSuccess((String) msg.obj);
          break;

        case DownloadService.MSG_DOWNLOAD_FAILED:
          ToastUtils.showShort("安装文件下载失败");
          break;

        default:
          break;
      }
    }
  };

  private void onDownloadSuccess(String filePath) {
    if (TextUtils.isEmpty(filePath)) {
      ToastUtils.showShort("安装文件下载失败");
      return;
    }
    ToastUtils.showShort("安装文件下载成功");
    install(filePath);
  }

  public void install(String filePath) {
    File file = new File(filePath);
    try {
      String[] command = {"chmod", "777", file.getPath()};
      ProcessBuilder builder = new ProcessBuilder(command);
      builder.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Intent intent = new Intent(Intent.ACTION_VIEW);
    // 由于没有在Activity环境下启动Activity,设置下面的标签
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Context context = mView.getContext();
    if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上 //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致 参数3共享的文件
      String authority = context.getPackageName() + ".fileprovider";
      System.out.println("authority = " + authority);
      Uri apkUri = FileProvider.getUriForFile(context, authority, file); //添加这一句表示对目标应用临时授权该Uri所代表的文件
      intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
      intent.setDataAndType(apkUri, DATA_TYPE_INSTALL);
    } else {
      intent.setDataAndType(Uri.fromFile(file), DATA_TYPE_INSTALL);
    }
    context.startActivity(intent);
  }


}

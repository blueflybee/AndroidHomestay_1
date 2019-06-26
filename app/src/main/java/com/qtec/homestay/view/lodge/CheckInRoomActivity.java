package com.qtec.homestay.view.lodge;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.baidu.ocr.ui.camera.CameraView;
import com.blankj.utilcode.util.ToastUtils;
import com.qtec.homestay.R;
import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.databinding.ActivityCheckInRoomBinding;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.mapp.req.CHeckInRoomResquest;
import com.qtec.homestay.domain.model.mapp.rsp.CheckInRoomResponse;
import com.qtec.homestay.domain.model.mapp.rsp.SelectRoomResponse;
import com.qtec.homestay.internal.di.components.DaggerLodgeComponent;
import com.qtec.homestay.internal.di.modules.LodgeModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.utils.CalendarUtil;
import com.qtec.homestay.utils.OcrUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.component.InputWatcher;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class CheckInRoomActivity extends BaseActivity implements RoomCheckInView {
  private static final String TAG = CheckInRoomActivity.class.getName();
  public static final String AK = "LAx0tC87DjfSPnCyHRuurCUa";
  public static final String SK = "88jfVmtA3cAh48lIAwCMmjGhGf6i9QdS";
  private ActivityCheckInRoomBinding mBinding;
  private static final int REQUEST_CODE_CAMERA = 102;
  private String mServiceDate;
  private int startGroup = -1;
  private int endGroup = -1;
  private int startchild = -1;
  private int endchild = -1;
  private String deviceSerialNo;

  //选中的起始时间
  private String mStartTime;
  //结束时间
  private String mEndTime;

  @Inject
  CheckInRoomPresenter mPresenter;

  private boolean mOrcToken = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_check_in_room);

    initializeInjector();

    initPresenter();

    initView();

    initAccessTokenWithAkSk();

  }

  private void initView() {
    initTitleBar("填写客户信息");

    if (TextUtils.isEmpty(getRoomNo())) {
      mBinding.imgMoreRoom.setVisibility(View.VISIBLE);
      mBinding.tvEnterRoomNo.setText("");
      mBinding.rlRoom.setClickable(true);
    } else {
      mBinding.imgMoreRoom.setVisibility(View.GONE);
      mBinding.tvEnterRoomNo.setText(getRoomNo());
      mBinding.rlRoom.setClickable(false);
      deviceSerialNo = getIntent().getStringExtra("DeviceSerialNo");
    }

    long time = System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date d1 = new Date(time);
    mServiceDate = format.format(d1);


    mBinding.imgCardId.setOnClickListener(v -> {
      if (!checkTokenStatus()) return;
      Intent intent = new Intent(this, CameraActivity.class);
      intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
          OcrUtil.getSaveFile(getApplication()).getAbsolutePath());
      intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
          true);
      // KEY_NATIVE_MANUAL设置了之后CameraActivity中不再自动初始化和释放模型
      // 请手动使用CameraNativeHelper初始化和释放模型
      // 推荐这样做，可以避免一些activity切换导致的不必要的异常
      intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL,
          true);
      intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
      startActivityForResult(intent, REQUEST_CODE_CAMERA);
    });

    InputWatcher watcher = new InputWatcher();
    InputWatcher.WatchCondition condition1 = new InputWatcher.WatchCondition();
    condition1.setByteRange(new InputWatcher.InputByteRange(1, 32));

    InputWatcher.WatchCondition condition2 = new InputWatcher.WatchCondition();
    condition2.setByteRange(new InputWatcher.InputByteRange(18, 18));

    InputWatcher.WatchCondition condition3 = new InputWatcher.WatchCondition();
    condition3.setRange(new InputWatcher.InputRange(11, 11));

    InputWatcher.WatchCondition condition4 = new InputWatcher.WatchCondition();
    condition4.setRange(new InputWatcher.InputRange(1, 1));

    watcher.addEt(mBinding.etName, condition1);
    watcher.addEt(mBinding.etPhoneNo, condition3);
    watcher.addEt(mBinding.etCardId, condition2);
/*    watcher.addEt(mBinding.etEnterRoomNo, condition4);
    watcher.addEt(mBinding.etTime, condition4);*/

    watcher.setInputListener(new InputWatcher.InputListener() {
      @Override
      public void onInput(boolean empty) {
        changeTitleRightBtnStyle(empty);
      }
    });
  }

  /**
   * ocr ak，sk初始化
   */
  private void initAccessTokenWithAkSk() {
    OCR.getInstance(this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
      @Override
      public void onResult(AccessToken result) {
        String token = result.getAccessToken();
        initOrcNative();
        mOrcToken = true;
      }

      @Override
      public void onError(OCRError error) {
        error.printStackTrace();
        ToastUtils.showShort("获取ocr token失败 " + error.getMessage());
      }
    }, getApplicationContext(), AK, SK);
  }

  private void initOrcNative() {
    //  初始化本地质量控制模型,释放代码在onDestory中
    //  调用身份证扫描必须加上 intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true); 关闭自动初始化和释放本地模型
    CameraNativeHelper.init(this, OCR.getInstance(getContext()).getLicense(),
        new CameraNativeHelper.CameraNativeInitCallback() {
          @Override
          public void onError(int errorCode, Throwable e) {
            String msg;
            switch (errorCode) {
              case CameraView.NATIVE_SOLOAD_FAIL:
                msg = "加载so失败，请确保apk中存在ui部分的so";
                break;
              case CameraView.NATIVE_AUTH_FAIL:
                msg = "授权本地质量控制token获取失败";
                break;
              case CameraView.NATIVE_INIT_FAIL:
                msg = "本地质量控制";
                break;
              default:
                msg = String.valueOf(errorCode);
            }
            ToastUtils.showShort("本地质量控制初始化错误，错误原因： " + msg);
          }
        });
  }

  private boolean checkTokenStatus() {
    if (!mOrcToken) {
      ToastUtils.showShort("ocr token还未成功获取");
    }
    return mOrcToken;
  }

  private String getRoomNo() {
    return getIntent().getStringExtra("RoomNo");
  }

  protected void changeTitleRightBtnStyle(boolean empty) {
    boolean isModify = !empty;
    if (isModify) {
  /*    if(getText(mBinding.e).length()==0 || getText(mBinding.tvEnterRoomNo).length()==0){
        mBinding.btnCheckInRoom.setBackgroundColor(getResources().getColor(R.color.green_ff99e5df));
        mBinding.btnCheckInRoom.setClickable(false);
      }else {
        mBinding.btnCheckInRoom.setBackgroundColor(getResources().getColor(R.color.green_00d7c0));
        mBinding.btnCheckInRoom.setClickable(true);
      }*/

      mBinding.btnCheckInRoom.setBackgroundColor(getResources().getColor(R.color.green_00d7c0));
      mBinding.btnCheckInRoom.setClickable(true);

    } else {
      mBinding.btnCheckInRoom.setBackgroundColor(getResources().getColor(R.color.green_ff99e5df));
      mBinding.btnCheckInRoom.setClickable(false);
    }
  }

  private void initPresenter() {
    mPresenter.setView(this);
  }

  private void initializeInjector() {
    DaggerLodgeComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .lodgeModule(new LodgeModule())
        .build()
        .inject(this);
  }

  @Override
  protected void onDestroy() {
    // 释放本地质量控制模型
    CameraNativeHelper.release();
    super.onDestroy();
    if (mPresenter != null) {
      mPresenter.destroy();
    }
  }

  public void checkInRoom(View view) {

    if (getText(mBinding.tvEnterRoomNo).length() == 0) {
      Toast.makeText(this, "请输入房间号", Toast.LENGTH_SHORT).show();
      return;
    }

    if (getText(mBinding.tvTime).length() == 0) {
      Toast.makeText(this, "请选择入住时间", Toast.LENGTH_SHORT).show();
      return;
    }

    CHeckInRoomResquest request = new CHeckInRoomResquest();
    request.setUserUniqueKey(PrefConstant.getUserUniqueKey());
    request.setDeviceSerialNo(deviceSerialNo);
    request.setEndTime(mEndTime + " 12:00:00");
    request.setIsSendMsg(mBinding.switchMsgSend.isChecked() ? "1" : "0");
    request.setResidentIdentifier(getText(mBinding.etCardId));
    request.setResidentPhone(getText(mBinding.etPhoneNo));
    request.setStartTime(mStartTime + " 12:00:00");
    request.setResidentType("1");
    request.setResidentName(getText(mBinding.etName));

    QtecEncryptInfo encryptInfo = new QtecEncryptInfo();
    encryptInfo.setData(request);

    mPresenter.checkInRoom(encryptInfo);
  }

  @Override
  public void checkInRoom(CheckInRoomResponse response) {
    Intent intent = new Intent();
    intent.putExtra("PWD", response.getLockPass());
    mNavigator.navigateTo(this, CheckInSuccessActivity.class, intent);
  }

  public void selectRoomClick(View view) {
    Intent intent = new Intent(this, SelectRoomActivity.class);
    startActivityForResult(intent, 2);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (data == null) return;

    if (requestCode == 2) {
      SelectRoomResponse room = (SelectRoomResponse) data.getSerializableExtra(Navigator.EXTRA_SELECT_ROOM);
      mBinding.tvEnterRoomNo.setText(room.getRoomNo());
      deviceSerialNo = room.getDeviceSerialNo();
    }

    if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
      if (data != null) {
        String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
        String filePath = OcrUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
        if (!TextUtils.isEmpty(contentType)) {
          if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
            recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
          } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
            recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
          }
        }
      }
    }
  }

  private void recIDCard(String idCardSide, String filePath) {
    IDCardParams param = new IDCardParams();
    param.setImageFile(new File(filePath));
    // 设置身份证正反面
    param.setIdCardSide(idCardSide);
    // 设置方向检测
    param.setDetectDirection(true);
    // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
    param.setImageQuality(20);

    OCR.getInstance(this).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
      @Override
      public void onResult(IDCardResult result) {
        if (result != null) {
          System.out.println("result = " + result.toString());
          mBinding.etName.setText(result.getName().toString().trim());
          mBinding.etCardId.setText(result.getIdNumber().toString().trim());
        }
      }

      @Override
      public void onError(OCRError error) {
        ToastUtils.showShort("识别失败：" + error.getMessage());
      }
    });
  }

  /**
   * 选择时间
   */
  public void selectCheckInTime(View view) {

    DatePopupWindow popupWindow = new DatePopupWindow(this, mServiceDate);
    //根据4个参数初始化
    if (startchild != -1 && startGroup != -1 && endGroup != -1 && endchild != -1) {
      popupWindow.setInit(startGroup, startchild, endGroup, endchild);
    } else {
      //设置根据mServiceDate设定今天和明天日期
      popupWindow.setDefaultSelect();
    }
    popupWindow.setDateOnClickListener(new DatePopupWindow.DateOnClickListener() {
      @Override
      public void getDate(List<DateInfo> list, int startGroupPosition, int startChildPosition, int endGroupPosition, int endChildPosition) {
        startGroup = startGroupPosition;
        startchild = startChildPosition;
        endGroup = endGroupPosition;
        endchild = endChildPosition;
        mStartTime = CalendarUtil.FormatDate(list.get(startGroupPosition).getList().get(startChildPosition).getDate());

        mEndTime = CalendarUtil.FormatDate(list.get(endGroupPosition).getList().get(endChildPosition).getDate());

        System.out.println(" 选择时间 mStartTime+\"  \"+mEndTime = " + mStartTime + "  " + mEndTime);

        try {

          String stTime = mStartTime.substring(mStartTime.lastIndexOf("-") - 1, mStartTime.length()).replaceAll("-", "月") + "日";
          String endTime = mEndTime.substring(mEndTime.lastIndexOf("-") - 1, mEndTime.length()).replaceAll("-", "月") + "日";
          mBinding.tvTime.setText(stTime + "至" + endTime);

        } catch (Exception e) {
          e.printStackTrace();
        }


      }
    });
    popupWindow.create(view);

  }
}

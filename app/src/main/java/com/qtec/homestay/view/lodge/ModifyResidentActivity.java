package com.qtec.homestay.view.lodge;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.support.annotation.NonNull;
import android.widget.TextView;

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
import com.blueflybee.titlebarlib.widget.TitleBar;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityModifyResidentBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.mapp.rsp.GetRoomManageResponse;
import com.qtec.homestay.domain.model.mapp.rsp.ModifyResidentResponse;
import com.qtec.homestay.domain.model.mapp.rsp.SelectRoomResponse;
import com.qtec.homestay.internal.di.components.DaggerLodgeComponent;
import com.qtec.homestay.internal.di.modules.LodgeModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.utils.InputUtil;
import com.qtec.homestay.utils.OcrUtil;
import com.qtec.homestay.utils.WidgetUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.component.InputWatcher;


import java.io.File;

import javax.inject.Inject;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/09/06
 *     desc   : ModifyResidentActivity
 * </pre>
 */
public class ModifyResidentActivity extends BaseActivity implements ModifyResidentView {

  private static final String TAG = ModifyResidentActivity.class.getName();
  private static final String AK = "LAx0tC87DjfSPnCyHRuurCUa";
  private static final String SK = "88jfVmtA3cAh48lIAwCMmjGhGf6i9QdS";
  private static final int REQUEST_CODE_CAMERA = 102;

  @Inject
  ModifyResidentPresenter mModifyResidentPresenter;

  private ActivityModifyResidentBinding mBinding;

  private boolean mOrcToken = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_modify_resident);
    initializeInjector();
    initPresenter();
    initData();
    initView();
    initAccessTokenWithAkSk();
  }

  private void initializeInjector() {
    DaggerLodgeComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .lodgeModule(new LodgeModule())
        .build()
        .inject(this);
  }

  private void initPresenter() {
    mModifyResidentPresenter.setView(this);
  }

  private void initData() {

  }

  private void initView() {
    initTitleBar("客户信息");
    InputWatcher watcher = new InputWatcher();
    InputWatcher.WatchCondition nameCondition = new InputWatcher.WatchCondition();
    nameCondition.setMinLength(1);
    InputWatcher.WatchCondition idCondition = new InputWatcher.WatchCondition();
    idCondition.setLength(18);
    InputWatcher.WatchCondition phoneCondition = new InputWatcher.WatchCondition();
    phoneCondition.setLength(11);
    watcher.addEt(mBinding.etName, nameCondition);
    watcher.addEt(mBinding.etId, idCondition);
    watcher.addEt(mBinding.etPhone, phoneCondition);
    TextView rightTextView = mTitleBar.getRightTextView();
    watcher.setInputListener(isEmpty -> {
      rightTextView.setClickable(!isEmpty);
      if (isEmpty) {
        rightTextView.setTextColor(getResources().getColor(R.color.gray_999999));
      } else {
        rightTextView.setTextColor(getResources().getColor(R.color.black_333333));
      }
    });

    InputUtil.inhibit(mBinding.etId, InputUtil.REGULAR_CHINESE_AND_SPACE, 18);
    InputUtil.inhibit(mBinding.etPhone, InputUtil.REGULAR_CHINESE_AND_SPACE, 11);

    mBinding.etName.setText(resident().getResidentName());
    mBinding.etId.setText(resident().getResidentIdentifier());
    mBinding.etPhone.setText(resident().getResidentPhone());
    mBinding.tvRoom.setText(resident().getRoomNo());
    mBinding.tvTime.setText(resident().getStartTime());
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

  @Override
  protected void onDestroy() {
    // 释放本地质量控制模型
    CameraNativeHelper.release();
    super.onDestroy();
    if (mModifyResidentPresenter != null) {
      mModifyResidentPresenter.destroy();
    }
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
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
          mBinding.etId.setText(result.getIdNumber().toString().trim());
        }
      }

      @Override
      public void onError(OCRError error) {
        ToastUtils.showShort("识别失败：" + error.getMessage());
      }
    });
  }

  public void scan(View view) {
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
  }

  @NonNull
  private String name() {
    return mBinding.etName.getText().toString().trim();
  }

  @NonNull
  private String id() {
    return mBinding.etId.getText().toString().trim();
  }

  @NonNull
  private String phone() {
    return mBinding.etPhone.getText().toString().trim();
  }


  @Override
  public void modifyResidentSuccess(ModifyResidentResponse response) {
    ToastUtils.showShort("客户信息修改成功");
    finish();
  }

  @Override
  protected void initTitleBar(String title) {
    super.initTitleBar(title);
    mTitleBar.getLeftTextView().setText("取消");
    mTitleBar.getLeftTextView().setText("取消");
    mTitleBar.getLeftTextView().setCompoundDrawables(null, null, null, null);
    mTitleBar.getRightTextView().setText("保存");
    mTitleBar.setListener((view, action, extra) -> {
      if (action == TitleBar.ACTION_LEFT_TEXT) {
        finish();
      } else if (action == TitleBar.ACTION_RIGHT_TEXT) {
        mModifyResidentPresenter.modifyResident(resident().getDeviceSerialNo(), name(), id(), phone());
      }
    });
  }

  private boolean checkTokenStatus() {
    if (!mOrcToken) {
      ToastUtils.showShort("ocr token还未成功获取");
    }
    return mOrcToken;
  }

  private GetRoomManageResponse resident() {
    GetRoomManageResponse data = (GetRoomManageResponse) getIntent().getSerializableExtra(Navigator.EXTRA_RESIDENT);
    return data == null ? new GetRoomManageResponse() : data;
  }
}
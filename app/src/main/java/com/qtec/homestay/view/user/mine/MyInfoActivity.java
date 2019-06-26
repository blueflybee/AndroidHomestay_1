package com.qtec.homestay.view.user.mine;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.baoyz.actionsheet.ActionSheet;
import com.blankj.utilcode.util.ToastUtils;
import com.hss01248.photoouter.PhotoCallback;
import com.hss01248.photoouter.PhotoUtil;
import com.qtec.homestay.R;
import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.databinding.ActivityMyInfoBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.mapp.rsp.LogoutResponse;
import com.qtec.homestay.domain.model.mapp.rsp.ModifyUserInfoResponse;
import com.qtec.homestay.internal.di.components.DaggerUserComponent;
import com.qtec.homestay.internal.di.modules.UserModule;
import com.qtec.homestay.utils.GlideUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.user.login.LoginActivity;
import com.qtec.homestay.view.user.mine.utils.ProxyTools;

import java.util.List;

import javax.inject.Inject;

/**
 * <pre>
 *     author : author
 *     e-mail :
 *     time   : 2018/07/27
 *     desc   : MyInfoActivity
 * </pre>
 */
public class MyInfoActivity extends BaseActivity implements LogoutView, MyInfoView {

  private static final String TAG = MyInfoActivity.class.getName();

  @Inject
  LogoutPresenter mLogoutPresenter;

  @Inject
  MyInfoPresenter mMyInfoPresenter;

  private ActivityMyInfoBinding mBinding;
  private PhotoCallback mPhotoCallback;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_info);
    initializeInjector();
    initPresenter();
    initData();
    initView();
  }

  private void initializeInjector() {
    DaggerUserComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .userModule(new UserModule())
        .build()
        .inject(this);
  }

  private void initPresenter() {
    mLogoutPresenter.setView(this);
    mMyInfoPresenter.setView(this);
  }

  private void initData() {
    mPhotoCallback = ProxyTools.getShowMethodInfoProxy(new PhotoCallback() {
      @Override
      public void onFail(String msg, Throwable r, int requestCode) {
        System.out.println("MyInfoActivity.onFail");
      }

      @Override
      public void onSuccessSingle(String originalPath, String compressedPath, int requestCode) {
        System.out.println("MyInfoActivity.onSuccessSingle");
        GlideUtil.loadCircleHeadImage(getContext(), compressedPath, mBinding.imgHead);
        mMyInfoPresenter.getStsToken(compressedPath);
      }

      @Override
      public void onSuccessMulti(List<String> originalPaths, List<String> compressedPaths, int requestCode) {}

      @Override
      public void onCancel(int requestCode) {
        System.out.println("MyInfoActivity.onCancel");
      }
    });
  }

  private void initView() {
    initTitleBar("账号信息");
    GlideUtil.loadCircleHeadImage(getContext(), PrefConstant.getUserHeadImg(), mBinding.imgHead, R.mipmap.set_person);
    setListeners();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mLogoutPresenter.destroy();
    mMyInfoPresenter.destroy();
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
  }

  public void headImage(View view) {

  }

  public void qq(View view) {

  }

  public void weChat(View view) {

  }

  public void bindPhone(View view) {

  }

  public void modifyPwd(View view) {
    mNavigator.navigateTo(getContext(), ModifyPwdActivity.class);
  }

  public void logout(View view) {
    showLogoutSheet();
  }

  private void setListeners() {
    mBinding.rlHeadImg.setOnClickListener(v -> {
      showHeadImgSheet();
    });

  }

  @Override
  public void onLogout(LogoutResponse response) {
    //((AndroidApplication) getApplication()).unbindBleService();
    mNavigator.navigateNewAndClearTask(getContext(), LoginActivity.class);
    finish();
  }

  private void showHeadImgSheet() {
    setTheme(R.style.ActionSheetStyleiOS7);
    ActionSheet.createBuilder(this, getSupportFragmentManager())
        .setCancelButtonTitle("取消")
        .setOtherButtonTitles("从相册中选择", "拍照")
        .setCancelableOnTouchOutside(true)
        .setListener(new ActionSheet.ActionSheetListener() {
          @Override
          public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
          }

          @Override
          public void onOtherButtonClick(ActionSheet actionSheet, int index) {
            switch (index) {
              // 调用自定义的相册
              case 0:
                PhotoUtil.begin()
                    .setNeedCropWhenOne(true)
                    .setNeedCompress(true)
                    .setMaxSelectCount(1)
                    .setCropMuskOval()
                    .setSelectGif()
                    .start(MyInfoActivity.this, 33, mPhotoCallback);
                break;
              // 跳转相机
              case 1:
                PhotoUtil.begin()
                    .setFromCamera(true)
                    .setNeedCropWhenOne(true)
                    .setNeedCompress(true)
                    .setMaxSelectCount(1)
                    .setCropMuskOval()
                    .setSelectGif()
                    .start(MyInfoActivity.this, 23, mPhotoCallback);
                break;
              default:
                break;
            }
          }
        }).show();
  }

  private void showLogoutSheet() {
    setTheme(R.style.ActionSheetStyleiOS7);
    ActionSheet.createBuilder(this, getSupportFragmentManager())
        .setCancelButtonTitle("取消")
        .setOtherButtonTitles("退出登录")
        .setCancelableOnTouchOutside(true)
        .setListener(new ActionSheet.ActionSheetListener() {
          @Override
          public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
          }

          @Override
          public void onOtherButtonClick(ActionSheet actionSheet, int index) {
            switch (index) {
              case 0:
                mLogoutPresenter.logout(PrefConstant.getUserPhone());

                break;

              default:
                break;
            }
          }
        }).show();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    PhotoUtil.onActivityResult(this, requestCode, resultCode, data);
  }

  private void saveUserInfoLocal() {
//    if (!Strings.isNullOrEmpty(userNickName())) {
//      mBinding.getSp().put(PrefConstant.SP_USER_NICK_NAME, userNickName());
//    }

    if (!TextUtils.isEmpty(mMyInfoPresenter.getImageUrl())) {
      PrefConstant.putUserHeadImg(mMyInfoPresenter.getImageUrl());
    }
  }

  @Override
  public void uploadHeadImageSuccess(String imageUrl) {
    runOnUiThread(() -> {
      hideLoading();
      saveUserInfoLocal();
      mMyInfoPresenter.modifyUserInfo(PrefConstant.getUserPhone(), mMyInfoPresenter.getImageUrl(), PrefConstant.getUserNickName());
    });
  }

  @Override
  public void uploadHeadImageFail() {
    runOnUiThread(() -> {
      hideLoading();
      ToastUtils.showShort("头像设置失败");
    });
  }

  @Override
  public void showModifySuccess(ModifyUserInfoResponse response) {
//    ToastUtils.showShort("头像设置成功");
  }
}
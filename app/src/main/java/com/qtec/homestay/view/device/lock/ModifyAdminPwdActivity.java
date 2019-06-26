package com.qtec.homestay.view.device.lock;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.blueflybee.titlebarlib.widget.TitleBar;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityModifyAdminPwdBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevsResponse;
import com.qtec.homestay.domain.model.mapp.rsp.ModifyAdmPassResponse;
import com.qtec.homestay.internal.di.components.DaggerLockComponent;
import com.qtec.homestay.internal.di.modules.LockModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.utils.InputUtil;
import com.qtec.homestay.utils.WidgetUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.component.InputWatcher;
import com.qtec.homestay.view.device.data.Lock;


import javax.inject.Inject;
/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/13
 *     desc   : ModifyAdminPwdActivity
 * </pre>
 */
public class ModifyAdminPwdActivity extends BaseActivity implements ModifyAdmPassView {

  private static final String TAG = ModifyAdminPwdActivity.class.getName();

  @Inject
  ModifyAdmPassPresenter mModifyAdminPwdPresenter;

  private ActivityModifyAdminPwdBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_modify_admin_pwd);
    initializeInjector();
    initPresenter();
    initData();
    initView();
  }

  private void initializeInjector() {
    DaggerLockComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .lockModule(new LockModule())
        .build()
        .inject(this);
  }

  private void initPresenter() {
    mModifyAdminPwdPresenter.setView(this);
  }

  private void initData() {

  }

  private void initView() {
    initTitleBar("修改密码");
    InputWatcher watcher = new InputWatcher();
    InputWatcher.WatchCondition condition = new InputWatcher.WatchCondition();
    condition.setLength(6);
    watcher.addEt(mBinding.etPwd, condition);
    watcher.setInputListener(isEmpty -> {
      mTitleBar.getRightTextView().setClickable(!isEmpty);
      if (isEmpty) {
        mTitleBar.getRightTextView().setTextColor(getResources().getColor(R.color.gray_cdcdcd));
      } else {
        mTitleBar.getRightTextView().setTextColor(getResources().getColor(R.color.black_333333));
      }
    });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mModifyAdminPwdPresenter != null) {
      mModifyAdminPwdPresenter.destroy();
    }
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
  }

	@NonNull
	private String pwd() {
		return mBinding.etPwd.getText().toString().trim();
	}

  private Lock lock() {
    Lock data = (Lock) getIntent().getSerializableExtra(Navigator.EXTRA_DEVICE);
    return data == null ? new Lock() : data;
  }

  @Override
  protected void initTitleBar(String title) {
    super.initTitleBar(title);
    mTitleBar.getRightTextView().setText("保存");
    mTitleBar.setListener((view, action, extra) -> {
      if (action == TitleBar.ACTION_LEFT_TEXT) {
        finish();
      } else if (action == TitleBar.ACTION_RIGHT_TEXT) {
        mModifyAdminPwdPresenter.modify(lock().getId(), pwd());
      }
    });
  }

  @Override
  public void showModifySuccess(ModifyAdmPassResponse response) {
    ToastUtils.showShort("管理员密码修改成功");
    finish();
  }
}
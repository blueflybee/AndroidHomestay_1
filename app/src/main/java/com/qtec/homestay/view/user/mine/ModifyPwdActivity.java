package com.qtec.homestay.view.user.mine;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityModifyPwdBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.mapp.rsp.ModifyPwdResponse;
import com.qtec.homestay.internal.di.components.DaggerUserComponent;
import com.qtec.homestay.internal.di.modules.UserModule;
import com.qtec.homestay.utils.InputUtil;
import com.qtec.homestay.utils.WidgetUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.component.InputWatcher;


import javax.inject.Inject;
/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/13
 *     desc   : ModifyPwdActivity
 * </pre>
 */
public class ModifyPwdActivity extends BaseActivity implements ModifyPwdView {

  private static final String TAG = ModifyPwdActivity.class.getName();

  @Inject
  ModifyPwdPresenter mModifyPwdPresenter;

  private ActivityModifyPwdBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_modify_pwd);
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
    mModifyPwdPresenter.setView(this);
  }

  private void initData() {

  }

  private void initView() {
    initTitleBar();
    InputWatcher watcher = new InputWatcher();
    InputWatcher.WatchCondition condition = new InputWatcher.WatchCondition();
    condition.setRange(new InputWatcher.InputRange(6, 20));
    watcher.addEt(mBinding.etPwd, condition);
    watcher.addEt(mBinding.etNewPwd, condition);
    watcher.setInputListener(isEmpty -> {
      mBinding.btnModifyPwd.setClickable(!isEmpty);
      if (isEmpty) {
        mBinding.btnModifyPwd.setBackgroundResource(R.drawable.shape_rec_btn_disable);
      } else {
        mBinding.btnModifyPwd.setBackgroundResource(R.drawable.btn_login_selector);
      }
    });

    InputUtil.inhibit(mBinding.etPwd, InputUtil.REGULAR_CHINESE_AND_SPACE, 20);
//    WidgetUtil.watchEditTextNoClear(mBinding.etPwd);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mModifyPwdPresenter != null) {
      mModifyPwdPresenter.destroy();
    }
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
  }

	public void changeInputType(View view) {
    Object tag = mBinding.ivShowNewPwd.getTag();
    if ("hide".equals(tag)) {
      mBinding.ivShowNewPwd.setImageResource(R.mipmap.otheropen);
      mBinding.ivShowNewPwd.setTag("show");
    } else {
      mBinding.ivShowNewPwd.setImageResource(R.mipmap.otherbihe);
      mBinding.ivShowNewPwd.setTag("hide");
    }
    InputUtil.showHidePwd(mBinding.etNewPwd);
	}

	public void modifyPwd(View view) {
    mModifyPwdPresenter.modifyPwd(pwd(), newPwd());
	}

	@NonNull
	private String pwd() {
		return mBinding.etPwd.getText().toString().trim();
	}

	@NonNull
	private String newPwd() {
		return mBinding.etNewPwd.getText().toString().trim();
	}


  @Override
  public void showModifySuccess(ModifyPwdResponse response) {
    ToastUtils.showShort("登录密码修改成功");
    finish();
  }
}
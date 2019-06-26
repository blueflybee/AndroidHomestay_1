package com.qtec.homestay.view.user.mine;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivityFeedbackBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.mapp.rsp.AddFeedbackResponse;
import com.qtec.homestay.internal.di.components.DaggerUserComponent;
import com.qtec.homestay.internal.di.modules.UserModule;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.component.InputWatcher;

import javax.inject.Inject;
/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/07/30
 *     desc   : FeedbackActivity
 * </pre>
 */
public class FeedbackActivity extends BaseActivity implements FeedbackView {

  private static final String TAG = FeedbackActivity.class.getName();

  @Inject
  FeedbackPresenter mFeedbackPresenter;

  private ActivityFeedbackBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_feedback);
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
    mFeedbackPresenter.setView(this);
  }

  private void initData() {

  }

  private void initView() {
    initTitleBar("意见反馈");
    mBinding.etFeedback.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {}

      @Override
      public void afterTextChanged(Editable s) {
        mBinding.tvInputCount.setText(String.valueOf(300 - s.length()));
      }
    });

    InputWatcher watcher = new InputWatcher();
    InputWatcher.WatchCondition condition = new InputWatcher.WatchCondition();
    condition.setMinLength(1);
    watcher.addEt(mBinding.etFeedback, condition);
    watcher.setInputListener(isEmpty -> {
      mBinding.btnFeedback.setClickable(!isEmpty);
      if (isEmpty) {
        mBinding.btnFeedback.setBackgroundResource(R.drawable.shape_rec_btn_disable);
      } else {
        mBinding.btnFeedback.setBackgroundResource(R.drawable.btn_login_selector);
      }
    });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mFeedbackPresenter != null) {
      mFeedbackPresenter.destroy();
    }
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
  }

	public void feedback(View view) {
		mFeedbackPresenter.add(feedback());
	}

	@NonNull
	private String feedback() {
		return mBinding.etFeedback.getText().toString().trim();
	}

  @Override
  public void onAddSuccess(AddFeedbackResponse response) {
    ToastUtils.showShort("反馈意见提交成功");
  }
}
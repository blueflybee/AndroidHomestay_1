package com.qtec.homestay.view.user.register;

import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.presenter.Presenter;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.mapp.req.CheckCodeRequest;
import com.qtec.homestay.domain.model.mapp.rsp.CheckCodeResponse;
import com.qtec.homestay.interactor.AppSubscriber;

import javax.inject.Inject;
import javax.inject.Named;
import com.qtec.homestay.domain.interactor.cloud.CheckCode;
import dagger.internal.Preconditions;

/**
 * <pre>
 *     author : wusj
 *     time   : 2018/09/05
 *     desc   : CheckCodePresenter
 * </pre>
 */
@PerActivity
public class CheckCodePresenter implements Presenter {

  private final CheckCode mCheckCode;
  private CheckCodeView mView;

  @Inject
  public CheckCodePresenter(@Named(CloudUseCaseComm.CHECK_CODE) CheckCode pCheckCode) {
    this.mCheckCode = Preconditions.checkNotNull(pCheckCode, "pCheckCode is null");
  }

  @Override
  public void resume() {}

  @Override
  public void pause() {}

  @Override
  public void destroy() {
    mCheckCode.dispose();
    mView = null;
  }

  public void setView(CheckCodeView view) {
    this.mView = view;
  }

  public void checkCode(String phone, String code) {
    CheckCodeRequest request = new CheckCodeRequest();
    request.setUserPhone(phone);
    request.setSmsCode(code);
    QtecEncryptInfo<CheckCodeRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);
    mView.showLoading();
    mCheckCode.execute(encryptInfo, new AppSubscriber<CheckCodeResponse>(mView) {
      @Override
      protected void doNext(CheckCodeResponse response) {
        mView.onCheckCodeSuccess(response);
      }
    });
  }
}
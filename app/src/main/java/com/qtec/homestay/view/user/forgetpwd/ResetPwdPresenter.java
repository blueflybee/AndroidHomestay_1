package com.qtec.homestay.view.user.forgetpwd;


import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.domain.interactor.cloud.ResetPwd;
import com.qtec.homestay.domain.interactor.cloud.ResetPwdGetIdCode;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.mapp.req.ResetPwdGetIdCodeRequest;
import com.qtec.homestay.domain.model.mapp.req.ResetPwdRequest;
import com.qtec.homestay.domain.model.mapp.rsp.ResetPwdGetIdCodeResponse;
import com.qtec.homestay.domain.model.mapp.rsp.ResetPwdResponse;
import com.qtec.homestay.interactor.AppSubscriber;
import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.presenter.Presenter;

import javax.inject.Inject;
import javax.inject.Named;

import static dagger.internal.Preconditions.checkNotNull;


/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2017/06/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@PerActivity
public class ResetPwdPresenter implements Presenter {

  private final ResetPwdGetIdCode resetPwdGetIdCode;
  private final ResetPwd resetPwd;
  private ResetPwdView resetPwdView;

  @Inject
  public ResetPwdPresenter(@Named(CloudUseCaseComm.RESET_PWD_GET_ID_CODE) ResetPwdGetIdCode resetPwdGetIdCode,
                           @Named(CloudUseCaseComm.RESET_PWD) ResetPwd resetPwd) {
    this.resetPwdGetIdCode = checkNotNull(resetPwdGetIdCode, "resetPwdGetIdCode cannot be null!");
    this.resetPwd = checkNotNull(resetPwd, "resetPwd cannot be null!");
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {

  }

  @Override
  public void destroy() {
    resetPwdGetIdCode.dispose();
    resetPwd.dispose();
  }

  public void setView(ResetPwdView resetPwdView) {
    this.resetPwdView = resetPwdView;
  }

  public void getIdCode(String username) {
    ResetPwdGetIdCodeRequest request = new ResetPwdGetIdCodeRequest();
    request.setUserPhone(username);
    QtecEncryptInfo<ResetPwdGetIdCodeRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);

    resetPwdView.showLoading();
    resetPwdGetIdCode.execute(encryptInfo, new AppSubscriber<ResetPwdGetIdCodeResponse>(resetPwdView) {
      @Override
      protected void doNext(ResetPwdGetIdCodeResponse response) {
        resetPwdView.onGetIdCodeSuccess();
      }
    });
  }

  public void resetPwd(String username, String smsCode, String pwd) {
    ResetPwdRequest request = new ResetPwdRequest();
    request.setUserPhone(username);
    request.setSmsCode(smsCode);
    request.setUserPassword(pwd);
    QtecEncryptInfo<ResetPwdRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);

    resetPwdView.showLoading();

    resetPwd.execute(encryptInfo, new AppSubscriber<ResetPwdResponse>(resetPwdView) {
      @Override
      protected void doNext(ResetPwdResponse response) {
        resetPwdView.showResetPwdSuccess();
      }
    });

  }
}

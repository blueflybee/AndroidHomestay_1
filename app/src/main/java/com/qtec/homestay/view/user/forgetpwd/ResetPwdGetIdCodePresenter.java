package com.qtec.homestay.view.user.forgetpwd;


import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.domain.interactor.cloud.ResetPwdGetIdCode;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.mapp.req.ResetPwdGetIdCodeRequest;
import com.qtec.homestay.domain.model.mapp.rsp.ResetPwdGetIdCodeResponse;
import com.qtec.homestay.interactor.AppSubscriber;
import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.presenter.Presenter;

import javax.inject.Inject;
import javax.inject.Named;

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
public class ResetPwdGetIdCodePresenter implements Presenter {

  private final ResetPwdGetIdCode resetPwdGetIdCode;
  private ResetPwdGetIdCodeView resetPwdGetIdCodeView;

  @Inject
  public ResetPwdGetIdCodePresenter(@Named(CloudUseCaseComm.RESET_PWD_GET_ID_CODE) ResetPwdGetIdCode resetPwdGetIdCode) {
    this.resetPwdGetIdCode = resetPwdGetIdCode;
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
  }

  public void setView(ResetPwdGetIdCodeView getIdCodeView) {
    this.resetPwdGetIdCodeView = getIdCodeView;
  }

  public void getIdCode(String username) {
    ResetPwdGetIdCodeRequest request = new ResetPwdGetIdCodeRequest();
    request.setUserPhone(username);
    QtecEncryptInfo<ResetPwdGetIdCodeRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);

    resetPwdGetIdCodeView.showLoading();

    resetPwdGetIdCode.execute(encryptInfo, new AppSubscriber<ResetPwdGetIdCodeResponse>(resetPwdGetIdCodeView) {
      @Override
      protected void doNext(ResetPwdGetIdCodeResponse response) {
        resetPwdGetIdCodeView.showGetIdCodeSuccess();
        resetPwdGetIdCodeView.openResetPwd(response);
      }
    });
  }

}

package com.qtec.homestay.view.user.register;


import android.text.TextUtils;

import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.domain.interactor.UseCase;
import com.qtec.homestay.domain.interactor.cloud.GetIdCode;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.mapp.req.GetIdCodeRequest;
import com.qtec.homestay.domain.model.mapp.rsp.GetIdCodeResponse;
import com.qtec.homestay.interactor.AppSubscriber;
import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.presenter.Presenter;

import org.bouncycastle.util.Strings;

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
public class GetIdCodePresenter implements Presenter {

  private final GetIdCode getIdCodeUseCase;
  private GetIdCodeView getIdCodeView;

  @Inject
  public GetIdCodePresenter(@Named(CloudUseCaseComm.GET_ID_CODE) GetIdCode getIdCodeUseCase) {
    this.getIdCodeUseCase = getIdCodeUseCase;
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {

  }

  @Override
  public void destroy() {
    getIdCodeUseCase.dispose();
  }

  public void setView(GetIdCodeView getIdCodeView) {
    this.getIdCodeView = getIdCodeView;
  }

  public void getIdCode(String username) {
    if (TextUtils.isEmpty(username)) {
      getIdCodeView.showUserPhoneEmp();
      return;
    }

    GetIdCodeRequest request = new GetIdCodeRequest();
    request.setUserPhone(username);
    QtecEncryptInfo<GetIdCodeRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);

    getIdCodeView.showLoading();
    getIdCodeUseCase.execute(encryptInfo, new AppSubscriber<GetIdCodeResponse>(getIdCodeView) {
      @Override
      protected void doNext(GetIdCodeResponse response) {
        getIdCodeView.showGetIdCodeSuccess();
        getIdCodeView.openRegister(response);
      }
    });
  }

}

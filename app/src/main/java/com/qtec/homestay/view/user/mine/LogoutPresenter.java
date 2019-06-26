package com.qtec.homestay.view.user.mine;

import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.domain.model.mapp.req.LogoutRequest;
import com.qtec.homestay.domain.model.mapp.rsp.LogoutResponse;
import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.presenter.Presenter;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.interactor.AppSubscriber;

import javax.inject.Inject;
import javax.inject.Named;

import com.qtec.homestay.domain.interactor.cloud.Logout;

import dagger.internal.Preconditions;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 18-7-16
 *     desc   : presenter
 *     version: 1.0
 * </pre>
 */
@PerActivity
public class LogoutPresenter implements Presenter {

  private final Logout mLogout;
  private LogoutView mView;

  @Inject
  public LogoutPresenter(@Named(CloudUseCaseComm.LOGOUT) Logout pLogout) {
    this.mLogout = Preconditions.checkNotNull(pLogout, "pLogout is null");
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {

  }

  @Override
  public void destroy() {
    mLogout.dispose();
  }

  public void setView(LogoutView view) {
    this.mView = view;
  }

  public void logout(String username) {
    LogoutRequest request = new LogoutRequest();
    request.setUserPhone(username);
    QtecEncryptInfo<LogoutRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);
    mView.showLoading();
    mLogout.execute(encryptInfo, new AppSubscriber<LogoutResponse>(mView) {

      @Override
      public void onStart() {
        super.onStart();
        // TODO: 18-7-16 mqqt
//        MQTTManager.instance().stop();
        PrefConstant.putAppToken("");
//        PrefConstant.putCloudUrl("");
      }

      @Override
      public void onError(Throwable e) {
        super.onError(e);
        mView.onLogout(null);
      }

      @Override
      protected void doNext(LogoutResponse response) {
        mView.onLogout(response);
      }
    });
  }

}

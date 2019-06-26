package com.qtec.homestay.view.lodge;

import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.domain.interactor.cloud.LeaveHotel;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.mapp.req.LeaveHotelRequest;
import com.qtec.homestay.domain.model.mapp.rsp.LeaveHotelResponse;
import com.qtec.homestay.interactor.AppSubscriber;
import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.presenter.Presenter;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.internal.Preconditions;

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
public class LeaveHotelPresenter implements Presenter {

  private final LeaveHotel useCase;
  private LeaveHotelView view;

  @Inject
  public LeaveHotelPresenter(@Named(CloudUseCaseComm.LEAVE_HOTEL) LeaveHotel useCase) {
    this.useCase = Preconditions.checkNotNull(useCase, "useCase is null");
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {

  }

  @Override
  public void destroy() {
    useCase.dispose();
  }

  public void setView(LeaveHotelView view) {
    this.view = view;
  }

  public void checkOut(String deviceId) {
    LeaveHotelRequest request = new LeaveHotelRequest();
    request.setDeviceSerialNo(deviceId);
    request.setUserUniqueKey(PrefConstant.getUserUniqueKey());

    QtecEncryptInfo<LeaveHotelRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);
    view.showLoading();
    useCase.execute(encryptInfo, new AppSubscriber<LeaveHotelResponse>(view) {
      @Override
      protected void doNext(LeaveHotelResponse response) {
        view.onCheckOut(response);
      }
    });
  }


}

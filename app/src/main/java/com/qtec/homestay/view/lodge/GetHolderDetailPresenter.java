package com.qtec.homestay.view.lodge;

import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.domain.interactor.cloud.HouseHolderDetail;
import com.qtec.homestay.domain.interactor.cloud.LeaveHotel;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.mapp.req.GetHoldDetailRequest;
import com.qtec.homestay.domain.model.mapp.rsp.GetHoldDetailResponse;
import com.qtec.homestay.domain.model.mapp.rsp.LeaveHotelResponse;
import com.qtec.homestay.domain.params.IRequest;
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
public class GetHolderDetailPresenter implements Presenter {

  private final HouseHolderDetail useCase;
  private HouseholdDetailView view;

  @Inject
  public GetHolderDetailPresenter(@Named(CloudUseCaseComm.HOUSE_HOLDER_DETAIL) HouseHolderDetail useCase) {
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

  public void setView(HouseholdDetailView view) {
    this.view = view;
  }


  public void getHolderDetail(String deviceId) {
    GetHoldDetailRequest request = new GetHoldDetailRequest();
    request.setUserUniqueKey(PrefConstant.getUserUniqueKey());
    request.setDeviceSerialNo(deviceId);

    QtecEncryptInfo<GetHoldDetailRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);
    view.showLoading();
    useCase.execute(encryptInfo, new AppSubscriber<GetHoldDetailResponse>(view) {
      @Override
      protected void doNext(GetHoldDetailResponse response) {
        view.holdDetail(response);
      }
    });
  }
}

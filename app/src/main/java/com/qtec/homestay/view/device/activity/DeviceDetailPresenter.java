package com.qtec.homestay.view.device.activity;

import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.domain.model.mapp.rsp.GetDeviceDetailResponse;
import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.presenter.Presenter;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.mapp.req.GetDeviceDetailRequest;
import com.qtec.homestay.interactor.AppSubscriber;

import javax.inject.Inject;
import javax.inject.Named;
import com.qtec.homestay.domain.interactor.cloud.GetRouterDetail;
import dagger.internal.Preconditions;

/**
 * <pre>
 *     author : wusj
 *     time   : 2018/08/09
 *     desc   : DeviceDetailPresenter
 * </pre>
 */
@PerActivity
public class DeviceDetailPresenter implements Presenter {

  private final GetRouterDetail mGetRouterDetail;
  private DeviceDetailView mView;

  @Inject
  public DeviceDetailPresenter(@Named(CloudUseCaseComm.GET_ROUTER_DETAIL) GetRouterDetail pGetRouterDetail) {
    this.mGetRouterDetail = Preconditions.checkNotNull(pGetRouterDetail, "pGetRouterDetail is null");
  }

  @Override
  public void resume() {}

  @Override
  public void pause() {}

  @Override
  public void destroy() {
    mGetRouterDetail.dispose();
  }

  public void setView(DeviceDetailView view) {
    this.mView = view;
  }

  public void getDeviceDetail(String deviceId) {
    GetDeviceDetailRequest request = new GetDeviceDetailRequest();
    request.setDeviceSerialNo(deviceId);
    request.setUserUniqueKey(PrefConstant.getUserUniqueKey());
    QtecEncryptInfo<GetDeviceDetailRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);
    mView.showLoading();
    mGetRouterDetail.execute(encryptInfo, new AppSubscriber<GetDeviceDetailResponse>(mView) {
      @Override
      protected void doNext(GetDeviceDetailResponse response) {
        mView.showDeviceDetail(response);

      }
    });
  }
}
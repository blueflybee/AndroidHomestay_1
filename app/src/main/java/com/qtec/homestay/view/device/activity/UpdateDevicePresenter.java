package com.qtec.homestay.view.device.activity;

import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.presenter.Presenter;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.mapp.req.AddDevRequest;
import com.qtec.homestay.domain.model.mapp.rsp.AddDevResponse;
import com.qtec.homestay.domain.model.mapp.req.UnbindDevRequest;
import com.qtec.homestay.domain.model.mapp.rsp.UnbindDevResponse;
import com.qtec.homestay.interactor.AppSubscriber;

import javax.inject.Inject;
import javax.inject.Named;
import com.qtec.homestay.domain.interactor.cloud.AddDev;
import com.qtec.homestay.domain.interactor.cloud.UnbindDev;
import dagger.internal.Preconditions;

/**
 * <pre>
 *     author : wusj
 *     time   : 2018/08/06
 *     desc   : UpdateDevicePresenter
 * </pre>
 */
@PerActivity
public class UpdateDevicePresenter implements Presenter {

  private final AddDev mAddDev;
  private final UnbindDev mUnbindDev;
  private UpdateDeviceView mView;

  @Inject
  public UpdateDevicePresenter(@Named(CloudUseCaseComm.ADD_DEV) AddDev pAddDev,
                        @Named(CloudUseCaseComm.UNBIND_DEV) UnbindDev pUnbindDev) {
    this.mAddDev = Preconditions.checkNotNull(pAddDev, "pAddDev is null");
    this.mUnbindDev = Preconditions.checkNotNull(pUnbindDev, "pUnbindDev is null");
  }

  @Override
  public void resume() {}

  @Override
  public void pause() {}

  @Override
  public void destroy() {
    mAddDev.dispose();
    mUnbindDev.dispose();
  }

  public void setView(UpdateDeviceView view) {
    this.mView = view;
  }

  public void add(AddDevRequest request) {
    QtecEncryptInfo<AddDevRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);
    mView.showLoading();
    mAddDev.execute(encryptInfo, new AppSubscriber<AddDevResponse>(mView) {
      @Override
      protected void doNext(AddDevResponse response) {
        mView.onAddDevice(response);
      }
    });
  }

  public void unbind(String deviceId) {
    UnbindDevRequest request = new UnbindDevRequest();
    request.setUserUniqueKey(PrefConstant.getUserUniqueKey());
    request.setDeviceSerialNo(deviceId);
    QtecEncryptInfo<UnbindDevRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);
    mView.showLoading();
    mUnbindDev.execute(encryptInfo, new AppSubscriber<UnbindDevResponse>(mView) {
      @Override
      protected void doNext(UnbindDevResponse response) {
        mView.onUnbindDevice(response);
      }
    });
  }
}
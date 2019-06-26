package com.qtec.homestay.view.device.activity;

import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.presenter.Presenter;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.mapp.req.GetUnlockRecordsRequest;
import com.qtec.homestay.domain.model.mapp.rsp.GetUnlockRecordsResponse;
import com.qtec.homestay.interactor.AppSubscriber;

import javax.inject.Inject;
import javax.inject.Named;
import com.qtec.homestay.domain.interactor.cloud.GetUnlockRecords;

import java.util.List;

import dagger.internal.Preconditions;

/**
 * <pre>
 *     author : wusj
 *     time   : 2018/08/13
 *     desc   : DeviceRecordPresenter
 * </pre>
 */
//加上@PerActivity为scope作用域单例子，去掉则为多例注入
//@PerActivity
public class DeviceRecordPresenter implements Presenter {

  private final GetUnlockRecords mGetUnlockRecords;
  private DeviceRecordView mView;

  @Inject
  public DeviceRecordPresenter(@Named(CloudUseCaseComm.GET_UNLOCK_RECORDS) GetUnlockRecords pGetUnlockRecords) {
    this.mGetUnlockRecords = Preconditions.checkNotNull(pGetUnlockRecords, "pGetUnlockRecords is null");
  }

  @Override
  public void resume() {}

  @Override
  public void pause() {}

  @Override
  public void destroy() {
    mGetUnlockRecords.dispose();
  }

  public void setView(DeviceRecordView view) {
    this.mView = view;
  }

  public void getUnlockRecords(String deviceId, int size, String limit, String type) {
    GetUnlockRecordsRequest request = new GetUnlockRecordsRequest();
    request.setDeviceSerialNo(deviceId);
    request.setPageSize(size);
    request.setLimit(limit);
    request.setIsAlarm(type);
    request.setUserUniqueKey(PrefConstant.getUserUniqueKey());
    QtecEncryptInfo<GetUnlockRecordsRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);
//    mView.showLoading();
    mGetUnlockRecords.execute(encryptInfo, new AppSubscriber<List<GetUnlockRecordsResponse>>(mView) {
      @Override
      protected void doNext(List<GetUnlockRecordsResponse> responses) {
        mView.showUnlockRecords(responses);
      }
    });
  }
}
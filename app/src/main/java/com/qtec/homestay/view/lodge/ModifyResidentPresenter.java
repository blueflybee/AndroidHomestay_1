package com.qtec.homestay.view.lodge;

import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.domain.model.mapp.rsp.GetRoomManageResponse;
import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.presenter.Presenter;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.mapp.req.ModifyResidentRequest;
import com.qtec.homestay.domain.model.mapp.rsp.ModifyResidentResponse;
import com.qtec.homestay.interactor.AppSubscriber;

import javax.inject.Inject;
import javax.inject.Named;
import com.qtec.homestay.domain.interactor.cloud.ModifyResident;
import dagger.internal.Preconditions;

/**
 * <pre>
 *     author : wusj
 *     time   : 2018/09/06
 *     desc   : ModifyResidentPresenter
 * </pre>
 */
@PerActivity
public class ModifyResidentPresenter implements Presenter {

  private final ModifyResident mModifyResident;
  private ModifyResidentView mView;

  @Inject
  public ModifyResidentPresenter(@Named(CloudUseCaseComm.MODIFY_RESIDENT) ModifyResident pModifyResident) {
    this.mModifyResident = Preconditions.checkNotNull(pModifyResident, "pModifyResident is null");
  }

  @Override
  public void resume() {}

  @Override
  public void pause() {}

  @Override
  public void destroy() {
    mModifyResident.dispose();
    mView = null;
  }

  public void setView(ModifyResidentView view) {
    this.mView = view;
  }

  void modifyResident(String deviceId, String name, String id, String phone) {
    ModifyResidentRequest request = new ModifyResidentRequest();
    request.setUserUniqueKey(PrefConstant.getUserUniqueKey());
    request.setDeviceSerialNo(deviceId);
    request.setResidentName(name);
    request.setResidentIdentifier(id);
    request.setResidentPhone(phone);
    QtecEncryptInfo<ModifyResidentRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);
    mView.showLoading();
    mModifyResident.execute(encryptInfo, new AppSubscriber<ModifyResidentResponse>(mView) {
      @Override
      protected void doNext(ModifyResidentResponse response) {
        mView.modifyResidentSuccess(response);
      }
    });
  }
}
package com.qtec.homestay.view.device.lock;

import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.presenter.Presenter;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.mapp.req.ModifyAdmPassRequest;
import com.qtec.homestay.domain.model.mapp.rsp.ModifyAdmPassResponse;
import com.qtec.homestay.interactor.AppSubscriber;

import javax.inject.Inject;
import javax.inject.Named;
import com.qtec.homestay.domain.interactor.cloud.ModifyAdmPass;
import dagger.internal.Preconditions;

/**
 * <pre>
 *     author : wusj
 *     time   : 2018/08/13
 *     desc   : ModifyAdmPassPresenter
 * </pre>
 */
@PerActivity
public class ModifyAdmPassPresenter implements Presenter {

  private final ModifyAdmPass mModifyAdmPass;
  private ModifyAdmPassView mView;

  @Inject
  public ModifyAdmPassPresenter(@Named(CloudUseCaseComm.MODIFY_ADM_PASS) ModifyAdmPass pModifyAdmPass) {
    this.mModifyAdmPass = Preconditions.checkNotNull(pModifyAdmPass, "pModifyAdmPass is null");
  }

  @Override
  public void resume() {}

  @Override
  public void pause() {}

  @Override
  public void destroy() {
    mModifyAdmPass.dispose();
  }

  public void setView(ModifyAdmPassView view) {
    this.mView = view;
  }

  public void modify(String lockId, String pwd) {
    ModifyAdmPassRequest request = new ModifyAdmPassRequest();
    request.setDeviceSerialNo(lockId);
    request.setLockPass(pwd);
    request.setUserUniqueKey(PrefConstant.getUserUniqueKey());
    QtecEncryptInfo<ModifyAdmPassRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);
    mView.showLoading();
    mModifyAdmPass.execute(encryptInfo, new AppSubscriber<ModifyAdmPassResponse>(mView) {
      @Override
      protected void doNext(ModifyAdmPassResponse response) {
        mView.showModifySuccess(response);
      }
    });
  }
}
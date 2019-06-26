package com.qtec.homestay.view.user.mine;

import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.presenter.Presenter;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.mapp.req.ModifyPwdRequest;
import com.qtec.homestay.domain.model.mapp.rsp.ModifyPwdResponse;
import com.qtec.homestay.interactor.AppSubscriber;

import javax.inject.Inject;
import javax.inject.Named;
import com.qtec.homestay.domain.interactor.cloud.ModifyPwd;
import dagger.internal.Preconditions;

/**
 * <pre>
 *     author : wusj
 *     time   : 2018/08/13
 *     desc   : ModifyPwdPresenter
 * </pre>
 */
@PerActivity
public class ModifyPwdPresenter implements Presenter {

  private final ModifyPwd mModifyPwd;
  private ModifyPwdView mView;

  @Inject
  public ModifyPwdPresenter(@Named(CloudUseCaseComm.MODIFY_PWD) ModifyPwd pModifyPwd) {
    this.mModifyPwd = Preconditions.checkNotNull(pModifyPwd, "pModifyPwd is null");
  }

  @Override
  public void resume() {}

  @Override
  public void pause() {}

  @Override
  public void destroy() {
    mModifyPwd.dispose();
  }

  public void setView(ModifyPwdView view) {
    this.mView = view;
  }

  public void modifyPwd(String oldPwd, String newPwd) {
    ModifyPwdRequest request = new ModifyPwdRequest();
    request.setUserPhone(PrefConstant.getUserPhone());
    request.setOldPassword(oldPwd);
    request.setNewPassword(newPwd);
    QtecEncryptInfo<ModifyPwdRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);
    mView.showLoading();
    mModifyPwd.execute(encryptInfo, new AppSubscriber<ModifyPwdResponse>(mView) {
      @Override
      protected void doNext(ModifyPwdResponse response) {
        mView.showModifySuccess(response);
      }
    });
  }
}
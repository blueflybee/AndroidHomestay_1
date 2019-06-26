package com.qtec.homestay.view.user.register;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.qtec.homestay.constant.AppConstant;
import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.data.net.CloudRestApiImpl;
import com.qtec.homestay.data.net.CloudUrlPath;
import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.domain.interactor.cloud.GetIdCode;
import com.qtec.homestay.domain.interactor.cloud.Register;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.mapp.req.GetIdCodeRequest;
import com.qtec.homestay.domain.model.mapp.req.RegisterRequest;
import com.qtec.homestay.domain.model.mapp.rsp.GetIdCodeResponse;
import com.qtec.homestay.domain.model.mapp.rsp.RegisterResponse;
import com.qtec.homestay.interactor.AppSubscriber;
import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.presenter.Presenter;

import javax.inject.Inject;
import javax.inject.Named;

import static dagger.internal.Preconditions.checkNotNull;

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
public class RegisterPresenter implements Presenter {

  private final GetIdCode getIdCodeUseCase;
  private final Register registerUseCase;
  private RegisterView registerView;

  @Inject
  public RegisterPresenter(@Named(CloudUseCaseComm.GET_ID_CODE) GetIdCode getIdCodeUseCase,
                           @Named(CloudUseCaseComm.REGISTER) Register registerUseCase) {
    this.getIdCodeUseCase = checkNotNull(getIdCodeUseCase, "getIdCodeUseCase cannot be null!");
    this.registerUseCase = checkNotNull(registerUseCase, "registerUseCase cannot be null!");
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
    registerUseCase.dispose();
  }

  public void setView(RegisterView getIdCodeView) {
    this.registerView = getIdCodeView;
  }

  public void getIdCode(String username) {
    GetIdCodeRequest request = new GetIdCodeRequest();
    request.setUserPhone(username);
    QtecEncryptInfo<GetIdCodeRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);

    registerView.showLoading();
    getIdCodeUseCase.execute(encryptInfo, new AppSubscriber<GetIdCodeResponse>(registerView) {
      @Override
      protected void doNext(GetIdCodeResponse response) {
        registerView.onGetIdCodeSuccess();

      }
    });
  }

  public void register(String username, String smsCode, String pwd) {
    RegisterRequest request = new RegisterRequest();
    request.setUserPhone(username);
    request.setSmsCode(smsCode);
    request.setUserPassword(pwd);
    request.setDeviceSerialNumber(PrefConstant.getMsgDeviceID());
    request.setPlatform(AppConstant.PLATFORM_ANDROID);
    //todo setDeviceToken
//    request.setDeviceToken(MQQTUtils.getAndroidID(registerView.getContext()));
    request.setPhoneModel(DeviceUtils.getManufacturer());
    QtecEncryptInfo<RegisterRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);
    registerView.showLoading();
    registerUseCase.execute(encryptInfo, new AppSubscriber<RegisterResponse>(registerView) {
      @Override
      protected void doNext(RegisterResponse response) {
        SPUtils spUtils = SPUtils.getInstance(PrefConstant.SP_NAME);
        PrefConstant.setUserId(response.getId());
        spUtils.put(PrefConstant.SP_USER_HEAD_IMG, response.getUserHeadImg());
        spUtils.put(PrefConstant.SP_USER_NICK_NAME, response.getUserNickName());
        spUtils.put(PrefConstant.SP_USER_UNIQUE_KEY, response.getUserUniqueKey());
        spUtils.put(PrefConstant.SP_USER_PHONE, response.getUserPhone());
        CloudUrlPath.setToken(response.getToken());
        PrefConstant.putAppToken(response.getToken());
        PrefConstant.putCloudUrl(CloudRestApiImpl.getApiPostConnection().getUrl());

        registerView.showRegisterSuccess(response);
      }
    });

  }
}

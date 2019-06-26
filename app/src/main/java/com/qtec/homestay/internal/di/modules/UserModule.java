package com.qtec.homestay.internal.di.modules;
import com.qtec.homestay.domain.interactor.cloud.CheckCode;
import com.qtec.homestay.domain.interactor.cloud.WxLogin;
import com.qtec.homestay.domain.interactor.cloud.GetStsToken;
import com.qtec.homestay.domain.interactor.cloud.ModifyUserInfo;
import com.qtec.homestay.domain.interactor.cloud.ModifyPwd;
import com.qtec.homestay.domain.interactor.cloud.AddFeedback;


import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.domain.executor.PostExecutionThread;
import com.qtec.homestay.domain.executor.ThreadExecutor;
import com.qtec.homestay.domain.interactor.UseCase;
import com.qtec.homestay.domain.interactor.cloud.CheckAppVersion;
import com.qtec.homestay.domain.interactor.cloud.GetIdCode;
import com.qtec.homestay.domain.interactor.cloud.Login;
import com.qtec.homestay.domain.interactor.cloud.Logout;
import com.qtec.homestay.domain.interactor.cloud.Register;
import com.qtec.homestay.domain.interactor.cloud.ResetPwd;
import com.qtec.homestay.domain.interactor.cloud.ResetPwdGetIdCode;
import com.qtec.homestay.domain.model.mapp.rsp.LoginResponse;
import com.qtec.homestay.domain.repository.CloudRepository;
import com.qtec.homestay.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides user related collaborators.
 * UserModule包括登录、注册、忘记密码、修改密码等用户模块的功能
 */
@Module
public class UserModule {

  public UserModule() {}

  @Provides
  @PerActivity
  @Named(CloudUseCaseComm.LOGIN)
  Login provideLoginUseCase(CloudRepository cloudRepository,
                                             ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

    return new Login(cloudRepository, threadExecutor, postExecutionThread);
  }

  @Provides
  @PerActivity
  @Named(CloudUseCaseComm.GET_ID_CODE)
  GetIdCode provideGetIdCode(CloudRepository cloudRepository,
                           ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new GetIdCode(cloudRepository, threadExecutor, postExecutionThread);
  }

  @Provides
  @PerActivity
  @Named(CloudUseCaseComm.REGISTER)
  Register provideRegister(CloudRepository cloudRepository,
                        ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new Register(cloudRepository, threadExecutor, postExecutionThread);
  }

  @Provides
  @PerActivity
  @Named(CloudUseCaseComm.RESET_PWD_GET_ID_CODE)
  ResetPwdGetIdCode provideResetPwdGetIdCode(CloudRepository cloudRepository,
                                 ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new ResetPwdGetIdCode(cloudRepository, threadExecutor, postExecutionThread);
  }

  @Provides
  @PerActivity
  @Named(CloudUseCaseComm.RESET_PWD)
  ResetPwd provideResetPwd(CloudRepository cloudRepository,
                         ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new ResetPwd(cloudRepository, threadExecutor, postExecutionThread);
  }

  @Provides
  @PerActivity
  @Named(CloudUseCaseComm.CHECK_APP_VERSION)
  CheckAppVersion provideCheckAppVersion(CloudRepository cloudRepository,
                             ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new CheckAppVersion(cloudRepository, threadExecutor, postExecutionThread);
  }

  @Provides
  @PerActivity
  @Named(CloudUseCaseComm.LOGOUT)
  Logout provideLogout(CloudRepository cloudRepository,
                       ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new Logout(cloudRepository, threadExecutor, postExecutionThread);
  }


	@Provides
	@PerActivity
	@Named(CloudUseCaseComm.ADD_FEEDBACK)
	AddFeedback provideAddFeedback(CloudRepository cloudRepository,
	                     ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
		return new AddFeedback(cloudRepository, threadExecutor, postExecutionThread);
	}

	@Provides
	@PerActivity
	@Named(CloudUseCaseComm.MODIFY_PWD)
	ModifyPwd provideModifyPwd(CloudRepository cloudRepository,
	                     ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
		return new ModifyPwd(cloudRepository, threadExecutor, postExecutionThread);
	}

	@Provides
	@PerActivity
	@Named(CloudUseCaseComm.MODIFY_USER_INFO)
	ModifyUserInfo provideModifyUserInfo(CloudRepository cloudRepository,
	                     ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
		return new ModifyUserInfo(cloudRepository, threadExecutor, postExecutionThread);
	}

	@Provides
	@PerActivity
	@Named(CloudUseCaseComm.GET_STS_TOKEN)
	GetStsToken provideGetStsToken(CloudRepository cloudRepository,
	                     ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
		return new GetStsToken(cloudRepository, threadExecutor, postExecutionThread);
	}

	@Provides
	@PerActivity
	@Named(CloudUseCaseComm.WX_LOGIN)
	WxLogin provideWxLogin(CloudRepository cloudRepository,
	                     ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
		return new WxLogin(cloudRepository, threadExecutor, postExecutionThread);
	}

	@Provides
	@PerActivity
	@Named(CloudUseCaseComm.CHECK_CODE)
	CheckCode provideCheckCode(CloudRepository cloudRepository,
	                     ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
		return new CheckCode(cloudRepository, threadExecutor, postExecutionThread);
	}
}
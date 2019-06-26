package com.qtec.homestay.internal.di.components;
import com.qtec.homestay.view.user.login.PerfectInfoActivity;
import com.qtec.homestay.view.user.mine.ModifyPwdActivity;
import com.qtec.homestay.view.user.mine.FeedbackActivity;
import com.qtec.homestay.view.user.mine.VersionInfoActivity;
import com.qtec.homestay.view.user.mine.MyInfoActivity;


import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.internal.di.modules.ActivityModule;
import com.qtec.homestay.internal.di.modules.UserModule;
import com.qtec.homestay.view.main.MainActivity;
import com.qtec.homestay.view.user.forgetpwd.ResetPwdActivity;
import com.qtec.homestay.view.user.forgetpwd.ResetPwdSmsCodeActivity;
import com.qtec.homestay.view.user.forgetpwd.ResetPwdGetIdCodeActivity;
import com.qtec.homestay.view.user.login.LoginActivity;
import com.qtec.homestay.view.user.mine.MineFragment;
import com.qtec.homestay.view.user.register.RegisterActivity;
import com.qtec.homestay.view.user.register.RegisterGetIdCodeActivity;
import com.qtec.homestay.view.user.register.RegisterSmsCodeActivity;

import dagger.Component;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, UserModule.class})
public interface UserComponent extends ActivityComponent {

  void inject(LoginActivity loginActivity);
  void inject(RegisterGetIdCodeActivity registerGetIdCodeActivity);
  void inject(RegisterActivity registerActivity);

  void inject(ResetPwdGetIdCodeActivity resetPwdGetIdCodeActivity);

  void inject(ResetPwdSmsCodeActivity resetPwdSmsCodeActivity);

  void inject(MainActivity mainActivity);

  void inject(ResetPwdActivity resetPwdActivity);

  void inject(RegisterSmsCodeActivity registerSmsCodeActivity);

  void inject(MineFragment mineFragment);
	void inject(MyInfoActivity myInfoActivity);
	void inject(VersionInfoActivity versionInfoActivity);
	void inject(FeedbackActivity feedbackActivity);
	void inject(ModifyPwdActivity modifyPwdActivity);
	void inject(PerfectInfoActivity perfectInfoActivity);
}

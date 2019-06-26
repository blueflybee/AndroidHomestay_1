package com.qtec.homestay.internal.di.components;
import com.qtec.homestay.view.device.lock.UnlockRecordsActivity;
import com.qtec.homestay.view.device.lock.fragment.AbnormalUnlockRecordsFragment;
import com.qtec.homestay.view.device.lock.fragment.AllUnlockRecordsFragment;
import com.qtec.homestay.view.device.lock.ModifyAdminPwdActivity;


import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.internal.di.modules.ActivityModule;
import com.qtec.homestay.internal.di.modules.LockModule;
import com.qtec.homestay.internal.di.modules.UserModule;
import com.qtec.homestay.view.user.login.LoginActivity;

import dagger.Component;

/**
 * A scope {@link PerActivity} component.
 * Injects lock specific ui.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, LockModule.class})
public interface LockComponent extends ActivityComponent {

	void inject(ModifyAdminPwdActivity modifyAdminPwdActivity);
	void inject(AllUnlockRecordsFragment allUnlockRecordsFragment);
	void inject(AbnormalUnlockRecordsFragment abnormalUnlockRecordsFragment);
	void inject(UnlockRecordsActivity unlockRecordsActivity);
}

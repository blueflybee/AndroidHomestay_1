package com.qtec.homestay.internal.di.components;
import com.qtec.homestay.view.device.activity.TopologyActivity;
import com.qtec.homestay.view.device.activity.MoreDeviceActivity;
import com.qtec.homestay.view.device.lock.AdminPwdActivity;
import com.qtec.homestay.view.device.lock.BindRouterInfoActivity;
import com.qtec.homestay.view.device.lock.LockSetActivity;
import com.qtec.homestay.view.device.lock.AddLockActivity;
import com.qtec.homestay.view.device.router.RouterSetActivity;
import com.qtec.homestay.view.device.router.ConfigRouterWifiActivity;
import com.qtec.homestay.view.device.lock.LockListActivity;
import com.qtec.homestay.view.device.router.RouterListActivity;


import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.internal.di.modules.ActivityModule;
import com.qtec.homestay.internal.di.modules.RouterModule;

import dagger.Component;

/**
 * A scope {@link PerActivity} component.
 * Injects router specific ui.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, RouterModule.class})
public interface RouterComponent extends ActivityComponent {

	void inject(RouterListActivity routerListActivity);
	void inject(LockListActivity lockListActivity);
	void inject(ConfigRouterWifiActivity configRouterWifiActivity);
	void inject(RouterSetActivity routerSetActivity);
	void inject(AddLockActivity addLockActivity);
	void inject(LockSetActivity lockSetActivity);
	void inject(BindRouterInfoActivity bindRouterInfoActivity);
	void inject(MoreDeviceActivity moreDeviceActivity);

  void inject(AdminPwdActivity adminPwdActivity);
	void inject(TopologyActivity topologyActivity);
}

package com.qtec.homestay.internal.di.components;
import com.qtec.homestay.internal.di.modules.LodgeModule;
import com.qtec.homestay.internal.di.modules.RouterModule;
import com.qtec.homestay.view.device.fragment.DeviceFragment;


import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.internal.di.modules.ActivityModule;
import com.qtec.homestay.internal.di.modules.UserModule;
import com.qtec.homestay.view.main.MainActivity;
import com.qtec.homestay.view.main.HomeFragment;
import com.qtec.homestay.view.user.mine.MineFragment;

import dagger.Component;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, UserModule.class, RouterModule.class, LodgeModule.class})
public interface MainComponent extends ActivityComponent {

  void inject(MainActivity mainActivity);

  void inject(MineFragment mineFragment);
  void inject(HomeFragment homeFragment);
	void inject(DeviceFragment deviceFragment);
}

package com.qtec.homestay.internal.di.components;
import com.qtec.homestay.internal.di.modules.RouterModule;
import com.qtec.homestay.view.lodge.ModifyResidentActivity;
import com.qtec.homestay.view.lodge.ResidentDetailActivity;
import com.qtec.homestay.view.lodge.SelectRoomActivity;
import com.qtec.homestay.view.device.lock.LockActivity;
import com.qtec.homestay.view.main.SearchRoomActivity;


import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.internal.di.modules.ActivityModule;
import com.qtec.homestay.internal.di.modules.LodgeModule;
import com.qtec.homestay.view.lodge.CheckInRoomActivity;
import com.qtec.homestay.view.lodge.HouseholdDetailsActivity;

import dagger.Component;

/**
 * A scope {@link PerActivity} component.
 * Injects lodge specific ui.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, LodgeModule.class, RouterModule.class})
public interface LodgeComponent extends ActivityComponent {
  void inject(CheckInRoomActivity checkInRoomActivity);
  void inject(HouseholdDetailsActivity householdDetailsActivity);
	void inject(SearchRoomActivity searchRoomActivity);
	void inject(SelectRoomActivity selectRoomActivity);

  void inject(LockActivity lockActivity);
	void inject(ResidentDetailActivity residentDetailActivity);
	void inject(ModifyResidentActivity modifyResidentActivity);
}

package com.qtec.homestay.internal.di.modules;
import com.qtec.homestay.domain.interactor.cloud.ModifyResident;


import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.domain.executor.PostExecutionThread;
import com.qtec.homestay.domain.executor.ThreadExecutor;
import com.qtec.homestay.domain.interactor.cloud.HouseHolderDetail;
import com.qtec.homestay.domain.interactor.cloud.LeaveHotel;
import com.qtec.homestay.domain.interactor.cloud.Login;
import com.qtec.homestay.domain.interactor.cloud.RoomCheckIn;
import com.qtec.homestay.domain.interactor.cloud.RoomManagerList;
import com.qtec.homestay.domain.interactor.cloud.SelectRoomList;
import com.qtec.homestay.domain.repository.CloudRepository;
import com.qtec.homestay.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 *
 * LodgeModule 住宿相关模块
 */
@Module
public class LodgeModule {

  public LodgeModule() {}

  @Provides
  @PerActivity
  @Named(CloudUseCaseComm.ROOM_INFO_LIST)
  RoomManagerList provideRoomManagerListUseCase(CloudRepository cloudRepository,
                            ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

    return new RoomManagerList(cloudRepository, threadExecutor, postExecutionThread);
  }

  @Provides
  @PerActivity
  @Named(CloudUseCaseComm.ROOM_CHECK_IN)
  RoomCheckIn provideRoomCheckInUseCase(CloudRepository cloudRepository,
                            ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

    return new RoomCheckIn(cloudRepository, threadExecutor, postExecutionThread);
  }

  @Provides
  @PerActivity
  @Named(CloudUseCaseComm.LEAVE_HOTEL)
  LeaveHotel provideLeaveHotelUseCase(CloudRepository cloudRepository,
                            ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

    return new LeaveHotel(cloudRepository, threadExecutor, postExecutionThread);
  }

  @Provides
  @PerActivity
  @Named(CloudUseCaseComm.HOUSE_HOLDER_DETAIL)
  HouseHolderDetail provideHouseHolderDetailUseCase(CloudRepository cloudRepository,
                            ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

    return new HouseHolderDetail(cloudRepository, threadExecutor, postExecutionThread);
  }

  @Provides
  @PerActivity
  @Named(CloudUseCaseComm.SELECT_ROOM)
  SelectRoomList provideSelectRoomListUseCase(CloudRepository cloudRepository,
                                                ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

    return new SelectRoomList(cloudRepository, threadExecutor, postExecutionThread);
  }


	@Provides
	@PerActivity
	@Named(CloudUseCaseComm.MODIFY_RESIDENT)
	ModifyResident provideModifyResident(CloudRepository cloudRepository,
	                     ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
		return new ModifyResident(cloudRepository, threadExecutor, postExecutionThread);
	}
}
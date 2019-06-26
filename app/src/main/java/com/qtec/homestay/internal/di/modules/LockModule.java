package com.qtec.homestay.internal.di.modules;


import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.domain.executor.PostExecutionThread;
import com.qtec.homestay.domain.executor.ThreadExecutor;
import com.qtec.homestay.domain.interactor.cloud.GetUnlockRecords;
import com.qtec.homestay.domain.interactor.cloud.ModifyAdmPass;
import com.qtec.homestay.domain.interactor.cloud.ModifyRoomNo;
import com.qtec.homestay.domain.repository.CloudRepository;
import com.qtec.homestay.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 *
 * LockModule 门锁相关模块
 */
@Module
public class LockModule {

  public LockModule() {}

  @Provides
  @PerActivity
  @Named(CloudUseCaseComm.MODIFY_ADM_PASS)
  ModifyAdmPass provideModifyAdmPass(CloudRepository cloudRepository,
                               ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new ModifyAdmPass(cloudRepository, threadExecutor, postExecutionThread);
  }

  @Provides
  @PerActivity
  @Named(CloudUseCaseComm.MODIFY_ROOM_NO)
  ModifyRoomNo provideModifyRoomNo(CloudRepository cloudRepository,
                                ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new ModifyRoomNo(cloudRepository, threadExecutor, postExecutionThread);
  }

  @Provides
  @PerActivity
  @Named(CloudUseCaseComm.GET_UNLOCK_RECORDS)
  GetUnlockRecords provideGetUnlockRecords(CloudRepository cloudRepository,
                                 ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new GetUnlockRecords(cloudRepository, threadExecutor, postExecutionThread);
  }
}
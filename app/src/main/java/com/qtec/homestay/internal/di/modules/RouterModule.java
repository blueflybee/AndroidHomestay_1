package com.qtec.homestay.internal.di.modules;
import com.qtec.homestay.domain.interactor.cloud.GetRouterDetail;
import com.qtec.homestay.domain.interactor.cloud.Transmit;


import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.domain.executor.PostExecutionThread;
import com.qtec.homestay.domain.executor.ThreadExecutor;
import com.qtec.homestay.domain.interactor.cloud.AddDev;
import com.qtec.homestay.domain.interactor.cloud.GetDevTree;
import com.qtec.homestay.domain.interactor.cloud.GetDevs;
import com.qtec.homestay.domain.interactor.cloud.UnbindDev;
import com.qtec.homestay.domain.repository.CloudRepository;
import com.qtec.homestay.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 *
 * RouterModule 路由器相关模块
 */
@Module
public class RouterModule {

  public RouterModule() {}

  @Provides
  @PerActivity
  @Named(CloudUseCaseComm.ADD_DEV)
  AddDev provideAddDev(CloudRepository cloudRepository,
                       ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new AddDev(cloudRepository, threadExecutor, postExecutionThread);
  }

  @Provides
  @PerActivity
  @Named(CloudUseCaseComm.GET_DEVS)
  GetDevs provideGetDevs(CloudRepository cloudRepository,
                        ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new GetDevs(cloudRepository, threadExecutor, postExecutionThread);
  }

  @Provides
  @PerActivity
  @Named(CloudUseCaseComm.UNBIND_DEV)
  UnbindDev provideUnbindDev(CloudRepository cloudRepository,
                          ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new UnbindDev(cloudRepository, threadExecutor, postExecutionThread);
  }

  @Provides
  @PerActivity
  @Named(CloudUseCaseComm.GET_DEV_TREE)
  GetDevTree provideGetDevTree(CloudRepository cloudRepository,
                             ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new GetDevTree(cloudRepository, threadExecutor, postExecutionThread);
  }


	@Provides
	@PerActivity
	@Named(CloudUseCaseComm.TRANSMIT)
	Transmit provideTransmit(CloudRepository cloudRepository,
	                     ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
		return new Transmit(cloudRepository, threadExecutor, postExecutionThread);
	}

	@Provides
	@PerActivity
	@Named(CloudUseCaseComm.GET_ROUTER_DETAIL)
	GetRouterDetail provideGetRouterDetail(CloudRepository cloudRepository,
	                     ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
		return new GetRouterDetail(cloudRepository, threadExecutor, postExecutionThread);
	}
}
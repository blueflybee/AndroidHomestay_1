package com.qtec.homestay.domain.interactor.cloud;

import com.qtec.homestay.domain.executor.PostExecutionThread;
import com.qtec.homestay.domain.executor.ThreadExecutor;
import com.qtec.homestay.domain.interactor.UseCase;
import com.qtec.homestay.domain.model.mapp.rsp.CheckInRoomResponse;
import com.qtec.homestay.domain.model.mapp.rsp.LeaveHotelResponse;
import com.qtec.homestay.domain.params.IRequest;
import com.qtec.homestay.domain.repository.CloudRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * 登录用例
 *
 * @author shaojun
 * @name Login
 * @package com.fernandocejas.android10.sample.domain.interactor
 * @date 15-9-9
 */
public class LeaveHotel extends UseCase<LeaveHotelResponse> {

  private final CloudRepository cloudRepository;

  @Inject public LeaveHotel(CloudRepository cloudRepository, ThreadExecutor threadExecutor,
                            PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.cloudRepository = cloudRepository;
  }

  @Override
  protected Observable<LeaveHotelResponse> buildUseCaseObservable(IRequest param) {
    return cloudRepository.leaveHotel(param);
  }
}

package com.qtec.homestay.domain.interactor.cloud;

import com.qtec.homestay.domain.executor.PostExecutionThread;
import com.qtec.homestay.domain.executor.ThreadExecutor;
import com.qtec.homestay.domain.interactor.UseCase;
import com.qtec.homestay.domain.model.mapp.rsp.GetRoomManageResponse;
import com.qtec.homestay.domain.model.mapp.rsp.LoginResponse;
import com.qtec.homestay.domain.params.IRequest;
import com.qtec.homestay.domain.repository.CloudRepository;

import java.util.List;

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
public class RoomManagerList extends UseCase<List<GetRoomManageResponse>> {

  private final CloudRepository cloudRepository;

  @Inject public RoomManagerList(CloudRepository cloudRepository, ThreadExecutor threadExecutor,
                                 PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.cloudRepository = cloudRepository;
  }

  @Override
  protected Observable<List<GetRoomManageResponse>> buildUseCaseObservable(IRequest param) {
    return cloudRepository.getRoomMangerInfoList(param);
  }
}

package com.qtec.homestay.domain.interactor.cloud;

import com.qtec.homestay.domain.executor.PostExecutionThread;
import com.qtec.homestay.domain.executor.ThreadExecutor;
import com.qtec.homestay.domain.interactor.UseCase;
import com.qtec.homestay.domain.model.mapp.rsp.ModifyRoomNoResponse;
import com.qtec.homestay.domain.params.IRequest;
import com.qtec.homestay.domain.repository.CloudRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 18-7-17
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ModifyRoomNo extends UseCase<ModifyRoomNoResponse> {

  private final CloudRepository cloudRepository;

  @Inject
  public ModifyRoomNo(CloudRepository cloudRepository, ThreadExecutor threadExecutor,
                      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.cloudRepository = cloudRepository;
  }

  @Override
  protected Observable<ModifyRoomNoResponse> buildUseCaseObservable(IRequest param) {
    return cloudRepository.modifyRoomNo(param);
  }
}


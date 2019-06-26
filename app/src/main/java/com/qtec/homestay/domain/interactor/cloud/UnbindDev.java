package com.qtec.homestay.domain.interactor.cloud;

import com.qtec.homestay.domain.executor.PostExecutionThread;
import com.qtec.homestay.domain.executor.ThreadExecutor;
import com.qtec.homestay.domain.interactor.UseCase;
import com.qtec.homestay.domain.model.mapp.rsp.UnbindDevResponse;
import com.qtec.homestay.domain.params.IRequest;
import com.qtec.homestay.domain.repository.CloudRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 18-7-16
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class UnbindDev extends UseCase<UnbindDevResponse> {

  private final CloudRepository cloudRepository;

  @Inject
  public UnbindDev(CloudRepository cloudRepository, ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.cloudRepository = cloudRepository;
  }

  @Override
  protected Observable<UnbindDevResponse> buildUseCaseObservable(IRequest param) {
    return cloudRepository.unbindDev(param);
  }
}


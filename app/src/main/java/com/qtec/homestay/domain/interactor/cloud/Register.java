package com.qtec.homestay.domain.interactor.cloud;

import com.qtec.homestay.domain.executor.PostExecutionThread;
import com.qtec.homestay.domain.executor.ThreadExecutor;
import com.qtec.homestay.domain.interactor.UseCase;
import com.qtec.homestay.domain.model.mapp.rsp.RegisterResponse;
import com.qtec.homestay.domain.params.IRequest;
import com.qtec.homestay.domain.repository.CloudRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 18-7-13
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class Register extends UseCase<RegisterResponse> {

  private final CloudRepository cloudRepository;

  @Inject
  public Register(CloudRepository cloudRepository, ThreadExecutor threadExecutor,
                  PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.cloudRepository = cloudRepository;
  }

  @Override
  protected Observable<RegisterResponse> buildUseCaseObservable(IRequest param) {
    return cloudRepository.register(param);
  }
}


package com.qtec.homestay.domain.interactor.cloud;

import com.qtec.homestay.domain.executor.PostExecutionThread;
import com.qtec.homestay.domain.executor.ThreadExecutor;
import com.qtec.homestay.domain.interactor.UseCase;
import com.qtec.homestay.domain.params.IRequest;
import com.qtec.homestay.domain.repository.CloudRepository;
import com.qtec.homestay.domain.model.mapp.rsp.WxLoginResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2018/08/24
 *     desc   : WxLogin
 * </pre>
 */
public class WxLogin extends UseCase<WxLoginResponse> {

  private final CloudRepository cloudRepository;

  @Inject public WxLogin(CloudRepository cloudRepository, ThreadExecutor threadExecutor,
                       PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.cloudRepository = cloudRepository;
  }

  @Override
  protected Observable<WxLoginResponse> buildUseCaseObservable(IRequest request) {
    return cloudRepository.wxLogin(request);
  }
}
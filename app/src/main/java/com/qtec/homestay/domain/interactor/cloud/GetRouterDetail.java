package com.qtec.homestay.domain.interactor.cloud;

import com.qtec.homestay.domain.executor.PostExecutionThread;
import com.qtec.homestay.domain.executor.ThreadExecutor;
import com.qtec.homestay.domain.interactor.UseCase;
import com.qtec.homestay.domain.model.mapp.rsp.GetDeviceDetailResponse;
import com.qtec.homestay.domain.params.IRequest;
import com.qtec.homestay.domain.repository.CloudRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2018/08/09
 *     desc   : GetRouterDetail
 * </pre>
 */
public class GetRouterDetail extends UseCase<GetDeviceDetailResponse> {

  private final CloudRepository cloudRepository;

  @Inject public GetRouterDetail(CloudRepository cloudRepository, ThreadExecutor threadExecutor,
                       PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.cloudRepository = cloudRepository;
  }

  @Override
  protected Observable<GetDeviceDetailResponse> buildUseCaseObservable(IRequest request) {
    return cloudRepository.getRouterDetail(request);
  }
}
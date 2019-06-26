package com.qtec.homestay.domain.interactor.cloud;

import com.qtec.homestay.domain.executor.PostExecutionThread;
import com.qtec.homestay.domain.executor.ThreadExecutor;
import com.qtec.homestay.domain.interactor.UseCase;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevsResponse;
import com.qtec.homestay.domain.params.IRequest;
import com.qtec.homestay.domain.repository.CloudRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 18-7-16
 *     desc   :根据设备类型获取用户名下设备列表
 *     version: 1.0
 * </pre>
 */
public class GetDevs extends UseCase<List<GetDevsResponse>> {

  private final CloudRepository cloudRepository;

  @Inject
  public GetDevs(CloudRepository cloudRepository, ThreadExecutor threadExecutor,
                 PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.cloudRepository = cloudRepository;
  }

  @Override
  protected Observable<List<GetDevsResponse>> buildUseCaseObservable(IRequest param) {
    return cloudRepository.getDevs(param);
  }
}


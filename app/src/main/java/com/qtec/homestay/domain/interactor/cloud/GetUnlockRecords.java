package com.qtec.homestay.domain.interactor.cloud;

import com.qtec.homestay.domain.executor.PostExecutionThread;
import com.qtec.homestay.domain.executor.ThreadExecutor;
import com.qtec.homestay.domain.interactor.UseCase;
import com.qtec.homestay.domain.model.mapp.rsp.GetUnlockRecordsResponse;
import com.qtec.homestay.domain.params.IRequest;
import com.qtec.homestay.domain.repository.CloudRepository;

import java.util.List;

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
public class GetUnlockRecords extends UseCase<List<GetUnlockRecordsResponse>> {

  private final CloudRepository cloudRepository;

  @Inject
  public GetUnlockRecords(CloudRepository cloudRepository, ThreadExecutor threadExecutor,
                          PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.cloudRepository = cloudRepository;
  }

  @Override
  protected Observable<List<GetUnlockRecordsResponse>> buildUseCaseObservable(IRequest param) {
    return cloudRepository.getUnlockRecords(param);
  }
}


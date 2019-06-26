package com.qtec.homestay.domain.interactor.cloud;

import com.qtec.homestay.domain.executor.PostExecutionThread;
import com.qtec.homestay.domain.executor.ThreadExecutor;
import com.qtec.homestay.domain.interactor.UseCase;
import com.qtec.homestay.domain.params.IRequest;
import com.qtec.homestay.domain.repository.CloudRepository;
import com.qtec.homestay.domain.model.mapp.rsp.ModifyResidentResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2018/09/06
 *     desc   : ModifyResident
 * </pre>
 */
public class ModifyResident extends UseCase<ModifyResidentResponse> {

  private final CloudRepository cloudRepository;

  @Inject public ModifyResident(CloudRepository cloudRepository, ThreadExecutor threadExecutor,
                       PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.cloudRepository = cloudRepository;
  }

  @Override
  protected Observable<ModifyResidentResponse> buildUseCaseObservable(IRequest request) {
    return cloudRepository.modifyResident(request);
  }
}
package com.qtec.homestay.domain.interactor.cloud;

import com.qtec.homestay.domain.executor.PostExecutionThread;
import com.qtec.homestay.domain.executor.ThreadExecutor;
import com.qtec.homestay.domain.interactor.UseCase;
import com.qtec.homestay.domain.params.IRequest;
import com.qtec.homestay.domain.repository.CloudRepository;
import com.qtec.homestay.domain.model.mapp.rsp.ModifyPwdResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2018/08/13
 *     desc   : ModifyPwd
 * </pre>
 */
public class ModifyPwd extends UseCase<ModifyPwdResponse> {

  private final CloudRepository cloudRepository;

  @Inject public ModifyPwd(CloudRepository cloudRepository, ThreadExecutor threadExecutor,
                       PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.cloudRepository = cloudRepository;
  }

  @Override
  protected Observable<ModifyPwdResponse> buildUseCaseObservable(IRequest request) {
    return cloudRepository.modifyPwd(request);
  }
}
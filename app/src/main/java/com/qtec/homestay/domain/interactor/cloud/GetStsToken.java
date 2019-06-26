package com.qtec.homestay.domain.interactor.cloud;

import com.qtec.homestay.domain.executor.PostExecutionThread;
import com.qtec.homestay.domain.executor.ThreadExecutor;
import com.qtec.homestay.domain.interactor.UseCase;
import com.qtec.homestay.domain.params.IRequest;
import com.qtec.homestay.domain.repository.CloudRepository;
import com.qtec.homestay.domain.model.mapp.rsp.GetStsTokenResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetStsTokenResponse.*;

import javax.inject.Inject;

import io.reactivex.Observable;
/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2018/08/15
 *     desc   : GetStsToken
 * </pre>
 */
public class GetStsToken extends UseCase<GetStsTokenResponse<CredentialsBean, AssumedRoleUserBean>> {

  private final CloudRepository cloudRepository;

  @Inject public GetStsToken(CloudRepository cloudRepository, ThreadExecutor threadExecutor,
                       PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.cloudRepository = cloudRepository;
  }

  @Override
  protected Observable<GetStsTokenResponse<CredentialsBean, AssumedRoleUserBean>> buildUseCaseObservable(IRequest request) {
    return cloudRepository.getStsToken(request);
  }
}
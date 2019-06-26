package com.qtec.homestay.view.user.mine;

import com.blankj.utilcode.util.SPUtils;
import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.presenter.Presenter;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.mapp.req.AddFeedbackRequest;
import com.qtec.homestay.domain.model.mapp.rsp.AddFeedbackResponse;
import com.qtec.homestay.interactor.AppSubscriber;

import javax.inject.Inject;
import javax.inject.Named;
import com.qtec.homestay.domain.interactor.cloud.AddFeedback;
import dagger.internal.Preconditions;

/**
 * <pre>
 *     author : author
 *     time   : 2018/07/30
 *     desc   : FeedbackPresenter
 * </pre>
 */
@PerActivity
public class FeedbackPresenter implements Presenter {

  private final AddFeedback mAddFeedback;
  private FeedbackView mView;

  @Inject
  public FeedbackPresenter(@Named(CloudUseCaseComm.ADD_FEEDBACK) AddFeedback pAddFeedback) {
    this.mAddFeedback = Preconditions.checkNotNull(pAddFeedback, "pAddFeedback is null");
  }

  @Override
  public void resume() {}

  @Override
  public void pause() {}

  @Override
  public void destroy() {
    mAddFeedback.dispose();
  }

  public void setView(FeedbackView view) {
    this.mView = view;
  }

  public void add(String content) {
    AddFeedbackRequest request = new AddFeedbackRequest();
    request.setFeedbackContent(content);
    request.setUserUniqueKey(PrefConstant.getUserUniqueKey());
    QtecEncryptInfo<AddFeedbackRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);
    mView.showLoading();
    mAddFeedback.execute(encryptInfo, new AppSubscriber<AddFeedbackResponse>(mView) {
      @Override
      protected void doNext(AddFeedbackResponse response) {
        mView.onAddSuccess(response);
      }
    });
  }
}
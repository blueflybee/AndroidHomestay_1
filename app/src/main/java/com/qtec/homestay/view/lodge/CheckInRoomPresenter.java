package com.qtec.homestay.view.lodge;

import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.domain.interactor.cloud.RoomCheckIn;
import com.qtec.homestay.domain.model.mapp.rsp.CheckInRoomResponse;
import com.qtec.homestay.domain.params.IRequest;
import com.qtec.homestay.interactor.AppSubscriber;
import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.presenter.Presenter;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.internal.Preconditions;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2017/06/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@PerActivity
public class CheckInRoomPresenter implements Presenter {

  private final RoomCheckIn checkInUseCase;
  private RoomCheckInView checkInView;

  @Inject
  public CheckInRoomPresenter(@Named(CloudUseCaseComm.ROOM_CHECK_IN) RoomCheckIn checkInUseCase) {
    this.checkInUseCase = Preconditions.checkNotNull(checkInUseCase, "checkInUseCase is null");
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {

  }

  @Override
  public void destroy() {
    checkInUseCase.dispose();
  }

  public void setView(RoomCheckInView checkInView) {
    this.checkInView = checkInView;
  }

  public void checkInRoom(IRequest request) {
    checkInView.showLoading();
    checkInUseCase.execute(request, new AppSubscriber<CheckInRoomResponse>(checkInView) {
      @Override
      protected void doNext(CheckInRoomResponse response) {
        checkInView.checkInRoom(response);
      }
    });
  }


}

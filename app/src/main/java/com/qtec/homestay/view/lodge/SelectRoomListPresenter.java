package com.qtec.homestay.view.lodge;

import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.domain.interactor.cloud.RoomManagerList;
import com.qtec.homestay.domain.interactor.cloud.SelectRoomList;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.mapp.req.SelectRoomRequest;
import com.qtec.homestay.domain.model.mapp.rsp.SelectRoomResponse;
import com.qtec.homestay.interactor.AppSubscriber;
import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.presenter.Presenter;

import java.util.List;

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
public class SelectRoomListPresenter implements Presenter {

  private final SelectRoomList roomLisUseCase;
  private SelectRoomView selectRoomView;

  @Inject
  public SelectRoomListPresenter(@Named(CloudUseCaseComm.SELECT_ROOM) SelectRoomList roomLisUseCase) {
    this.roomLisUseCase = Preconditions.checkNotNull(roomLisUseCase, "roomLisUseCase is null");
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {

  }

  @Override
  public void destroy() {
    roomLisUseCase.dispose();
  }

  public void setView(SelectRoomView selectRoomView) {
    this.selectRoomView = selectRoomView;
  }

  public void selectRoomList() {
    SelectRoomRequest request = new SelectRoomRequest();
    request.setUserUniqueKey(PrefConstant.getUserUniqueKey());

    QtecEncryptInfo<SelectRoomRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);
    selectRoomView.showLoading();
    roomLisUseCase.execute(encryptInfo, new AppSubscriber<List<SelectRoomResponse>>(selectRoomView) {

      @Override
      public void onError(Throwable e) {
        super.onError(e);
        selectRoomView.showNoRoom();
      }

      @Override
      protected void doNext(List<SelectRoomResponse> response) {
        selectRoomView.getRoomList(response);
      }
    });
  }


}

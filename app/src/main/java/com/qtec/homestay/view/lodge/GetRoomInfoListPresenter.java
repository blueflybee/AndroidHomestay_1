package com.qtec.homestay.view.lodge;

import com.blankj.utilcode.util.EncryptUtils;
import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.domain.interactor.cloud.RoomManagerList;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.mapp.req.GetRoomManageRequest;
import com.qtec.homestay.domain.model.mapp.rsp.GetRoomManageResponse;
import com.qtec.homestay.domain.params.IRequest;
import com.qtec.homestay.interactor.AppSubscriber;
import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.presenter.Presenter;

import java.lang.ref.PhantomReference;
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
public class GetRoomInfoListPresenter implements Presenter {

  private final RoomManagerList roomLisUseCase;
  private RoomManageView roomManageView;

  @Inject
  public GetRoomInfoListPresenter(@Named(CloudUseCaseComm.ROOM_INFO_LIST) RoomManagerList roomLisUseCase) {
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

  public void setView(RoomManageView roomManageView) {
    this.roomManageView = roomManageView;
  }

  public void getRoomInfoList(int size, String limit) {
    GetRoomManageRequest request = new GetRoomManageRequest();
    request.setUserUniqueKey(PrefConstant.getUserUniqueKey());
    request.setPageSize(size);
    request.setLimit(limit);
    QtecEncryptInfo<GetRoomManageRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);
    roomManageView.showLoading();
    roomLisUseCase.execute(encryptInfo, new AppSubscriber<List<GetRoomManageResponse>>(roomManageView) {

      @Override
      public void onError(Throwable e) {
        super.onError(e);
        roomManageView.showNoRoomInfo();
      }

      @Override
      protected void doNext(List<GetRoomManageResponse> response) {
        if (response == null || response.isEmpty()) {
          roomManageView.showNoRoomInfo();
        } else {
          roomManageView.showRoomInfo(response);
        }
      }
    });
  }


}

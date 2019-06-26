package com.qtec.homestay.view.device.activity;

import android.graphics.Rect;

import com.blueflybee.uilibrary2.data.TopologyNode;
import com.qtec.homestay.R;
import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.presenter.Presenter;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.mapp.req.GetDevsRequest;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevsResponse;
import com.qtec.homestay.domain.model.mapp.req.GetDevTreeRequest;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevTreeResponse;
import com.qtec.homestay.interactor.AppSubscriber;

import javax.inject.Inject;
import javax.inject.Named;

import com.qtec.homestay.domain.interactor.cloud.GetDevs;
import com.qtec.homestay.domain.interactor.cloud.GetDevTree;
import com.qtec.homestay.view.device.data.Device;

import java.util.ArrayList;
import java.util.List;

import dagger.internal.Preconditions;

/**
 * <pre>
 *     author : wusj
 *     time   : 2018/08/02
 *     desc   : GetDevicePresenter
 * </pre>
 */
@PerActivity
public class GetDevicePresenter implements Presenter {

  private final GetDevs mGetDevs;
  private final GetDevTree mGetDevTree;
  private GetDeviceView mView;

  @Inject
  public GetDevicePresenter(@Named(CloudUseCaseComm.GET_DEVS) GetDevs pGetDevs,
                            @Named(CloudUseCaseComm.GET_DEV_TREE) GetDevTree pGetDevTree) {
    this.mGetDevs = Preconditions.checkNotNull(pGetDevs, "pGetDevs is null");
    this.mGetDevTree = Preconditions.checkNotNull(pGetDevTree, "pGetDevTree is null");
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  @Override
  public void destroy() {
    mGetDevs.dispose();
    mGetDevTree.dispose();
  }

  public void setView(GetDeviceView view) {
    this.mView = view;
  }

  public void getDevs(String devType, int size, String limit) {
    GetDevsRequest request = new GetDevsRequest();
    request.setDeviceType(devType);
    request.setPageSize(size);
    request.setLimit(limit);
    request.setUserUniqueKey(PrefConstant.getUserUniqueKey());
    QtecEncryptInfo<GetDevsRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);
    mView.showLoading();
    mGetDevs.execute(encryptInfo, new AppSubscriber<List<GetDevsResponse>>(mView) {
      @Override
      protected void doNext(List<GetDevsResponse> responses) {
        mView.showDevices(responses, devType);
      }
    });
  }

  void getDevTree() {
    GetDevTreeRequest request = new GetDevTreeRequest();
    request.setUserUniqueKey(PrefConstant.getUserUniqueKey());
    QtecEncryptInfo<GetDevTreeRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);
    mView.showLoading();
    mGetDevTree.execute(encryptInfo, new AppSubscriber<TopologyNode>(mView) {
      @Override
      protected void doNext(TopologyNode responses) {
        mView.showTopology(responses);
      }
    });
  }



}
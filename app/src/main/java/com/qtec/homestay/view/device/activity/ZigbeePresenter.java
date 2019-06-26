package com.qtec.homestay.view.device.activity;

import android.text.TextUtils;

import com.blankj.utilcode.util.DeviceUtils;
import com.google.gson.reflect.TypeToken;
import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.data.net.RouterUrlPath;
import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.domain.mapper.JsonMapper;
import com.qtec.homestay.domain.model.core.QtecResult;
import com.qtec.homestay.domain.model.mapp.req.LockBindRouterRequest;
import com.qtec.homestay.domain.model.mapp.req.LockUnbindRouterRequest;
import com.qtec.homestay.domain.model.mapp.req.QueryLockBindRouterRequest;
import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.presenter.Presenter;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.mapp.req.TransmitRequest;
import com.qtec.homestay.domain.model.mapp.rsp.TransmitResponse;
import com.qtec.homestay.interactor.AppSubscriber;

import javax.inject.Inject;
import javax.inject.Named;

import com.qtec.homestay.domain.interactor.cloud.Transmit;

import java.lang.reflect.Type;

import dagger.internal.Preconditions;

/**
 * <pre>
 *     author : wusj
 *     time   : 2018/08/08
 *     desc   : ZigbeePresenter
 * </pre>
 */
@PerActivity
public class ZigbeePresenter implements Presenter {

  public static final String LOCK_BIND_ROUTER_FAIL = "门锁绑定网关失败";
  public static final String LOCK_UNBIND_ROUTER_FAIL = "门锁解绑网关失败";
  private final Transmit mTransmit;
  private ZigbeeView mView;
  private JsonMapper mJsonMapper = new JsonMapper();

  @Inject
  public ZigbeePresenter(@Named(CloudUseCaseComm.TRANSMIT) Transmit pTransmit) {
    this.mTransmit = Preconditions.checkNotNull(pTransmit, "pTransmit is null");
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  @Override
  public void destroy() {
    mTransmit.dispose();
  }

  public void setView(ZigbeeView view) {
    this.mView = view;
  }

  public void bind(String lockId, String routerId) {
    LockBindRouterRequest routerRequest = new LockBindRouterRequest();
    routerRequest.setDevid(lockId);
    QtecEncryptInfo<TransmitRequest> encryptInfo = createTransRequest(
        routerRequest, routerId, RouterUrlPath.PATH_BIND_ROUTER_TO_LOCK, RouterUrlPath.METHOD_POST);

    mView.showLoading();
    mTransmit.execute(encryptInfo, new AppSubscriber<TransmitResponse>(mView) {
      @Override
      protected void doNext(TransmitResponse response) {
        try {
          Type type = new TypeToken<QtecResult>() {
          }.getType();
          QtecResult qtecResult = (QtecResult) mJsonMapper.fromJson(response.getEncryptInfo(), type);
          if (qtecResult == null) {
            mView.showBindFail(LOCK_BIND_ROUTER_FAIL);
          } else if (qtecResult.getCode() != 0) {
            mView.showBindFail(TextUtils.isEmpty(qtecResult.getMsg()) ? LOCK_BIND_ROUTER_FAIL : qtecResult.getMsg());
          } else {
            mView.showBindSuccess();
          }
        } catch (Exception e) {
          e.printStackTrace();
          mView.showBindFail(LOCK_BIND_ROUTER_FAIL);
        }
      }
    });
  }

  public void unbind(String lockId, String routerId) {
    LockUnbindRouterRequest routerRequest = new LockUnbindRouterRequest();
    routerRequest.setDevid(lockId);
    QtecEncryptInfo<TransmitRequest> encryptInfo = createTransRequest(
        routerRequest, routerId, RouterUrlPath.PATH_UNBIND_ROUTER_TO_LOCK, RouterUrlPath.METHOD_POST);

    mView.showLoading();
    mTransmit.execute(encryptInfo, new AppSubscriber<TransmitResponse>(mView) {
      @Override
      protected void doNext(TransmitResponse response) {
        try {
          Type type = new TypeToken<QtecResult>() {
          }.getType();
          QtecResult qtecResult = (QtecResult) mJsonMapper.fromJson(response.getEncryptInfo(), type);
          if (qtecResult == null) {
            mView.showUnbindFail(LOCK_UNBIND_ROUTER_FAIL);
          } else if (qtecResult.getCode() != 0) {
            mView.showUnbindFail(TextUtils.isEmpty(qtecResult.getMsg()) ? LOCK_UNBIND_ROUTER_FAIL : qtecResult.getMsg());
          } else {
            mView.showUnbindSuccess();
          }
        } catch (Exception e) {
          e.printStackTrace();
          mView.showUnbindFail(LOCK_UNBIND_ROUTER_FAIL);
        }
      }
    });
  }

  public void query(String lockId, String routerId) {
    QueryLockBindRouterRequest routerRequest = new QueryLockBindRouterRequest();
    routerRequest.setDevid(lockId);
    QtecEncryptInfo<TransmitRequest> encryptInfo = createTransRequest(
        routerRequest, routerId, RouterUrlPath.PATH_QUERY_BIND_ROUTER_TO_LOCK, RouterUrlPath.METHOD_POST);

    mView.showLoading();
    mTransmit.execute(encryptInfo, new AppSubscriber<TransmitResponse>(mView) {
      @Override
      protected void doNext(TransmitResponse response) {
        try {
          Type type = new TypeToken<QtecResult>() {
          }.getType();
          QtecResult qtecResult = (QtecResult) mJsonMapper.fromJson(response.getEncryptInfo(), type);
          if (qtecResult == null) {
            mView.showUnbindFail(LOCK_UNBIND_ROUTER_FAIL);
          } else if (qtecResult.getCode() != 0) {
            mView.showUnbindFail(TextUtils.isEmpty(qtecResult.getMsg()) ? LOCK_UNBIND_ROUTER_FAIL : qtecResult.getMsg());
          } else {
            mView.showQuerySuccess();
          }
        } catch (Exception e) {
          e.printStackTrace();
          mView.showUnbindFail(LOCK_UNBIND_ROUTER_FAIL);
        }
      }
    });
  }

  private QtecEncryptInfo<TransmitRequest> createTransRequest(Object routerData, String routerId, String url, String method) {
    QtecEncryptInfo<Object> routerEncryptInfo = new QtecEncryptInfo<>();
    routerEncryptInfo.setRequestUrl(url);
    routerEncryptInfo.setMethod(method);
    routerEncryptInfo.setData(routerData);

    TransmitRequest request = new TransmitRequest();
    request.setRouterSerialNo(routerId);
    request.setEncryption("0");
    request.setDeviceId(DeviceUtils.getAndroidID());
    request.setKeyInvalid(0);
    request.setReuse(0);
    request.setEncryptInfo(mJsonMapper.toJson(routerEncryptInfo));
    request.setUserId(PrefConstant.getUserUniqueKey());

    QtecEncryptInfo<TransmitRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);

    return encryptInfo;
  }
}
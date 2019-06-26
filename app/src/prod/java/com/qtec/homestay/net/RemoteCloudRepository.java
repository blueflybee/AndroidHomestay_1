package com.qtec.homestay.net;
import com.qtec.homestay.domain.model.mapp.rsp.ModifyResidentResponse;
import com.qtec.homestay.domain.model.mapp.rsp.CheckCodeResponse;
import com.qtec.homestay.domain.model.mapp.rsp.WxLoginResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetStsTokenResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetStsTokenResponse.*;
import com.qtec.homestay.domain.model.mapp.rsp.ModifyUserInfoResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetDeviceDetailResponse;
import com.qtec.homestay.domain.model.mapp.rsp.ModifyPwdResponse;
import com.qtec.homestay.domain.model.mapp.rsp.TransmitResponse;

import com.qtec.homestay.data.net.CloudRestApi;
import com.qtec.homestay.data.net.CloudRestApiImpl;
import com.qtec.homestay.domain.mapper.JsonMapper;
import com.qtec.homestay.domain.model.mapp.rsp.AddDevResponse;
import com.qtec.homestay.domain.model.mapp.rsp.AddFeedbackResponse;
import com.qtec.homestay.domain.model.mapp.rsp.CheckAppVersionResponse;
import com.qtec.homestay.domain.model.mapp.rsp.CheckInRoomResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevTreeResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevsResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetHoldDetailResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetIdCodeResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetRoomManageResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetUnlockRecordsResponse;
import com.qtec.homestay.domain.model.mapp.rsp.LeaveHotelResponse;
import com.qtec.homestay.domain.model.mapp.rsp.LoginResponse;
import com.qtec.homestay.domain.model.mapp.rsp.LogoutResponse;
import com.qtec.homestay.domain.model.mapp.rsp.ModifyAdmPassResponse;
import com.qtec.homestay.domain.model.mapp.rsp.ModifyRoomNoResponse;
import com.qtec.homestay.domain.model.mapp.rsp.RegisterResponse;
import com.qtec.homestay.domain.model.mapp.rsp.ResetPwdGetIdCodeResponse;
import com.qtec.homestay.domain.model.mapp.rsp.ResetPwdResponse;
import com.qtec.homestay.domain.model.mapp.rsp.SelectRoomResponse;
import com.qtec.homestay.domain.model.mapp.rsp.UnbindDevResponse;
import com.qtec.homestay.domain.params.IRequest;
import com.qtec.homestay.domain.repository.CloudRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2017/06/08
 *     desc   : {@link RemoteCloudRepository} for retrieving user data from remote repository.
 *     version: 1.0
 * </pre>
 */
@SuppressWarnings("unchecked")
@Singleton
public class RemoteCloudRepository implements CloudRepository {

  private final CloudRestApi cloudRestApi;
  private JsonMapper mJsonMapper;

  @Inject
  public RemoteCloudRepository(CloudRestApiImpl cloudRestApi) {
    this.cloudRestApi = cloudRestApi;
    this.mJsonMapper = new JsonMapper();
  }

  @Override
  public Observable<LoginResponse> login(IRequest request) {
    return cloudRestApi.login(request);
  }

  @Override
  public Observable<List<GetRoomManageResponse>> getRoomMangerInfoList(IRequest request) {
    return cloudRestApi.getRoomInfoList(request);
  }

  @Override
  public Observable<List<SelectRoomResponse>> selectRoomList(IRequest request) {
    return cloudRestApi.selectRoomList(request);
  }

  @Override
  public Observable<CheckInRoomResponse> checkInRoom(IRequest request) {
    return cloudRestApi.checkInRoom(request);
  }

  @Override
  public Observable<LeaveHotelResponse> leaveHotel(IRequest request) {
    return cloudRestApi.leaveHotel(request);
  }

  @Override
  public Observable<GetHoldDetailResponse> houseHolderDetail(IRequest request) {
    return cloudRestApi.householderDetail(request);
  }

  @Override
  public Observable<GetIdCodeResponse> getIdCode(IRequest request) {
    return cloudRestApi.getIdCode(request);
  }

  @Override
  public Observable<RegisterResponse> register(IRequest request) {
    return cloudRestApi.register(request);
  }

  @Override
  public Observable<ResetPwdGetIdCodeResponse> resetPwdGetIdCode(IRequest request) {
    return cloudRestApi.resetPwdGetIdCode(request);
  }

  @Override
  public Observable<ResetPwdResponse> resetPwd(IRequest request) {
    return cloudRestApi.resetPwd(request);
  }

  @Override
  public Observable<CheckAppVersionResponse> checkAppVersion(IRequest request) {
    return cloudRestApi.checkAppVersion(request);
  }

  @Override
  public Observable<LogoutResponse> logout(IRequest request) {
    return cloudRestApi.logout(request);
  }

  @Override
  public Observable<AddDevResponse> addDev(IRequest request) {
    return cloudRestApi.addDev(request);
  }

  @Override
  public Observable<List<GetDevsResponse>> getDevs(IRequest request) {
    return cloudRestApi.getDevs(request);
  }

  @Override
  public Observable<UnbindDevResponse> unbindDev(IRequest request) {
    return cloudRestApi.unbindDev(request);
  }

  @Override
  public Observable<List<GetDevTreeResponse>> getDevTree(IRequest request) {
    return cloudRestApi.getDevTree(request);
  }

  @Override
  public Observable<ModifyAdmPassResponse> modifyAdmPass(IRequest request) {
    return cloudRestApi.modifyAdmPass(request);
  }

  @Override
  public Observable<ModifyRoomNoResponse> modifyRoomNo(IRequest request) {
    return cloudRestApi.modifyRoomNo(request);
  }

  @Override
  public Observable<List<GetUnlockRecordsResponse>> getUnlockRecords(IRequest request) {
    return cloudRestApi.getUnlockRecords(request);
  }
	@Override
	public Observable<AddFeedbackResponse> addFeedback(IRequest request) {
		return cloudRestApi.addFeedback(request);
	}
	@Override
	public Observable<TransmitResponse> transmit(IRequest request) {
		return cloudRestApi.transmit(request);
	}
	@Override
	public Observable<GetDeviceDetailResponse> getRouterDetail(IRequest request) {
		return cloudRestApi.getRouterDetail(request);
	}
	@Override
	public Observable<ModifyPwdResponse> modifyPwd(IRequest request) {
		return cloudRestApi.modifyPwd(request);
	}
	@Override
	public Observable<ModifyUserInfoResponse> ModifyUserInfo(IRequest request) {
		return cloudRestApi.ModifyUserInfo(request);
	}
	@Override
	public Observable<GetStsTokenResponse<CredentialsBean, AssumedRoleUserBean>> getStsToken(IRequest request) {
		return cloudRestApi.getStsToken(request);
	}
	@Override
	public Observable<WxLoginResponse> wxLogin(IRequest request) {
		return cloudRestApi.wxLogin(request);
	}
	@Override
	public Observable<CheckCodeResponse> checkCode(IRequest request) {
		return cloudRestApi.checkCode(request);
	}
	@Override
	public Observable<ModifyResidentResponse> modifyResident(IRequest request) {
		return cloudRestApi.modifyResident(request);
	}
}
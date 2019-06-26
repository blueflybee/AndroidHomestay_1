package com.qtec.homestay.data.net;
import com.qtec.homestay.domain.model.mapp.rsp.ModifyResidentResponse;
import com.qtec.homestay.domain.model.mapp.rsp.CheckCodeResponse;
import com.qtec.homestay.domain.model.mapp.rsp.WxLoginResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetStsTokenResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetStsTokenResponse.*;
import com.qtec.homestay.domain.model.mapp.rsp.ModifyUserInfoResponse;
import com.qtec.homestay.domain.model.mapp.rsp.ModifyPwdResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetDeviceDetailResponse;
import com.qtec.homestay.domain.model.mapp.rsp.TransmitResponse;

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

import java.util.List;

import io.reactivex.Observable;

/**
 * CloudRestApi for retrieving data from the cloud.
 */
public interface CloudRestApi {

  String URL_DEVELOP = "http://192.168.90.76:10099";
  String URL_TEST = "http://192.168.92.75:10099";
//  String URL_GATEWAY_3_CARETEC = "https://gateway.3caretec.com/";

  /**
   * Retrieves an {@link Observable} which will emit a {@link LoginResponse}.
   */
  Observable<LoginResponse> login(IRequest request);
  Observable<List<GetRoomManageResponse>> getRoomInfoList(IRequest request);

  Observable<List<SelectRoomResponse>> selectRoomList(IRequest request);

  Observable<CheckInRoomResponse> checkInRoom(IRequest request);

  Observable<LeaveHotelResponse> leaveHotel(IRequest request);

  Observable<GetHoldDetailResponse> householderDetail(IRequest request);

  Observable<GetIdCodeResponse> getIdCode(IRequest request);

  Observable<RegisterResponse> register(IRequest request);

  Observable<ResetPwdGetIdCodeResponse> resetPwdGetIdCode(IRequest request);

  Observable<ResetPwdResponse> resetPwd(IRequest request);

  Observable<CheckAppVersionResponse> checkAppVersion(IRequest request);

  Observable<LogoutResponse> logout(IRequest request);

  Observable<AddDevResponse> addDev(IRequest request);

  Observable<List<GetDevsResponse>> getDevs(IRequest request);

  Observable<UnbindDevResponse> unbindDev(IRequest request);

  Observable<List<GetDevTreeResponse>> getDevTree(IRequest request);

  Observable<ModifyAdmPassResponse> modifyAdmPass(IRequest request);

  Observable<ModifyRoomNoResponse> modifyRoomNo(IRequest request);

  Observable<List<GetUnlockRecordsResponse>> getUnlockRecords(IRequest request);
	Observable<AddFeedbackResponse> addFeedback(IRequest request);
	Observable<TransmitResponse> transmit(IRequest request);
	Observable<GetDeviceDetailResponse> getRouterDetail(IRequest request);
	Observable<ModifyPwdResponse> modifyPwd(IRequest request);
	Observable<ModifyUserInfoResponse> ModifyUserInfo(IRequest request);
	Observable<GetStsTokenResponse<CredentialsBean, AssumedRoleUserBean>> getStsToken(IRequest request);
	Observable<WxLoginResponse> wxLogin(IRequest request);
	Observable<CheckCodeResponse> checkCode(IRequest request);
	Observable<ModifyResidentResponse> modifyResident(IRequest request);
}

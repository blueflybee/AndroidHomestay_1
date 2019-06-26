package com.qtec.homestay.data.net;
import com.qtec.homestay.domain.model.mapp.rsp.ModifyResidentResponse;
import com.qtec.homestay.domain.model.mapp.rsp.CheckCodeResponse;
import com.qtec.homestay.domain.model.mapp.rsp.WxLoginResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetStsTokenResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetStsTokenResponse.*;
import com.qtec.homestay.domain.model.mapp.rsp.ModifyUserInfoResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetDeviceDetailResponse;
import com.qtec.homestay.domain.model.mapp.rsp.ModifyPwdResponse;
import com.qtec.homestay.domain.model.mapp.rsp.TransmitResponse;
import com.qtec.homestay.domain.model.mapp.rsp.AddFeedbackResponse;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.qtec.homestay.data.exception.CloudNoSuchLockException;
import com.qtec.homestay.data.exception.LoginInvalidException;
import com.qtec.homestay.data.exception.NetworkConnectionException;
import com.qtec.homestay.data.exception.PasswordErrMoreTimesException;
import com.qtec.homestay.data.exception.PasswordErrThreeTimesException;
import com.qtec.homestay.domain.mapper.JsonMapper;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.core.QtecResult;
import com.qtec.homestay.domain.model.mapp.rsp.AddDevResponse;
import com.qtec.homestay.domain.model.mapp.rsp.CheckAppVersionResponse;
import com.qtec.homestay.domain.model.mapp.rsp.CheckInRoomResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevTreeResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevsResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetHoldDetailResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetIdCodeResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetUnlockRecordsResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetRoomManageResponse;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

import static com.qtec.homestay.data.net.ApiResult.ApiResultType.FAILED;
import static com.qtec.homestay.data.net.ApiResult.ApiResultType.SUCCESS;

/**
 * {@link CloudRestApi} implementation for retrieving data from the network.
 */
@SuppressWarnings("unchecked")
@Singleton
public class CloudRestApiImpl implements CloudRestApi {
  //PASSWORD_ERROR_THREE_TIMES("输入密码连续错误三次以上,是否重置密码",10080),
  //PASSWORD_ERROR_MORE_TIMES("短时间内登录次数过多,请稍后重试",10081)
  private static final int PASSWORD_ERROR_THREE_TIMES = 10080;
  private static final int PASSWORD_ERROR_MORE_TIMES = 10081;
  private static final int NO_SUCH_LOCK = 10;
  private static final int LOGIN_INVALID = 10086;
  public static final String EXP_NO_CONNECTION = "没有网络连接，请先连接网络";

  private static IPostConnection apiPostConnection;
  private final Context context;

  /**
   * Constructor of the class
   * @param context {@link Context}.
   */
  @Inject
  public CloudRestApiImpl(Context context) {

    if (context == null) {
      throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
    }
    this.context = context;
  }

  public static IPostConnection getApiPostConnection() {
    return apiPostConnection;
  }

  public static void setApiPostConnection(IPostConnection apiPostConnection) {
    CloudRestApiImpl.apiPostConnection = apiPostConnection;
  }

  @Override
  public Observable<LoginResponse> login(final IRequest request) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
        encryptInfo.setMethod(CloudUrlPath.METHOD_POST);
        encryptInfo.setRequestUrl(CloudUrlPath.PATH_LOGIN);
        Type type = new TypeToken<QtecResult<LoginResponse>>() {
        }.getType();
        ApiResult<LoginResponse> apiResult = requestService(encryptInfo, type);
        if (apiResult.success()) {
          emitter.onNext(apiResult.getData());
          emitter.onComplete();
        } else {
          emitter.onError(apiResult.getException());
        }
      } else {
        emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
      }
    });
  }

  @Override
  public Observable<GetIdCodeResponse> getIdCode(final IRequest request) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
        encryptInfo.setMethod(CloudUrlPath.METHOD_POST);
        encryptInfo.setRequestUrl(CloudUrlPath.PATH_GET_ID_CODE);
        Type type = new TypeToken<QtecResult<GetIdCodeResponse>>() {
        }.getType();
        ApiResult<GetIdCodeResponse> apiResult = requestService(encryptInfo, type);
        if (apiResult.success()) {
          emitter.onNext(apiResult.getData());
          emitter.onComplete();
        } else {
          emitter.onError(apiResult.getException());
        }
      } else {
        emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
      }
    });
  }

  @Override
  public Observable<RegisterResponse> register(final IRequest request) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
        encryptInfo.setMethod(CloudUrlPath.METHOD_POST);
        encryptInfo.setRequestUrl(CloudUrlPath.PATH_REGISTER);
        Type type = new TypeToken<QtecResult<RegisterResponse>>() {
        }.getType();
        ApiResult<RegisterResponse> apiResult = requestService(encryptInfo, type);
        if (apiResult.success()) {
          emitter.onNext(apiResult.getData());
          emitter.onComplete();
        } else {
          emitter.onError(apiResult.getException());
        }
      } else {
        emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
      }
    });
  }

  @Override
  public Observable<ResetPwdGetIdCodeResponse> resetPwdGetIdCode(final IRequest request) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
        encryptInfo.setMethod(CloudUrlPath.METHOD_POST);
        encryptInfo.setRequestUrl(CloudUrlPath.PATH_RESET_PWD_GET_ID_CODE);
        Type type = new TypeToken<QtecResult<ResetPwdGetIdCodeResponse>>() {
        }.getType();
        ApiResult<ResetPwdGetIdCodeResponse> apiResult = requestService(encryptInfo, type);
        if (apiResult.success()) {
          emitter.onNext(apiResult.getData());
          emitter.onComplete();
        } else {
          emitter.onError(apiResult.getException());
        }
      } else {
        emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
      }
    });
  }

  @Override
  public Observable<ResetPwdResponse> resetPwd(final IRequest request) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
        encryptInfo.setMethod(CloudUrlPath.METHOD_POST);
        encryptInfo.setRequestUrl(CloudUrlPath.PATH_RESET_PWD_FORGET);
        Type type = new TypeToken<QtecResult<ResetPwdResponse>>() {
        }.getType();
        ApiResult<ResetPwdResponse> apiResult = requestService(encryptInfo, type);
        if (apiResult.success()) {
          emitter.onNext(apiResult.getData());
          emitter.onComplete();
        } else {
          emitter.onError(apiResult.getException());
        }
      } else {
        emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
      }
    });
  }

  @Override
  public Observable<CheckAppVersionResponse> checkAppVersion(final IRequest request) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
        encryptInfo.setMethod(CloudUrlPath.METHOD_GET);
        encryptInfo.setRequestUrl(CloudUrlPath.PATH_CHECK_APP_VERSION);
        Type type = new TypeToken<QtecResult<CheckAppVersionResponse>>() {
        }.getType();
        ApiResult<CheckAppVersionResponse> apiResult = requestService(encryptInfo, type);
        if (apiResult.success()) {
          emitter.onNext(apiResult.getData());
          emitter.onComplete();
        } else {
          emitter.onError(apiResult.getException());
        }
      } else {
        emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
      }
    });
  }

  @Override
  public Observable<LogoutResponse> logout(final IRequest request) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
        encryptInfo.setMethod(CloudUrlPath.METHOD_POST);
        encryptInfo.setRequestUrl(CloudUrlPath.PATH_LOGOUT);
        Type type = new TypeToken<QtecResult<LogoutResponse>>() {
        }.getType();
        ApiResult<LogoutResponse> apiResult = requestService(encryptInfo, type);
        if (apiResult.success()) {
          emitter.onNext(apiResult.getData());
          emitter.onComplete();
        } else {
          emitter.onError(apiResult.getException());
        }
      } else {
        emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
      }
    });
  }

  @Override
  public Observable<AddDevResponse> addDev(final IRequest request) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
        encryptInfo.setMethod(CloudUrlPath.METHOD_POST);
        encryptInfo.setRequestUrl(CloudUrlPath.PATH_ADD_DEV);
        Type type = new TypeToken<QtecResult<AddDevResponse>>() {
        }.getType();
        ApiResult<AddDevResponse> apiResult = requestService(encryptInfo, type);
        if (apiResult.success()) {
          emitter.onNext(apiResult.getData());
          emitter.onComplete();
        } else {
          emitter.onError(apiResult.getException());
        }
      } else {
        emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
      }
    });
  }

  @Override
  public Observable<List<GetDevTreeResponse>> getDevTree(final IRequest request) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
        encryptInfo.setMethod(CloudUrlPath.METHOD_GET);
        encryptInfo.setRequestUrl(CloudUrlPath.PATH_GET_DEV_TREE);
        Type type = new TypeToken<QtecResult<List<GetDevTreeResponse>>>() {
        }.getType();
        ApiResult<List<GetDevTreeResponse>> apiResult = requestService(encryptInfo, type);
        if (apiResult.success()) {
          emitter.onNext(apiResult.getData());
          emitter.onComplete();
        } else {
          emitter.onError(apiResult.getException());
        }
      } else {
        emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
      }
    });
  }

  @Override
  public Observable<UnbindDevResponse> unbindDev(final IRequest request) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
        encryptInfo.setMethod(CloudUrlPath.METHOD_POST);
        encryptInfo.setRequestUrl(CloudUrlPath.PATH_UNBIND_DEV);
        Type type = new TypeToken<QtecResult<UnbindDevResponse>>() {
        }.getType();
        ApiResult<UnbindDevResponse> apiResult = requestService(encryptInfo, type);
        if (apiResult.success()) {
          emitter.onNext(apiResult.getData());
          emitter.onComplete();
        } else {
          emitter.onError(apiResult.getException());
        }
      } else {
        emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
      }
    });
  }

  @Override
  public Observable<List<GetDevsResponse>> getDevs(final IRequest request) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
        encryptInfo.setMethod(CloudUrlPath.METHOD_GET);
        encryptInfo.setRequestUrl(CloudUrlPath.PATH_GET_DEVS);
        Type type = new TypeToken<QtecResult<List<GetDevsResponse>>>() {
        }.getType();
        ApiResult<List<GetDevsResponse>> apiResult = requestService(encryptInfo, type);
        if (apiResult.success()) {
          emitter.onNext(apiResult.getData());
          emitter.onComplete();
        } else {
          emitter.onError(apiResult.getException());
        }
      } else {
        emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
      }
    });
  }

  @Override
  public Observable<ModifyAdmPassResponse> modifyAdmPass(final IRequest request) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
        encryptInfo.setMethod(CloudUrlPath.METHOD_POST);
        encryptInfo.setRequestUrl(CloudUrlPath.PATH_MODIFY_ADM_PASS);
        Type type = new TypeToken<QtecResult<ModifyAdmPassResponse>>() {
        }.getType();
        ApiResult<ModifyAdmPassResponse> apiResult = requestService(encryptInfo, type);
        if (apiResult.success()) {
          emitter.onNext(apiResult.getData());
          emitter.onComplete();
        } else {
          emitter.onError(apiResult.getException());
        }
      } else {
        emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
      }
    });
  }
  @Override
  public Observable<ModifyRoomNoResponse> modifyRoomNo(final IRequest request) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
        encryptInfo.setMethod(CloudUrlPath.METHOD_POST);
        encryptInfo.setRequestUrl(CloudUrlPath.PATH_MODIFY_ROOM_NO);
        Type type = new TypeToken<QtecResult<ModifyRoomNoResponse>>() {
        }.getType();
        ApiResult<ModifyRoomNoResponse> apiResult = requestService(encryptInfo, type);
        if (apiResult.success()) {
          emitter.onNext(apiResult.getData());
          emitter.onComplete();
        } else {
          emitter.onError(apiResult.getException());
        }
      } else {
        emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
      }
    });
  }
  @Override
  public Observable<List<GetUnlockRecordsResponse>> getUnlockRecords(final IRequest request) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
        encryptInfo.setMethod(CloudUrlPath.METHOD_GET);
        encryptInfo.setRequestUrl(CloudUrlPath.PATH_GET_UNLOCK_RECORDS);
        Type type = new TypeToken<QtecResult<List<GetUnlockRecordsResponse>>>() {
        }.getType();
        ApiResult<List<GetUnlockRecordsResponse>> apiResult = requestService(encryptInfo, type);
        if (apiResult.success()) {
          emitter.onNext(apiResult.getData());
          emitter.onComplete();
        } else {
          emitter.onError(apiResult.getException());
        }
      } else {
        emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
      }
    });
  }

  @Override
  public Observable<List<GetRoomManageResponse>> getRoomInfoList(IRequest request) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
        encryptInfo.setMethod(CloudUrlPath.METHOD_GET);
        encryptInfo.setRequestUrl(CloudUrlPath.PATH_ROOM_INFO_LIST);
        Type type = new TypeToken<QtecResult<List<GetRoomManageResponse>>>() {
        }.getType();
        ApiResult<List<GetRoomManageResponse>> apiResult = requestService(encryptInfo, type);
        if (apiResult.success()) {
          emitter.onNext(apiResult.getData());
          emitter.onComplete();
        } else {
          emitter.onError(apiResult.getException());
        }
      } else {
        emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
      }
    });
  }

  @Override
  public Observable<List<SelectRoomResponse>> selectRoomList(IRequest request) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
        encryptInfo.setMethod(CloudUrlPath.METHOD_GET);
        encryptInfo.setRequestUrl(CloudUrlPath.PATH_SELECT_ROOM_LIST);
        Type type = new TypeToken<QtecResult<List<SelectRoomResponse>>>() {
        }.getType();
        ApiResult<List<SelectRoomResponse>> apiResult = requestService(encryptInfo, type);
        if (apiResult.success()) {
          emitter.onNext(apiResult.getData());
          emitter.onComplete();
        } else {
          emitter.onError(apiResult.getException());
        }
      } else {
        emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
      }
    });
  }

  @Override
  public Observable<CheckInRoomResponse> checkInRoom(IRequest request) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
        encryptInfo.setMethod(CloudUrlPath.METHOD_POST);
        encryptInfo.setRequestUrl(CloudUrlPath.PATH_ROOM_CHECK_IN);
        Type type = new TypeToken<QtecResult<CheckInRoomResponse>>() {
        }.getType();
        ApiResult<CheckInRoomResponse> apiResult = requestService(encryptInfo, type);
        if (apiResult.success()) {
          emitter.onNext(apiResult.getData());
          emitter.onComplete();
        } else {
          emitter.onError(apiResult.getException());
        }
      } else {
        emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
      }
    });
  }

  @Override
  public Observable<LeaveHotelResponse> leaveHotel(IRequest request) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
        encryptInfo.setMethod(CloudUrlPath.METHOD_POST);
        encryptInfo.setRequestUrl(CloudUrlPath.PATH_LEAVL_HOTEL);
        Type type = new TypeToken<QtecResult<LeaveHotelResponse>>() {
        }.getType();
        ApiResult<LeaveHotelResponse> apiResult = requestService(encryptInfo, type);
        if (apiResult.success()) {
          emitter.onNext(apiResult.getData());
          emitter.onComplete();
        } else {
          emitter.onError(apiResult.getException());
        }
      } else {
        emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
      }
    });
  }

  @Override
  public Observable<GetHoldDetailResponse> householderDetail(IRequest request) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
        encryptInfo.setMethod(CloudUrlPath.METHOD_GET);
        encryptInfo.setRequestUrl(CloudUrlPath.PATH_HOUSE_HOLDER_DETAIL);
        Type type = new TypeToken<QtecResult<GetHoldDetailResponse>>() {
        }.getType();
        ApiResult<GetHoldDetailResponse> apiResult = requestService(encryptInfo, type);
        if (apiResult.success()) {
          emitter.onNext(apiResult.getData());
          emitter.onComplete();
        } else {
          emitter.onError(apiResult.getException());
        }
      } else {
        emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
      }
    });
  }

  private ApiResult requestService(IRequest request, Type type) {
    ((QtecEncryptInfo) request).setToken(CloudUrlPath.getToken());
    ApiResult apiResult = new ApiResult();
    JsonMapper jsonMapper = new JsonMapper();
    String requestMsg;
    try {
      requestMsg = jsonMapper.toJson(request);
    } catch (JsonIOException e) {
      e.printStackTrace();
      apiResult.setResultType(FAILED);
      apiResult.setException(new JsonIOException("请求数据解析异常"));
      return apiResult;
    }

    Logger.t("c_request").json(requestMsg);

    String result;
    try {
      result = apiPostConnection.requestSyncCall(requestMsg, ((QtecEncryptInfo) request).getRequestUrl(), ((QtecEncryptInfo) request));
    } catch (IOException e) {
      e.printStackTrace();
      apiResult.setResultType(FAILED);
      apiResult.setException(e);
      return apiResult;
    }

    Logger.t("c_response").json(result);

    try {
      JSONObject jsonObject = new JSONObject(result);
      int code = jsonObject.getInt("code");
      String msg = jsonObject.getString("msg");
      if (code == LOGIN_INVALID) {
        apiResult.setResultType(FAILED);
        apiResult.setException(new LoginInvalidException(msg));
        return apiResult;
      }
    } catch (JSONException e) {
      e.printStackTrace();
      apiResult.setResultType(FAILED);
      apiResult.setException(new JSONException("应答数据解析异常"));
      return apiResult;
    }

    QtecResult qtecResult;
    try {
      qtecResult = (QtecResult) jsonMapper.fromJsonNullStringToEmpty(result, type);
    } catch (JsonSyntaxException e) {
      e.printStackTrace();
      Logger.t("c_response_exp_json").json(result);
      apiResult.setResultType(FAILED);
      apiResult.setException(new JsonSyntaxException("应答数据解析异常"));
      return apiResult;
    }

    if (qtecResult == null) {
      apiResult.setResultType(FAILED);
      apiResult.setException(new IOException("应答数据为空"));
      return apiResult;
    }

    if (qtecResult.getCode() == -1) {
      apiResult.setResultType(FAILED);
      apiResult.setException(new IOException("异常的服务 code:-1"));
      return apiResult;
    }

    if (qtecResult.getCode() == LOGIN_INVALID) {
      apiResult.setResultType(FAILED);
      apiResult.setException(new LoginInvalidException(qtecResult.getMsg()));
      return apiResult;
    }

    if (qtecResult.getCode() == PASSWORD_ERROR_THREE_TIMES) {
      apiResult.setResultType(FAILED);
      apiResult.setException(new PasswordErrThreeTimesException(qtecResult.getMsg(), PASSWORD_ERROR_THREE_TIMES));
      return apiResult;
    }

    if (qtecResult.getCode() == PASSWORD_ERROR_MORE_TIMES) {
      apiResult.setResultType(FAILED);
      apiResult.setException(new PasswordErrMoreTimesException(qtecResult.getMsg(), PASSWORD_ERROR_MORE_TIMES));
      return apiResult;
    }

    if (qtecResult.getCode() == NO_SUCH_LOCK) {
      apiResult.setResultType(FAILED);
      apiResult.setException(new CloudNoSuchLockException(qtecResult.getMsg(), NO_SUCH_LOCK));
      return apiResult;
    }

    if (qtecResult.getCode() != 0) {
      String msg = TextUtils.isEmpty(qtecResult.getMsg()) ? "未知服务异常" : qtecResult.getMsg();
      apiResult.setResultType(FAILED);
      apiResult.setException(new IOException(msg + " code:" + qtecResult.getCode()));
      return apiResult;
    }

    Object data = qtecResult.getData();
    if (data == null) {
      apiResult.setResultType(FAILED);
      apiResult.setException(new IOException("应答数据data部分为空"));
      return apiResult;
    }

    apiResult.setResultType(SUCCESS);
    apiResult.setException(null);
    apiResult.setData(data);
    return apiResult;
  }

  /**
   * Checks if the device has any active internet connection.
   *
   * @return true device with internet connection, otherwise false.
   */
  private boolean isThereInternetConnection() {
    boolean isConnected;
    ConnectivityManager connectivityManager =
        (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());
    return isConnected;
  }

	@Override
	public Observable<AddFeedbackResponse> addFeedback(final IRequest request) {
		return Observable.create(emitter -> {
			if (isThereInternetConnection()) {
				QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
				encryptInfo.setMethod(CloudUrlPath.METHOD_POST);
				encryptInfo.setRequestUrl(CloudUrlPath.PATH_ADD_FEEDBACK);
				Type type = new TypeToken<QtecResult<AddFeedbackResponse>>() {
				}.getType();
				ApiResult<AddFeedbackResponse> apiResult = requestService(encryptInfo, type);
				if (apiResult.success()) {
				  emitter.onNext(apiResult.getData());
				  emitter.onComplete();
				} else {
				  emitter.onError(apiResult.getException());
				}
			} else {
			  emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
			}
		});
	}

	@Override
	public Observable<TransmitResponse> transmit(final IRequest request) {
		return Observable.create(emitter -> {
			if (isThereInternetConnection()) {
				QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
				encryptInfo.setMethod(CloudUrlPath.METHOD_POST);
				encryptInfo.setRequestUrl(CloudUrlPath.PATH_TRANSMIT);
				Type type = new TypeToken<QtecResult<TransmitResponse>>() {
				}.getType();
				ApiResult<TransmitResponse> apiResult = requestService(encryptInfo, type);
				if (apiResult.success()) {
				  emitter.onNext(apiResult.getData());
				  emitter.onComplete();
				} else {
				  emitter.onError(apiResult.getException());
				}
			} else {
			  emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
			}
		});
	}

	@Override
	public Observable<GetDeviceDetailResponse> getRouterDetail(final IRequest request) {
		return Observable.create(emitter -> {
			if (isThereInternetConnection()) {
				QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
				encryptInfo.setMethod(CloudUrlPath.METHOD_GET);
				encryptInfo.setRequestUrl(CloudUrlPath.PATH_GET_ROUTER_DETAIL);
				Type type = new TypeToken<QtecResult<GetDeviceDetailResponse>>() {
				}.getType();
				ApiResult<GetDeviceDetailResponse> apiResult = requestService(encryptInfo, type);
				if (apiResult.success()) {
				  emitter.onNext(apiResult.getData());
				  emitter.onComplete();
				} else {
				  emitter.onError(apiResult.getException());
				}
			} else {
			  emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
			}
		});
	}

	@Override
	public Observable<ModifyPwdResponse> modifyPwd(final IRequest request) {
		return Observable.create(emitter -> {
			if (isThereInternetConnection()) {
				QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
				encryptInfo.setMethod(CloudUrlPath.METHOD_POST);
				encryptInfo.setRequestUrl(CloudUrlPath.PATH_MODIFY_PWD);
				Type type = new TypeToken<QtecResult<ModifyPwdResponse>>() {
				}.getType();
				ApiResult<ModifyPwdResponse> apiResult = requestService(encryptInfo, type);
				if (apiResult.success()) {
				  emitter.onNext(apiResult.getData());
				  emitter.onComplete();
				} else {
				  emitter.onError(apiResult.getException());
				}
			} else {
			  emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
			}
		});
	}

	@Override
	public Observable<ModifyUserInfoResponse> ModifyUserInfo(final IRequest request) {
		return Observable.create(emitter -> {
			if (isThereInternetConnection()) {
				QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
				encryptInfo.setMethod(CloudUrlPath.METHOD_POST);
				encryptInfo.setRequestUrl(CloudUrlPath.PATH_MODIFY_USER_INFO);
				Type type = new TypeToken<QtecResult<ModifyUserInfoResponse>>() {
				}.getType();
				ApiResult<ModifyUserInfoResponse> apiResult = requestService(encryptInfo, type);
				if (apiResult.success()) {
				  emitter.onNext(apiResult.getData());
				  emitter.onComplete();
				} else {
				  emitter.onError(apiResult.getException());
				}
			} else {
			  emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
			}
		});
	}

	@Override
	public Observable<GetStsTokenResponse<CredentialsBean, AssumedRoleUserBean>> getStsToken(final IRequest request) {
		return Observable.create(emitter -> {
			if (isThereInternetConnection()) {
				QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
				encryptInfo.setMethod(CloudUrlPath.METHOD_GET);
				encryptInfo.setRequestUrl(CloudUrlPath.PATH_GET_STS_TOKEN);
				Type type = new TypeToken<QtecResult<GetStsTokenResponse<CredentialsBean, AssumedRoleUserBean>>>() {
				}.getType();
				ApiResult<GetStsTokenResponse<CredentialsBean, AssumedRoleUserBean>> apiResult = requestService(encryptInfo, type);
				if (apiResult.success()) {
				  emitter.onNext(apiResult.getData());
				  emitter.onComplete();
				} else {
				  emitter.onError(apiResult.getException());
				}
			} else {
			  emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
			}
		});
	}

	@Override
	public Observable<WxLoginResponse> wxLogin(final IRequest request) {
		return Observable.create(emitter -> {
			if (isThereInternetConnection()) {
				QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
				encryptInfo.setMethod(CloudUrlPath.METHOD_POST);
				encryptInfo.setRequestUrl(CloudUrlPath.PATH_WX_LOGIN);
				Type type = new TypeToken<QtecResult<WxLoginResponse>>() {
				}.getType();
				ApiResult<WxLoginResponse> apiResult = requestService(encryptInfo, type);
				if (apiResult.success()) {
				  emitter.onNext(apiResult.getData());
				  emitter.onComplete();
				} else {
				  emitter.onError(apiResult.getException());
				}
			} else {
			  emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
			}
		});
	}

	@Override
	public Observable<CheckCodeResponse> checkCode(final IRequest request) {
		return Observable.create(emitter -> {
			if (isThereInternetConnection()) {
				QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
				encryptInfo.setMethod(CloudUrlPath.METHOD_POST);
				encryptInfo.setRequestUrl(CloudUrlPath.PATH_CHECK_CODE);
				Type type = new TypeToken<QtecResult<CheckCodeResponse>>() {
				}.getType();
				ApiResult<CheckCodeResponse> apiResult = requestService(encryptInfo, type);
				if (apiResult.success()) {
				  emitter.onNext(apiResult.getData());
				  emitter.onComplete();
				} else {
				  emitter.onError(apiResult.getException());
				}
			} else {
			  emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
			}
		});
	}

	@Override
	public Observable<ModifyResidentResponse> modifyResident(final IRequest request) {
		return Observable.create(emitter -> {
			if (isThereInternetConnection()) {
				QtecEncryptInfo encryptInfo = (QtecEncryptInfo) request;
				encryptInfo.setMethod(CloudUrlPath.METHOD_POST);
				encryptInfo.setRequestUrl(CloudUrlPath.PATH_MODIFY_RESIDENT);
				Type type = new TypeToken<QtecResult<ModifyResidentResponse>>() {
				}.getType();
				ApiResult<ModifyResidentResponse> apiResult = requestService(encryptInfo, type);
				if (apiResult.success()) {
				  emitter.onNext(apiResult.getData());
				  emitter.onComplete();
				} else {
				  emitter.onError(apiResult.getException());
				}
			} else {
			  emitter.onError(new NetworkConnectionException(EXP_NO_CONNECTION));
			}
		});
	}
}

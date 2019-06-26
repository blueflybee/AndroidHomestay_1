package com.qtec.homestay.domain.model.mapp.req;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2017/06/07
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class GetHoldDetailRequest {
  private String userUniqueKey;
  private String deviceSerialNo;

  public String getUserUniqueKey() {
    return userUniqueKey;
  }

  public void setUserUniqueKey(String userUniqueKey) {
    this.userUniqueKey = userUniqueKey;
  }

  public String getDeviceSerialNo() {
    return deviceSerialNo;
  }

  public void setDeviceSerialNo(String deviceSerialNo) {
    this.deviceSerialNo = deviceSerialNo;
  }

  @Override
  public String toString() {
    return "GetHoldDetailRequest{" +
        "userUniqueKey='" + userUniqueKey + '\'' +
        ", deviceSerialNo='" + deviceSerialNo + '\'' +
        '}';
  }
}

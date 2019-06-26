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
public class LeaveHotelRequest {
  private String  deviceSerialNo;
  private String userUniqueKey;

  public String getDeviceSerialNo() {
    return deviceSerialNo;
  }

  public void setDeviceSerialNo(String deviceSerialNo) {
    this.deviceSerialNo = deviceSerialNo;
  }

  public String getUserUniqueKey() {
    return userUniqueKey;
  }

  public void setUserUniqueKey(String userUniqueKey) {
    this.userUniqueKey = userUniqueKey;
  }

  @Override
  public String toString() {
    return "LeaveHotelRequest{" +
        "deviceSerialNo='" + deviceSerialNo + '\'' +
        ", userUniqueKey='" + userUniqueKey + '\'' +
        '}';
  }

  //  "deviceSerialNo":"设备序列号",
//      "userUniqueKey":"用户唯一标识"

}

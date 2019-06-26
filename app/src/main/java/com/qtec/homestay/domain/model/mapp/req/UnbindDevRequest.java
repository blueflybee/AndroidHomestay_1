package com.qtec.homestay.domain.model.mapp.req;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 18-7-16
 *     desc   : presenter
 *     version: 1.0
 * </pre>
 */
public class UnbindDevRequest {

  /**
   * userUniqueKey : 13557321203
   * deviceSerialNo : 设备序列号
   */

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
}

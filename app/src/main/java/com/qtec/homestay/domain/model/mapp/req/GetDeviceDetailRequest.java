package com.qtec.homestay.domain.model.mapp.req;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2018/08/09
 *     desc   : $description
 * </pre>
 */
public class GetDeviceDetailRequest {

  /**
   * userUniqueKey : 用户唯一标识符
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
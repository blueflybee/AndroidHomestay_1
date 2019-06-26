package com.qtec.homestay.domain.model.mapp.req;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 18-7-17
 *     desc   : presenter
 *     version: 1.0
 * </pre>
 */
public class ModifyAdmPassRequest {

  /**
   * deviceSerialNo : 设备序列号
   * userUniqueKey : 用户唯一标识
   * lockPass :
   */

  private String deviceSerialNo;
  private String userUniqueKey;
  private String lockPass;

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

  public String getLockPass() {
    return lockPass;
  }

  public void setLockPass(String lockPass) {
    this.lockPass = lockPass;
  }
}

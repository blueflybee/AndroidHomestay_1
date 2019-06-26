package com.qtec.homestay.domain.model.mapp.req;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2018/09/06
 *     desc   : $description
 * </pre>
 */
public class ModifyResidentRequest {

  /**
   * deviceSerialNo : 设备序列号
   * userUniqueKey : 用户唯一标识
   * residentName : 住户姓名
   * residentIdentifier : 住户身份证号
   * residentPhone : 住户手机号
   */

  private String deviceSerialNo;
  private String userUniqueKey;
  private String residentName;
  private String residentIdentifier;
  private String residentPhone;

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

  public String getResidentName() {
    return residentName;
  }

  public void setResidentName(String residentName) {
    this.residentName = residentName;
  }

  public String getResidentIdentifier() {
    return residentIdentifier;
  }

  public void setResidentIdentifier(String residentIdentifier) {
    this.residentIdentifier = residentIdentifier;
  }

  public String getResidentPhone() {
    return residentPhone;
  }

  public void setResidentPhone(String residentPhone) {
    this.residentPhone = residentPhone;
  }
}
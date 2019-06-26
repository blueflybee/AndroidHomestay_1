package com.qtec.homestay.domain.model.mapp.req;

/**
 * <pre>
 *     author :
 *     e-mail :
 *     time   : 2017/06/07
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class CHeckInRoomResquest {

  private String deviceSerialNo;
  private String userUniqueKey;
  private String residentName;

  private String residentIdentifier;
  private String residentPhone;
  private String startTime;

  private String endTime;
  private String residentType;
  private String isSendMsg;

/*  "deviceSerialNo":"设备序列号", // 房间号
      "userUniqueKey":"用户唯一标识",
      "residentName":"住户姓名",
      "residentIdentifier":"住户身份证号",
      "residentPhone":"住户手机号",
      "startTime":"入住起始时间",
      "endTime":"离店时间",
      "residentType":"住户类型",
      "isSendMsg":"是否发送短信，0否，1是"*/

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

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getResidentType() {
    return residentType;
  }

  public void setResidentType(String residentType) {
    this.residentType = residentType;
  }

  public String getIsSendMsg() {
    return isSendMsg;
  }

  public void setIsSendMsg(String isSendMsg) {
    this.isSendMsg = isSendMsg;
  }

  @Override
  public String toString() {
    return "CHeckInRoomResquest{" +
        "deviceSerialNo='" + deviceSerialNo + '\'' +
        ", userUniqueKey='" + userUniqueKey + '\'' +
        ", residentName='" + residentName + '\'' +
        ", residentIdentifier='" + residentIdentifier + '\'' +
        ", residentPhone='" + residentPhone + '\'' +
        ", startTime='" + startTime + '\'' +
        ", endTime='" + endTime + '\'' +
        ", residentType='" + residentType + '\'' +
        ", isSendMsg='" + isSendMsg + '\'' +
        '}';
  }
}

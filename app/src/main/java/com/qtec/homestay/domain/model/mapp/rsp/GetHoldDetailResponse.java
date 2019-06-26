package com.qtec.homestay.domain.model.mapp.rsp;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2017/06/07
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class GetHoldDetailResponse {
  private String residentName;
  private String residentPhone;
  private String residentIdentifier;
  private String startTime;
  private String endTime;
  private String lockPass;
  private String residentType;

//"residentName":"住户姓名",
//    "residentPhone":"住户手机号",
//    "residentIdentifier":"住户身份证号码",
//    "startTime":"入住起始时间",
//    "endTime":"离店时间",
//    "lockPass":"开锁密码",
//    "residentType":"住户类型"


  public String getResidentName() {
    return residentName;
  }

  public void setResidentName(String residentName) {
    this.residentName = residentName;
  }

  public String getResidentPhone() {
    return residentPhone;
  }

  public void setResidentPhone(String residentPhone) {
    this.residentPhone = residentPhone;
  }

  public String getResidentIdentifier() {
    return residentIdentifier;
  }

  public void setResidentIdentifier(String residentIdentifier) {
    this.residentIdentifier = residentIdentifier;
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

  public String getLockPass() {
    return lockPass;
  }

  public void setLockPass(String lockPass) {
    this.lockPass = lockPass;
  }

  public String getResidentType() {
    return residentType;
  }

  public void setResidentType(String residentType) {
    this.residentType = residentType;
  }

  @Override
  public String toString() {
    return "GetHoldDetailResponse{" +
        "residentName='" + residentName + '\'' +
        ", residentPhone='" + residentPhone + '\'' +
        ", residentIdentifier='" + residentIdentifier + '\'' +
        ", startTime='" + startTime + '\'' +
        ", endTime='" + endTime + '\'' +
        ", lockPass='" + lockPass + '\'' +
        ", residentType='" + residentType + '\'' +
        '}';
  }
}

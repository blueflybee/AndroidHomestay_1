package com.qtec.homestay.view.device.data;

/**
 * <pre>
 *     author : shaojun
 *     e-mail : wusj@qtec.cn
 *     time   : 2018/09/05
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class Lock extends Device {

  private String roomNo;
  private String batteryPower;
  private String residentName;
  private String lockPass;
  private String residentStatus;
  private String residentPhone;
  private String startTime;
  private String endTime;
  private String networkStatus;
  private String residentIdentifier;
  private String masterSerialNo;

  public String getRoomNo() {
    return roomNo;
  }

  public void setRoomNo(String roomNo) {
    this.roomNo = roomNo;
  }

  public String getBatteryPower() {
    return batteryPower;
  }

  public void setBatteryPower(String batteryPower) {
    this.batteryPower = batteryPower;
  }

  public String getResidentName() {
    return residentName;
  }

  public void setResidentName(String residentName) {
    this.residentName = residentName;
  }

  public String getLockPass() {
    return lockPass;
  }

  public void setLockPass(String lockPass) {
    this.lockPass = lockPass;
  }

  public String getResidentStatus() {
    return residentStatus;
  }

  public void setResidentStatus(String residentStatus) {
    this.residentStatus = residentStatus;
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

  public String getNetworkStatus() {
    return networkStatus;
  }

  public void setNetworkStatus(String networkStatus) {
    this.networkStatus = networkStatus;
  }

  public String getResidentIdentifier() {
    return residentIdentifier;
  }

  public void setResidentIdentifier(String residentIdentifier) {
    this.residentIdentifier = residentIdentifier;
  }

  public String getMasterSerialNo() {
    return masterSerialNo;
  }

  public void setMasterSerialNo(String masterSerialNo) {
    this.masterSerialNo = masterSerialNo;
  }
}

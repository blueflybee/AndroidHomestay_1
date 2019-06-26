package com.qtec.homestay.domain.model.mapp.rsp;

import java.io.Serializable;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2017/06/07
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class GetRoomManageResponse implements Serializable{

  /**
   * id : 住户表对应的id
   * deviceSerialNo : 设备序列号
   * deviceName : 设备名称
   * roomNo : 门锁对应的房间号
   * batteryPower : 电量
   * residentName : 住户姓名
   * lockPass : 开锁密码
   * residentStatus : 入住状态,1存在住户，0不存在住户
   * residentPhone : 住户手机号
   * startTime : 入住起始时间
   * endTime : 离店时间
   * "networkStatus":"门锁组网状态：0未组网，1已组网",
   */

  private String id;
  private String deviceSerialNo;
  private String deviceName;
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDeviceSerialNo() {
    return deviceSerialNo;
  }

  public void setDeviceSerialNo(String deviceSerialNo) {
    this.deviceSerialNo = deviceSerialNo;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

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
}

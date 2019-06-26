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
public class AddDevRequest {

  /**
   * userUniqueKey : 用户唯一标识符
   * deviceType : 0标识light网关，1标识门锁
   * deviceSerialNo : 设备序列号
   * deviceName : 设备名称
   * deviceModel : 设备型号，非必传
   * deviceVersion : 设备版本号，非必传
   * mac : 设备的唯一mac地址，添加门锁必传字段
   * bluetoothName : 蓝牙名称，添加门锁必传字段
   * roomNo : 房间号,非必传字段,添加门锁必传，目前先限制房间号码必须为数字
   */

  private String userUniqueKey;
  private String deviceType;
  private String deviceSerialNo;
  private String deviceName;
  private String deviceModel;
  private String deviceVersion;
  private String mac;
  private String bluetoothName;
  private String roomNo;

  public String getUserUniqueKey() {
    return userUniqueKey;
  }

  public void setUserUniqueKey(String userUniqueKey) {
    this.userUniqueKey = userUniqueKey;
  }

  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
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

  public String getDeviceModel() {
    return deviceModel;
  }

  public void setDeviceModel(String deviceModel) {
    this.deviceModel = deviceModel;
  }

  public String getDeviceVersion() {
    return deviceVersion;
  }

  public void setDeviceVersion(String deviceVersion) {
    this.deviceVersion = deviceVersion;
  }

  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  public String getBluetoothName() {
    return bluetoothName;
  }

  public void setBluetoothName(String bluetoothName) {
    this.bluetoothName = bluetoothName;
  }

  public String getRoomNo() {
    return roomNo;
  }

  public void setRoomNo(String roomNo) {
    this.roomNo = roomNo;
  }
}

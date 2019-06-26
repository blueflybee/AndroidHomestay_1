package com.qtec.homestay.domain.model.mapp.rsp;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2018/08/09
 *     desc   : $description
 * </pre>
 */
public class GetDeviceDetailResponse {

  /**
   * deviceSerialNo : 设备序列号
   * deviceName : 设备名称
   * deviceModel : 设备型号
   * deviceVersion : 设备版本
   * deviceType : 设备类型
   * mac : 设备mac地址
   * deviceDetail : {"lockPass":"门锁-管理员使用的密码","bluetoothName":"门锁-蓝牙名称"}
   */

  private String deviceSerialNo;
  private String deviceName;
  private String deviceModel;
  private String deviceVersion;
  private String deviceType;
  private String mac;
  private String deviceDetail;
  /**
   * deviceDetail : {"lockPass":"门锁-管理员使用的密码","bluetoothName":"门锁-蓝牙名称"}
   * roomNo : 门锁-对应的房间号
   * networkStatus : 门锁组网状态：0未组网，1已组网
   * masterSerialNo : 绑定的网关序列号
   */

  private String roomNo;
  private String networkStatus;
  private String masterSerialNo;


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

  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  public String getDeviceDetail() {
    return deviceDetail;
  }

  public void setDeviceDetail(String deviceDetail) {
    this.deviceDetail = deviceDetail;
  }

  public String getRoomNo() {
    return roomNo;
  }

  public void setRoomNo(String roomNo) {
    this.roomNo = roomNo;
  }

  public String getNetworkStatus() {
    return networkStatus;
  }

  public void setNetworkStatus(String networkStatus) {
    this.networkStatus = networkStatus;
  }

  public String getMasterSerialNo() {
    return masterSerialNo;
  }

  public void setMasterSerialNo(String masterSerialNo) {
    this.masterSerialNo = masterSerialNo;
  }

  @Override
  public String toString() {
    return "GetDeviceDetailResponse{" +
        "deviceSerialNo='" + deviceSerialNo + '\'' +
        ", deviceName='" + deviceName + '\'' +
        ", deviceModel='" + deviceModel + '\'' +
        ", deviceVersion='" + deviceVersion + '\'' +
        ", deviceType='" + deviceType + '\'' +
        ", mac='" + mac + '\'' +
        ", deviceDetail='" + deviceDetail + '\'' +
        ", roomNo='" + roomNo + '\'' +
        ", networkStatus='" + networkStatus + '\'' +
        ", masterSerialNo='" + masterSerialNo + '\'' +
        '}';
  }

  public static class DeviceDetailBean {
    /**
     * lockPass : 门锁-管理员使用的密码
     * bluetoothName : 门锁-蓝牙名称
     */

    private String lockPass;
    private String bluetoothName;

    public String getLockPass() {
      return lockPass;
    }

    public void setLockPass(String lockPass) {
      this.lockPass = lockPass;
    }

    public String getBluetoothName() {
      return bluetoothName;
    }

    public void setBluetoothName(String bluetoothName) {
      this.bluetoothName = bluetoothName;
    }

    @Override
    public String toString() {
      return "DeviceDetailBean{" +
          "lockPass='" + lockPass + '\'' +
          ", bluetoothName='" + bluetoothName + '\'' +
          '}';
    }
  }
}
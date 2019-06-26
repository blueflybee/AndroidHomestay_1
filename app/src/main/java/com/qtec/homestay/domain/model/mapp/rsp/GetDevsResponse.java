package com.qtec.homestay.domain.model.mapp.rsp;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.qtec.homestay.domain.mapper.JsonMapper;
import com.qtec.homestay.domain.model.core.QtecResult;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 18-7-16
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class GetDevsResponse implements Serializable {

  /**
   * id : 用户设备映射表对应的记录ID
   * deviceSerialNo : 设备序列号
   * deviceNickName : 类型为门锁时，默认为门锁对应的房间号；类型为网关时，默认为网关的deviceName。这个字段目前并没有使用，可以算作是预留字段
   * deviceName : 设备名称
   * deviceModel : 设备型号
   * deviceVersion : 设备版本
   * deviceType : 设备类型
   * mac : 设备mac地址
   * deviceDetail : {"lockPass":"门锁-管理员使用的密码","bluetoothName":"门锁-蓝牙名称"}
   * roomNo : 门锁-对应的房间号
   * batteryPower : 门锁-电量
   * networkStatus : 门锁组网状态：0未组网，1已组网
   * masterSerialNo : 绑定的网关序列号
   */

  private String id;
  private String deviceSerialNo;
  private String deviceNickName;
  private String deviceName;
  private String deviceModel;
  private String deviceVersion;
  private String deviceType;
  private String mac;
  private String deviceDetail;
  private String roomNo;
  private String batteryPower;
  private String networkStatus;
  private String masterSerialNo;

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

  public String getDeviceNickName() {
    return deviceNickName;
  }

  public void setDeviceNickName(String deviceNickName) {
    this.deviceNickName = deviceNickName;
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

  public String getBatteryPower() {
    return batteryPower;
  }

  public void setBatteryPower(String batteryPower) {
    this.batteryPower = batteryPower;
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
    return "GetDevsResponse{" +
        "id='" + id + '\'' +
        ", deviceSerialNo='" + deviceSerialNo + '\'' +
        ", deviceNickName='" + deviceNickName + '\'' +
        ", deviceName='" + deviceName + '\'' +
        ", deviceModel='" + deviceModel + '\'' +
        ", deviceVersion='" + deviceVersion + '\'' +
        ", deviceType='" + deviceType + '\'' +
        ", mac='" + mac + '\'' +
        ", deviceDetail='" + deviceDetail + '\'' +
        ", roomNo='" + roomNo + '\'' +
        ", batteryPower='" + batteryPower + '\'' +
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

    public static DeviceDetailBean fromJson(String deviceDetail) {
      DeviceDetailBean bean = new DeviceDetailBean();
      if (TextUtils.isEmpty(deviceDetail)) return bean;
      Type type = new TypeToken<DeviceDetailBean>() {
      }.getType();
      return (DeviceDetailBean) new JsonMapper().fromJsonNullStringToEmpty(deviceDetail, type);
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

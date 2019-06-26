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
public class ModifyRoomNoRequest {

  /**
   * roomNo : 房间编号
   * deviceSerialNo : 设备序列号
   * userUniqueKey : 用户唯一标识
   */

  private String roomNo;
  private String deviceSerialNo;
  private String userUniqueKey;

  public String getRoomNo() {
    return roomNo;
  }

  public void setRoomNo(String roomNo) {
    this.roomNo = roomNo;
  }

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
}

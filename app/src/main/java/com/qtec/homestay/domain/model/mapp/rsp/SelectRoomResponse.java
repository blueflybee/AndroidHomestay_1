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
public class SelectRoomResponse implements Serializable{
  private String roomNo;
  private String deviceSerialNo;

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

  @Override
  public String toString() {
    return "SelectRoomResponse{" +
        "roomNo='" + roomNo + '\'' +
        ", deviceSerialNo='" + deviceSerialNo + '\'' +
        '}';
  }
}

package com.qtec.homestay.domain.model.mapp.rsp;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 18-7-16
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class GetDevTreeResponse implements Serializable {

  private String deviceSerialNo;
  private List<LockBean> lockList;

  public String getDeviceSerialNo() {
    return deviceSerialNo;
  }

  public void setDeviceSerialNo(String deviceSerialNo) {
    this.deviceSerialNo = deviceSerialNo;
  }

  public List<LockBean> getLockList() {
    return lockList;
  }

  public void setLockList(List<LockBean> lockList) {
    this.lockList = lockList;
  }

  public static class LockBean implements Serializable {
    private String deviceSerialNo;
    private String roomNo;

    public String getDeviceSerialNo() {
      return deviceSerialNo;
    }

    public void setDeviceSerialNo(String deviceSerialNo) {
      this.deviceSerialNo = deviceSerialNo;
    }

    public String getRoomNo() {
      return roomNo;
    }

    public void setRoomNo(String roomNo) {
      this.roomNo = roomNo;
    }
  }

}

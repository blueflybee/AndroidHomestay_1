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
public class GetUnlockRecordsRequest {

  /**
   * userUniqueKey : 用户唯一标识符
   * deviceSerialNo : 设备序列号
   * limit : 传-1代表请求第一页，否则传服务端上一次返回的列表中最后一个记录对应的recordUniqueKey
   * pageSize : 每页大小
   * isAlarm : -1代表请求全部类型的设备记录，0代表请求正常开门记录，1代表请求异常记录，默认-1
   */

  private String userUniqueKey;
  private String deviceSerialNo;
  private String limit;
  private int pageSize;
  private String isAlarm;

  public String getUserUniqueKey() {
    return userUniqueKey;
  }

  public void setUserUniqueKey(String userUniqueKey) {
    this.userUniqueKey = userUniqueKey;
  }

  public String getDeviceSerialNo() {
    return deviceSerialNo;
  }

  public void setDeviceSerialNo(String deviceSerialNo) {
    this.deviceSerialNo = deviceSerialNo;
  }

  public String getLimit() {
    return limit;
  }

  public void setLimit(String limit) {
    this.limit = limit;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public String getIsAlarm() {
    return isAlarm;
  }

  public void setIsAlarm(String isAlarm) {
    this.isAlarm = isAlarm;
  }
}
